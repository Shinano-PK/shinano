$(document).ready(function () {
    $("#addFlight").submit(function (event) {

        var formData = {
            idPlane: $("#idPlane").val(),
            idFlightSchedule: $("#idFlightSchedule").val(),
            delay: $("#delay").val(),
            status: $("#status").val(),
            runway: $("#runway").val()
        };

        console.log(formData);


        $.ajax({
            type: "POST",
            url: "/flight-creator",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            data: JSON.stringify(formData),
            encode: true,
            success: function () {
                location.reload();
            },
        });

        event.preventDefault();

    });
});


