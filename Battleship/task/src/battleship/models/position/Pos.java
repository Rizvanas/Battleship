package battleship.models.position;

public class Pos {
    public int row;
    public int col;
    public boolean hit;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
        this.hit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Pos)) {
            return false;
        }

        Pos other = (Pos) obj;

        return row == other.row && col == other.col;
    }
}
