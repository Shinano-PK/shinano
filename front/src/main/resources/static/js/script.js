window.addEventListener("resize", lePadding);

function lePadding() {
    let mainLi = document.getElementById("actionButtons").getElementsByTagName("li");

    let actions = mainLi.length;
    console.log(mainLi.item(0).offsetWidth);
    console.log(window.innerWidth);
    console.log(window.outerWidth);
}



