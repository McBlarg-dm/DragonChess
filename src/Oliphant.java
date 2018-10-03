import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/22/2017.
 *
 * Moves and captures exactly like a rook in chess.
 */
public class Oliphant extends GamePiece
{
    protected Oliphant(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 5;
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
        int[] param;
        paramsOrtho = new int[][]{
                {plane, col, row + 1},
                {plane, col + 1, row},
                {plane, col, row - 1},
                {plane, col - 1, row}
        };
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        //Controls direction
        for (int x = 0; x < 4; x++) {
            param = DragonChess.arrCopy(paramsOrtho[x]);
            while (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
                if (DragonChess.squareContains(param, gameBoard).team == oppositeTeam())
                    break;
                param[1] += orthoColGrowth[x];
                param[2] += orthoRowGrowth[x];
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
