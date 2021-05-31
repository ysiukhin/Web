var index,
    rIndex,
    table = document.getElementById("table");

// check the empty input
function checkEmptyInput() {
    console.info("checkEmptyInput");
    var isEmpty = false,
        id = document.getElementById("id").value,
        first_name = document.getElementById("first_name").value,
        last_name = document.getElementById("last_name").value;
    middle_name = document.getElementById("middle_name").value;
    email = document.getElementById("email").value;
    md5 = document.getElementById("md5").value;

    if (id === "") {
        alert("First Name Connot Be Empty");
        isEmpty = true;
    } else if (first_name === "") {
        alert("Last Name Connot Be Empty");
        isEmpty = true;
    } else if (last_name === "") {
        alert("last_name Connot Be Empty");
        isEmpty = true;
    } else if (middle_name === "") {
        alert("middle_name Connot Be Empty");
        isEmpty = true;
    } else if (email === "") {
        alert("email Connot Be Empty");
        isEmpty = true;
    } else if (md5 === "") {
        alert("md5 Connot Be Empty");
        isEmpty = true;
    }
    return isEmpty;
}

// add Row
function addHtmlTableRow() {
    console.info("addHtmlTableRow");
    // get the table by id
    // create a new row and cells
    // get value from input text
    // set the values into row cell's
    if (!checkEmptyInput()) {
        var newRow = table.insertRow(table.length),
            cell1 = newRow.insertCell(0),
            cell2 = newRow.insertCell(1),
            cell3 = newRow.insertCell(2),
            cell4 = newRow.insertCell(3),
            cell5 = newRow.insertCell(4),
            cell6 = newRow.insertCell(5),
            id = document.getElementById("id").value,
            first_name = document.getElementById("first_name").value,
            last_name = document.getElementById("last_name").value;
        middle_name = document.getElementById("middle_name").value;
        email = document.getElementById("email").value;
        md5 = document.getElementById("md5").value;

        cell1.innerHTML = id;
        cell2.innerHTML = first_name;
        cell3.innerHTML = last_name;
        cell4.innerHTML = middle_name;
        cell5.innerHTML = email;
        cell6.innerHTML = md5;
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

            document.getElementById("id").value = this.cells[0].innerHTML;
            document.getElementById("first_name").value = this.cells[1].innerHTML;
            document.getElementById("last_name").value = this.cells[2].innerHTML;
            document.getElementById("middle_name").value = this.cells[3].innerHTML;
            document.getElementById("email").value = this.cells[4].innerHTML;
            document.getElementById("md5").value = this.cells[5].innerHTML;
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
    var id = document.getElementById("id").value,
        first_name = document.getElementById("first_name").value,
        last_name = document.getElementById("last_name").value;
    middle_name = document.getElementById("middle_name").value;
    email = document.getElementById("email").value;
    md5 = document.getElementById("md5").value;
    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[1].innerHTML = first_name;
        table.rows[rIndex].cells[2].innerHTML = last_name;
        table.rows[rIndex].cells[3].innerHTML = middle_name;
        table.rows[rIndex].cells[4].innerHTML = email;
        table.rows[rIndex].cells[5].innerHTML = md5;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("id").value = "";
    document.getElementById("first_name").value = "";
    document.getElementById("last_name").value = "";
    document.getElementById("middle_name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("md5").value = "";
}