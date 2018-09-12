package ir.ashkanabd.AI;

import ir.ashkanabd.game.AI;
import ir.ashkanabd.game.Cell;

public class TeamAI extends AI {

    @Override
    public String getTeamName() {
        return "team";
    }

    @Override
    public Cell think(Cell[][] map) {
        try {
            for (int i = 0; i < this.getColNumber(); i++) {
                for (int j = 0; j < this.getRowNumber(); j++) {
                    if (map[i][j].isFree()) {
                        return map[i][j];
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
