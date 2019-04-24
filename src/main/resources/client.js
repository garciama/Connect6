/*
 *   Place client application Javascript code here.
 */

var xLoc;
var yLoc;
var headerHeight;

var main = function() {

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
            } else if (!response.ok) {
                // shouldn't see this ever i think maybe
                console.log("broke");
            } else {
                // probably want to show the board to the user then, because they just made a game and want to play
                // their move
                console.log("game created successfully")
                drawGameBoard();
            }
    });
};

var drawGameBoard = function () {
    hideMenuAndNavAndFooter();
    let gameBoard = document.getElementById("gameBoard-canvas");
    let ctx = gameBoard.getContext("2d");

    gameBoard.width = 1000;
    gameBoard.height = 532;

    ctx.fillStyle = "white";
    ctx.fillRect(375, 0, 532, 532);

    for(var i = 0; i < 19; i++){
        ctx.moveTo(i * 28 + 375, 0);
        ctx.lineTo(i * 28 + 375, 532);
        ctx.stroke();
        ctx.moveTo(375, i  * 28);
        ctx.lineTo(907, i * 28);
        ctx.stroke();
    }


}

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

var hideMenuAndNavAndFooter = function () {
    hideMenu();
    document.getElementById("footer").style.display = 'none';
    document.getElementById("nav").style.display = 'none';
}

var hideMenu = function(){
    document.getElementById("art1").style.display = 'none';
    document.getElementById("art2").style.display = 'none';
    document.getElementById("art3").style.display = 'none';
    document.getElementById("createGameGetUsers").style.display = 'none';
};

var drawLeaderBoard = function(jsonLeaderBoard){

    let leaderBoard = document.getElementById("leaderBoard-canvas");
    let ctx = leaderBoard.getContext("2d");
    leaderBoard.width = 1500;
    leaderBoard.height = 500;

    let w = leaderBoard.width;
    let h = leaderBoard.height;

    let rows = (JSON.parse(jsonLeaderBoard)).leaderboardRows;

    drawLeaderBoardHeader(leaderBoard);

    yLoc += (headerHeight/2) + 12;
    var rowsLength = rows.length;
    var colWidth = w/6;
    for (var i = 0; i < rowsLength; i++){

        ctx.fillStyle = "white";
        ctx.fillRect(2, yLoc - 15, w - 10, (yLoc - 15));

        ctx.fillStyle = "black";
        ctx.fillText(i, xLoc, yLoc);
        ctx.fillText(rows[i].name, xLoc + colWidth, yLoc);
        ctx.fillText(rows[i].score, xLoc + 2*colWidth, yLoc);
        yLoc += 6;

        if (i < rowsLength - 1){
           ctx.beginPath();
           ctx.moveTo(2, yLoc);
           ctx.lineTo(w - 10, yLoc);
            ctx.stroke();
        }

       yLoc += 15;
    }
    //Move the yLoc back up after the loop.
    yLoc += -15;

    ctx.lineWidth = "2";
    ctx.strokeStyle = "gray";
    ctx.rect(2,2, w - 10, yLoc);
    ctx.stroke();
};

var drawLeaderBoardHeader = function(canvas){
    ctx = canvas.getContext("2d");
    let w = canvas.width;
    let h = canvas.height;

    ctx.lineWidth = "5";
    ctx.fillStyle = "white";
    headerHeight = 50;
    ctx.fillRect( 2, 2, w - 10, headerHeight );

    ctx.strokeStyle = "gray";
    ctx.lineWidth = "3";
    ctx.rect(2, 2, w - 10, headerHeight);
    ctx.stroke();

    ctx.lineWidth = "2";
    var colWidth = w/6;
    for (var i = 0; i < w - 10; i+= colWidth){
        ctx.beginPath();
        if (i != 0){
            ctx.moveTo(i, 2);
            ctx.lineTo(i, headerHeight);
            ctx.stroke();
        }
    }

    xLoc = (colWidth/2) - 10;
    yLoc = 30;

        ctx.font = "14px Sans SC"
        ctx.fillStyle = "black";
        ctx.fillText("Rank", xLoc, yLoc);
        ctx.fillText("Name", xLoc + colWidth, yLoc);
        ctx.fillText("Score", xLoc + 2*colWidth, yLoc);
        ctx.fillText("Wins", xLoc + 3*colWidth, yLoc);
        ctx.fillText("Losses", xLoc + 4*colWidth, yLoc);
        ctx.fillText("Ties", xLoc + 5*colWidth, yLoc);



};


document.addEventListener("DOMContentLoaded", main);
