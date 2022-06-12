$(document).ready(function () {
    $("#addFlight").submit(function (event) {

        var formData = {
            flightNumber: $("#flightNumber").val(),
            from: $("#from").val(),
            destination: $("#destination").val(),
            startTime: $("#startTime").val(),
            startDate: $("#startDate").val(),
            endTime: $("#endTime").val(),
            endDate: $("#endDate").val(),
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


