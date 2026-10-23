# ⚔️ RPG Battle Arena: Java Edition

This project evolves a standard Java character roster into a fully interactive, turn-based combat simulator. Originally starting as a basic lesson on Data Abstraction, the application now serves as a dynamic arena where players can register fighters, pick their classes, and clash in a custom graphical interface.

The goal was to blend essential programming concepts—like custom data structures and object-oriented design—with an engaging, playable experience.

## What's Inside? (Core Features)

*   **Turn-Based Combat System:** Instead of just printing text to a console, you can now pit two fighters against each other. Players take turns choosing between basic attacks or heavy-hitting special moves until one combatant's health drops to zero.
*   **Four Distinct Classes:** 
    *   **Warrior:** Relies on building up *Stamina* to unleash a devastating Shield Bash.
    *   **Mage:** Starts with full *Mana* for early burst damage via Lightning Storm, but needs to manage it carefully.
    *   **Archer:** Generates *Focus* with standard bow attacks to eventually fire a piercing Wind Arrow.
    *   **Ninja:** A fast assassin that gathers *Shadow Energy* to execute a massive backstab combo.
*   **Persistent Roster & Match History:** Registered fighters are stored in the guild roster permanently. After a match concludes, the arena records the latest winner and loser. Fighters are automatically healed and can be selected for a rematch anytime.
*   **Smart Validation:** The system prevents you from registering duplicate names, using invalid characters, or trying to make a fighter battle themselves. 

## Under the Hood (Technical Details)

While the game is fun to play, the architecture behind it fulfills specific academic and structural requirements:

*   **Abstraction in Action:** An abstract `GameCharacter` class acts as the blueprint. It handles shared logic (like taking damage and tracking health) while forcing every specific class to define their own unique combat and resource mechanics.
*   **Custom Abstract Data Type (ADT):** You won't find standard Java `ArrayLists` managing the roster here. The application stores players using a custom-built Singly Linked List, demonstrating how data flows and connects from scratch.
*   **Polished Swing GUI:** The interface was built using Java Swing, featuring a dark-mode theme, color-coded status updates, a live combat log, and hover animations to make the battle experience feel responsive.

## How to Play

1.  **Compile and Run:** Launch the program by running the `Main.java` file.
2.  **Register Your Fighters:** On the left side of the screen, type in a name, pick a class from the dropdown, and click "Register Fighter". You need at least two combatants to start.
3.  **Setup the Match:** Select your two fighters from the Player 1 and Player 2 dropdowns.
4.  **Fight!** Hit "Start Battle!" to enter the arena. Use the action buttons at the bottom right to trade blows and watch the combat log until a victor emerges.