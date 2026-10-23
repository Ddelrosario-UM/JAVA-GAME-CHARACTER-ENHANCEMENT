package game_character_enhancement;

public class Warrior extends GameCharacter {
    private int stamina;
    
    public Warrior(String name) {
        super(name, 150);
        this.stamina = 20; 
    }

    @Override
    public String attack(GameCharacter target) {
        stamina = Math.min(100, stamina + 20);
        target.takeDamage(12);
        return getName() + " swings a mighty sword at " + target.getName() + " for 12 dmg! (+20 Stamina)";
    }

    @Override
    public String specialMove(GameCharacter target) {
        if (stamina >= 40) {
            stamina -= 40;
            target.takeDamage(35);
            return getName() + " uses [Shield Bash] on " + target.getName() + " for 35 dmg! (-40 Stamina)";
        }
        return getName() + " fumbled! Needs 40 Stamina for Shield Bash.";
    }

    @Override
    public String getStatus() {
        return "HP: " + health + "/" + maxHealth + " | Stamina: " + stamina;
    }

    @Override
    public void resetResources() {
        this.stamina = 20;
    }
}