async function callback(url) {

    const response = await fetch(url);

    var data = await response.json();

    let yearInput = document.getElementById("year");
    yearInput.setAttribute("min", data.startYear);
    yearInput.setAttribute("max", data.endYear);
}

function changeModelManufacturingYears(selectNode) {
    let modelName = selectNode.value;

    callback("/offers/add/" + modelName);
}

function changeModelManufacturingYearsOnUpdate(selectNode){
    let modelName = selectNode.value;

    callback("/offers/updateModel/" + modelName);
}

