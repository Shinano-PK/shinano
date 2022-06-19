$(document).ready(function () {
    $("#registerForm").submit(function (event) {
        var formData = {
            email: $("#username").val(),
            username: $("#name").val(),
            password: $("#password").val()
        };

        console.log(JSON.stringify(formData));

        $.ajax({
            type: "POST",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/register",
            data: JSON.stringify(formData),
            encode: true,

        });

        event.preventDefault();

    });
});