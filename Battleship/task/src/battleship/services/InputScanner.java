package battleship.services;

import battleship.models.Battlefield;
import battleship.models.position.Pos;
import battleship.models.ship.Ship;

import java.util.Arrays;
import java.util.Scanner;

public class InputScanner {
    private Scanner scanner;

    public InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Pos[] getShipPlacementPos(Ship.Size shipSize, Battlefield battlefield) {
        GamePrinter.printMessage(Message.deriveMessage(shipSize));

        while (true) {
            String[] coords = scanner.nextLine().trim().split(" ");

            int startRow = parseRow(coords[0].charAt(0));
            int startCol = parseCol(coords[0].substring(1));
            int endRow = parseRow(coords[1].charAt(0));
            int endCol = parseCol(coords[1].substring(1));

            Pos startPos = new Pos(startRow, startCol);
            Pos endPos = new Pos(endRow, endCol);

            if (GameRuleValidator.posIsOutOfBounds(startPos) ||
                    GameRuleValidator.posIsOutOfBounds(endPos) ||
                    GameRuleValidator.isDiagonal(startPos, endPos)
            ) {
                GamePrinter.printValidationError(GameRuleValidator.ValidationError.WRONG_LOCATION);
                continue;
            }

            if (!GameRuleValidator.enteredShipSizeIsCorrect(startPos, endPos, shipSize)) {
                GamePrinter.printValidationError(GameRuleValidator.ValidationError.WRONG_LENGTH);
                continue;
            }

            if (GameRuleValidator.shipsOverlap(battlefield, startPos, endPos, shipSize)) {
                GamePrinter.printValidationError(GameRuleValidator.ValidationError.SHIPS_OVERLAP);
                continue;
            }

            return new Pos[]{startPos, endPos};
        }
    }

    public Pos getShotPos() {
        while (true) {
            String coordinate = scanner.next();
            int row = parseRow(coordinate.charAt(0));
            int col = parseCol(coordinate.substring(1));
            Pos shotPos = new Pos(row, col);

            if (GameRuleValidator.posIsOutOfBounds(shotPos)) {
                GamePrinter.printValidationError(GameRuleValidator.ValidationError.WRONG_COORDINATES);
                continue;
            }

            return shotPos;
        }
    }

    public void passTheMove() {
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private int parseRow(char row) {
        return Arrays.binarySearch(Battlefield.ROW_LABELS, row);
    }

    private int parseCol(String col) {
        return Integer.parseInt(col) - 1;
    }

    public enum Message {
        TAKE_A_SHOT("Take a shot!"),
        FOR_CARRIER("Enter the coordinates of the Aircraft Carrier (5 cells):"),
        FOR_BATTLESHIP("Enter the coordinates of the Battleship (4 cells):"),
        FOR_SUBMARINE("Enter the coordinates of the Submarine (3 cells):"),
        FOR_CRUISER("Enter the coordinates of the Cruiser (3 cells):"),
        FOR_DESTROYER("Enter the coordinates of the Destroyer (2 cells):");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public static String deriveMessage(Ship.Size size) {
            switch (size) {
                case CARRIER:
                    return FOR_CARRIER.message;
                case BATTLESHIP:
                    return FOR_BATTLESHIP.message;
                case SUBMARINE:
                    return FOR_SUBMARINE.message;
                case CRUISER:
                    return FOR_CRUISER.message;
                case DESTROYER:
                    return FOR_DESTROYER.message;
                default:
                    throw new IllegalArgumentException("No such Ship.Size exists.");
            }
        }

        public String getMessage() {
            return message;
        }
    }
}
