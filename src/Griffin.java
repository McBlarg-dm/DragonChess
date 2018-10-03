import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/20/2017.
 *
 *
 */
public class Griffin extends GamePiece
{
    protected Griffin(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        canPromote = false;
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
        ArrayList<int[]> validMoves = new ArrayList<>();
        int[] param;
        int i = 0;
        paramsDiag = new int[][]{
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        if (plane == 0)
        {
            //Moves elsewhere in Sky
            //top left options
            param = new int[]{0, col - 3, row + 2};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{0, col - 2, row + 3};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Top right options
            param = new int[]{0, col + 2, row + 3};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{0, col + 3, row + 2};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Bottom right options
            param = new int[]{0, col + 3, row - 2};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{0, col + 2, row - 3};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //Bottom left options
            param = new int[]{0, col - 2, row - 3};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{0, col - 3, row - 2};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
            {
                validMoves.add(i, DragonChess.arrCopy(param));
                i++;
            }

            //Moving out of sky plane
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        else
        {
            //Moving within Ground plane
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
            //Moving back to sky plane
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                param[0] = 0;
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    validMoves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        return validMoves;
    }
    protected double getWorth() {return worth;}
}
