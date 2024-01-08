let y;
let r;
let x;
let coordinatesX;
let coordinatesY;
drawAll();
document.getElementById("area_svg").addEventListener("mousedown", function (e){
    if (!checkR()){
        $('#r').addClass('text-error');
        return;
    }
    else{
        $('#r').removeClass('text-error');

    }
    coordinatesX = (e.offsetX -150) / 120 * r ;
    coordinatesY = (150 - e.offsetY) / 120 *r;
    document.getElementById("input-f:coord-y").value = coordinatesY.toString().substr(0, 4);
    document.getElementById("input-f:coord-x_input").value = coordinatesX.toString().substr(0, 4);
    document.getElementById("input-f:submitBtn").click();
    setTimeout(function () {
        drawAll();
    }, 300);
});

$("#input-f").find("input[type=radio]").click(function() {
    deletePoints();
    drawAll();
});

document.getElementById("input-f:submitBtn").addEventListener('click', function (e) {
    x = document.getElementById("input-f:coord-x_input").value;
    y = document.getElementById("input-f:coord-y").value;
    let ok1 = testX();
    let ok2 = testY();
    if (ok1 && ok2 && checkR()){
        setTimeout(function () {
            drawPoint(x, y, r, checkAnswer(), true);
        }, 300);
    }
    return false;

});

function testX(){
    x = document.getElementById("input-f:coord-x_input").value;
    let check = false;
    try {
        check = x > -4 && x < 4;
    }catch (e){
        check = false;
    }
    if (!check){
        $('#x').addClass('text-error');
    }
    if (check){
        $('#x').removeClass('text-error');
    }
    return check;
}

function testY(){
    y = document.getElementById("input-f:coord-y").value;
    let check = false;
    try {
        check = y > -3 && y < 5;
    }catch (e){
        check = false;
    }
    if (!check){
        $('#y').addClass('text-error');
    }
    if (check){
        $('#y').removeClass('text-error');
    }
    return check;
}

function checkR() {
    let check = $("#input-f").find("input:checked").length;
    if (check == 1) {
        r = $("#input-f").find("input:checked")[0].value;
        $('#r').removeClass('text-error');

        return true
    } else{
        $('#r').addClass('text-error');
        return false;
    }
}

function drawAll() {
    $(".outputTable tbody tr").each(function () {
        let xP = parseFloat($(this).find("td:nth-child(1)").text());
        let yP = parseFloat($(this).find("td:nth-child(2)").text());
        let rP = parseFloat($(this).find("td:nth-child(3)").text());
        let ansP = $(this).find("td:nth-child(6)").text().toString().trim().toLowerCase();
        let flagR;
        let flagColor;
        if (!isNaN(xP) && !isNaN(yP)) {
            flagColor = (ansP.length === 2);
            if (r) {
                flagR = (r == rP);
                drawPoint(xP, yP, rP, flagColor, flagR);
            } else {
                flagR = false;
                drawPoint(xP, yP, rP, flagColor, flagR);
            }
        }
    });
}

function drawPoint(x, y, r, color, flagR) {
    let point = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
    point.setAttribute('cx', (120 * x / r + 150).toString());
    point.setAttribute('cy', (-120 * y / r + 150).toString());
    point.setAttribute('r', 3 .toString());
    point.setAttribute('data-x', x);
    point.setAttribute('data-y', y);
    point.classList.add("circle");
    if (!flagR) {
        point.style.fill = "green";
    }
    else if (color){
        point.style.fill = "green";
    }
    else {
        point.style.fill = "red";
    }
    document.getElementsByTagName("svg")[0].appendChild(point);

}


function deletePoints() {
    var svg = document.getElementsByTagName("svg")[0].getElementsByClassName("circle");
    $(svg).remove();
}

document.getElementById("input-f:clearBtn").addEventListener('click', function (e) {
    deletePoints();
});


function checkAnswer() {
    return (x >= 0 && y >= 0 && x * x + y * y <= r * r) ||
        (x <= 0 && y <= 0 && x >= -r/2 && y >= -r) ||
        (x >= 0 && y <= 0 && y >= (2 * x - r));
}



