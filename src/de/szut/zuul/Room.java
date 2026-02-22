package de.szut.zuul;

/**
 A "Room" represents one location in the scenery of the game.
 It is connected to other rooms via exits.  The exits are labelled north,east, south, west.
 For each direction, the room stores a reference to the neighboring room, or null if there is no exit in that direction.
 */
public class Room 
{
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public Room upExit;
    public Room downExit;

    // Room description is something like "a kitchen" or "an open courtyard".
    public Room(String description)
    {
        this.description = description;
    }

    // Define the exits of this room.  Every direction either leads to another room or is null (no exit there).
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down)
    {
        if (north != null) northExit = north;
        if (east != null)  eastExit = east;
        if (south != null) southExit = south;
        if (west != null)  westExit = west;
        if (up != null)    upExit = up;
        if (down != null)  downExit = down;
    }

    public String getDescription() // R
    {
        return description;
    }

}
