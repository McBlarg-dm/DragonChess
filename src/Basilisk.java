import java.util.ArrayList;


/**
 * Created by Shokhenah on 3/2/2017.
 *
 * The Basilisk may move or capture one cell directly or diagonally forward
 * and may also move without capturing one cell straight backwards.
 *
 * Though the Basilisk is restricted to the bottom board, it has the power
 * to freeze any opposing piece on the cell directly above it on the middle board.
 * Freezing is automatic whether an opposing piece moves over the Basilisk or the
 Basilisk moves under the opposing piece.
 Freezing is temporary -- once the Basilisk moves away or is captured,
 the frozen piece regains its normal powers.

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - - - - - f - - - - - -      MIDDLE (2)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - x x x - - - - -
 4   - - - - - B - - - - - -      BOTTOM (3)
 3   - - - - - m - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

     a b c d e f g h i j k l
 */
public class Basilisk extends GamePiece {
    //Should starting positions for pieces be moved into DragonChess?
    protected Basilisk(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        super(location, team, gameBoard);
        canPromote = false;
        worth = 3;
    }
    @Override
    protected boolean canMove(String dest)
    {
        return super.canMove(dest);
    }


    @Override
    protected ArrayList<int[]> moveList() {
        ArrayList<int[]> validMoves = new ArrayList<>();
        int i = 0;
        int[] param = new int[]{plane, col, row - 1};
        if (sideWhite) {
            //Move backwards
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Capture ahead
            param = new int[]{plane, col - 1, row + 1};
            for (int x = 0; x < 3; x++) {
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                param[1]++;
            }
        }
        else {
            //Move backwards
            param = new int[]{plane, col - 1, row + 1};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Capture ahead
            param = new int[]{plane, col - 1, row - 1};
            for (int x = 0; x < 3; x++) {
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                param[1]++;
            }
        }
        return validMoves;

    }

    protected double getWorth(){
        return worth;
    }
    protected boolean isFrozen() {
        return false;
    }
}

