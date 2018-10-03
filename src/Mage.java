import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/1/2017.
 *
 * The Mage moves and captures exactly as a chess Queen while on the middle board
 * and may also move and capture to the cell above and below it.

 While on the top or bottom board, the Mage may only move or capture one cell forward, backward, right or left.
 It may also move or capture one or two cells straight up or down. It may NOT leap over a piece on the middle board while
 going from top to bottom or vice versa.

 8   -[x]- - - - - - - - - -
 7  [x[M]x]- - - - - - - - -
 6   -[x]- - - - - - - - - -
 5   - - - - - x - - - - - -
 4   - - - - - - - - - - - -      TOP (1)
 3   - - - - - - - - - - - -
 2   - - - - - - - - - -{x}-
 1   - - - - - - - - - - - -

 8   - - x - - x - - x - - -
 7   -[x]- x - x - x - - - -
 6   - - - - x x x - - - - -
 5   x x x x x M x x x x x x
 4   - - - - x x x - - - - -      MIDDLE (2)
 3   - - - x - x - x - - - -
 2   - - x - - x - - x -{x}-
 1   - x - - - x - - - x - -

 8   - - - - - - - - - - - -
 7   -[x]- - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - - - - x - - - - - -
 4   - - - - - - - - - - - -      BOTTOM (3)
 3   - - - - - - - - - -{x}-
 2   - - - - - - - - -{x{M}x}
 1   - - - - - - - - - -{x}-

 a b c d e f g h i j k l
 */
public class Mage extends GamePiece {
    protected Mage(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 11;
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
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        if (plane == 1) {
            //Orthogonal Movement
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
            //Diagonal Movement
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
            //Movement to other planes
            param = new int[]{0, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            param = new int[]{2, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
        }
        //Handle choices for Sky or Cave
        else {
            //Movement on that plane
            for (int x = 0; x < 4; x++) {
                param = paramsOrtho[x];
                if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
            //Moving to other planes
            param = new int[]{1, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //If in Cave, check Sky cell
            param = new int[]{0, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team
                    && plane == 2) {
                moves.add(i, DragonChess.arrCopy(param));
                i++;
            }
            //If in Sky, check Cave cell
            param = new int[]{2, col, row};
            if (DragonChess.onBoard(param) && DragonChess.squareContains(param, gameBoard).team != team
                    && plane == 0) {
                moves.add(i, DragonChess.arrCopy(param));
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
