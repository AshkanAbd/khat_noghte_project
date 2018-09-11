package ir.ashkanabd.network;

public abstract class AI {

    private boolean timeout = true;
    private int myID = -1;
    private int myScore = -1;
    private int oppScore = -1;
    private int oppID = -1;

    protected abstract String getTeamName();

    protected abstract Cell think(Cell[][] map);

    void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    protected int getOppScore() {
        return oppScore;
    }

    void setOppScore(int oppScore) {
        this.oppScore = oppScore;
    }

    protected int getMyScore() {
        return myScore;
    }

    void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    protected int getMyID() {
        return myID;
    }

    void setMyID(int myID) {
        this.myID = myID;
    }

    protected int getOppID() {
        return oppID;
    }

    void setOppID(int oppID) {
        this.oppID = oppID;
    }

    protected int getRowNumber() {
        return 9;
    }

    protected int getColNumber() {
        return 9;
    }

    boolean isTimeout() {
        return timeout;
    }
}
