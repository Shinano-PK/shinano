$(document).ready(function () {
    $("#manageLuggage").submit(function (event) {
        var valuesFlightNumber = $.map($(".flightNumber"), function (elem) {
            return $(elem).val();
        });
        var valuesCollectSlot = $.map($(".collectSlot"), function (elem) {
            return $(elem).val();
        });
        var valuesParkingSlot = $.map($(".parkingSlot"), function (elem) {
            return $(elem).val();
        });
        var valuesDropOffSlot = $.map($(".dropOffSlot"), function (elem) {
            return $(elem).val();
        });

        var formData = [];
        var flight = {};

        for (let i = 0; i < valuesCollectSlot.length; i++) {
            flight = {
                flightNumber: valuesFlightNumber[i],
                collectSlot: valuesCollectSlot[i],
                parkingSlot: valuesParkingSlot[i]
            };
            formData.push(flight);
            console.log(formData);
        }
        for (let i = 0; i < valuesDropOffSlot.length; i++) {
            flight = {
                flightNumber: valuesFlightNumber[i],
                dropOffSlot: valuesDropOffSlot[i],
                parkingSlot: valuesParkingSlot[i]
            };
            formData.push(flight);
            console.log(formData);
        }

        formData = JSON.stringify(Object.assign({}, formData));


        $.ajax({
            type: "POST",
            url: "/luggageManager",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});