package ir.ashkanabd.network;

public class Cell {
    private boolean free;
    private boolean block;
    private boolean node;
    private int marked;
    private Integer x;
    private Integer y;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        free = false;
        block = false;
        marked = -1;
        node = false;
    }

    public Cell() {
        this.x = 0;
        this.y = 0;
        free = false;
        block = false;
        marked = -1;
        node = false;
    }

    void reset() {
        x = 0;
        y = 0;
        this.block = false;
        this.node = false;
        this.free = false;
        this.marked = -1;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isNode() {
        return node;
    }

    public int getMarked() {
        return marked;
    }

    void setBlock() {
        this.block = true;
    }

    void setX(Integer x) {
        this.x = x;
    }

    void setFree() {
        this.free = true;
    }


    void setMarked(int marked) {
        this.marked = marked;
    }

    void setNode() {
        this.node = true;
    }

    void setY(Integer y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x.hashCode() * y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell c = (Cell) obj;
            return (c.x.equals(x) && c.y.equals(y));
        }
        return false;
    }

    @Override
    public String toString() {
        return "<" + x + " , " + y + ">";
    }
}
