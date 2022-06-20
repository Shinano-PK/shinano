$(document).ready(function () {
    $('#loginSuccess').hide();
    $('#loginFailed').hide();

    $("#loginForm").submit(function (event) {
        $('#loginSuccess').hide();
        $('#loginFailed').hide();

        var formData = {
            login: $("#username").val(),
            password: $("#password").val()
        };

        console.log(JSON.stringify(formData));

        $.ajax({
            type: "POST",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/login-page",
            data: JSON.stringify(formData),
            encode: true,
            success: function () {
                $('#loginForm').hide();
                $('#loginSuccess').show();
            },
            error: function (jqXHR, exception) {
                $('#loginFailed').show();
            }
        });

        event.preventDefault();

    });
});