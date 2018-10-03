import java.util.ArrayList;

/**
 * Created by mclar on 3/30/2017.
 *
 *
 */
public class NullPiece extends GamePiece {
    //Should starting positions for pieces be moved into DragonChess?
    protected NullPiece(){
        canPromote = false;
        worth = 0;
        team = DragonChess.Team.Null;
    }
    @Override
    protected boolean canMove(String dest) {
        return false;
    }
    protected boolean isFrozen() {return false;}
    protected ArrayList<int[]> moveList() {
        return null;
    }

    protected double getWorth(){
        return worth;
    }
}
