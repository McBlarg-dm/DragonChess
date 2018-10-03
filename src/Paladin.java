import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/2/2017.
 *
 * The Paladin may move and capture as a chess King or Knight while on the middle board.
 * It moves and captures as a chess King while on the top or bottom boards.
 * It may move or capture from board to board by making a 3d Knight's move -- two cells
 * in any direction and then one cell in a perpendicular direction. It's move is unblockable.

 8   - - - - - - - - - - - -
 7   -[x x x]- - - - -{x}- -
 6   -[x[P]x]- - - -{x}-{x}-
 5   -[x x x]- x - - -{x}- -
 4   - - - - - - - - - - - -      TOP (1)
 3   - - - x - - - x - - - -
 2   - - - - - - - - - - - -
 1   - - - - - x - - - - - -

 8   - -[x]- - - - - -{x}- -
 7   - - - - - - - - - - - -
 6  [x]- - -[x]- -{x}- - -{x}
 5   - - - - x - x - - - - -
 4   - -[x]x x x x x -{x}- -      MIDDLE (2)
 3   - - - - x P x - - - - -
 2   - - - x x x x x - - - -
 1   - - - - x - x - - - - -

 8   - - - - - - - - - - - -
 7   - -[x]- - - - -{x x x}-
 6   -[x]-[x]- - - -{x{P}x}-
 5   - -[x]- - x - -{x x x}-
 4   - - - - - - - - - - - -      BOTTOM (3)
 3   - - - x - - - x - - - -
 2   - - - - - - - - - - - -
 1   - - - - - x - - - - - -

     a b c d e f g h i j k l
 */
public class Paladin extends GamePiece {
    protected Paladin(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard)
    {
        super(location, team, gameBoard);
        worth = 10;
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
        int[][] paramsKing = {
                {plane, col, row + 1},
                {plane, col + 1, row},
                {plane, col, row - 1},
                {plane, col - 1, row},
                {plane, col + 1, row + 1},
                {plane, col + 1, row - 1},
                {plane, col - 1, row - 1},
                {plane, col - 1, row + 1}
        };
        int[][] paramsPlanes = new int[][] {
                {plane - 1, col, row + 2},
                {plane - 1, col + 2, row},
                {plane - 1, col, row - 2},
                {plane - 1, col - 2, row},
                {plane + 1, col, row + 2},
                {plane + 1, col + 2, row},
                {plane + 1, col, row - 2},
                {plane + 1, col - 2, row}
        };
        int[][] paramsKnight = new int[][] {
                {1, col - 2, row + 1},
                {1, col - 1, row + 2},
                {1, col + 1, row + 2},
                {1, col + 2, row + 1},
                {1, col + 2, row - 1},
                {1, col + 1, row - 2},
                {1, col - 1, row - 2},
                {1, col - 2, row - 1}
        };
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
        int[] param;
        if (plane == 1) {
            //Handle adjacent squares
            for (int x = 0; x < 8; x++) {
                if (DragonChess.onBoard(paramsKing[x])
                        && DragonChess.squareContains(paramsKing[x], gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(paramsKing[x]));
                    i++;
                }
            }
            //Handle the Knight's style movement
            for (int x = 0; x < 8; x++) {
                if (DragonChess.onBoard(paramsKnight[x])
                        && DragonChess.squareContains(paramsKnight[x], gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(paramsKnight[x]));
                    i++;
                }
            }

            //Handle planes change, both at once.
            for (int x = 0; x < 8; x++) {
                if (DragonChess.onBoard(paramsPlanes[x])
                        && DragonChess.squareContains(paramsPlanes[x], gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(paramsPlanes[x]));
                    i++;
                }
            }

        }
        if (plane == 2) {
            //Moving to Ground - uses second half of array
            for (int x = 4; x < 8; x++) {
                if (DragonChess.onBoard(paramsPlanes[x])
                        && DragonChess.squareContains(paramsPlanes[x], gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(paramsPlanes[x]));
                    i++;
                }
            }
            //Move to Sky
            for (int x = 0; x < 4; x++) {
                param = paramsOrtho[x]; //Set the plane to check to Sky
                param[0] = 0;
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        if (plane == 0){
            //Moving to Ground - uses first half of array
            for (int x = 0; x < 4; x++) {
                if (DragonChess.onBoard(paramsPlanes[x])
                        && DragonChess.squareContains(paramsPlanes[x], gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(paramsPlanes[x]));
                    i++;
                }
            }
            //Move to Cave
            for (int x = 0; x < 4; x++) {
                param = paramsOrtho[x]; //Set the plane to check to Sky
                param[0] = 2;
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
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
