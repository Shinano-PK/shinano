$(document).ready(function () {

    $("#manageFlights").submit(function (event) {
        event.preventDefault();
        console.log("clicked");
        let form = document.getElementById("manageFlights");

        //TODO przygotować backend do przydzielania pasów
        $.post(
            "endpoint",
            $(this).serializeArray()
        );

    });
});


