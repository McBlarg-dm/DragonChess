import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/2/2017.
 *
 * The Warrior has nearly the same move as the chess Pawn. It may move but not capture one cell forward. It may a capturing move one cell diagonally forward. It does NOT have the option of moving two cells on its first move.

 On reaching the opponent's baseline, the Warrior promotes to a Hero only.

 It is restricted to the middle board.

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - c m c - - - - -
 4   - - - - - W - - - - - -      MIDDLE (2)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -
     a b c d e f g h i j k l
 */
public class Warrior extends GamePiece {
    //Should starting positions for pieces be moved into DragonChess?
    protected Warrior(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        super(location, team, gameBoard);
        canPromote = true;
        worth = 1;
    }

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
        ArrayList<int[]> validMoves = new ArrayList<>();
        int i = 0;
        int[] param;
        if (sideWhite) {
            //Move forward
            param = new int[]{plane, col, row + 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Capture Diagonally ahead
            param = new int[]{plane, col + 1, row + 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{plane, col - 1, row + 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                validMoves.add(i, param);
            }
        }
        else {
            param = new int[]{plane, col, row - 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Move Diag ahead
            param = new int[]{plane, col + 1, row - 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{plane, col - 1, row - 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                validMoves.add(i, param);
            }

        }
        return validMoves;

    }

    protected double getWorth(){
        return worth;
    }
}
