package battleship.services;

import battleship.factories.ShipFactory;
import battleship.models.Battlefield;
import battleship.models.position.Pos;
import battleship.models.ship.Ship;

public class GameRuleValidator {

    public static boolean posIsOutOfBounds(Pos pos) {
        return pos.row < 0 || pos.col < 0 || pos.col > Battlefield.HEIGHT - 1;
    }

    public static boolean isDiagonal(Pos start, Pos end) {
        return start.row != end.row && start.col != end.col;
    }

    public static boolean shipsOverlap(Battlefield battlefield, Pos start, Pos end, Ship.Size shipSize) {
        Ship mockShip = ShipFactory.construct(shipSize, start, end);

        for (var ship : battlefield.getShips()) {
            if (ship == null) {
                continue;
            }
            for (var shipPos : ship.getPositions()) {
                for (var mockShipPos : mockShip.getPositions()) {
                    int rowDiff = Math.abs(mockShipPos.row - shipPos.row);
                    int colDiff = Math.abs(mockShipPos.col - shipPos.col);
                    if (rowDiff <= 1 && colDiff <= 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean enteredShipSizeIsCorrect(Pos start, Pos end, Ship.Size shipSize) {
        if (start.row == end.row) {
            return Math.abs(end.col - start.col) == shipSize.getSize() - 1;
        } else if (start.col == end.col) {
            return Math.abs(end.row - start.row) == shipSize.getSize() - 1;
        } else {
            return false;
        }
    }

    public enum ValidationError {
        SHIPS_OVERLAP("Error! You placed it too close to another one. Try again:"),
        WRONG_LOCATION("Error! Wrong ship location! Try again:"),
        WRONG_LENGTH("Error! Wrong ship length! Try again:"),
        WRONG_COORDINATES("Error! You entered wrong coordinates! Try again:");

        private final String message;

        ValidationError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
