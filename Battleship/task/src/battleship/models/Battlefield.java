package battleship.models;

import battleship.services.EventManager;
import battleship.factories.ShipFactory;
import battleship.models.position.Pos;
import battleship.models.ship.Ship;

public class Battlefield {
    public static final byte LENGTH = 10;
    public static final byte HEIGHT = 10;
    public static final byte SHIP_COUNT = 5;
    public static final char EMPTY_CELL = '~';
    public static final char OCCUPIED_CELL = 'O';
    public static final char DESTROYED_CELL = 'X';
    public static final char MISSED_CELL = 'M';
    public static final char[] ROW_LABELS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    public static final int[] COL_LABELS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private final char[][] grid;
    private Ship[] ships;
    private final EventManager eventManager;

    public Battlefield(EventManager eventManager) {
        this.eventManager = eventManager;
        this.grid = new char[HEIGHT][LENGTH];
        this.ships = new Ship[SHIP_COUNT];
        this.initializeGrid();
    }

    public char[][] getGrid() {
        return grid;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void initializeGrid() {
        for (byte i = 0; i < HEIGHT; i++) {
            for (byte j = 0; j < LENGTH; j++) {
                grid[i][j] = EMPTY_CELL;
            }
        }
    }

    public void reset() {
        initializeGrid();
        this.ships = new Ship[SHIP_COUNT];
    }

    public void placeShip(Ship.Size eShip, Pos start, Pos end) {
        Ship ship = ShipFactory.construct(eShip, start, end);
        for (int i = 0; i < ships.length; i++) {
            if (ships[i] == null) {
                ships[i] = ship;
                break;
            }
        }

        for (var shipPos : ship.getPositions()) {
            grid[shipPos.row][shipPos.col] = OCCUPIED_CELL;
        }
    }

    public void takeAShot(Pos shotPos) {
        boolean shipWasHit = false;
        for (var ship : ships) {
            if (ship != null) {
                for (var shipPos : ship.getPositions()) {
                    if (shipPos.equals(shotPos)) {
                        shipPos.setHit(true);
                        shipWasHit = true;
                        grid[shotPos.row][shotPos.col] = DESTROYED_CELL;
                        ship.tryDestroying();
                        break;
                    }
                }
            }
            if (shipWasHit) {
                if (ship.isDestroyed()) {
                    if (allShipsDestroyed()) {
                        eventManager.notify(Event.PLAYER_VICTORY);
                    } else {
                        eventManager.notify(Event.SHIP_DESTROYED);
                    }
                } else {
                    eventManager.notify(Event.SHIP_HIT);
                }
                break;
            }
        }
        if (!shipWasHit) {
            grid[shotPos.row][shotPos.col] = MISSED_CELL;
            eventManager.notify(Event.SHIP_MISSED);

        }
    }

    public boolean allShipsDestroyed() {
        for (var ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}
