package com.example.ollamaembeddingtest.test

import com.example.ollamaembeddingtest.core.model.embeded.Embeded
import com.example.ollamaembeddingtest.core.model.embeded.EmbededLabel
import com.example.ollamaembeddingtest.core.model.ollama.EmbeddingModel
import com.example.ollamaembeddingtest.core.model.ollama.OllamaOutPort
import com.example.ollamaembeddingtest.embeded.repository.EmbededLabelRepository
import com.example.ollamaembeddingtest.embeded.repository.EmbededRepository
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.io.InputStream

@Service
class Loader(
    private val ollamaOutPort: OllamaOutPort,
    private val embededLabelRepository: EmbededLabelRepository,
    private val embededRepository: EmbededRepository,
) {
    fun initData(
        inputStream: InputStream,
        model: EmbeddingModel,
        embededGroupId: Int,
    ): Flux<Embeded> {
        val worksheet = getExcelWorksheet(inputStream)
        val excelData = convertExcelData(worksheet, model)

        return excelData
            .toFlux()
            .flatMap { row ->
                ollamaOutPort
                    .getEmbededValue(model, row.value)
                    .flatMap { embeddingResult ->
                        val embeded =
                            Embeded(
                                id = null,
                                embededGroupId = embededGroupId,
                                value = row.value,
                                model = model,
                                embedData = embeddingResult.embeddings.flatMap { it.toList() },
                            )
                        embededRepository
                            .save(embeded)
                            .flatMap { savedEmbeded ->
                                val label =
                                    EmbededLabel(
                                        id = null,
                                        embededId = savedEmbeded.id ?: throw RuntimeException(),
                                        label = "품사",
                                        value = row.type,
                                    )
                                embededLabelRepository.save(label).thenReturn(savedEmbeded)
                            }
                    }
            }
    }

    private fun convertExcelData(
        worksheet: HSSFSheet,
        model: EmbeddingModel,
    ): ArrayList<ExcelRow> {
        val excelDatas = arrayListOf<ExcelRow>()
        val set = mutableSetOf<String>()

        for (i in 0..<worksheet.physicalNumberOfRows) {
            val row = worksheet.getRow(i)
            val value =
                row
                    .getCell(1)
                    .toString()
                    .replace(Regex("\\d"), "")

            if (set.contains(value)) {
                continue
            }

            val type = row.getCell(2).toString()

            set.add(value)

            val rowData = ExcelRow(value, type)
            excelDatas.add(rowData)

            println("$value : $type")
        }
        return excelDatas
    }

    private fun getExcelWorksheet(inputStream: InputStream): HSSFSheet {
        val workbook = HSSFWorkbook(inputStream)
        val worksheet = workbook.getSheetAt(0)

        return worksheet
    }
}

class ExcelRow(
    var value: String,
    var type: String,
)
