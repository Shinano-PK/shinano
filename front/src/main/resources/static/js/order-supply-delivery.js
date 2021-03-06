$(document).ready(function () {
    $("#orderSupplies").submit(function (event) {
        var valuesItemId = $.map($(".itemId"), function (elem) {
            return $(elem).text();
        });
        var valuesQuantity = $.map($(".quantity"), function (elem) {
            return $(elem).val();
        });
        var valuesNames = $.map($(".names"), function (elem) {
            return $(elem).text();
        });

        var formData = [];
        var item = {};

        for (let i = 0; i < valuesItemId.length; i++) {
            item = {
                id: valuesItemId[i],
                name: valuesNames[i],
                description: "",
                amount: parseInt(valuesQuantity[i]),
                request: 0
            };
            formData[i] = item;
            console.log(formData);
        }



        $.ajax({
            type: "POST",
            headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'},
            url: "/restock-supply",
            data: JSON.stringify(formData),
            encode: true,
            success: function () {
                location.reload();
            },
        });

        event.preventDefault();

    });
});


