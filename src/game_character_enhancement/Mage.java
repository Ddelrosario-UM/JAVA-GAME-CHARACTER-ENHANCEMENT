package game_character_enhancement;

public class Mage extends GameCharacter {
    private int mana;
    
    public Mage(String name) {
        super(name, 90);
        this.mana = 100;
    }

    @Override
    public String attack(GameCharacter target) {
        mana = Math.min(100, mana + 15);
        target.takeDamage(10);
        return getName() + " casts a minor fireball at " + target.getName() + " for 10 dmg! (+15 Mana)";
    }

    @Override
    public String specialMove(GameCharacter target) {
        if (mana >= 50) {
            mana -= 50;
            target.takeDamage(45);
            return getName() + " summons a [Lightning Storm] on " + target.getName() + " for 45 dmg! (-50 Mana)";
        }
        return getName() + " fumbled! Needs 50 Mana for Lightning Storm.";
    }

    @Override
    public String getStatus() {
        return "HP: " + health + "/" + maxHealth + " | Mana: " + mana;
    }

    @Override
    public void resetResources() {
        this.mana = 100;
    }
}