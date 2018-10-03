import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/2/2017.
 *
 * While on the middle board, the King has the normal move and capture of a chess King
 * and may also move and capture to the cell above and below it.
 *
 * While on the top or bottom board, the ONLY thing the King may do is move or capture to
 * the cell directly above it or below it back on the middle board.
 * The King is a sitting duck when driven off the middle board.
 *
 * There is no castling move.

 8   - - - - - - - - - - - -
 7   -[K]- - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - x - - - - - -
 4   - - - - - - - - - - - -      TOP (1)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   -[x]- - - - - - - - - -
 6   - - - - x x x - - - - -
 5   - - - - x K x - - - - -
 4   - - - - x x x - -{x}- -      MIDDLE (2)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - x - - -{K}- -
 4   - - - - - - - - - - - -      BOTTOM (3)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

     a b c d e f g h i j k l
 */
public class King extends GamePiece {
    protected King(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 1000;
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
    protected ArrayList<int[]> moveList()
    {
        if (isFrozen())
            return new ArrayList<>();
        int[][] params = {
                {plane, col, row + 1},
                {plane, col + 1, row},
                {plane, col, row - 1},
                {plane, col - 1, row},
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        int[] param;
        //King movement pattern, only while on Ground
        //Return to Ground if in Sky
        param = new int[]{plane + 1, col, row};
        if (plane == 0 && DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
            moves.add(i, new int[]{plane + 1, col, row});
            i++;
        }
        //Return to Ground if in Cave
        param = new int[]{plane - 1, col, row};
        if (plane == 2 && DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
            moves.add(i, new int[]{plane - 1, col, row});
            i++;
        }
        //Moves like Chess king on the ground, and can move up/down a plane.
        if (plane == 1){
            param = new int[]{plane - 1, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, new int[]{plane - 1, col, row});
                i++;
            }
            param = new int[]{plane + 1, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, new int[]{plane + 1, col, row});
                i++;
            }
            for (int x = 0; x < 8 && DragonChess.onBoard(params[x]); x++) {
                moves.add(i, DragonChess.arrCopy(params[x]));
                i++;
            }
        }
        //Return list of moves.
        return moves;
    }
    protected double getWorth()
    {
        return worth;
    }
}

