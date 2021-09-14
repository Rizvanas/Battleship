package battleship.models;

public enum Event {
    SHIP_HIT("You hit a ship!"),
    SHIP_MISSED("You missed!"),
    SHIP_DESTROYED("You sank a ship!"),
    GAME_STARTED("The game starts!"),
    PLAYER_VICTORY("You sank the last ship. You won. Congratulations!");

    private String message;

    Event(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
