var index,
    rIndex,
    table = document.getElementById("table");

function check(message) {
    var record = document.getElementById("id").value;
    if (record === "") {
        alert(message);
        return false;
    }
    return true;
}

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        id = document.getElementById("id").value,
        kind_en = document.getElementById("kind_en").value,
        kind_ru = document.getElementById("kind_ru").value;


    if (id === "") {
        alert("Id Connot Be Empty");
        isEmpty = true;
    } else if (kind_en === "") {
        alert("First Name Connot Be Empty");
        isEmpty = true;
    }
    if (kind_ru === "") {
        alert("First Name Connot Be Empty");
        isEmpty = true;
    }
    return isEmpty;
}

// add Row
function addHtmlTableRow() {
    console.log("selectedRowToInput");
    if (!checkEmptyInput()) {
        var newRow = table.insertRow(table.length),
            cell1 = newRow.insertCell(0),
            cell2 = newRow.insertCell(1),
            cell3 = newRow.insertCell(2),

            id = document.getElementById("id").value,
            kind_en = document.getElementById("kind_en").value,
            kind_ru = document.getElementById("kind_ru").value;

        cell1.innerHTML = id;
        cell2.innerHTML = kind_en;
        cell3.innerHTML = kind_ru;

        selectedRowToInput();
    }
}


// display selected row data into input text
function selectedRowToInput() {
    console.info("selectedRowToInput");
    for (var i = 3; i < table.rows.length; i++) {
        table.rows[i].onclick = function () {
            // get the seected row index
            rIndex = this.rowIndex;

            if (typeof index !== "undefined") {
                table.rows[index].classList.toggle("selected");
            }

            document.getElementById("id").value = this.cells[0].innerHTML;
            document.getElementById("kind_en").value = this.cells[1].innerHTML;
            document.getElementById("kind_ru").value = this.cells[2].innerHTML;
            console.log(typeof index);
            // get the selected row index
            index = this.rowIndex;
            // add class selected to the row
            this.classList.toggle("selected");
            console.log(typeof index);
        };
    }
}

selectedRowToInput();

function editHtmlTbleSelectedRow() {
    console.info("editHtmlTbleSelectedRow");
    var
        id = document.getElementById("id").value,
        kind_en = document.getElementById("kind_en").value;
    kind_ru = document.getElementById("kind_ru").value;
    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[1].innerHTML = kind_en;
        table.rows[rIndex].cells[2].innerHTML = kind_ru;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("id").value = "";
    document.getElementById("kind_en").value = "";
    document.getElementById("kind_ru").value = "";
}