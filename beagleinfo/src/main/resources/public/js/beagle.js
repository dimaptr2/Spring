/**
 * Created by dpetrov on 19.04.17.
 */

// Usage of JQuery library
$(function () {

    var tableContent = " \
        <table id=\"output-table\" class=\"responstable\"> \
        <thead id=\"table-head\"> \
            <tr> \
                <th>Год</th><th>Месяц</th><th>День</th><th>День недели</th><th>Время</th><th>Количество</th> \
            </tr> \
        </thead> \
        <tbody id=\"table-body\"> \
        </tbody> \
    ";

    $("#fromDate").datepicker(
        {
            dateFormat: "dd-mm-yy",
            monthNames: ["Январь", "Февраль", "Март", "Апрель",
                "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
            dayNamesMin: ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"]
        }
    );

    $("#toDate").datepicker(
        {
            dateFormat: "dd-mm-yy",
            monthNames: ["Январь", "Февраль", "Март", "Апрель",
                "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
            dayNamesMin: ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"]
        }
    );

    // Click on the run-button
    $("#run1").click(function (event) {

        var dt1 = $("#fromDate").val();
        var dt2 = $("#toDate").val();

        if (failedDates(dt1, dt2)) {
            alert("Неправильный диапазон дат");
            return;
        }

        var btnValue = $("#run1").val();
        // POST request to the server
        $.post('/info', {"fromDate": dt1, "toDate": dt2, "btn": btnValue}, function (data) {
            addTableHeader();
            $("#output-area").append("</table>\n");
        });

    }); // POST

    $("#run2").click(function (event) {

        var dt1 = $("#fromDate").val();
        var dt2 = $("#toDate").val();

        if (failedDates(dt1, dt2)) {
            alert("Неправильный диапазон дат");
            return;
        }

        var btnValue = $("#run2").val();

        // POST request to the server
        $.post('/info', {"fromDate": dt1, "toDate": dt2, "btn": btnValue}, function (data) {
            addTableHeader();
            $("#output-area").append("</table>\n");
        });

    }); // POST

    // click on the clean button
    $("#clean").click(function (event) {
        $("#output-table").remove();
    });

    // Dates comparing
    function failedDates(d1, d2) {

        var answer = false;

        var arr1 = d1.split("-");
        var arr2 = d2.split("-");

        var day1 = parseInt(arr1[0]);
        var day2 = parseInt(arr2[0]);
        var month1 = parseInt(arr1[1]) + 1;
        var month2 = parseInt(arr2[1]) + 1;
        var year1 = parseInt(arr1[2]);
        var year2 = parseInt(arr2[2]);

        if (d1.toString() === "" || d2.toString() === "") {
            answer = true;
        }

        if (year1 > year2) {
            answer = true;
        }
        if (year1 <= year2 && month1 > month2) {
            answer = true;
        }
        if (year1 <= year2 && month1 <= month2 && day1 > day2) {
            answer = true;
        }

        return answer;
    }

    function addTableHeader() {
        $("#output-table").remove();
        $("#output-area").append(tableContent);
    }

});