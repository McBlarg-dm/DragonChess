import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/20/2017.
 *
 * Dragon OP
 *
 *
 */
public class Dragon extends GamePiece
{
    protected Dragon(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        canPromote = false;
        worth = 8;
        plane = 0;
    }
    protected boolean isFrozen() {return false;}
    protected boolean canMove(String dest)
    {
        return super.canMove(dest);
    }
    protected double getWorth(){return worth;}
    /*
     * This returns a list of all valid moves from teh Dragon's location.
     *
     * There is a catch though, due to the Dragon's ranged capture to ground from Sky
     * the list includes coordinates for Ground, but these are the capture states, NOT
     * a move to that plane.
     *
     * Moves like Bishop, blockable.
     */
    protected ArrayList<int[]> moveList()
    {
        int[] param;
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        paramsDiag = new int[][]{
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        paramsOrtho = new int[][]{
                {plane, col, row + 1},
                {plane, col + 1, row},
                {plane, col, row - 1},
                {plane, col - 1, row}
        };
        //Handle the ranged capture first. 5 possibilities.
        param = new int[] {1, col, row};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam())
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        for (int x = 0; x < 4; x++) {
            param = DragonChess.arrCopy(paramsOrtho[x]);
            param[0] = 1;
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team == oppositeTeam()) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
        }
        //Handle Sky Plane Movement, 4 Orthogonal places.
        for (int x = 0; x < 4; x++) {
            param = paramsOrtho[x];
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
        }
        //Handle Diagonals
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
}
