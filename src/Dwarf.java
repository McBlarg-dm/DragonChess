import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/2/2017.
 *
 * While on the bottom or middle board, the Dwarf may capture one cell diagonally forward or move without capturing one cell forward or laterally.

 It may move up to the middle board by capturing to the cell directly above.

 It may move back to the bottom board by making a non-capturing move to the cell directly below.

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - - - - - - - -[c m c]-      MIDDLE (2)
 3   - - c - - - - -[m[D]m]-
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - c m c - - - - - - - -      BOTTOM (3)
 3   - m D m - - - - -[m]- -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

     a b c d e f g h i j k l

    We're implementing a variant rule where Dwarves reverse move direction upon reaching a side.
 */
public class Dwarf extends GamePiece {
    protected Dwarf(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        super(location, team, gameBoard);
        //TODO:Flagged as promotable, but calls change to sideWhite instead.
        canPromote = true;
        worth = 2;
    }
    @Override
    protected boolean canMove(String dest)
    {
        return super.canMove(dest);
    }

    protected boolean isFrozen() {
        return plane == 1
                && (DragonChess.squareContains(new int[]{plane - 1, col, row}, gameBoard) instanceof Basilisk)
                && (DragonChess.squareContains(new int[]{plane - 1, col, row}, gameBoard).team == oppositeTeam());
    }

    protected ArrayList<int[]> moveList() {
        if (isFrozen())
            return new ArrayList<>();
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        if (sideWhite) {
            //White moves
            int[][] paramMoves = {
                    {plane, col - 1, row},
                    {plane, col, row + 1},
                    {plane, col + 1, row}
            };
            int[][] paramCapts = {
                    {plane, col - 1, row + 1},
                    {plane, col + 1, row + 1}
            };
            for (int x = 0; x < 3; x++) {
                if (DragonChess.onBoard(paramMoves[x])
                        && DragonChess.squareContains(paramMoves[x], gameBoard) instanceof NullPiece){
                    moves.add(i, DragonChess.arrCopy(paramMoves[x]));
                    i++;
                }
            }
            for (int x = 0; x < 2; x++) {
                if (DragonChess.onBoard(paramCapts[x])
                        && DragonChess.squareContains(paramCapts[x], gameBoard).team == oppositeTeam()){
                    moves.add(i, DragonChess.arrCopy(paramCapts[x]));
                    i++;
                }
            }
        }
        else {
            //Black moves
            int[][] paramMoves = {
                    {plane, col - 1, row},
                    {plane, col, row - 1},
                    {plane, col + 1, row}
            };
            int[][] paramCapts = {
                    {plane, col + 1, row - 1},
                    {plane, col - 1, row - 1}
            };
            for (int x = 0; x < 3; x++) {
                if (DragonChess.onBoard(paramMoves[x])
                        && DragonChess.squareContains(paramMoves[x], gameBoard) instanceof NullPiece){
                    moves.add(i, DragonChess.arrCopy(paramMoves[x]));
                    i++;
                }
            }
            for (int x = 0; x < 2; x++) {
                if (DragonChess.onBoard(paramCapts[x])
                        && DragonChess.squareContains(paramCapts[x], gameBoard).team == oppositeTeam()){
                    moves.add(i, DragonChess.arrCopy(paramCapts[x]));
                    i++;
                }
            }
        }
        int[] param;
        if (plane == 2) {
            //Capture directly above to Ground
            param = new int[]{1, col, row};
            if (DragonChess.onBoard(param)
                    && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()){
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
        }
        if (plane == 1) {
            //Move directly down to Cave
            param = new int[]{2, col, row};
            if (DragonChess.onBoard(param)
                    && DragonChess.squareContains(param, gameBoard) instanceof NullPiece){
                moves.add(i, DragonChess.arrCopy(param));
            }
        }
        return moves;

    }

    protected double getWorth(){
        return worth;
    }

    //Used by engine when piece reaches the end of the board.
    protected void switchDirection() {
            sideWhite = !sideWhite;
    }
}
