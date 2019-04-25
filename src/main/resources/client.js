/*
 *   Place client application Javascript code here.
 */

var xLoc;
var yLoc;
var headerHeight;
var gameID;
var redPlayer;
var bluePlayer;
var gridLocation;

var main = function() {

    let createUserButton = document.getElementById("createUserButton");
    createUserButton.addEventListener("click", createAccEvent);

    let seeLeaderboardButton = document.getElementById("leaderboardButton");
    seeLeaderboardButton.addEventListener("click", leaderBoardEvent);

    let createGameButton = document.getElementById("createNewGameButton");
    createGameButton.addEventListener("click", createNewGameEvent);

    let joinGameButton = document.getElementById("joinGameButton");
    createGameButton.addEventListener("click", joinGameEvent);


};

var createNewGameEvent = function() {
    let user1 = document.getElementById("user1").value;
    let user2 = document.getElementById("user2").value;

    redPlayer = user1;
    bluePlayer = user2;

    console.log(redPlayer);

    let json = {
        red: user1,
        blue: user2
    };

    fetch("game/createGame", {method: "PUT", body: JSON.stringify(json)})
        .then(function (response) {
            if (response.status == 404) {
                // TODO: write this to the page directly
                console.log("user not found");
            } else if (!response.ok) {
                // shouldn't see this ever i think maybe
                console.log("broke");
            } else {
                response.text().then( function(value) {
                console.log("id: " + value);
                gameID = value;
                drawGameBoard();

                });
            }
    });
};

var joinGameEvent = function(e){
    hideMenu();

};

var drawGameBoard = function () {
    hideMenuAndNavAndFooter();
    let gameBoard = document.getElementById("gameBoard-canvas");
    let ctx = gameBoard.getContext("2d");

    gameBoard.width = 1000;
    gameBoard.height = 532;

    ctx.fillStyle = "#bf912f";
    ctx.fillRect(375, 0, 532, 532);

    for(var i = 0; i < 19; i++){
        ctx.moveTo(i * 28 + 375, 0);
        ctx.lineTo(i * 28 + 375, 532);
        ctx.stroke();
        ctx.moveTo(375, i  * 28);
        ctx.lineTo(907, i * 28);
        ctx.stroke();
    }

    gameBoard.addEventListener('click', function(evt) {
        var mousePos = getMousePosition(gameBoard, evt);
        gridLocation = getGridLocation(mousePos.x, mousePos.y, 28);
        placePieceEvent(redPlayer);
            /*gridLocation.addEventListener('click', function(evt) {
            var clicked = evt.target;
            clicked.style.background = "black";
            clicked.setAttribute('fill', 'red');
        }, false)*/
        console.log("Row: " + gridLocation.row + " Column: " + gridLocation.column);
    }, false);

};

var playGame = function(){

};



function getMousePosition(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return { x: evt.clientX-rect.left, y: evt.clientY-rect.top};
};

function getGridLocation(posX, posY, gridSize) {
    var cellRow = Math.floor(posY / gridSize);
    var cellCol = Math.floor((posX-375) / gridSize);
    return {row: cellRow, column: cellCol};
};

var placePieceEvent = function(nameVal){
    let gameBoard = document.getElementById("gameBoard-canvas");
    let ctx = gameBoard.getContext("2d");
    var xVal = gridLocation.column;
    var yVal = gridLocation.row;

    let data = {
        x: xVal,
        y: yVal,
        name: nameVal
    };

    console.log(xVal + " " + yVal);

    fetch("game/makeMove/" + gameID , {method: "PUT", headers:{"Content-Type": "application/json"}, body: JSON.stringify(data)} )
            .then( function(response){
                if (!response.ok){
                    console.log("can't make move " + response.status);
                    return false;
                } else {
                    response.text().then( function(value) {
                    //specify error to user
                    ctx.fillStyle = "red";
                    ctx.fillRect(375 + (xVal * 28), (yVal *28), 28, 28);
                    console.log(value);
                    return true;
                    });
                }
            });
};
/*function changeColor(evt) {
    var clicked = evt.target;
    clicked.style.background = "black";
    clicked.setAttribute('fill', 'red');
}*/

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
};

var drawLeaderBoard = function(jsonLeaderBoard){

    let leaderBoard = document.getElementById("leaderBoard-canvas");
    let ctx = leaderBoard.getContext("2d");
    leaderBoard.width = 1500;
    leaderBoard.height = 750;

    let w = leaderBoard.width;
    let h = leaderBoard.height;

    let rows = (JSON.parse(jsonLeaderBoard)).leaderboardRows;

    drawLeaderBoardHeader(leaderBoard);

    yLoc += (headerHeight/2) + 12;
    var rowsLength = rows.length;
    var colWidth = w/6;

    ctx.lineWidth = "2";
    ctx.strokeStyle = "gray";
    for (var i = 0; i < rowsLength; i++){

        //draw the white background bar by bar
        ctx.fillStyle = "#d1f7a5";
        ctx.fillRect(2, yLoc - 15, w - 10, 21 );

        ctx.fillStyle = "black";
        ctx.fillText(i+1, xLoc, yLoc);
        ctx.fillText(rows[i].name, xLoc + colWidth, yLoc);
        ctx.fillText(rows[i].score, xLoc + 2*colWidth, yLoc);
        ctx.fillText(rows[i].wins, xLoc + 3*colWidth, yLoc);
        ctx.fillText(rows[i].losses, xLoc + 4*colWidth, yLoc);
        ctx.fillText(rows[i].ties, xLoc + 5*colWidth, yLoc);

        ctx.beginPath();
        ctx.lineWidth = "2";
        ctx.strokeStyle = "gray";


        ctx.moveTo(xLoc + (colWidth/2)+10, yLoc - 15);
        ctx.lineTo(xLoc + (colWidth/2)+10, yLoc + 6);
        ctx.stroke();

        ctx.moveTo(xLoc + (colWidth/2)+colWidth + 10, yLoc - 15);
        ctx.lineTo(xLoc + (colWidth/2)+colWidth + 10, yLoc + 6);
        ctx.stroke();

        ctx.moveTo(xLoc + (colWidth/2)+ 2*colWidth + 10, yLoc - 15);
        ctx.lineTo(xLoc + (colWidth/2)+ 2*colWidth + 10, yLoc + 6);
        ctx.stroke();

        ctx.moveTo(xLoc + (colWidth/2)+ 3*colWidth + 10, yLoc - 15);
        ctx.lineTo(xLoc + (colWidth/2)+ 3*colWidth + 10, yLoc + 6);
        ctx.stroke();

         ctx.moveTo(xLoc + (colWidth/2)+ 4*colWidth + 10, yLoc - 15);
         ctx.lineTo(xLoc + (colWidth/2)+ 4*colWidth + 10, yLoc + 6);
         ctx.stroke();


        //Draw left vertical line
        ctx.moveTo(2, yLoc - 15);
        ctx.lineTo(2, yLoc + 6);
        ctx.stroke();

        //Draw the right vertical line
        ctx.moveTo(w - 8, yLoc - 15);
        ctx.lineTo(w - 8, yLoc + 6);
        ctx.stroke();

        yLoc += 6;

        //Draw the horizontal lines between rows
        ctx.beginPath();
        ctx.moveTo(2, yLoc);
        ctx.lineTo(w - 8, yLoc);
        ctx.stroke();

       yLoc += 15;
    }


    //Move the yLoc back up after the loop.
    yLoc += -15;

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
