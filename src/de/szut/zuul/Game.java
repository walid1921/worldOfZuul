package de.szut.zuul;

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    //! Create the game and initialise its internal map.
    public Game()
    {
        createRooms(); // build the internal map
        parser = new Parser(); // prepare input parsing
    }

    //! Create all the rooms and link their exits together.
    private void createRooms()
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

        marketsquare.setExit("north", tavern);
        marketsquare.setExit("east", templePyramid);
        marketsquare.setExit("west", sacrificialSite);

        templePyramid.setExit("north", hut);
        templePyramid.setExit("west", marketsquare);
        templePyramid.setExit("up", wizardRoom);
        templePyramid.setExit("down", cellar);

        tavern.setExit("east", hut);
        tavern.setExit("south", marketsquare);

        sacrificialSite.setExit("east", marketsquare);
        sacrificialSite.setExit("down", cave);

        hut.setExit("east", jungle);
        hut.setExit("south", templePyramid);
        hut.setExit("west", tavern);

        jungle.setExit("west", hut);

        wizardRoom.setExit("down", templePyramid);

        cellar.setExit("up", templePyramid);
        cellar.setExit("west", secretPassage);

        secretPassage.setExit("east", cellar);
        secretPassage.setExit("west", cave);

        cave.setExit("east", secretPassage);
        cave.setExit("south", beach);
        cave.setExit("up", sacrificialSite);

        beach.setExit("north", cave);

        // templePyramid up -> wizardRoom, and wizardRoom down -> templePyramid
        // templePyramid down -> cellar, and cellar up -> templePyramid
        // cellar west -> secretPassage, secretPassage east -> cellar
        // secretPassage west -> cave, cave east -> secretPassage
        // cave up -> sacrificialSite, and sacrificialSite down -> cave
        // cave south -> beach, and beach north -> cave

        currentRoom = marketsquare;  // start game on marketsquare
    }

    //! Play
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

    //! Welcome
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

    //! Print out information about the current room
    private void printRoomInformation() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: " + currentRoom.exitsToString());
    }

    //! Cmd Process a command. Return true if the command ends the game, false otherwise.
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

    //! Help
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    //! Go
    // Try to go in one direction. If there is an exit, enter the new room, otherwise print an error message.
    private void goRoom(Command command)
    {

        if(!command.hasSecondWord()) { // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave the current room.
        Room nextRoom =  currentRoom.getExit(direction);;

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printRoomInformation();
            System.out.println();
        }
    }

    //! Quit
    private boolean quit(Command command) // "Quit" was entered. Check the rest of the command to see whether we really quit the game.
    {
        if(command.hasSecondWord()) { // if there is no second word, we don't know what to quit...
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // true, if this command quits the game, false otherwise.
        }
    }
}
