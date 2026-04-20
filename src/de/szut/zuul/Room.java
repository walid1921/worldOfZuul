package de.szut.zuul;

import java.util.HashMap;

/**
 A "Room" represents one location in the scenery of the game.
 It is connected to other rooms via exits.  The exits are labelled north,east, south, west.
 For each direction, the room stores a reference to the neighboring room, or null if there is no exit in that direction.
 */
public class Room 
{
    private String description;
    private final HashMap<String, Room> exits; // stores exits of this room.
    private final HashMap<String, Item> items; // stores items in this room.

    // Room description is something like "a kitchen" or "an open courtyard".
    public Room(String description)
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items= new HashMap<>();
    }

    // Define the exits of this room.  Every direction either leads to another room or is null (no exit there).
    public void setExit(String direction, Room neighbour)
    {
        if (direction == null || neighbour == null) {
            throw new IllegalArgumentException("Direction and neighbour cannot be null");
        }
        exits.put(direction, neighbour);
    }

    public Room getExit(String direction) { // Now the Game does not need to know how exits are stored.
        return exits.get(direction);
    }

    public String exitsToString (){ // Now Room controls its own exit logic.
        StringBuilder StringBuilder = new StringBuilder();

        for(String direction : exits.keySet()){
            StringBuilder.append(direction).append(" ");
        }
        return StringBuilder.toString();
    }

    public String getDescription() // returns a description of the room
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n"
                + "Exits: " + exitsToString() + "\n"
                + "Items in this room: " + getItemsToString();
    }

    public void putItem (Item newItem) {
        if (newItem == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.items.put(newItem.getName(), newItem);
    }

    public Item removeItem (String name) {
        if (name == null) {
            return null;
        }

        return this.items.remove(name);
    }

    public String getItemsToString() {
        if (items.isEmpty()) {
            return "none";
        }

        StringBuilder sb = new StringBuilder();

        for (Item item : items.values() ) {
            sb.append(item.getName()).append(", ");
        }

        return sb.toString().trim();
    }

}
