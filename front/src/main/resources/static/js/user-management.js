$(document).ready(function () {
    $("#manageUsers").submit(function (event) {
        var valuesUsername = $.map($(".username"), function (elem) {
            return $(elem).text();
        });
        console.log("username.length=" + valuesUsername.length);
        var valuesEmail = $.map($(".email"), function (elem) {
            return $(elem).text();
        });
        var valuesPassword = $.map($(".password"), function (elem) {
            return $(elem).text();
        });
        var valuesEnabled = $.map($(".enabled"), function (elem) {
            return $(elem).val();
        });
        var valuesCreated = $.map($(".created"), function (elem) {
            return $(elem).text();
        });
        var valuesAuthority = $.map($(".authority"), function (elem) {
            return $(elem).text();
        });
        var valuesUserId = $.map($(".userId"), function (elem) {
            return $(elem).text();
        });

        var formData = [];
        var user = {};

        for (let i = 0; i < valuesUsername.length; i++) {
            user = {
                username: valuesUsername[i],
                email: valuesEmail[i],
                password: valuesPassword[i],
                enabled: valuesEnabled[i],
                created: valuesCreated[i],
                authority: valuesAuthority[i],
                userId: valuesUserId[i]
            };
            formData[i] = user;
            console.log(formData);
        }



        $.ajax({
            type: "PUT",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/users-management",
            data: JSON.stringify(formData),
            encode: true,
            success: function () {
                location.reload();
            },
        });

        event.preventDefault();

    });
});


