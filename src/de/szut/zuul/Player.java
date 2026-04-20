package de.szut.zuul;
import de.szut.zuul.states.Healthy;
import de.szut.zuul.states.State;
import de.szut.zuul.states.Wounded;

import java.util.LinkedList;

public class Player {
    private Room currentRoom;
    private double loadCapacity = 10;
    private LinkedList<Item> itemsArrList = new LinkedList<>();
    private State currentState;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.currentState = Wounded.getInstance();
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void goTo(Room newRoom) {
        currentRoom = newRoom;
    }

    public boolean takeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (!isTakingPossible(item)) {
            return false;
        }

        itemsArrList.addLast(item);
        return true;
    }


    public Item dropItem(String name) {
        if (name == null) {
            return null;
        }

        for (Item item : itemsArrList) {
            if (item.getName().equals(name)) {
                itemsArrList.remove(item);
                return item;
            }
        }

        return null;
    }

    public String showStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("> Status of the player\n");
        sb.append("loadCapacity: ").append(loadCapacity).append(" kg\n");
        sb.append("taken items: ");

        if (itemsArrList.isEmpty()) {
            sb.append("nothing\n");
        } else {
            for (int i = 0; i < itemsArrList.size(); i++) {
                Item it = itemsArrList.get(i);
                sb.append(it.getName()).append(", ").append(it.getWeight()).append(" kg");
                if (i < itemsArrList.size() - 1) {
                    sb.append("; ");
                }
            }
            sb.append("\n");
        }

        sb.append("absorbed weight: ").append(calculateWeight()).append(" kg\n");
        sb.append("current state: ").append(currentState.getClass().getSimpleName().toLowerCase()).append("\n");
        return sb.toString();
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public void heal() {
        currentState.heal(this);
    }

    public void injureEasily() {
        currentState.injureEasily(this);
    }

    public void injureHeavy() {
        currentState.injureHeavy(this);
    }

    private double calculateWeight() {
        double weight = 0;
        for (Item item : itemsArrList) {
            weight += item.getWeight();
        }
        return weight;
    }

    private boolean isTakingPossible(Item item) {
        return calculateWeight() + item.getWeight() <= loadCapacity;
    }
}
