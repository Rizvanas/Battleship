package battleship.services;

import battleship.models.Game;
import battleship.models.Battlefield;

public class GamePrinter {
    public static void printBattlefield(Game.State gameState, Battlefield battlefield) {

        printColLabels();
        for (int i = 0; i < Battlefield.HEIGHT; i++) {
            System.out.print(Battlefield.ROW_LABELS[i]);
            for (int j = 0; j < Battlefield.LENGTH; j++) {
                char currentCell = battlefield.getGrid()[i][j];
                if (gameState == Game.State.FIGHTING && currentCell == Battlefield.OCCUPIED_CELL) {
                    System.out.print(" " + Battlefield.EMPTY_CELL);
                } else {
                    System.out.print(" " + currentCell);
                }
            }
            System.out.println();
        }
    }

    public static void printBattlefield(Game.State gameState, Battlefield b1, Battlefield b2) {
        printBattlefield(gameState, b1);
        System.out.println("---------------------");
        printBattlefield(Game.State.FIGHTING_ENDED, b2);
    }

    private static void printColLabels() {
        System.out.print(" ");
        for (var label : Battlefield.COL_LABELS) {
            System.out.print(" " + label);
        }
        System.out.println();
    }

    public static void printValidationError(GameRuleValidator.ValidationError error) {
        System.out.println();
        System.out.println(error.getMessage());
        System.out.println();
    }

    public static void printMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            String command = os.contains("Windows") ? "cls" : "clear";
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
