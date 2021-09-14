package battleship.models.ship;

import battleship.models.position.Pos;

public class Battleship extends Ship {

    public final static int SIZE = Size.BATTLESHIP.getSize();

    public Battleship(Pos start, Pos end) {
        super(start, end);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
