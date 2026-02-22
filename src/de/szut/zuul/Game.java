package de.szut.zuul;

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    public Game()  // Create the game and initialise its internal map.
    {
        createRooms(); // build the internal map
        parser = new Parser(); // prepare input parsing
    }

    private void createRooms() // Create all the rooms and link their exits together.
    {
        Room marketsquare, templePyramid, tavern, sacrificialSite, hut, jungle, wizardRoom, cellar, secretPassage, cave, beach;
      
        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        wizardRoom = new Room("in a wizard's room");
        cellar = new Room("in a cellar");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");

        // initialise room exits
        marketsquare.setExits(tavern, templePyramid, null, sacrificialSite, null, null);

        templePyramid.setExits(hut, null, null, marketsquare, wizardRoom, cellar);

        tavern.setExits(null, hut, marketsquare, null, null, null);

        sacrificialSite.setExits(null, marketsquare, null, null, null, cave);

        hut.setExits(null, jungle, templePyramid, tavern, null, null);

        jungle.setExits(null, null, null, hut, null, null);

        wizardRoom.setExits(null, null, null, null, null, templePyramid);

        cellar.setExits(null, null, null, secretPassage, templePyramid, null);

        secretPassage.setExits(null, cellar, null, cave, null, null);

        cave.setExits(null, secretPassage, beach, null, sacrificialSite, null);

        beach.setExits(cave, null, null, null, null, null);

        // templePyramid up -> wizardRoom, and wizardRoom down -> templePyramid
        // templePyramid down -> cellar, and cellar up -> templePyramid
        // cellar west -> secretPassage, secretPassage east -> cellar
        // secretPassage west -> cave, cave east -> secretPassage
        // cave up -> sacrificialSite, and sacrificialSite down -> cave
        // cave south -> beach, and beach north -> cave

        currentRoom = marketsquare;  // start game on marketsquare
    }

    public void play() // Main play routine.  Loops until end of play.
    {            
        printWelcome();
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() // Print out the opening message for the player.
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomInformation();
        System.out.println();
    }

    private void printRoomInformation() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if(currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if(currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if(currentRoom.westExit != null) {
            System.out.print("west ");
        }
        if(currentRoom.upExit != null) {
            System.out.print("up ");
        }
        if(currentRoom.downExit != null) {
            System.out.print("down ");
        }
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    private void goRoom(Command command) // Try to go in one direction. If there is an exit, enter the new room, otherwise print an error message.
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave the current room.
        Room nextRoom = null;

        if(direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }
        if(direction.equals("up")) {
            nextRoom = currentRoom.upExit;
        }
        if(direction.equals("down")) {
            nextRoom = currentRoom.downExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printRoomInformation();
            System.out.println();
        }
    }

    private boolean quit(Command command) // "Quit" was entered. Check the rest of the command to see whether we really quit the game.
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // true, if this command quits the game, false otherwise.
        }
    }
}
