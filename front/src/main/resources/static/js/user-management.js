$(document).ready(function () {
    $("#manageUsers").submit(function (event) {
        var valuesUserId = $.map($(".userId"), function (elem) {
            return $(elem).val();
        });
        var valuesAction = $.map($(".action"), function (elem) {
            return $(elem).val();
        });

        var formData = [];
        var user = {};

        for (let i = 0; i < valuesUserId.length; i++) {
            user = {
                itemId: valuesUserId[i],
                quantity: valuesAction[i]
            };
            formData[i] = user;
            console.log(formData);
        }



        $.ajax({
            type: "POST",
            url: "/manageUsers",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


