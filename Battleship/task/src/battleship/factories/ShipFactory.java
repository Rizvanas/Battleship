package battleship.factories;

import battleship.models.position.Pos;
import battleship.models.ship.*;

public class ShipFactory {
    public static Ship construct(Ship.Size ship, Pos start, Pos end) {
        switch (ship) {
            case DESTROYER:
                return new Destroyer(start, end);
            case CRUISER:
                return new Cruiser(start, end);
            case SUBMARINE:
                return new Submarine(start, end);
            case BATTLESHIP:
                return new Battleship(start, end);
            case CARRIER:
                return new Carrier(start, end);
            default:
                throw new IllegalArgumentException("No such ShipEnum value exists");
        }
    }
}
