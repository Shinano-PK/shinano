$(document).ready(function () {
    $('#registerSuccess').hide();
    $('#registerFailed').hide();

    $("#registerForm").submit(function (event) {
        $('#registerSuccess').hide();
        $('#registerFailed').hide();

        var formData = {
            email: $("#username").val(),
            username: $("#name").val(),
            password: $("#password").val()
        };

        console.log(JSON.stringify(formData));

        $.ajax({
            type: "POST",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/register-page",
            data: JSON.stringify(formData),
            encode: true,
            success: function () {
                $('#registerSuccess').show();
                $('#registerForm').hide();
            },
            error: function (jqXHR, exception) {
                $('#registerFailed').show();
            }
        });

        event.preventDefault();

    });
});