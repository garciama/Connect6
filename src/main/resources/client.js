/*
 *   Place client application Javascript code here.
 */

var main = function() {
    // Draw some shapes to the canvas
    //drawBoard();

    // As an example, we'll make a request to the server when the button is clicked
    // and display the results in the HTML page.
    let btn = document.getElementById("createUserButton");
    btn.addEventListener("click", createAccEvent);

    let btn2 = document.getElementById("leaderboardButton");
    btn2.addEventListener("click", buttonClickEvent);
};

var createAccEvent = function(e){
    var name = {name: document.getElementById("uname").value};
    console.log(name);

    fetch("menu/createUser", {method: "POST", body: JSON.stringify(name)} )
        .then( function(response){
            let el = document.getElementById("create-user-area");
            if (!response.ok){
                el.innerText = "Error code: " + response.status;
                el.style.fontWeight = "bold";
                el.style.color = "red";
            } else {
                response.text().then( function(value) {
                el.innerText = "Response: " + value;
                el.style.color = "green";
                el.style.fontWeight = "bold";
                });
            }
        });
};

var buttonClickEvent = function(e) {
    // Send a GET request to the server and display response in the
    // "leaderboard-response-area" span element.  For details about the fetch function
    // see:  https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
    fetch("/menu/leaderboard", { method: "GET"} )
        .then( function(response) {
            let el = document.getElementById("leaderboard-response-area");
            if( ! response.ok ) {
                el.innerText = "Error code: " + response.status;
                el.style.fontWeight = "bold";
                el.style.color = "red";
            } else {
                response.text().then( function(value) {
                    el.innerText = "Response: " + value;
                    el.style.color = "green";
                    el.style.fontWeight = "bold";
                });
            }
        });
};

var drawBoard = function() {
    let canvas = document.getElementById("board-canvas");

    let ctx = canvas.getContext("2d");
    let w = canvas.width;
    let h = canvas.height;

    // Clear the canvas with a background color
    ctx.fillStyle = "rgb(10,200,10)";
    ctx.fillRect(0, 0, w, h);

    // Draw a few shapes to the canvas.  For a list of available drawing methods
    // see:  https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D
    ctx.fillStyle = "black";

    // Wall
    ctx.strokeRect(75, 140, 150, 110);

    // Door
    ctx.fillRect(130, 190, 40, 60);

    // Roof
    ctx.beginPath();
    ctx.moveTo(50, 140);
    ctx.lineTo(150, 60);
    ctx.lineTo(250, 140);
    ctx.closePath();
    ctx.stroke();

};

document.addEventListener("DOMContentLoaded", main);
