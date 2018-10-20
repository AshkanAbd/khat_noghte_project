package ir.ashkanabd.game;

import java.util.Scanner;

class RandomChooser {
    private Cell[][] map;

    RandomChooser(String mapToString) {
        map = buildMap(mapToString);
    }

    String choose() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[i][j].isFree())
                    return map[i][j].getX() + "-" + map[i][j].getY();
            }
        }
        return null;
    }

    private Cell[][] buildMap(String mapToString) {
        Scanner scn = new Scanner(mapToString);
        String line;
        Cell cellMap[][] = new Cell[9][9];
        scn.nextLine();
        scn.nextLine();
        for (int i = 0; i < 9; i++) {
            line = scn.nextLine();
            for (int j = 0; j < 9; j++) {
                cellMap[i][j] = new Cell(i, j);
                if (line.charAt(j) == 'A') cellMap[i][j].setMarked(1);
                if (line.charAt(j) == 'B') cellMap[i][j].setMarked(2);
                if (line.charAt(j) == '-') cellMap[i][j].setFree();
                if (line.charAt(j) == '@') cellMap[i][j].setNode();
                if (line.charAt(j) == '#') cellMap[i][j].setBlock();
            }
        }
        return cellMap;
    }
}
