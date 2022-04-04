$(document).ready(function () {

    $("#manageLuggage").submit(function (event) {
        event.preventDefault();
        console.log("clicked");
        let form = document.getElementById("manageLuggage");

        //TODO przygotować backend do transportu bagaży
        $.post(
            "endpoint",
            $(this).serializeArray()
        );

    });
});


