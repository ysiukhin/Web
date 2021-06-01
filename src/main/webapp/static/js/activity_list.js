var index,
    rIndex,
    table = document.getElementById("table");

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        // id = document.getElementById("id").value,
        activity = document.getElementById("activity").value,
        activity_kind = document.getElementById("activity_kind").value;

    // if (id === "") {
    //     alert("Id Connot Be Empty");
    //     isEmpty = true;
    // } else
    if (activity === "") {
        alert("First Name Connot Be Empty");
        isEmpty = true;
    } else if (activity_kind === "") {
        alert("Last Name Connot Be Empty");
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
            // cell3 = newRow.insertCell(2),

            // id = document.getElementById("id").value,
            activity = document.getElementById("activity").value,
            activity_kind = document.getElementById("activity_kind").value;

        // cell1.innerHTML = id;
        cell1.innerHTML = activity;
        cell2.innerHTML = activity_kind;
        // call the function to set the event to the new row
        selectedRowToInput();
    }
}

// display selected row data into input text
function selectedRowToInput() {
    console.info("selectedRowToInput");
    for (var i = 1; i < table.rows.length; i++) {
        table.rows[i].onclick = function () {
            // get the seected row index
            rIndex = this.rowIndex;

            if (typeof index !== "undefined") {
                table.rows[index].classList.toggle("selected");
            }

            // document.getElementById("id").value = this.cells[0].innerHTML;
            document.getElementById("activity").value = this.cells[0].innerHTML;
            document.getElementById("activity_kind").value = this.cells[1].innerHTML;
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
        // id = document.getElementById("id").value,
        activity = document.getElementById("activity").value,
        activity_kind = document.getElementById("activity_kind").value;
    if (!checkEmptyInput()) {
        // table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[0].innerHTML = activity;
        table.rows[rIndex].cells[1].innerHTML = activity_kind;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    // document.getElementById("id").value = "";
    document.getElementById("activity").value = "";
    document.getElementById("activity_kind").value = "";
}