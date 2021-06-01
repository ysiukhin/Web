var index,
    rIndex,
    table = document.getElementById("table");

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        // id = document.getElementById("id").value,
        request = document.getElementById("request").value,
        // account_id = document.getElementById("account_id").value,
        account_first_name = document.getElementById("account_first_name").value,
        account_last_name = document.getElementById("account_last_name").value,
        // activity_id = document.getElementById("activity_id").value,
        activity = document.getElementById("activity").value;

    // if (id === "") {
    //     alert("Id Connot Be Empty");
    //     isEmpty = true;
    // } else
    if (request === "") {
        alert("Request Connot Be Empty");
        isEmpty = true;
    } else
        //     if (account_id === "") {
        //     alert("Account_id Connot Be Empty");
        //     isEmpty = true;
        // } else
    if (account_first_name === "") {
        alert("account_first_name Connot Be Empty");
        isEmpty = true;
    } else if (account_last_name === "") {
        alert("account_last_name Connot Be Empty");
        isEmpty = true;
    } else
        //     if (activity_id === "") {
        //     alert("activity_id Connot Be Empty");
        //     isEmpty = true;
        // } else
    if (activity === "") {
        alert("Password Connot Be Empty");
        isEmpty = true;
    }
    return isEmpty;
}

// add Row
function addHtmlTableRow() {
    console.log("selectedRowToInput");
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
            // cell5 = newRow.insertCell(4),
            // cell6 = newRow.insertCell(5),
            // cell7 = newRow.insertCell(6),
            // id = document.getElementById("id").value,
            request = document.getElementById("request").value,
            // account_id = document.getElementById("account_id").value,
            account_first_name = document.getElementById("account_first_name").value,
            account_last_name = document.getElementById("account_last_name").value,
            // activity_id = document.getElementById("activity_id").value,
            activity = document.getElementById("activity").value;


        // cell1.innerHTML = id;
        cell1.innerHTML = request;
        // cell3.innerHTML = account_id;
        cell2.innerHTML = account_first_name;
        cell3.innerHTML = account_last_name;
        // cell6.innerHTML = activity_id;
        cell4.innerHTML = activity;
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
            document.getElementById("request").value = this.cells[0].innerHTML;
            // document.getElementById("account_id").value = this.cells[2].innerHTML;
            document.getElementById("account_first_name").value = this.cells[1].innerHTML;
            document.getElementById("account_last_name").value = this.cells[2].innerHTML;
            // document.getElementById("activity_id").value = this.cells[5].innerHTML;
            document.getElementById("activity").value = this.cells[3].innerHTML;
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

function editHtmlTableSelectedRow() {
    console.info("editHtmlTbleSelectedRow");
    var
        // id = document.getElementById("id").value,
        request = document.getElementById("request").value,
        // account_id = document.getElementById("account_id").value,
        account_first_name = document.getElementById("account_first_name").value,
        account_last_name = document.getElementById("account_last_name").value,
        // activity_id = document.getElementById("activity_id").value,
        activity = document.getElementById("activity").value;

    if (!checkEmptyInput()) {
        // table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[0].innerHTML = request;
        // table.rows[rIndex].cells[2].innerHTML = account_id;
        table.rows[rIndex].cells[1].innerHTML = account_first_name;
        table.rows[rIndex].cells[2].innerHTML = account_last_name;
        // table.rows[rIndex].cells[5].innerHTML = activity_id;
        table.rows[rIndex].cells[3].innerHTML = activity;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    // document.getElementById("id").value = "";
    document.getElementById("request").value = "";
    // document.getElementById("account_id").value = "";
    document.getElementById("account_first_name").value = "";
    document.getElementById("account_last_name").value = "";
    // document.getElementById("activity_id").value = "";
    document.getElementById("activity").value = "";
}