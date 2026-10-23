package game_character_enhancement;

public abstract class GameCharacter {
    private String name;
    protected int maxHealth;
    protected int health;

    public GameCharacter(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public abstract String attack(GameCharacter target);
    public abstract String specialMove(GameCharacter target);
    public abstract String getStatus();
    public abstract void resetResources();

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void healToFull() {
        this.health = maxHealth;
        resetResources();
    }

    public String getName() {
        return name;
    }
}