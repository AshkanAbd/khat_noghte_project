# <b>API and Document</b>

# TeamAI class
<b>void getTeamName() : </b>  Enter yout team in this method
<br>
<b>Cell think(Cell[][] map) : </b> Main method where you have to write your codes in it, return of this method is your final decision
<br>
<b>int getMyScore() : </b> Returns your score
<br>
<b>int getOppScore() : </b> Returns your opponent score
<br>
<b>int getMyId() : </b> Returns your ID in server (1 or 2)
<br>
<b>int getOppId() : </b> Returns your opponent ID in server (1 or 2)
<br>
<b>int getRowNumber() : </b> Returns number of rows
<br>
<b>int getColNumber() : </b> Returns number of columns

# Cell class
<b>Cell() : </b> Constructor of cell class
<br>
<b>Cell(int x, int y) : </b> Constructor of cell class
<br>
<b>int getX() : </b> Returns x coordinate of cell
<br>
<b>int getY() : </b> Returns y Coordinate of cell
<br>
<b>boolean isBlock() : </b> Returns that cell is a block or not
<br>
<b>boolean isFree() : </b> Returns that cell is free or not
<br>
<b>boolean isNode() : </b> Returns that cell is a node or not
<br>
<b>int getMarked() : </b> Returns the player ID that fills it

# Notes
<b>Server port is 1898</b>
<br>
<b>You have 1 minute maximum time to make a decision, after that your code will be stopped</b>
