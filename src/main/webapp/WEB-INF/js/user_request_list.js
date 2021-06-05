var index,
    rIndex,
    // table = Array.from(document.querySelectorAll("tr.tblrow"));
    table = document.getElementById("table");

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        activity_kind = document.getElementById("activity_kind").value,
        activity = document.getElementById("activity").value,
        current_activity = document.getElementById("current_activity").value,
        current_request = document.getElementById("current_request").value;
        activity_id = document.getElementById("activity_id").value,
            account_activity_id = document.getElementById("account_activity_id").value,
        request_id = document.getElementById("request_id").value;

    //     if (id === "") {
    //     alert("Id Connot Be Empty");
    //     isEmpty = true;
    // } else if (request === "") {
    //     alert("Request Connot Be Empty");
    //     isEmpty = true;
    // } else if (account_activity_id === "") {
    //     alert("account_activity_id Connot Be Empty");
    //     isEmpty = true;
    // } else if (account_first_name === "") {
    //     alert("account_first_name Connot Be Empty");
    //     isEmpty = true;
    // } else if (account_last_name === "") {
    //     alert("account_last_name Connot Be Empty");
    //     isEmpty = true;
    // } else if (activity_id === "") {
    //     alert("activity_id Connot Be Empty");
    //     isEmpty = true;
    // } else if (activity === "") {
    //     alert("Password Connot Be Empty");
    //     isEmpty = true;
    // } else if (status === "") {
    //     alert("Password Connot Be Empty");
    //     isEmpty = true;
    // } else if (status === "") {
    //     alert("Password Connot Be Empty");
    //     isEmpty = true;
    // }

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
            cell4 = newRow.insertCell(3),
            cell5 = newRow.insertCell(4),
            cell6 = newRow.insertCell(5),
            cell7 = newRow.insertCell(6),
            // cell8 = newRow.insertCell(7),
            // cell9 = newRow.insertCell(7),
            activity_kind = document.getElementById("activity_kind").value,
            activity = document.getElementById("activity").value,
            current_activity = document.getElementById("current_activity").value,
            current_request = document.getElementById("current_request").value;
        activity_id = document.getElementById("activity_id").value,
            account_activity_id = document.getElementById("account_activity_id").value,
            request_id = document.getElementById("request_id").value;


        cell1.innerHTML = activity_kind;
        cell2.innerHTML = activity;
        cell3.innerHTML = current_activity;
        cell4.innerHTML = current_request;
        cell5.innerHTML = activity_id;
        cell6.innerHTML = account_activity_id;
        cell7.innerHTML = request_id;
        // cell8.innerHTML = status;
        // cell9.innerHTML = req_status;
        // call the function to set the event to the new row
        selectedRowToInput();
    }
}

// display selected row data into input text
function selectedRowToInput() {
    console.info("selectedRowToInput");
    for (var i = 3; i < table.rows.length - 1; i++) {
        console.log("table.rows[i].cells: 3" + table.rows[i].cells[3].classList.value);
        console.log("table.rows[i].cells: 4" + table.rows[i].cells[4].classList.value);
        if (table.rows[i].cells[3].classList.contains("processed")) {
            continue;
        }
        table.rows[i].onclick = function () {
            // get the seected row index
            rIndex = this.rowIndex;
            // console.log("table.rows[i].cells: " + table.rows[i].cells[1]);
            if (typeof index !== "undefined") {
                table.rows[index].classList.toggle("selected");
            }

            document.getElementById("activity_kind").value = this.cells[0].innerHTML;
            document.getElementById("activity").value = this.cells[1].innerHTML;
            document.getElementById("current_activity").value = this.cells[2].innerHTML;
            document.getElementById("current_request").value = this.cells[3].innerHTML;
            document.getElementById("activity_id").value = this.cells[4].innerHTML;
            document.getElementById("account_activity_id").value = this.cells[5].innerHTML;
            document.getElementById("request_id").value = this.cells[6].innerHTML;

            // console.log(typeof index);
            console.log(rIndex);
            // get the selected row index
            index = this.rowIndex;
            // add class selected to the row
            this.classList.toggle("selected");
            // console.log(typeof index);
        };
    }
}

selectedRowToInput();

function editHtmlTableSelectedRow() {
    console.info("editHtmlTbleSelectedRow");
    var
        activity_kind = document.getElementById("activity_kind").value,
        activity = document.getElementById("activity").value,
        current_activity = document.getElementById("current_activity").value,
        current_request = document.getElementById("current_request").value;
    activity_id = document.getElementById("activity_id").value,
        account_activity_id = document.getElementById("account_activity_id").value,
        request_id = document.getElementById("request_id").value;


    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = activity_kind;
        table.rows[rIndex].cells[1].innerHTML = activity;
        table.rows[rIndex].cells[2].innerHTML = current_activity;
        table.rows[rIndex].cells[3].innerHTML = current_request;
        table.rows[rIndex].cells[4].innerHTML = activity_id;
        table.rows[rIndex].cells[5].innerHTML = account_activity_id;
        table.rows[rIndex].cells[6].innerHTML = request_id;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("activity_kind").value = "";
    document.getElementById("activity").value = "";
    document.getElementById("current_activity").value = "";
    document.getElementById("current_request").value = "";
    document.getElementById("activity_id").value = "";
    document.getElementById("account_activity_id").value = "";
    document.getElementById("request_id").value = "";
}