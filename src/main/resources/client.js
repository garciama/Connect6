/*
 *   Place client application Javascript code here.
 */

var xLoc;
var yLoc;
var headerHeight;

var main = function() {
    // Draw some shapes to the canvas
    //drawBoard();

    // As an example, we'll make a request to the server when the button is clicked
    // and display the results in the HTML page.
    let btn = document.getElementById("createUserButton");
    btn.addEventListener("click", createAccEvent);

    let btn2 = document.getElementById("leaderboardButton");
    btn2.addEventListener("click", leaderBoardEvent);


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
