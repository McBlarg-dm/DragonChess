import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/1/2017.
 *
 * Moves and captures exactly like a Bishop in chess. It never leaves the middle board.
 *
 * 8   - x - - - - - - - x - -
 * 7   - - x - - - - - x - - -
 * 6   - - - x - - - x - - - -
 * 5   - - - - x - x - - - - -
 * 4   - - - - - T - - - - - -      MIDDLE (2)
 * 3   - - - - x - x - - - - -
 * 2   - - - x - - - x - - - -
 * 1   - - x - - - - - x - - -
 *
 *     a b c d e f g h i j k l
 */
public class Thief extends GamePiece {
    protected Thief(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 4;
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
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        int[] param;
        paramsDiag = new int[][]{
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        //Controls direction
        for (int x = 0; x < 4; x++) {
            param = DragonChess.arrCopy(paramsDiag[x]);
            while (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
                if (DragonChess.squareContains(param, gameBoard).team == oppositeTeam())
                    break;
                param[1] += diagColGrowth[x];
                param[2] += diagRowGrowth[x];
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
