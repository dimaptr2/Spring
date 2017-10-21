// JQuery asynchronous code
$( function () {

    // Click on the Year-field
    $("#year").click(function (event) {
        $("#year").val(new Date().getFullYear());
    });

}); // end of my jquery block
