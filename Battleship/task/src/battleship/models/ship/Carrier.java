package battleship.models.ship;

import battleship.models.position.Pos;

public class Carrier extends Ship {

    public final static int SIZE = Size.CARRIER.getSize();

    public Carrier(Pos start, Pos end) {
        super(start, end);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
