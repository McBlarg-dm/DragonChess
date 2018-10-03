import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/22/2017.
 *
 * Moves exactly like a knight in Chess.
 */
public class Unicorn extends GamePiece {
    protected Unicorn(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 2.5;
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
        //Top left options
        int[] param = {1, col - 2, row + 1};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        param = new int[]{1, col - 1, row + 2};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        //Top right options
        param = new int[]{1, col + 1, row + 2};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        param = new int[]{1, col + 2, row + 1};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        //Bottom right options
        param = new int[]{1, col + 2, row - 1};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        param = new int[]{1, col + 1, row - 2};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        //Bottom left options
        param = new int[]{1, col - 1, row - 2};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
            i++;
        }
        param = new int[]{1, col - 2, row - 1};
        if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team)
        {
            moves.add(i, DragonChess.arrCopy(param));
        }
        return moves;
    }
    protected double getWorth()
    {
        return worth;
    }
}
