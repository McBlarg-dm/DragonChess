import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/15/2017.
 *
 * Class for the Sylph piece.
 */
public class Sylph extends GamePiece {
    //Should starting positions for pieces be moved into DragonChess?
    protected Sylph(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        super(location, team, gameBoard);
        canPromote = false;
        worth = 1;
    }
    protected boolean isFrozen() {
        return plane == 1
                && (DragonChess.squareContains(new int[]{plane - 1, col, row}, gameBoard) instanceof Basilisk)
                && (DragonChess.squareContains(new int[]{plane - 1, col, row}, gameBoard).team == oppositeTeam());
    }
    protected boolean canMove(String dest)
    {
        return super.canMove(dest);
    }


    @Override
    protected ArrayList<int[]> moveList() {
        if (isFrozen())
            return new ArrayList<>();
        ArrayList<int[]> validMoves = new ArrayList<>();
        int i = 0;
        int[][] blackStarts = {
                {0, 0, 6},
                {0, 2, 6},
                {0, 4, 6},
                {0, 6, 6},
                {0, 8, 6},
                {0, 10, 6}
        };
        int[][] whiteStarts = {
                {0, 0, 1},
                {0, 2, 1},
                {0, 4, 1},
                {0, 6, 1},
                {0, 8, 1},
                {0, 10, 1}
        };
        //Return to the space above.
        if (DragonChess.squareContains(new int[]{0, col, row}, gameBoard) instanceof NullPiece && plane == 1) {
            validMoves.add(i, new int[]{0, col, row});
            i++;
        }
        //Return to a starting square for respective team.
        if (team == DragonChess.Team.Black)
        {
            for (int x = 0; x < 6; x++) {
                if (DragonChess.squareContains(blackStarts[x], gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(blackStarts[x]));
                    i++;
                }
            }
        }
        else
        {
            for (int x = 0; x < 6; x++) {
                if (DragonChess.squareContains(whiteStarts[x], gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(whiteStarts[x]));
                    i++;
                }
            }
        }
        //Move around Sky plane
        int[] param;
        if (plane == 0) {
            //Capture Below
            param = new int[]{1, col, row};
            if (DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            if (sideWhite) {
                //Capture ahead
                param = new int[]{0, col, row + 1};
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                //Move Diag ahead
                param = new int[]{0, col + 1, row + 1};
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                param = new int[]{0, col - 1, row + 1};
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                }
            }
            else {
                //Capture ahead
                param = new int[]{0, col, row - 1};
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                //Move Diag ahead
                param = new int[]{0, col + 1, row - 1};
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
                param = new int[]{0, col - 1, row - 1};
                if ( DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                }

            }
        }
        return validMoves;

    }

    protected double getWorth(){
        return worth;
    }
}
