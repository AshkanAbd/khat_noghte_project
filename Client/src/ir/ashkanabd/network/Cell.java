package ir.ashkanabd.network;

public class Cell {
    private boolean free;
    private boolean block;
    private boolean node;
    private boolean mark;
    private int marked;
    private Integer x;
    private Integer y;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        free = false;
        block = false;
        mark = false;
        node = false;
        marked = -1;
    }

    public Cell() {
        this.x = 0;
        this.y = 0;
        free = false;
        block = false;
        node = false;
        mark = false;
        marked = -1;
    }

    void reset() {
        this.block = false;
        this.node = false;
        this.free = false;
        this.mark = false;
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
        setMark();
        this.marked = marked;
    }

    void setNode() {
        this.node = true;
    }

    void setY(Integer y) {
        this.y = y;
    }

    public boolean isMark() {
        return mark;
    }

    private void setMark() {
        this.mark = true;
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
