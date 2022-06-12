$(document).ready(function () {
    $("#manageFlights").submit(function (event) {
        var valuesFlightNumber = $.map($(".flightNumber"), function (elem) {
            return $(elem).val();
        });
        var valuesLane = $.map($(".lane"), function (elem) {
            return $(elem).val();
        });

        var formData = [];
        var flight = {};

        for (let i = 0; i < valuesFlightNumber.length; i++) {
            flight = {
                flightNumber: valuesFlightNumber[i],
                lane: valuesLane[i]
            };
            formData.push(flight);
            console.log(formData);
        }

        formData = JSON.stringify(Object.assign({}, formData));


        $.ajax({
            type: "POST",
            url: "/flightManager",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


