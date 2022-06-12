$(document).ready(function () {
    $("#addFlight").submit(function (event) {

        var formData = {
            flightNumber: $("#flightNumber").val(),
            from: $("#from").val(),
            destination: $("#destination").val(),
            time: $("#time").val(),
            date: $("#date").val(),
            status: $("#status").val()
        };


        $.ajax({
            type: "POST",
            url: "/addFlight",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


