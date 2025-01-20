let root
let series
let zoomableContainer
document.addEventListener("DOMContentLoaded", ()=>{
    initChart()
})

function initChart() {
    root = am5.Root.new("chartdiv");
    root.setThemes([
        am5themes_Animated.new(root)
    ]);
}

function loadingChart(response) {
// Create root element
// https://www.amcharts.com/docs/v5/getting-started/#Root_element

// Set themes
// https://www.amcharts.com/docs/v5/concepts/themes/


    // var data = {
    //     value: 0,
    //     children: []
    // }
    //
    // for (var i = 0; i < 15; i++) {
    //     data.children.push({ name: "node " + i, value: Math.random() * 20 + 1 })
    // }

    var data = {
        value: 0,
        children: []
    }



    for (var i = 0; i < response.length; i++) {
        let temp = response[i]

        // data.children.push({ name: temp.value, value: (1-temp.similarity)*(response.length-i)})
        data.children.push({ name: temp.value, value: (1-temp.similarity)*(response.length-i)})
    }

// Create wrapper container
    zoomableContainer = root.container.children.push(
        am5.ZoomableContainer.new(root, {
            width: am5.p100,
            height: am5.p100,
            pinchZoom: true
        })
    );

    var zoomTools = zoomableContainer.children.push(am5.ZoomTools.new(root, {
        target: zoomableContainer
    }));

// Create series
// https://www.amcharts.com/docs/v5/charts/hierarchy/#Adding
    series = zoomableContainer.contents.children.push(
        am5hierarchy.ForceDirected.new(root, {
            singleBranchOnly: false,
            downDepth: 2,
            topDepth: 1,
            initialDepth: 1,
            maxRadius: 100,
            minRadius: 10,
            valueField: "value",
            categoryField: "name",
            childDataField: "children",
            manyBodyStrength: -13,
            centerStrength: 1.5
        })
    );

    series.get("colors").setAll({
        step: 1
    });

    series.links.template.setAll({
        strokeWidth: 2
    });

    series.nodes.template.setAll({
        tooltipText: null,
        cursorOverStyle: "pointer"
    });

    var selectedDataItem;

// handle clicking on nodes and link/unlink them
    series.nodes.template.events.on("click", function(e) {
        // check if we have a selected data item
        let item = e.target.dataItem.dataContext
        window.location.href = encodeURI(`./?search=${item.name}&model=${modelsSelector.value}&limit=${searchLimit.value}`)
    })

    series.data.setAll([data]);
    series.set("selectedDataItem", series.dataItems[0]);

// Make stuff animate on load
    series.appear(1000, 100);


}