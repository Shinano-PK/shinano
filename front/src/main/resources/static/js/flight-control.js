$(document).ready(function () {
    $("#manageFlights").submit(function (event) {
        var valuesIdFlight = $.map($(".idFlight"), function (elem) {
            return $(elem).text();
        });
        var valuesTime = $.map($(".time"), function (elem) {
            return $(elem).text();
        });
        var valuesDestination = $.map($(".destination"), function (elem) {
            return $(elem).text();
        });
        var valuesFrom = $.map($(".from"), function (elem) {
            return $(elem).text();
        });
        var valuesRunway = $.map($(".runway"), function (elem) {
            return $(elem).val();
        });
        var valuesStatus = $.map($(".status"), function (elem) {
            return $(elem).text();
        });

        var formData = [];
        var flight = {};

        for (let i = 0; i < valuesIdFlight.length; i++) {
            flight = {
                idFlight: valuesIdFlight[i],
                time: valuesTime[i],
                destination: valuesDestination[i],
                from: valuesFrom[i],
                status: valuesStatus[i],
                runway: valuesRunway[i]
            };
            formData[i] = flight;
            console.log(formData);
        }



        $.ajax({
            type: "PUT",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/flight-control",
            data: JSON.stringify(formData),
            dataType: "application/json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


