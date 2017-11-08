$( function () {

    $("#fromDate").datepicker(
        getDateValues()
    );

    $("#toDate").datepicker(
        getDateValues()
    );

    function getDateValues() {
        var dates = {
            dateFormat: "yy-mm-dd",
            monthNames: ["Январь", "Февраль", "Март", "Апрель",
                "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
            dayNamesMin: ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"]
        };
        return dates;
    }

    // Insert default values

    // low time
    $("#timeLow").ready(function () {
        $("#timeLow").val('00:00:00');
    });

    // high time
    $("#timeHigh").ready(function () {
        $("#timeHigh").val('23:59:59');
    });

    // Click on the button
    $("#btnOk").click(function (event) {

        var dt1 = $("#fromDate").val();
        var dt2 = $("#toDate").val();
        var tm1 = $("#timeLow").val();
        var tm2 = $("#timeHigh").val();

        if (dt1 > dt2 || tm1 > tm2) {
            alert('Неправильный формат даты или времени');
            return;
        }

        // Make the POST request
        $.post(
            "/boxes",
            {'fromDate': dt1, 'toDate': dt2, 'timeLow': tm1, 'timeHigh': tm2},
            function (data) {
                $("table tbody").find("tr").remove();
                if (data.length > 0) {
                    var row = '';
                    for (var j = 0; j < data.length; j++) {
                        row = row + '<tr>' + '<td>' + data[j].id + '</td>'
                            + '<td>' + data[j].currentDate + '</td>'
                            + '<td>' + data[j].currentTime + '</td>'
                            + '</tr>\n';
                    } // elements
                    $("table tbody").append(row);
                }
            }
        );
        
    });

});