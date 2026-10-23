package game_character_enhancement;

public interface CharacterADT {
    void addCharacter(GameCharacter c);
    boolean removeCharacter(String name);
    GameCharacter searchCharacter(String name);
    GameCharacter[] getAll();
}