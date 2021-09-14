package battleship;

import battleship.models.Battlefield;
import battleship.models.Event;
import battleship.models.Game;
import battleship.models.player.Player;
import battleship.services.EventManager;
import battleship.services.InputScanner;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var eventManager = new EventManager(Event.values());
        var player1BattleField = new Battlefield(eventManager);
        var player2BattleField = new Battlefield(eventManager);
        var player1 = new Player(1, player1BattleField);
        var player2 = new Player(2, player2BattleField);
        var inputScanner = new InputScanner(scanner);

        var game = new Game(player1, player2, inputScanner);
        eventManager.subscribe(game, Event.values());

        game.run();

        scanner.close();
    }
}
