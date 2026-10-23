package game_character_enhancement;

public class CharacterLinkedList implements CharacterADT {
    private class Node {
        GameCharacter data;
        Node next;
        Node(GameCharacter data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public CharacterLinkedList() {
        this.head = null;
    }

    @Override
    public void addCharacter(GameCharacter c) {
        Node newNode = new Node(c);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    @Override
    public boolean removeCharacter(String name) {
        if (head == null) return false;
        if (head.data.getName().equalsIgnoreCase(name)) {
            head = head.next;
            return true;
        }
        Node current = head;
        while (current.next != null && !current.next.data.getName().equalsIgnoreCase(name)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            return true;
        }
        return false;
    }

    @Override
    public GameCharacter searchCharacter(String name) {
        Node current = head;
        while (current != null) {
            if (current.data.getName().equalsIgnoreCase(name)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public GameCharacter[] getAll() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        GameCharacter[] arr = new GameCharacter[count];
        current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
}