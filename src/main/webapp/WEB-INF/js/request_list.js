var index,
    rIndex,
    // table = Array.from(document.querySelectorAll("tr.tblrow"));
    table = document.getElementById("table");

// check the empty input
function checkEmptyInput() {
    console.log("selectedRowToInput");
    var isEmpty = false,
        id = document.getElementById("id").value,
        account_id = document.getElementById("account_id").value,
        account_first_name = document.getElementById("account_first_name").value,
        account_last_name = document.getElementById("account_last_name").value,
        activity_id = document.getElementById("activity_id").value,
        activity = document.getElementById("activity").value,
        request = document.getElementById("request").value,
        email = document.getElementById("email").value;

    if (id === "") {
        alert("Id Connot Be Empty");
        isEmpty = true;
    } else if (request === "") {
        alert("Request Connot Be Empty");
        isEmpty = true;
    } else if (account_id === "") {
        alert("Account_id Connot Be Empty");
        isEmpty = true;
    } else if (account_first_name === "") {
        alert("account_first_name Connot Be Empty");
        isEmpty = true;
    } else if (account_last_name === "") {
        alert("account_last_name Connot Be Empty");
        isEmpty = true;
    } else if (activity_id === "") {
        alert("activity_id Connot Be Empty");
        isEmpty = true;
    } else if (activity === "") {
        alert("Password Connot Be Empty");
        isEmpty = true;
    } else if (status === "") {
        alert("Password Connot Be Empty");
        isEmpty = true;
    } else if (email === "") {
        alert("Password Connot Be Empty");
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
            cell4 = newRow.insertCell(3),
            cell5 = newRow.insertCell(4),
            cell6 = newRow.insertCell(5),
            cell7 = newRow.insertCell(6),
            cell8 = newRow.insertCell(7),
            id = document.getElementById("id").value,
            account_id = document.getElementById("account_id").value,
            account_first_name = document.getElementById("account_first_name").value,
            account_last_name = document.getElementById("account_last_name").value,
            activity_id = document.getElementById("activity_id").value,
            activity = document.getElementById("activity").value,
            request = document.getElementById("request").value,
            email = document.getElementById("email").value;


        cell1.innerHTML = id;
        cell2.innerHTML = account_id;
        cell3.innerHTML = account_first_name;
        cell4.innerHTML = account_last_name;
        cell5.innerHTML = activity_id;
        cell6.innerHTML = activity;
        cell7.innerHTML = request;
        cell8.innerHTML = email;
        // call the function to set the event to the new row
        selectedRowToInput();
    }
}

// display selected row data into input text
function selectedRowToInput() {
    console.info("selectedRowToInput");
    for (var i = 3; i < table.rows.length - 1; i++) {
        // if (table.rows[i].cells[7].classList.contains("processed")) {
        //     // console.log("table.rows[i].cells: " + table.rows[i].cells[1]);
        //     continue;
        // }
        table.rows[i].onclick = function () {
            // get the seected row index
            rIndex = this.rowIndex;
            // console.log("table.rows[i].cells: " + table.rows[i].cells[1]);
            if (typeof index !== "undefined") {
                table.rows[index].classList.toggle("selected");
            }

            document.getElementById("id").value = this.cells[0].innerHTML;
            document.getElementById("account_id").value = this.cells[1].innerHTML;
            document.getElementById("account_first_name").value = this.cells[2].innerHTML;
            document.getElementById("account_last_name").value = this.cells[3].innerHTML;
            document.getElementById("activity_id").value = this.cells[4].innerHTML;
            document.getElementById("activity").value = this.cells[5].innerHTML;
            document.getElementById("request").value = this.cells[6].innerHTML;
            document.getElementById("email").value = this.cells[7].innerHTML;
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
        id = document.getElementById("id").value,
        account_id = document.getElementById("account_id").value,
        account_first_name = document.getElementById("account_first_name").value,
        account_last_name = document.getElementById("account_last_name").value,
        activity_id = document.getElementById("activity_id").value,
        activity = document.getElementById("activity").value,
        request = document.getElementById("request").value,
        email = document.getElementById("email").value;


    if (!checkEmptyInput()) {
        table.rows[rIndex].cells[0].innerHTML = id;
        table.rows[rIndex].cells[1].innerHTML = account_id;
        table.rows[rIndex].cells[2].innerHTML = account_first_name;
        table.rows[rIndex].cells[3].innerHTML = account_last_name;
        table.rows[rIndex].cells[4].innerHTML = activity_id;
        table.rows[rIndex].cells[5].innerHTML = activity;
        table.rows[rIndex].cells[6].innerHTML = request;
        table.rows[rIndex].cells[7].innerHTML = email;
    }
}

function removeSelectedRow() {
    console.info("removeSelectedRow");
    table.deleteRow(rIndex);
    // clear input text
    document.getElementById("id").value = "";
    document.getElementById("account_id").value = "";
    document.getElementById("account_first_name").value = "";
    document.getElementById("account_last_name").value = "";
    document.getElementById("activity_id").value = "";
    document.getElementById("activity").value = "";
    document.getElementById("request").value = "";
    document.getElementById("email").value = "";
}