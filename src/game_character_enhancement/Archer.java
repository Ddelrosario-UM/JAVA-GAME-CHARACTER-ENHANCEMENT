package game_character_enhancement;

public class Archer extends GameCharacter {
    private int focus;
    
    public Archer(String name) {
        super(name, 110);
        this.focus = 10;
    }

    @Override
    public String attack(GameCharacter target) {
        focus = Math.min(100, focus + 25);
        target.takeDamage(15);
        return getName() + " shoots a basic bow attack at " + target.getName() + " for 15 dmg! (+25 Focus)";
    }

    @Override
    public String specialMove(GameCharacter target) {
        if (focus >= 50) {
            focus -= 50;
            target.takeDamage(38);
            return getName() + " channels a [Wind Arrow] at " + target.getName() + " for 38 dmg! (-50 Focus)";
        }
        return getName() + " fumbled! Needs 50 Focus for Wind Arrow.";
    }

    @Override
    public String getStatus() {
        return "HP: " + health + "/" + maxHealth + " | Focus: " + focus;
    }

    @Override
    public void resetResources() {
        this.focus = 10;
    }
}