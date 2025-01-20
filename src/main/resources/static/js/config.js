let config = {
    element: {
        projectList: document.getElementById("project-selector"),
        embededGroupList: document.getElementById("embeded-group-selector"),
        embededList: document.getElementById("embeded-list"),
        resultTable: document.getElementById("result-table"),
    },
}

fetch("/api/project").then(async (res) => {
    const response = await res.json()
    console.log(response)
    response.forEach(({id, name}) => {
        let option = document.createElement("option")
        option.label = name
        option.value = id
        config.element.projectList.appendChild(option)

    })
})

function projectOnSelected(e) {
    console.log(e.label, e.value)
    console.log()

    fetch(`/api/embeded-group?projectId=${e.value}`).then(async (res) => {
        const response = await res.json()
        console.log(response)
        response.forEach(({id, name}) => {
            let option = document.createElement("option")
            option.label = name
            option.value = id
            config.element.embededGroupList.appendChild(option)

        })
    })

}

function search() {
    fetch("/api/embeded?embededGroupId=1&offset=0&limit=100&model=BGE_M3")
        .then(async (res) => {
            let response = await res.json()
            console.log(response)
            response.forEach(({id, embedData, embededGroupId, model, value}) => {
                const row = document.getElementById("row-template")
                const tr = document.importNode(row.content,true)
                const td = tr.querySelectorAll("td")
                td[0].innerHTML=id
                td[1].innerHTML=model
                td[2].innerHTML=value
                // td[3]
                config.element.resultTable.append(tr)

                // row.replace("{id}",id)
                // row.replace("{model}",model)
                // row.replace("{value}",value)
                //

            })
        })


}