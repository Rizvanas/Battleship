package battleship.models;

import battleship.services.EventListener;
import battleship.models.player.Player;
import battleship.models.position.Pos;
import battleship.models.ship.Ship;
import battleship.services.GamePrinter;
import battleship.services.InputScanner;

public class Game implements EventListener {
    private State state;
    private final InputScanner inputScanner;
    private Event lastEvent;
    private final Player player1;
    private final Player player2;

    public Game(Player player1, Player player2, InputScanner inputScanner) {
        this.inputScanner = inputScanner;
        this.player1 = player1;
        this.player2 = player2;
        this.lastEvent = Event.GAME_STARTED;
        state = State.PLACEMENT;
    }

    public void run() {
        player1.reset();
        player2.reset();

        Player currentPlayer = player1;
        for  (int i = 0; i < 2; i++) {
            System.out.println(currentPlayer.getName() + ", place your ships on the game field");
            System.out.println();
            placeShips(currentPlayer);
            inputScanner.passTheMove();
            GamePrinter.clearConsole();
            currentPlayer = getOtherPlayer(currentPlayer);
        }

        fight(currentPlayer);
    }

    private void placeShips(Player player) {
        this.state = State.PLACEMENT;
        for (var shipSize : Ship.Size.values()) {
            GamePrinter.printBattlefield(this.state, player.getBattlefield());
            Pos[] shipPos = inputScanner.getShipPlacementPos(shipSize, player.getBattlefield());
            player.getBattlefield().placeShip(shipSize, shipPos[0], shipPos[1]);
            System.out.println();
        }

        GamePrinter.printBattlefield(this.state, player.getBattlefield());
        System.out.println();
    }

    private void fight(Player currentPlayer) {
        this.state = State.FIGHTING;

        while(this.state == State.FIGHTING) {
            GamePrinter.printBattlefield(this.state,
                    getOtherPlayer(currentPlayer).getBattlefield(),
                    currentPlayer.getBattlefield());

            System.out.println();
            System.out.println(currentPlayer.getName() + ", it's your turn:");
            System.out.println();

            getOtherPlayer(currentPlayer)
                    .getBattlefield()
                    .takeAShot(inputScanner.getShotPos());

            processLastEvent();
            inputScanner.passTheMove();
            System.out.println();
            GamePrinter.clearConsole();

            currentPlayer = getOtherPlayer(currentPlayer);
        }
    }

    public void processLastEvent() {
        switch (lastEvent) {
            case SHIP_HIT:
            case SHIP_MISSED:
            case SHIP_DESTROYED:
                GamePrinter.printMessage(lastEvent.getMessage());
                break;
            case PLAYER_VICTORY:
                this.state = State.FIGHTING_ENDED;
                GamePrinter.printMessage(lastEvent.getMessage());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Event event) {
        this.lastEvent = event;
    }

    private Player getOtherPlayer(Player player) {
        return player.equals(player1) ? this.player2 : this.player1;
    }

    public enum State {
        PLACEMENT,
        FIGHTING,
        FIGHTING_ENDED,
        GAME_ENDED
    }
}
