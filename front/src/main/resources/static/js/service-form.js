$(document).ready(function () {
    $('#raportSent').hide();

    $("#reportMalfunction").submit(function (event) {

        $('#report').hide();
        $('#raportSent').show();

        var formData = {
            message: $("#message").val(),
            planeId: $("#planeId").val(),
            userId: $("#userId").val()
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


