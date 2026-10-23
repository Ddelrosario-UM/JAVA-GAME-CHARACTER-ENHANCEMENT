package game_character_enhancement;

public class Ninja extends GameCharacter {
    private int shadowEnergy;
    
    public Ninja(String name) {
        super(name, 100);
        this.shadowEnergy = 30;
    }

    @Override
    public String attack(GameCharacter target) {
        shadowEnergy = Math.min(100, shadowEnergy + 30);
        target.takeDamage(18);
        return getName() + " executes a backstab on " + target.getName() + " for 18 dmg! (+30 Energy)";
    }

    @Override
    public String specialMove(GameCharacter target) {
        if (shadowEnergy >= 60) {
            shadowEnergy -= 60;
            target.takeDamage(42);
            return getName() + " strikes with [Shadow Attack] on " + target.getName() + " for 42 dmg! (-60 Energy)";
        }
        return getName() + " fumbled! Needs 60 Energy for Shadow Attack.";
    }

    @Override
    public String getStatus() {
        return "HP: " + health + "/" + maxHealth + " | Energy: " + shadowEnergy;
    }

    @Override
    public void resetResources() {
        this.shadowEnergy = 30;
    }
}