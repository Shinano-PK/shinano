$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        var formData = {
            login: $("#username").val(),
            password: $("#password").val()
        };

        console.log(JSON.stringify(formData));

        $.ajax({
            type: "POST",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/login",
            data: JSON.stringify(formData),
            encode: true,

        });

        event.preventDefault();

    });
});