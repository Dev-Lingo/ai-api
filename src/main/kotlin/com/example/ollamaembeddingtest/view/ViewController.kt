package com.example.ollamaembeddingtest.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {
    @GetMapping
    fun index(): String = "index"

    @GetMapping("/config")
    fun config(): String = "config"
}
