const urlParams = new URL(location.href).searchParams;

const index = {
    element: {
        modelsSelector: undefined,
        projectSelector: undefined,
        embededGroupSelector: undefined,
        searchBtn: undefined,
        searchInput: undefined,
        searchLimit: undefined
    },
    data: {
        modelList: [],
        projectList: [],
        embededGroupList: []
    },
    selected: {
        selectedModel: urlParams.get('model'),
        selectedProjectId: urlParams.get('projectId'),
        selectedProjectLabel: undefined,
        selectedEmbededGroupId: urlParams.get('embededGroupId'),
        selectedEmbededGroupLabel: undefined,
        search: urlParams.get('search'),
        limit: urlParams.get('limit')
    },
    params: {
        model: undefined,
        search: undefined,
        limit: undefined,
        searchBar: false,
        projectId: undefined,
        embededGroupId: undefined
    }
}


document.addEventListener("DOMContentLoaded", async () => {
    init()
    initElement()
    initEvent()

    await loadEmbeddingModel()
    await loadProjects()

    initValue()
})

function resizeSearchBar() {
    let target = index.element.searchInput.parentElement.parentElement
    let isMini = target.classList.contains("hidden")
    if (!isMini) {
        target.classList.add("hidden")
    } else {
        target.classList.remove("hidden")
    }
}

function search(search, model, limit = 10, projectId, embededGroupId) {
    fetch(`/api/embeded/search?search=${search}&model=${model}&limit=${limit}&projectId=${projectId}&embededGroupId=${embededGroupId}`)
        .then(async (res) => {
            let response = await res.json()
            loadingChart(response)
        })
}

function init() {

    index.params.search = urlParams.get('search');
    index.params.model = urlParams.get('model');
    index.params.limit = urlParams.get('limit');
    index.params.searchBar = urlParams.get('searchBar');
    index.params.projectId = urlParams.get('projectId');
    index.params.embededGroupId = urlParams.get('embededGroupId');

}

function initValue() {
    if (!index.params.model || !index.params.search) {
        return
    }

    index.element.searchInput.value = index.params.search
    index.element.modelsSelector.value = index.params.model
    index.element.searchLimit.value = index.params.limit
    console.log(index.selected)
    search(index.selected.search, index.selected.selectedModel, index.selected.limit,index.selected.selectedProjectId,index.selected.selectedEmbededGroupId)

}

function initEvent() {
    if (index.params.model) {
        index.element.modelsSelector.value = index.params.model
    }
    index.element.searchBtn.addEventListener("click", () => {
        root.container.children.clear();
        window.location.href = encodeURI(`./?search=${index.selected.search}&model=${index.selected.selectedModel}&limit=${index.selected.limit}&projectId=${index.selected.selectedProjectId}&embededGroupId=${index.selected.selectedEmbededGroupId}`)
    })

    index.element.searchInput.addEventListener("input", (e) => {
        index.selected.search = e.target.value
    })

    index.element.searchLimit.addEventListener("input", (e) => {
        console.log(e.target.value)
        index.selected.limit = e.target.value
    })

    index.element.modelsSelector.addEventListener("change", (e) => {
        const {label, value} = e.target.selectedOptions[0]
        index.selected.selectedModel = value
    })

    index.element.embededGroupSelector.addEventListener("change", (e) => {
        const {label, value} = e.target.selectedOptions[0]
        index.selected.selectedEmbededGroupId = value

    })


    index.element.searchInput.addEventListener("keypress", (e) => {
        if (e.code === 'Enter') {
            index.element.searchBtn.click()
        }
    })
    index.element.searchLimit.addEventListener("keypress", (e) => {
        if (e.code === 'Enter') {
            index.element.searchBtn.click()
        }
    })

    index.element.projectSelector.addEventListener("change", (e) => {
        const {label, value} = e.target.selectedOptions[0]
        console.log(e.target.selectedOptions[0])
        loadEmbededGroup(value)

    })
}

function initElement() {
    index.element.modelsSelector = document.getElementById("models-selector")
    index.element.searchBtn = document.getElementById("search-btn")
    index.element.searchInput = document.getElementById("search-input")
    index.element.searchLimit = document.getElementById("search-limit")
    index.element.projectSelector = document.getElementById("project-selector")
    index.element.embededGroupSelector = document.getElementById("embeded-group-selector")
}

async function loadEmbeddingModel() {
    return fetch("/api/embeded/models")
        .then(async (res) => {
            let response = await res.json()

            index.data.modelList = response

            response.forEach((model) => {
                let createOption = document.createElement('option')
                createOption.innerText = model
                index.element.modelsSelector.appendChild(createOption)
            })
        })
}

async function loadProjects() {
    return fetch("/api/project")
        .then(async (res) => {
            let response = await res.json()
            index.data.projectList = response

            response.forEach(({name, id}) => {
                let createOption = document.createElement('option')
                createOption.label = name
                createOption.value = id
                if (id == index.selected.selectedProjectId) {
                    createOption.selected = true
                    loadEmbededGroup(id)
                }
                index.element.projectSelector.appendChild(createOption)
            })
        })
}

async function loadEmbededGroup(projectId) {
    return fetch(`/api/embeded-group?projectId=${projectId}`)
        .then(async (res) => {
            index.element.embededGroupSelector.innerHTML=null
            let response = await res.json()

            index.data.embededGroupList = response

            response.forEach(({name, id}) => {
                let createOption = document.createElement('option')

                createOption.label = name
                createOption.value = id
                if (id == index.selected.selectedEmbededGroupId) {
                    createOption.selected = true
                }
                index.element.embededGroupSelector.appendChild(createOption)
            })
        })
}