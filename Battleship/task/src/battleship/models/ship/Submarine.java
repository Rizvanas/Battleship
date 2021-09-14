package battleship.models.ship;

import battleship.models.position.Pos;

public class Submarine extends Ship {
    public final static int SIZE = Size.SUBMARINE.getSize();

    public Submarine(Pos start, Pos end) {
        super(start, end);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
