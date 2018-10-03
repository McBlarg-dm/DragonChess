import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/22/2017.
 *
 * While on the middle board, the Hero may move or
 * capture one or two cells diagonally, jumping over
 * a piece of either color if necessary. The Hero can
 * also move or capture to the top and bottom boards
 * by making an unblockable move to a cell
 * diagonally adjacent to the cell directly above or below it.
 * While on the top or bottom board, the Hero cannot move
 * except to return to the middle board by making an
 * unblockable move or capture to a cell diagonally adjacent
 * to the cell directly above or below it as the case may be.
 */
public class Hero extends GamePiece {
    protected Hero(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 4.5;
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
        paramsDiag = new int[][]{
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        int[] param;
        //If in the sky or ground
        if (plane == 0 || plane == 2)
        {
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                param[0] = 1;
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        //If on middle
        else {
            //To prevent changes to the original 4 coords
            for (int y = 0; y < 4; y++) {
                param = DragonChess.arrCopy(paramsDiag[y]);
                for (int x = 0; x < 2 && DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team; x++) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                    param[1] += diagColGrowth[y];
                    param[2] += diagRowGrowth[y];
                }
            }
            //Moves to Sky
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                param[0] = 0;
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
            //Move to Cave
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                param[0] = 2;
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        return moves;
    }
    protected double getWorth()
    {
        return worth;
    }
}
