/*
 *   Place client application Javascript code here.
 */

var main = function() {
    // Draw some shapes to the canvas
    //drawBoard();

    // As an example, we'll make a request to the server when the button is clicked
    // and display the results in the HTML page.
    let createUserButton = document.getElementById("createUserButton");
    createUserButton.addEventListener("click", createAccEvent);

    let seeLeaderboardButton = document.getElementById("leaderboardButton");
    seeLeaderboardButton.addEventListener("click", leaderBoardEvent);

    let createGameButton = document.getElementById("submitUsersForNewGameButton");
    createGameButton.addEventListener("click", createNewGameEvent);


};

var createNewGameEvent = function() {
   // hideMenu();

    let user1 = document.getElementById("user1").value;
    let user2 = document.getElementById("user2").value;

    let json = {
        red: user1,
        blue: user2
    };

    fetch("game/createGame", {method: "PUT", body: JSON.stringify(json)})
        .then(function (response) {
            if (response.status == 404) {
                // user not found, indicate this to user
                console.log("user not found");
            }
            else if (!response.ok) {
                // shouldn't see this ever i think maybe
                console.log("broke");
            } else {
                // probably want to show the board to the user then, because they just made a game and want to play
                // their move
                console.log("game created successfully")
            }
    });
};

var createAccEvent = function(e){
    e.preventDefault();
    var name = {name: document.getElementById("uname").value};

    fetch("menu/createUser", {method: "POST", body: JSON.stringify(name)} )
        .then( function(response){
            let el = document.getElementById("create-user-area");
            if (!response.ok){
                if (response.status == 400){
                    el.innerText = "User Already Exists"
                    el.style.color = "red";
                } else {
                el.innerText = "Error code: " + response.status;
                el.style.fontWeight = "bold";
                el.style.color = "red";
                }
            } else {
                response.text().then( function(value) {
                el.innerText = value;
                el.style.color = "green";
                el.style.fontWeight = "bold";
                });
            }
        });
};

var leaderBoardEvent = function(e) {
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
                    hideMenu();
                    drawLeaderBoard(value);
                });
            }
        });

};

var hideMenu = function(){
    document.getElementById("art1").style.display = 'none';
    document.getElementById("art2").style.display = 'none';
    document.getElementById("art3").style.display = 'none';
};

var drawLeaderBoard = function(jsonLeaderBoard){
    let leaderBoard = document.getElementById("leaderBoard-canvas");


    let ctx = leaderBoard.getContext("2d");
    let w = leaderBoard.width;
    let h = leaderBoard.height;

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
