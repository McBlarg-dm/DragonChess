import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

import static java.lang.Thread.sleep;

/**
 * Created by Shokhenah on 2/7/2017.
 *
 * This is the class to act as the engine of the DragonChess Game.
 *
 * For an expansion of DragonChess rules look to:
 * http://www.chessvariants.com/3d.dir/dragonchess.html
 *            Black
 * 8  - - g - - - r - - - g -
 * 7  s - s - s - s - s - s -
 * 6  - - - - - - - - - - - -
 * 5  - - - - - - - - - - - -
 * 4  - - - - - - - - - - - -      TOP (1)
 * 3  - - - - - - - - - - - -
 * 2  S - S - S - S - S - S -
 * 1  - - G - - - R - - - G -
 *
 * 8  o u h t c m k p t h u o
 * 7  w w w w w w w w w w w w
 * 6  - - - - - - - - - - - -
 * 5  - - - - - - - - - - - -
 * 4  - - - - - - - - - - - -      MIDDLE (2)
 * 3  - - - - - - - - - - - -
 * 2  W W W W W W W W W W W W
 * 1  O U H T C M K P T H U O
 *
 * 8  - - b - - - e - - - b -
 * 7  - d - d - d - d - d - d
 * 6  - - - - - - - - - - - -
 * 5  - - - - - - - - - - - -
 * 4  - - - - - - - - - - - -      BOTTOM (3)
 * 3  - - - - - - - - - - - -
 * 2  - D - D - D - D - D - D
 * 1  - - B - - - E - - - B -
 *            White
 *    a b c d e f g h i j k l
 *
 *    15 Distinct pieces that all behave differently.
 */
public class DragonChess extends AbstractGame {
    // Plane, Column, Row
    private GamePiece[][][] gameBoard = new GamePiece[3][12][8];
    private int[] whiteKing = new int[]{1, 6, 0};
    private int[] blackKing = new int[]{1, 6, 7};
    private String userMove;
    private boolean moveEntered;
    private double boardState = 0.0;

    private JEditorPane boardDisplay;
    private JTextArea moveHistory;
    private JPanel Chessboard;
    private JTextField userConsole;
    private JTextArea infoDisplay;

    public enum Team {Black, White, Null}

    public static void main(String[] args){
        //Make an instance of the game
        DragonChess game = new DragonChess();

        //Handle GUI start-up
        JFrame frame = new JFrame("DragonChess");
        frame.setContentPane(game.Chessboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        game.displayMessage("default.txt");
        //Begin Playing
        game.play(1);
    }

    protected DragonChess() {
        initializeBoard();
        userConsole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMove = userConsole.getText();
                if (userMove.charAt(0) == '/') {
                    if (userMove.equals("/winning")) {
                        winning();
                    }
                    else if (userMove.equals("/back")) {
                        displayStatus();
                        displayMessage("default.txt");
                    }
                    else
                        displayMoves(userMove);
                }
                else {
                    moveEntered = true;
                }
                userConsole.setText("");
            }
        });
    }

    /*
      build the game board in its initial state.
     */
    private void initializeBoard() {
        //Sky Black
        //Sylphs
        gameBoard[0][0][6] = new Sylph(new int[]{0,0,6}, Team.Black, gameBoard);
        gameBoard[0][2][6] = new Sylph(new int[]{0,2,6}, Team.Black, gameBoard);
        gameBoard[0][4][6] = new Sylph(new int[]{0,4,6}, Team.Black, gameBoard);
        gameBoard[0][6][6] = new Sylph(new int[]{0,6,6}, Team.Black, gameBoard);
        gameBoard[0][8][6] = new Sylph(new int[]{0,8,6}, Team.Black, gameBoard);
        gameBoard[0][10][6] = new Sylph(new int[]{0,10,6}, Team.Black, gameBoard);
        //Griffins
        gameBoard[0][2][7] = new Griffin(new int[]{0,2,7}, Team.Black, gameBoard);
        gameBoard[0][10][7] = new Griffin(new int[]{0,10,7}, Team.Black, gameBoard);
        //Dragon
        gameBoard[0][6][7] = new Dragon(new int[]{0,6,7}, Team.Black, gameBoard);
        //Sky White
        gameBoard[0][0][1] = new Sylph(new int[]{0,0,1}, Team.White, gameBoard);
        gameBoard[0][2][1] = new Sylph(new int[]{0,2,1}, Team.White, gameBoard);
        gameBoard[0][4][1] = new Sylph(new int[]{0,4,1}, Team.White, gameBoard);
        gameBoard[0][6][1] = new Sylph(new int[]{0,6,1}, Team.White, gameBoard);
        gameBoard[0][8][1] = new Sylph(new int[]{0,8,1}, Team.White, gameBoard);
        gameBoard[0][10][1] = new Sylph(new int[]{0,10,1}, Team.White, gameBoard);
        //Griffins
        gameBoard[0][2][0] = new Griffin(new int[]{0,2,0}, Team.White, gameBoard);
        gameBoard[0][10][0] = new Griffin(new int[]{0,10,0}, Team.White, gameBoard);
        //Dragon
        gameBoard[0][6][0] = new Dragon(new int[]{0,6,0}, Team.White, gameBoard);

        //Ground Black
        //Warriors
        for (int i = 0; i < 12; i++)
            gameBoard[1][i][6] = new Warrior(new int[]{1,i,6}, Team.Black, gameBoard);
        //The others
        gameBoard[1][0][7] = new Oliphant(new int[]{1,0,7}, Team.Black, gameBoard);
        gameBoard[1][1][7] = new Unicorn(new int[]{1,1,7}, Team.Black, gameBoard);
        gameBoard[1][2][7] = new Hero(new int[]{1,2,7}, Team.Black, gameBoard);
        gameBoard[1][3][7] = new Thief(new int[]{1,3,7}, Team.Black, gameBoard);
        gameBoard[1][4][7] = new Cleric(new int[]{1,4,7}, Team.Black, gameBoard);
        gameBoard[1][5][7] = new Mage(new int[]{1,5,7}, Team.Black, gameBoard);
        gameBoard[1][6][7] = new King(new int[]{1,6,7}, Team.Black, gameBoard);
        gameBoard[1][7][7] = new Paladin(new int[]{1,7,7}, Team.Black, gameBoard);
        gameBoard[1][8][7] = new Thief(new int[]{1,8,7}, Team.Black, gameBoard);
        gameBoard[1][9][7] = new Hero(new int[]{1,9,7}, Team.Black, gameBoard);
        gameBoard[1][10][7] = new Unicorn(new int[]{1,10,7}, Team.Black, gameBoard);
        gameBoard[1][11][7] = new Oliphant(new int[]{1,11,7}, Team.Black, gameBoard);
        //Ground White
        for (int i = 0; i < 12; i++)
            gameBoard[1][i][1] = new Warrior(new int[]{1,i,1}, Team.White, gameBoard);
        //The others
        gameBoard[1][0][0] = new Oliphant(new int[]{1,0,0}, Team.White, gameBoard);
        gameBoard[1][1][0] = new Unicorn(new int[]{1,1,0}, Team.White, gameBoard);
        gameBoard[1][2][0] = new Hero(new int[]{1,2,0}, Team.White, gameBoard);
        gameBoard[1][3][0] = new Thief(new int[]{1,3,0}, Team.White, gameBoard);
        gameBoard[1][4][0] = new Cleric(new int[]{1,4,0}, Team.White, gameBoard);
        gameBoard[1][5][0] = new Mage(new int[]{1,5,0}, Team.White, gameBoard);
        gameBoard[1][6][0] = new King(new int[]{1,6,0}, Team.White, gameBoard);
        gameBoard[1][7][0] = new Paladin(new int[]{1,7,0}, Team.White, gameBoard);
        gameBoard[1][8][0] = new Thief(new int[]{1,8,0}, Team.White, gameBoard);
        gameBoard[1][9][0] = new Hero(new int[]{1,9,0}, Team.White, gameBoard);
        gameBoard[1][10][0] = new Unicorn(new int[]{1,10,0}, Team.White, gameBoard);
        gameBoard[1][11][0] = new Oliphant(new int[]{1,11,0}, Team.White, gameBoard);
        //Cave
        //Dwarves
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 1) {
                gameBoard[2][i][6] = new Dwarf(new int[]{2,i,6}, Team.Black, gameBoard);
                gameBoard[2][i][1] = new Dwarf(new int[]{2,i,1}, Team.White, gameBoard);
            }
        }
        //Basilisks
        gameBoard[2][2][7] = new Basilisk(new int[]{2,2,7}, Team.Black, gameBoard);
        gameBoard[2][2][0] = new Basilisk(new int[]{2,2,0}, Team.White, gameBoard);
        gameBoard[2][10][7] = new Basilisk(new int[]{2,10,7}, Team.Black, gameBoard);
        gameBoard[2][10][0] = new Basilisk(new int[]{2,10,0}, Team.White, gameBoard);
        //Elementals
        gameBoard[2][6][7] = new Elemental(new int[]{2,6,7}, Team.Black, gameBoard);
        gameBoard[2][6][0] = new Elemental(new int[]{2,6,0}, Team.White, gameBoard);
        //Fill in the empty spaces with NullPieces
        for (int p = 0; p < 3; p++){
            for (int c = 0; c < 12; c++){
                for (int r = 0; r < 8; r++){
                    if (gameBoard[p][c][r] == null)
                        gameBoard[p][c][r] = new NullPiece();
                }
            }
        }
    }
    /*
        Lets us know if the game over. Game ends if a king is captured or if he check-mated without escape.
        Will have to find the position of both kings. If only one king is found, the game is over.

        If two kings are found it must parse all the pieces and see if a king is in a check status.
     */
    protected boolean isGameOver() {
        return !(squareContains(blackKing, gameBoard) instanceof King
                && squareContains(whiteKing, gameBoard) instanceof King);
    }

    @Override
    /*
     * Will use a summed list of controlled pieces and values to determine who
     * is in the lead.
     */
    protected Player winning() {
        Player winner = super.winning();
        infoDisplay.setText("");
        if (winner == Player.human) {
            infoDisplay.append("You're in the lead!");
        }
        else if (winner == Player.computer) {
            infoDisplay.append("The computer is in the lead...");
        }
        else {
            infoDisplay.append("So far you're equals.");
        }
        return winner;
    }

    @Override
    /*
     * Literally just passes to a println.
     *
     */
    protected void displayMessage(String file) {
        //Read in files based on the name.
        String path = "src\\infoFiles\\" + file;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            infoDisplay.setText("");
            infoDisplay.setFont(new Font("monospaced", Font.PLAIN, 15));
            while ((line = br.readLine()) != null) {
                infoDisplay.append(line + "\n");
            }
        } catch (java.io.IOException e) {
            infoDisplay.append("There was an error reading\nthe associated text file.");
            e.printStackTrace();
        }
    }

    protected void displayMoves(String pieceToQuestion) {
        char piece;
        if (pieceToQuestion.length() < 3) {
            piece = pieceToQuestion.toLowerCase().charAt(1);
            switch (piece) {
                case 's':
                    displayMessage("Slyph.txt");
                    break;
                case 'g':
                    displayMessage("Griffin.txt");
                    break;
                case 'r':
                    displayMessage("Dragon.txt");
                    break;
                case 'o':
                    displayMessage("Oliphant.txt");
                    break;
                case 'u':
                    displayMessage("Unicorn.txt");
                    break;
                case 'h':
                    displayMessage("Hero.txt");
                    break;
                case 't':
                    displayMessage("Thief.txt");
                    break;
                case 'c':
                    displayMessage("Cleric.txt");
                    break;
                case 'm':
                    displayMessage("Mage.txt");
                    break;
                case 'k':
                    displayMessage("King.txt");
                    break;
                case 'p':
                    displayMessage("Paladin.txt");
                    break;
                case 'w':
                    displayMessage("Warrior.txt");
                    break;
                case 'b':
                    displayMessage("Basilisk.txt");
                    break;
                case 'e':
                    displayMessage("Elemental.txt");
                    break;
                case 'd':
                    displayMessage("Dwarf.txt");
                    break;
                default:
                    infoDisplay.setText("That was not a valid piece. Please try again.");
            }
        }
        else {
            infoDisplay.setText("That was not a valid command. Please try again.");
            return;
        }
        /*
        ArrayList<int[]> query = piece.moveList();
        String board = "";

        boardDisplay.setText(null);
        boardDisplay.setContentType("text/html");
        boardDisplay.setFont(new Font("Courier", Font.PLAIN, 15));
        board += "<body>DragonChess Board:<br>";
        for (int r = 7; r >= 0; r--) {
            board += (r + 1) + " "; //Print the row index
            for (int p = 0; p < 3; p++) {
                for (int c = 0; c < 12; c++){
                    GamePiece square = gameBoard[p][c][r];
                    if (query.contains(new int[]{p,c,r})){
                        if (square instanceof NullPiece)
                            board += " " + "<b>x</b>";
                        else
                            board += " " + "<b>" + convertSymbol(square) + "</b>";
                    }
                    else {
                        board += " " + convertSymbol(square);
                    }
                }
                board += "&nbsp;&nbsp;"; //Separate the boards
            }
            board += "<br>";
        }
        board += "<br></body>";
        //Print Column indexes
        for (int i = 0; i < 3; i++) {
            board += "  ";
            for (char var = 'a'; var < 'm'; var++) {
                board += " " + var;
            }
        }
        board += "<br>";
        boardDisplay.setText(board);

        // create a JEditorPane that renders HTML and defaults to the system font.
        //JEditorPane editorPane =
        boardDisplay = new JEditorPane(new HTMLEditorKit().getContentType(),board);
        // set the text of the JEditorPane to the given text.
        boardDisplay.setText(board);

        // add a CSS rule to force body tags to use the default label font
        // instead of the value in javax.swing.text.html.default.csss
        Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { font-family: monospace; " +
                "font-size: " + 15 + "pt; }";
        ((HTMLDocument)boardDisplay.getDocument()).getStyleSheet().addRule(bodyRule);

        boardDisplay.setOpaque(false);
        boardDisplay.setBorder(null);
        boardDisplay.setEditable(false);
        */
    }
    @Override
    /*
     * Helper method to do the math for the winning function.
     *
     * Blacks are positive, whites are negative
     */
    protected double evaluate() {
        return boardState; //Changes when captures are made.
        /*
        int boardState = 0;
        int p, c, r;
        for (p = 0; p < 3; p++) {
            for (c = 0; c < 12; c++){
                for (r = 0; r < 8; r++){
                    try {
                        GamePiece square = squareContains(new int[]{p, c, r}, gameBoard);
                        if (square.team == Team.Black)
                            boardState += square.getWorth();
                        else
                            boardState -= square.getWorth();
                    } catch (NullPointerException e){
                        //Empty catch because we just need to move on.
                    }
                }
            }
        }
        return boardState;
        */
    }

    protected AbstractGame clone() {
        DragonChess clone = new DragonChess();
        GamePiece[][][] cloneBoard = clone.gameBoard;
        for (int p = 0; p < 3; p++) {
            for (int c = 0; c < 12; c++) {
                for (int r = 0; r < 8; r++) {
                    //Need to return new instances of the pieces declared with the clone board reference
                    cloneBoard[p][c][r] = cloneAssist(new int[]{p, c, r}, cloneBoard);
                }
            }
        }
        clone.moveNumber = moveNumber;
        clone.boardState = boardState;
        return clone;
    }
    private GamePiece cloneAssist(int[] location, GamePiece[][][] cloneBoard) {
        GamePiece piece = squareContains(location, gameBoard);
        if (piece instanceof Basilisk)
            return new Basilisk(location, piece.team, cloneBoard);
        if (piece instanceof Cleric)
            return new Cleric(location, piece.team, cloneBoard);
        if (piece instanceof Dragon)
            return new Dragon(location, piece.team, cloneBoard);
        if (piece instanceof Dwarf)
            return new Dwarf(location, piece.team, cloneBoard);
        if (piece instanceof Elemental)
            return new Elemental(location, piece.team, cloneBoard);
        if (piece instanceof Griffin)
            return new Griffin(location, piece.team, cloneBoard);
        if (piece instanceof Hero)
            return new Hero(location, piece.team, cloneBoard);
        if (piece instanceof King)
            return new King(location, piece.team, cloneBoard);
        if (piece instanceof Mage)
            return new Mage(location, piece.team, cloneBoard);
        if (piece instanceof Oliphant)
            return new Oliphant(location, piece.team, cloneBoard);
        if (piece instanceof Paladin)
            return new Paladin(location, piece.team, cloneBoard);
        if (piece instanceof Sylph)
            return new Sylph(location, piece.team, cloneBoard);
        if (piece instanceof Thief)
            return new Thief(location, piece.team, cloneBoard);
        if (piece instanceof Unicorn)
            return new Unicorn(location, piece.team, cloneBoard);
        if (piece instanceof Warrior)
            return new Warrior(location, piece.team, cloneBoard);
        return new NullPiece();
    }
    /*
     * Alters game state to reflect a given move.
     *
     */
    protected void makeMove(String move) {
        super.makeMove(move);
        String[] locs = parseMove(move);
        int[] start = new int[]{
                convertPlanetoCoord(locs[0].charAt(0)),
                convertColtoCoord(locs[0].charAt(1)),
                convertRowtoCoord(locs[0].charAt(2))
        };
        int[] target = new int[]{
                convertPlanetoCoord(locs[1].charAt(0)),
                convertColtoCoord(locs[1].charAt(1)),
                convertRowtoCoord(locs[1].charAt(2))
        };
        GamePiece movingPiece = squareContains(start, gameBoard);
        GamePiece targetPiece = squareContains(target, gameBoard);
        double targetVal = targetPiece.getWorth();
        //Alters the boardState to simplify evaluate
        if (targetPiece.team == Team.Black)
            boardState -= targetVal;
        else
            boardState += targetVal;
        //Handle the actual moving.
        if (movingPiece instanceof Dragon && target[0] == 1)
        {
            //Delete piece at location
            insertToBoard(target, new NullPiece());
        }
        else {
            //Move
            if (movingPiece instanceof King)
            {
                if (movingPiece.team == Team.Black)
                    blackKing = arrCopy(target);
                else
                    whiteKing = arrCopy(target);
            }
            if (movingPiece.canPromote) {
                //Dwarf direction switch
                if (movingPiece instanceof Dwarf && (target[2] == 7 || target[2] == 0)) {
                    insertToBoard(start, new NullPiece()); //Overrides the taken piece
                    insertToBoard(target, movingPiece);
                    ((Dwarf) movingPiece).switchDirection();
                    return;
                }
                //Promotion to hero
                if (movingPiece instanceof Warrior) {
                    if (movingPiece.team == Team.White && target[2] == 7) {
                        insertToBoard(start, new NullPiece()); //Overrides the taken piece
                        insertToBoard(target, new Hero(arrCopy(target), movingPiece.team, gameBoard));
                        return;
                    }
                    if (movingPiece.team == Team.Black && target[2] == 0) {
                        insertToBoard(start, new NullPiece()); //Overrides the taken piece
                        insertToBoard(target, new Hero(arrCopy(target), movingPiece.team, gameBoard));
                        return;
                    }
                }
            }
            insertToBoard(start, new NullPiece()); //Overrides the taken piece
            insertToBoard(target, movingPiece);
        }
        String movingString;
        String targetString;
        //Handle aggressor
        if (movingPiece.team == Team.White) {
            movingString = "White " + movingPiece.getClass().getSimpleName() + " at "
                    + convertCoordtoPlane(start[0])
                    + convertCoordtoCol(start[1])
                    + convertCoordtoRow(start[2]) + " ";
        }
        else {
            movingString = "Black " + movingPiece.getClass().getSimpleName() + " at "
                    + convertCoordtoPlane(start[0])
                    + convertCoordtoCol(start[1])
                    + convertCoordtoRow(start[2]) + " ";
        }
        //Handle victim
        if (targetPiece.team == Team.White)
            targetString = "captures White " + targetPiece.getClass().getSimpleName() + " at "
                    + convertCoordtoPlane(target[0])
                    + convertCoordtoCol(target[1])
                    + convertCoordtoRow(target[2]);
        else if (targetPiece.team == Team.Black)
            targetString = "captures Black " + targetPiece.getClass().getSimpleName() + " at "
                    + convertCoordtoPlane(target[0])
                    + convertCoordtoCol(target[1])
                    + convertCoordtoRow(target[2]);
        else
            targetString = "moves to " + convertCoordtoPlane(target[0])
                    + convertCoordtoCol(target[1])
                    + convertCoordtoRow(target[2]);
        moveHistory.append(movingString + targetString + "\n");
    }

    private void insertToBoard(int[] space, GamePiece newPiece){
        newPiece.plane = space[0];
        newPiece.col = space[1];
        newPiece.row = space[2];
        gameBoard[space[0]][space[1]][space[2]] = newPiece;
    }

    @Override
    /*
     * Query a move from the user. Reads a string in from the console and returns it.
     */
    protected String getUserMove(){
        while (!moveEntered) {
            try {
                sleep(7);
            } catch (InterruptedException e) {

            }
        }
        moveEntered = false;
        return userMove;
    }

    @Override
    /*
     * Returns a Vector(may change to ArrayList) that
     * contains a list of all possible moves to be made.
     *
     * Going to be super huge and may not be used.
     * Should make it account for Checkmate.
     *
     */
    protected Vector<String> computeMoves() {
        Vector<String> compMoves = new Vector<>();
        Team mover = turn();
        int p,c,r;
        GamePiece square;
        for (p = 0; p < 3; p++) {
            for (c = 0; c < 12; c++){
                for (r = 0; r < 8; r++){
                    square = squareContains(new int[]{p,c,r}, gameBoard);
                    if (square.team == mover){
                        ArrayList<int[]> moves = square.moveList();
                        for (int[] currMove : moves) {
                            String move = "" + convertCoordtoPlane(p)
                                    + convertCoordtoCol(c)
                                    + convertCoordtoRow(r) + " to "
                                    + convertCoordtoPlane(currMove[0])
                                    + convertCoordtoCol(currMove[1])
                                    + convertCoordtoRow(currMove[2]);
                            compMoves.addElement(move);
                        }
                    }
                }
            }
        }
        return compMoves;
    }


    @Override
    /*
     * Print or pass the current board state.
     */
    protected void displayStatus() {
        String board;
        boardDisplay.setText(null);
        boardDisplay.setContentType("text/plain");
        boardDisplay.setFont(new Font("monospaced", Font.PLAIN, 15));
        board = "DragonChess Board:\n";
        for (int r = 7; r >= 0; r--) {
            board += (r + 1) + " "; //Print the row index
            for (int p = 0; p < 3; p++) {
                for (int c = 0; c < 12; c++){

                    GamePiece square = gameBoard[p][c][r];
                    board += " " + convertSymbol(square);
                }
                board += "  "; //Separate the boards
            }
            board += "\n";
        }
        board += "\n";
        //Print Column indexes
        for (int i = 0; i < 3; i++) {
            board += "  ";
            for (char var = 'a'; var < 'm'; var++) {
                board += " " + var;
            }
        }
        board += "\n";
        boardDisplay.setText(board);
    }

    private char convertSymbol(GamePiece var) {
        if (var instanceof Basilisk) {
            if (var.team == Team.Black)
                return 'b';
            else
                return 'B';
        }
        else if (var instanceof Cleric) {
            if (var.team == Team.Black)
                return 'c';
            else
                return 'C';
        }
        else if (var instanceof Dragon) {
            if (var.team == Team.Black)
                return 'r';
            else
                return 'R';
        }
        else if (var instanceof Dwarf) {
            if (var.team == Team.Black)
                return 'd';
            else
                return 'D';
        }
        else if (var instanceof Elemental) {
            if (var.team == Team.Black)
                return 'e';
            else
                return 'E';
        }
        else if (var instanceof Griffin) {
            if (var.team == Team.Black)
                return 'g';
            else
                return 'G';
        }
        else if (var instanceof Hero) {
            if (var.team == Team.Black)
                return 'h';
            else
                return 'H';
        }
        else if (var instanceof King) {
            if (var.team == Team.Black)
                return 'k';
            else
                return 'K';
        }
        else if (var instanceof Mage) {
            if (var.team == Team.Black)
                return 'm';
            else
                return 'M';
        }
        else if (var instanceof Oliphant) {
            if (var.team == Team.Black)
                return 'o';
            else
                return 'O';
        }
        else if (var instanceof Paladin) {
            if (var.team == Team.Black)
                return 'p';
            else
                return 'P';
        }
        else if (var instanceof Sylph) {
            if (var.team == Team.Black)
                return 's';
            else
                return 'S';
        }
        else if (var instanceof Thief) {
            if (var.team == Team.Black)
                return 't';
            else
                return 'T';
        }
        else if (var instanceof Unicorn) {
            if (var.team == Team.Black)
                return 'u';
            else
                return 'U';
        }
        else if (var instanceof Warrior) {
            if (var.team == Team.Black)
                return 'w';
            else
                return 'W';
        }
        else
            return '-';
    }

    @Override
    /*
     * Given move follows rules.
     *
     */
    protected boolean isLegal(String move) {
        //Check that the piece at the given place is of the right team
        String[] parsedMove = parseMove(move);
        boolean decision;
        try {
            int piecePlaneStart = convertPlanetoCoord(parsedMove[0].charAt(0));
            int pieceColStart = convertColtoCoord(parsedMove[0].charAt(1));
            int pieceRowStart = convertRowtoCoord(parsedMove[0].charAt(2));
            GamePiece piece = gameBoard[piecePlaneStart][pieceColStart][pieceRowStart];
            decision = (piece.canMove(parsedMove[1]));
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException s){
            //infoDisplay.setText("An Array Index error was thrown. Move treated as Invalid.\nPlease double check the syntax.");
            decision = false;
        }
        if (!decision)
            infoDisplay.setText("The move \"" + move + "\" is illegal, please double check the syntax.");
        return decision;
    }

    //Return who's turn it is by color
    protected Team turn() {
        if (movesCompleted() % 2 == 0)
            return Team.White;
        else
            return Team.Black;
    }

    /**
     * Since move strings are in the form of
     * 'Ga6 to Ga5' we need to[out the
     * starting and ending coords of the move.
     *
     * First char is the plane (S, G, C)
     * Second char is column (a-l)
     * Third char is the row (1-8)
     *
     * Later methods will parse the string themselves.
     *
     * @param move String representing a desired move.
     * @return String array with the Coordinates
     */
    private String[] parseMove(String move) {
        String[] coordinates = {"", ""};
        // parse move string
        try {
            String[] parse = move.split(" ");
            coordinates[0] = parse[0];
            coordinates[1] = parse[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            infoDisplay.setText("That was not a valid command. Please try again.");
            moveEntered = false;
        }
        return coordinates;
    }

    protected static boolean onBoard(int[] move) {
        return (move[0] < 3 && move[0] >= 0) && (move[1] < 12 && move[1] >= 0) && (move[2] < 8 && move[2] >=0);
    }
    //Public helper to convert char to Plane index
    public static int convertPlanetoCoord(char p) {
        switch (p) {
            case 'S': {return 0;}
            case 'G': {return 1;}
            case 'C': {return 2;}
            default: {return -1;}
        }
    }

    //Public helper to convert char to row index.
    public static int convertRowtoCoord(char r) {
        switch (r) {
            case '1': {return 0;}
            case '2': {return 1;}
            case '3': {return 2;}
            case '4': {return 3;}
            case '5': {return 4;}
            case '6': {return 5;}
            case '7': {return 6;}
            case '8': {return 7;}
            default: {return -1;}
        }
    }

    //public helper to convert char to column index
    public static int convertColtoCoord(char c) {
        switch (c) {
            case 'a': {return 0;}
            case 'b': {return 1;}
            case 'c': {return 2;}
            case 'd': {return 3;}
            case 'e': {return 4;}
            case 'f': {return 5;}
            case 'g': {return 6;}
            case 'h': {return 7;}
            case 'i': {return 8;}
            case 'j': {return 9;}
            case 'k': {return 10;}
            case 'l': {return 11;}
            default: {return -1;}
        }
    }
        //Public helper to convert char to Plane index
    public static char convertCoordtoPlane(int p) {
        switch (p) {
            case 0: {return 'S';}
            case 1: {return 'G';}
            case 2: {return 'C';}
            default: {return ' ';}
        }
    }

    //Public helper to convert char to row index. Coord
    public static char convertCoordtoRow(int r) {
        switch (r) {
            case 0: {return '1';}
            case 1: {return '2';}
            case 2: {return '3';}
            case 3: {return '4';}
            case 4: {return '5';}
            case 5: {return '6';}
            case 6: {return '7';}
            case 7: {return '8';}
            default: {return ' ';}
        }
    }

    //public helper to convert char to column index
    public static char convertCoordtoCol(int c) {
        switch (c) {
            case 0: {return 'a';}
            case 1: {return 'b';}
            case 2: {return 'c';}
            case 3: {return 'd';}
            case 4: {return 'e';}
            case 5: {return 'f';}
            case 6: {return 'g';}
            case 7: {return 'h';}
            case 8: {return 'i';}
            case 9: {return 'j';}
            case 10: {return 'k';}
            case 11: {return 'l';}
            default: {return ' ';}
        }
    }

    public static int[] arrCopy(int[] param) {
        return new int[]{param[0], param[1], param[2]};
    }

    public static GamePiece squareContains(int[] coord, GamePiece[][][] board){
        return board[coord[0]][coord[1]][coord[2]];
    }
}
