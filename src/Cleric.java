import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/1/2017.
 *
 * The Cleric moves and captures exactly as a chess King does on whichever board it happens to be on. It may also move or capture to the cell directly above or below it.

 8   x x x - - - - - - - - -
 7   x C x - - - - - - - - -
 6   x x x - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - - - - - - - - - - - -      TOP (1)
 3   - - - - - - - - - - - -
 2   - - - -[x]- - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   - x - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - - - - - - - - - - - -      MIDDLE (2)
 3   - - -[x x x]- - - - - -
 2   - - -[x[C]x]- - - -{x}-
 1   - - -[x x x]- - - - - -

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - - - - - - - -
 4   - - - - - - - - - - - -      BOTTOM (3)
 3   - - - - - - - - -{x x x}
 2   - - - -[x]- - - -{x{C}x}
 1   - - - - - - - - -{x x x}

 a b c d e f g h i j k l
 */
public class Cleric extends GamePiece {
    protected Cleric(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 9;
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
        //King movement pattern, on every plane.
        for (int x = 0; x < 8; x++) {
            if (DragonChess.onBoard(params[x]) && DragonChess.squareContains(params[x], gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(params[x]));
                i++;
            }
        }
        //Changes planes
        param = new int[]{plane - 1, col, row};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        param = new int[]{plane + 1, col, row};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
            moves.add(i, DragonChess.arrCopy(param));
        }
        //Return list of moves.
        return moves;
    }
    protected double getWorth()
    {
        return worth;
    }
}
