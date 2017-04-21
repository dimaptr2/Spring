$(function() {

     var window1 = "";
     var bra = "<div id=\"active-area\" class=\"dynamic-area\">\n";
       var label1 = "<label class=\"label1\">Имя: </label><br>";
       var fname = "<input id=\"firstName\" type=\"text\" class=\"input1 input2\"><br>";
       var label2 = "<label class=\"label1\">Фамилия: </label><br>";
       var lname = "<input id=\"lastName\" type=\"text\" class=\"input1 input2\"><br>";
       var label3 = "<label class=\"label1\">Отчество: </label><br>";
       var sname = "<input id=\"secondName\" type=\"text\" class=\"input1 input2\"><br>";
       var label4 = "<label class=\"label1\">Телефон: </label><br>";
       var phone = "<input id=\"phone\" type=\"text\" class=\"input1 input2\""
       + " placeholder=\"+7(___) ___-__-__\">";
       var label5 = "<label class=\"label1\"> Вн. номер: </label>";
       var ext = "<input id=\"extension\" type=\"text\" class=\"input1 input2 mini1 mini2\"><br>";
       var label6 = "<label class=\"label1\">E-mail: </label><br>";
       var email = "<input id=\"email\" type=\"text\" class=\"input1 input2\"><br>";

       var buttonSave = "<button id=\"btnSave\" class=\"button1 button2\">Сохранить</button>";

       var window1 = bra;
       window1 = window1 + label1 + "\n" + fname + "\n" + label2 + "\n" + lname + "\n" + label3 + "\n" + sname;
       window1 = window1 + "\n" + label4 + "\n" + phone + "\n" + label5 + "\n" + ext;
       window1 = window1 + "\n" + label6 + "\n" + email + "\n" + buttonSave;
       var ket = "\n" + "</div>";
       window1 = window1 + ket;

    var buttonSearch = "<button id=\"btnSearch\" class=\"button1 button2\">Поиск</button>";

    var window2 = "<div id=\"active-area\" class=\"dynamic-area\">\n";
        window2 = window2 + "<label class=\"label1\">Найти сорудника?</label><br>\n";
        window2 = window2 + "<input id=\"mask\" type=\"text\" name=\"mask\" class=\"input1 input2 maxi1 maxi2\"><br>\n";
        window2 = window2 + buttonSearch;
        window2 = window2 + "\n</div>";
    

    // click in the create button
    $("#create").click(function(event) {
        $("#active-area").remove();
        $("#middle-area").html(window1);
        $("#btnSave").click(function(event) {

        });
    });

    // Change the data on the screen
    $("#change").click(function(event) {
        $("#active-area").remove();
        $("#middle-area").html(window2);
        // search emplyees and add a button 
        $("#btnSearch").click(function(event) {
            var mask = $("#mask").val();
            $.post("/book", {mask: mask}, function(data) {
                var keys = ["mask"];
                var result = JSON.stringify(data, keys);
                $("#answer").remove();
                var answer = JSON.parse(result);
                $("#active-area").append("<p id=\"answer\" class=\"signum-style\">" + answer + "</p>");
            }); // post
        }); // button search event

    });  // change

    $("#read").click(function(event) {
        $("#active-area").remove();
        $("#middle-area").html(window2);
        // search emploees
        $("#btnSearch").click(function(event) {
            var value = $("#mask").val();
            $("#answer").remove();
            $("#active-area").append("<p id=\"answer\" class=\"signum-style\">" + value + "</p>");
        });
    }); // read

    $("#clean").click(function(event) {
        $("#active-area").remove();
    }); // delete

   

}); // jquery using