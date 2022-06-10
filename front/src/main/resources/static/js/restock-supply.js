$(document).ready(function () {
    $("#manageSupplies").submit(function (event) {
        var valuesItemId = $.map($(".itemId"), function (elem) {
            return $(elem).val();
        });
        var valuesQuantity = $.map($(".quantity"), function (elem) {
            return $(elem).val();
        });

        var formData = [];
        var item = {};

        for (let i = 0; i < valuesItemId.length; i++) {
            item = {
                itemId: valuesItemId[i],
                quantity: valuesQuantity[i],
            };
            formData.push(item);
            console.log(formData);
        }

        formData = JSON.stringify(Object.assign({}, formData));


        $.ajax({
            type: "POST",
            url: "/manageSupplies",
            data: formData,
            dataType: "json",
            encode: true,
        }).done(function (data) {
            console.log(data);
        });

        event.preventDefault();

    });
});


