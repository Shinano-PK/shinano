$(document).ready(function () {
    $("#reportMalfunction").submit(function (event) {

        var formData = {
            message: $("#message").val(),
            itemId: $("#itemId").val()
        }


        $.ajax({
            type: "POST",
            url: "/reportService",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


