var index,
    rIndex,
    // table = Array.from(document.querySelectorAll("tr.tblrow"));
    table = document.getElementById("table");

function check(message) {
    var record = document.getElementById("record").value;
    if (record === "") {
        alert(message);
        return false;
    }
    return true;
}

document.getElementById("record").value

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        account_activity_id = document.getElementById("account_activity_id").value,
        activity_kind = document.getElementById("activity_kind").value,
        activity = document.getElementById("activity").value,
        timer_start = document.getElementById("timer_start").value,
        record = document.getElementById("record").value;

    // activity_id = document.getElementById("activity_id").value,
    // request_id = document.getElementById("request_id").value;

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

            account_activity_id = document.getElementById("account_activity_id").value,
            activity_kind = document.getElementById("activity_kind").value,
            activity = document.getElementById("activity").value,
            timer_start = document.getElementById("timer_start").value,
            record = document.getElementById("record").value;

        cell1.innerHTML = account_activity_id;
        cell2.innerHTML = activity_kind;
        cell3.innerHTML = activity;
        cell4.innerHTML = record;
        cell5.innerHTML = record;
        selectedRowToInput();
    }
}

// display selected row data into input text
function selectedRowToInput() {
    console.info("selectedRowToInput");
    for (var i = 3; i < table.rows.length - 1; i++) {
        console.log("table.rows[" + i + "]: " + table.rows[i].classList.contains("processed"));
        if (!table.rows[i].classList.contains("processed")) {
            // console.log("table.rows[i]: " + table.rows[i].classList.value);
            // continue;
            table.rows[i].onclick = function () {
                // get the seected row index
                rIndex = this.rowIndex;
                // console.log("table.rows[i].cells: " + table.rows[i].cells[1]);
                if (typeof index !== "undefined") {
                    table.rows[index].classList.toggle("selected");
                }
                document.getElementById("account_activity_id").value = this.cells[0].innerHTML;
                document.getElementById("activity_kind").value = this.cells[1].innerHTML;
                document.getElementById("activity").value = this.cells[2].innerHTML;
                document.getElementById("timer_start").value = this.cells[3].innerHTML;
                document.getElementById("record").value = this.cells[4].innerHTML;

                // console.log(typeof index);
                console.log(rIndex);
                // get the selected row index

                // get the selected row index
                index = this.rowIndex;
                // add class selected to the row
                this.classList.toggle("selected");
                // console.log(typeof index);
            };
        }
    }
}

selectedRowToInput();

function editHtmlTableSelectedRow() {
    console.info("editHtmlTbleSelectedRow");
    var
        account_activity_id = document.getElementById("account_activity_id").value,
        activity_kind = document.getElementById("activity_kind").value,
        activity = document.getElementById("activity").value,
        timer_start = document.getElementById("timer_start").value,
        record = document.getElementById("record").value;


    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = account_activity_id;
        table.rows[rIndex].cells[1].innerHTML = activity_kind;
        table.rows[rIndex].cells[2].innerHTML = activity;
        table.rows[rIndex].cells[3].innerHTML = timer_start;
        table.rows[rIndex].cells[4].innerHTML = record;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("account_activity_id").value = "";
    document.getElementById("activity_kind").value = "";
    document.getElementById("activity").value = "";
    document.getElementById("timer_start").value = "";
    document.getElementById("record").value = "";
}