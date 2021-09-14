package battleship.models.player;

import battleship.models.Battlefield;

public class Player {
    private final int id;
    private final String name;
    private final Battlefield battlefield;

    public Player(int id, Battlefield battlefield) {
        this.id = id;
        this.name = "Player " + id;
        this.battlefield = battlefield;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void reset() {
        battlefield.reset();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Player)) {
            return false;
        }

        Player other = (Player) obj;

        return this.id == other.id;
    }
}
