var index,
    rIndex,
    table = document.getElementById("table");


function check(message) {
    var record = document.getElementById("kind_id").value;
    if (record === "") {
        alert(message);
        return false;
    }
    return true;
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
            document.getElementById("activity_en").value = this.cells[1].innerHTML;
            document.getElementById("activity_ru").value = this.cells[2].innerHTML;
            document.getElementById("activity_kind").value = this.cells[4].innerHTML;
            // document.getElementById("activity_kind").value = this.cells[3].innerHTML;
            document.getElementById("kind_id").value = this.cells[4].innerHTML;
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
        activity_en = document.getElementById("activity_en").value,
        activity_ru = document.getElementById("activity_ru").value,
        activity_kind = document.getElementById("activity_kind").value;
    kind_id = document.getElementById("kind_id").value;

    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[1].innerHTML = activity_en;
        table.rows[rIndex].cells[2].innerHTML = activity_ru;
        table.rows[rIndex].cells[3].innerHTML = activity_kind;
        table.rows[rIndex].cells[4].innerHTML = kind_id;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("id").value = "";
    document.getElementById("activity_en").value = "";
    document.getElementById("activity_ru").value = "";
    document.getElementById("activity_kind").value = "";
    document.getElementById("kind_id").value = "";
}