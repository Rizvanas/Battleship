package battleship.models.ship;

import battleship.models.position.Pos;

public class Cruiser extends Ship {
    public final static int SIZE = Size.CRUISER.getSize();

    public Cruiser(Pos start, Pos end) {
        super(start, end);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
