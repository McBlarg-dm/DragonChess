import java.util.ArrayList;

/**
 * Created by Shokhenah on 2/8/2017.
 *
 * Abstract class for Game Pieces.
 */
public abstract class GamePiece implements Cloneable{
    protected GamePiece(int[] location, DragonChess.Team team, GamePiece[][][] gameBoard){
        this.gameBoard = gameBoard;
        this.team = team;
        plane = location[0];
        col = location[1];
        row = location[2];
        if (team == DragonChess.Team.White)
            sideWhite = true;
        else
            sideWhite = false;
    }
    protected GamePiece(){}
    protected boolean canPromote = false;
    protected boolean sideWhite;
    protected DragonChess.Team team;
    protected GamePiece[][][] gameBoard;
    protected double worth;
    protected int plane;
    protected int col;
    protected int row;
    protected int[] diagRowGrowth = {1, -1, -1, 1};
    protected int[] diagColGrowth = {1, 1, -1, -1};
    protected int[] orthoRowGrowth = {1, 0, -1, 0};
    protected int[] orthoColGrowth = {0, 1, 0, -1};
    protected int[][] paramsOrtho;
    protected int[][] paramsDiag;

    protected abstract ArrayList<int[]> moveList();
    protected abstract boolean isFrozen();
    protected boolean canMove(String dest){
        int destPlane = DragonChess.convertPlanetoCoord(dest.charAt(0));
        int destCol = DragonChess.convertColtoCoord(dest.charAt(1));
        int destRow = DragonChess.convertRowtoCoord(dest.charAt(2));
        //Setup some variables
        boolean containsMove = false;
        int[] destCoord = {destPlane, destCol, destRow};
        ArrayList<int[]> moves = moveList();
        // Check if the destination is in the set returned by moveList.
        for (int[] i: moves){
            if (i[0] == destCoord[0] && i[1] == destCoord[1] && i[2] == destCoord[2])
                containsMove = true;
        }
        return containsMove;
    }
    protected double getWorth(){
        return worth;
    }
    protected DragonChess.Team oppositeTeam() {
        if (team == DragonChess.Team.White)
            return DragonChess.Team.Black;
        else
            return DragonChess.Team.White;
    }
}
