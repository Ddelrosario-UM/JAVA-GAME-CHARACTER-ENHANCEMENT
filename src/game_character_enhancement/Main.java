package game_character_enhancement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    
    private CharacterADT roster;
    private JTextField nameField;
    private JComboBox<String> createClassDropdown;
    private JComboBox<String> p1Dropdown;
    private JComboBox<String> p2Dropdown;
    
    // Battle Variables
    private GameCharacter player1;
    private GameCharacter player2;
    private boolean isP1Turn = true;
    private boolean battleActive = false;

    // UI Components
    private JTextArea combatLog;
    private JLabel lblP1Status;
    private JLabel lblP2Status;
    private JLabel lblTurnIndicator;
    private JLabel lblMatchRecord; // Tracks Winner/Loser
    private JButton btnBasic;
    private JButton btnSpecial;

    // Theme Colors
    private final Color bgDark = new Color(30, 34, 42);
    private final Color bgPanel = new Color(40, 44, 52);
    private final Color textLight = new Color(220, 223, 228);
    private final Color accentColor = new Color(97, 175, 239);
    private final Color p1Color = new Color(152, 195, 121);
    private final Color p2Color = new Color(224, 108, 117);
    private final Color goldColor = new Color(229, 192, 123);

    public Main() {
        roster = new CharacterLinkedList();
        
        setTitle("RPG Battle Arena");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(bgDark);

        JLabel header = new JLabel("⚔️ Java Battle Arena ⚔️", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(accentColor);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(header, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(setupLeftPanel());
        splitPane.setRightComponent(setupRightPanel());
        splitPane.setDividerLocation(320);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        leftPanel.setBackground(bgDark);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 10));

        // 1. Creation Panel
        JPanel createPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        createPanel.setBackground(bgPanel);
        createPanel.setBorder(createTitledBorder(" 1. Register Player "));

        nameField = new JTextField();
        createClassDropdown = new JComboBox<>(new String[]{"Warrior", "Mage", "Archer", "Ninja"});
        JButton btnCreate = styleButton("Register Fighter");

        createPanel.add(new JLabel(" Player Name:") {{ setForeground(textLight); }});
        createPanel.add(nameField);
        createPanel.add(createClassDropdown);
        createPanel.add(btnCreate);

        btnCreate.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty() || !name.matches("^[a-zA-Z0-9]{3,12}$")) {
                JOptionPane.showMessageDialog(this, "Name must be 3-12 alphanumeric characters.");
                return;
            }
            if (roster.searchCharacter(name) != null) {
                JOptionPane.showMessageDialog(this, "Player name is already registered!");
                return;
            }
            String cls = (String) createClassDropdown.getSelectedItem();
            switch (cls) {
                case "Warrior" -> roster.addCharacter(new Warrior(name));
                case "Mage" -> roster.addCharacter(new Mage(name));
                case "Archer" -> roster.addCharacter(new Archer(name));
                case "Ninja" -> roster.addCharacter(new Ninja(name));
            }
            refreshDropdowns();
            nameField.setText("");
            JOptionPane.showMessageDialog(this, name + " the " + cls + " is ready for battle!");
        });

        // 2. Battle Setup Panel
        JPanel setupPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        setupPanel.setBackground(bgPanel);
        setupPanel.setBorder(createTitledBorder(" 2. Setup Match "));

        p1Dropdown = new JComboBox<>();
        p2Dropdown = new JComboBox<>();
        refreshDropdowns();
        
        JButton btnStart = styleButton("Start Battle!");
        btnStart.setBackground(new Color(198, 120, 221));

        setupPanel.add(new JLabel(" Select Player 1:") {{ setForeground(p1Color); }});
        setupPanel.add(p1Dropdown);
        setupPanel.add(new JLabel(" Select Player 2:") {{ setForeground(p2Color); }});
        setupPanel.add(p2Dropdown);
        setupPanel.add(btnStart);

        btnStart.addActionListener(e -> startBattle());

        leftPanel.add(createPanel);
        leftPanel.add(setupPanel);
        return leftPanel;
    }

    private JPanel setupRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(bgDark);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 15));

        // Arena Status Header
        JPanel topContainer = new JPanel(new BorderLayout(5, 5));
        topContainer.setBackground(bgDark);
        
        lblMatchRecord = new JLabel("Latest Record: No matches played yet.", SwingConstants.CENTER);
        lblMatchRecord.setForeground(goldColor);
        lblMatchRecord.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMatchRecord.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statusPanel.setBackground(bgDark);
        
        lblP1Status = new JLabel("P1: Waiting...", SwingConstants.CENTER);
        lblP1Status.setForeground(p1Color);
        lblP1Status.setFont(new Font("Consolas", Font.BOLD, 14));
        
        lblP2Status = new JLabel("P2: Waiting...", SwingConstants.CENTER);
        lblP2Status.setForeground(p2Color);
        lblP2Status.setFont(new Font("Consolas", Font.BOLD, 14));
        
        statusPanel.add(lblP1Status);
        statusPanel.add(lblP2Status);
        
        topContainer.add(lblMatchRecord, BorderLayout.NORTH);
        topContainer.add(statusPanel, BorderLayout.CENTER);
        rightPanel.add(topContainer, BorderLayout.NORTH);

        // Combat Log
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setBackground(bgPanel);
        combatLog.setForeground(textLight);
        combatLog.setFont(new Font("Consolas", Font.PLAIN, 13));
        
        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setBorder(createTitledBorder(" Combat Log "));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Actions Footer
        JPanel actionPanel = new JPanel(new BorderLayout(5, 5));
        actionPanel.setBackground(bgDark);

        lblTurnIndicator = new JLabel("Register fighters and click Start Battle.", SwingConstants.CENTER);
        lblTurnIndicator.setForeground(textLight);
        lblTurnIndicator.setFont(new Font("Segoe UI", Font.BOLD, 16));
        actionPanel.add(lblTurnIndicator, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnPanel.setBackground(bgDark);
        btnBasic = styleButton("Basic Attack");
        btnSpecial = styleButton("Special Move");
        btnBasic.setEnabled(false);
        btnSpecial.setEnabled(false);

        btnBasic.addActionListener(e -> executeTurn(false));
        btnSpecial.addActionListener(e -> executeTurn(true));

        btnPanel.add(btnBasic);
        btnPanel.add(btnSpecial);
        actionPanel.add(btnPanel, BorderLayout.SOUTH);

        rightPanel.add(actionPanel, BorderLayout.SOUTH);
        return rightPanel;
    }

    private void refreshDropdowns() {
        p1Dropdown.removeAllItems();
        p2Dropdown.removeAllItems();
        GameCharacter[] all = roster.getAll();
        for (GameCharacter c : all) {
            String entry = c.getName() + " (" + c.getClass().getSimpleName() + ")";
            p1Dropdown.addItem(entry);
            p2Dropdown.addItem(entry);
        }
    }

    private void startBattle() {
        if (p1Dropdown.getSelectedItem() == null || p2Dropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "You must register at least two characters first!");
            return;
        }
        
        if (p1Dropdown.getSelectedItem().equals(p2Dropdown.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Players must choose different characters! A fighter cannot battle themselves.");
            return;
        }

        // Extract pure names from the dropdown selection format: "Name (Class)"
        String p1Selection = p1Dropdown.getSelectedItem().toString().split(" \\(")[0];
        String p2Selection = p2Dropdown.getSelectedItem().toString().split(" \\(")[0];

        player1 = roster.searchCharacter(p1Selection);
        player2 = roster.searchCharacter(p2Selection);
        
        // Refill health and resources so characters can be used multiple times
        player1.healToFull();
        player2.healToFull();

        battleActive = true;
        isP1Turn = true;
        
        combatLog.setText(">>> BATTLE START! <<<\n");
        combatLog.append(player1.getName() + " VS " + player2.getName() + "\n\n");
        
        updateBattleUI();
        btnBasic.setEnabled(true);
        btnSpecial.setEnabled(true);
    }

    private void executeTurn(boolean useSpecial) {
        if (!battleActive) return;

        GameCharacter attacker = isP1Turn ? player1 : player2;
        GameCharacter defender = isP1Turn ? player2 : player1;

        String actionLog = useSpecial ? attacker.specialMove(defender) : attacker.attack(defender);
        combatLog.append((isP1Turn ? "[P1] " : "[P2] ") + actionLog + "\n");

        if (!defender.isAlive()) {
            battleActive = false;
            combatLog.append("\n🏆 " + attacker.getName() + " is the VICTOR! 🏆\n");
            combatLog.append("💀 " + defender.getName() + " has fallen.\n");
            
            // Record Latest Winner and Loser
            lblMatchRecord.setText("Latest Record: 🏆 Winner: " + attacker.getName() + " | 💀 Loser: " + defender.getName());
            
            btnBasic.setEnabled(false);
            btnSpecial.setEnabled(false);
            lblTurnIndicator.setText("Battle Over! " + attacker.getName() + " Wins.");
            updateBattleUI();
            return;
        }

        isP1Turn = !isP1Turn;
        updateBattleUI();
    }

    private void updateBattleUI() {
        if (player1 != null && player2 != null) {
            lblP1Status.setText("P1 " + player1.getName() + " -> " + player1.getStatus());
            lblP2Status.setText("P2 " + player2.getName() + " -> " + player2.getStatus());
        }
        if (battleActive) {
            if (isP1Turn) {
                lblTurnIndicator.setText("Player 1's Turn (" + player1.getName() + ")");
                lblTurnIndicator.setForeground(p1Color);
            } else {
                lblTurnIndicator.setText("Player 2's Turn (" + player2.getName() + ")");
                lblTurnIndicator.setForeground(p2Color);
            }
        }
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
    }

    private JButton styleButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(59, 64, 72));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(accentColor, 1), title);
        border.setTitleColor(accentColor);
        return border;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}