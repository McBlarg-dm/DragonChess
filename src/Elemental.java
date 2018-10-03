import java.util.ArrayList;

/**
 * Created by Shokhenah on 3/2/2017.
 *
 * While on the lower board, the Elemental may move or capture one or two cells orthogonally.
 * (It may NOT leap over occupied cells.) It may also move but not capture one cell diagonally.

 It may also make a capturing move to the middle board by moving one cell orthogonally
 and then one cell up. The intermediate cell MUST be empty.

 The Elemental may not move while on the middle board except to return to
 the bottom by moving or capturing one cell down and then one cell orthogonally.
 The intermediate cell MUST be empty.

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - - - - - - - - - - -
 5   - - x - - - - - - - - -
 4   - x - x - - - - -[E]- -      MIDDLE (2)
 3   - - x - - - - - - - - -
 2   - - - - - - - - - - - -
 1   - - - - - - - - - - - -

 8   - - - - - - - - - - - -
 7   - - - - - - - - - - - -
 6   - - x - - - - - - - - -
 5   - m x m - - - - -[x]- -
 4   x x E x x - - -[x]-[x]-      BOTTOM (3)
 3   - m x m - - - - -[x]- -
 2   - - x - - - - - - - - -
 1   - - - - - - - - - - - -

     a b c d e f g h i j k l
 */
public class Elemental extends GamePiece{
    //Should starting positions for pieces be moved into DragonChess?
    protected Elemental(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        super(location, team, gameBoard);
        canPromote = false;
        worth = 3;
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

    protected ArrayList<int[]> moveList() {
        //Take special not that intermediate cells must be empty.
        if (isFrozen())
            return new ArrayList<>();
        ArrayList<int[]> moves = new ArrayList<>();
        int i = 0;
        int[] param;
        if (plane == 2) {
            paramsOrtho = new int[][]{
                    {plane, col, row + 1},
                    {plane, col + 1, row},
                    {plane, col, row - 1},
                    {plane, col - 1, row}
            };
            paramsDiag = new int[][]{
                    {plane, col + 1, row + 1},
                    {plane, col + 1, row - 1},
                    {plane, col - 1, row - 1},
                    {plane, col - 1, row + 1}
            };
            //Handle the Orthogonal Captures/moves
            for (int y = 0; y < 4; y++) {
                param = DragonChess.arrCopy(paramsOrtho[y]);
                for (int x = 0; x < 2 && DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team; x++) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                    if (DragonChess.squareContains(param, gameBoard).team == oppositeTeam())
                        break;
                    param[1] += diagColGrowth[y];
                    param[2] += diagRowGrowth[y];
                }
            }
            //Handle the diagonal moves
            for (int x = 0; x < 4; x++) {
                param = DragonChess.arrCopy(paramsDiag[x]);
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard) instanceof NullPiece) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
            //Handle Move to Ground. BLOCKABLE
            boolean blocked;
            param = new int[] {plane - 1, col, row};
            blocked = !(DragonChess.onBoard(param)
                    && DragonChess.squareContains(param, gameBoard) instanceof NullPiece);
            for (int x = 0; x < 4 && !blocked; x++) {
                param = DragonChess.arrCopy(paramsOrtho[x]);
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        else {
            //Handle Move to Ground. BLOCKABLE
            boolean blocked;
            param = new int[] {plane + 1, col, row};
            blocked = !(DragonChess.onBoard(param)
                    && DragonChess.squareContains(param, gameBoard) instanceof NullPiece);
            for (int x = 0; x < 4 && !blocked; x++) {
                param = DragonChess.arrCopy(paramsOrtho[x]);
                if (DragonChess.onBoard(param)
                        && DragonChess.squareContains(param, gameBoard).team != team) {
                    moves.add(i, DragonChess.arrCopy(param));
                    i++;
                }
            }
        }
        return moves;

    }

    protected double getWorth(){
        return worth;
    }
}