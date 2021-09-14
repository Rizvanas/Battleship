package battleship.models.ship;

import battleship.models.position.Pos;

public abstract class Ship {
    protected boolean destroyed = false;
    protected Pos[] positions = new Pos[this.getSize()];

    public Ship(Pos start, Pos end) {
        setPositions(start, end);
    }

    public abstract int getSize();

    public Pos[] getPositions() {
        return positions;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void tryDestroying() {
        boolean allPosHit = true;
        for (var pos : positions) {
            if (pos != null && !pos.isHit()) {
                allPosHit = false;
                break;
            }
        }
        destroyed = allPosHit;
    }

    private void setPositions(Pos start, Pos end) {

        if (start.row == end.row && Math.abs(start.col - end.col) == getSize() - 1) {
            fillPositions(start, end, true);
        } else if (start.col == end.col && Math.abs(start.row - end.row) == getSize() - 1) {
            fillPositions(start, end, false);
        }
    }

    private void fillPositions(Pos start, Pos end, boolean horizontal) {
        for (int i = 0; i < getSize(); i++) {
            if (horizontal) {
                int startCol = (Math.min(start.col, end.col));
                this.positions[i] = new Pos(start.row, startCol + i);
            } else {
                int startRow = Math.min(start.row, end.row);
                this.positions[i] = new Pos(startRow + i, start.col);
            }
        }
    }

    public enum Size {
        CARRIER(5),
        BATTLESHIP(4),
        SUBMARINE(3),
        CRUISER(3),
        DESTROYER(2);

        private final int shipSize;
        Size(int shipSize) {
            this.shipSize = shipSize;
        }

        public int getSize() {
            return shipSize;
        }
    }
}
