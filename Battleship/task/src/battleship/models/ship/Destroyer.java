package battleship.models.ship;

import battleship.models.position.Pos;

public class Destroyer extends Ship {
    public final static int SIZE = Size.DESTROYER.getSize();

    public Destroyer(Pos start, Pos end) {
        super(start, end);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
