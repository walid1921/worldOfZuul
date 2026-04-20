package de.szut.zuul;

public class Game 
{
    private final Parser parser;
    private Player player;

    //! Create the game and initialise its internal map.
    public Game()
    {
        createRooms(); // build the internal map
        parser = new Parser(); // prepare input parsing
    }

    //! Create all the rooms and link their exits together.
    private void createRooms()
    {

        Item bow, treasure, arrows, cocoa, knife, spear, food, jewellery;
        Herb herb;

        bow = new Item("bow", "a bow made of wood", 0.5);
        treasure = new Item("treasure", "a little treasure chest with coins", 7.5);
        arrows = new Item("arrows", "a lot of arrows in a quiver", 1.0);
        herb = new Herb("herb", "a medicine herb", 0.5);
        cocoa = new Item("cocoa", "a little cocoa tree", 5.0);
        knife = new Item("knife", "a big, sharp knife", 1.0);
        spear = new Item("spear", "a spear with slingshot", 5.0);
        food = new Item("food", "a plate of hearty meat and corn porridge", 0.5);
        jewellery = new Item("jewellery", "a very pretty headdress", 1.0);

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
        marketsquare.putItem(bow);

        templePyramid.setExit("north", hut);
        templePyramid.setExit("west", marketsquare);
        templePyramid.setExit("up", wizardRoom);
        templePyramid.setExit("down", cellar);

        cellar.setExit("west", secretPassage);
        cellar.setExit("up", templePyramid);
        cellar.putItem(jewellery);

        wizardRoom.setExit("down", templePyramid);
        wizardRoom.putItem(arrows);

        tavern.setExit("east", hut);
        tavern.setExit("south", marketsquare);
        tavern.putItem(food);

        sacrificialSite.setExit("east", marketsquare);
        sacrificialSite.setExit("down", cave);
        sacrificialSite.putItem(knife);

        cave.setExit("up", sacrificialSite);
        cave.setExit("east", secretPassage);
        cave.setExit("south", beach);
        cave.putItem(treasure);

        hut.setExit("west", tavern);
        hut.setExit("east", jungle);
        hut.setExit("south", templePyramid);
        hut.putItem(spear);

        jungle.setExit("west", hut);
        jungle.putItem(herb);
        jungle.putItem(cocoa);

        secretPassage.setExit("east", cellar);
        secretPassage.setExit("west", cave);

        beach.setExit("north", cave);

        // templePyramid up -> wizardRoom, and wizardRoom down -> templePyramid
        // templePyramid down -> cellar, and cellar up -> templePyramid
        // cellar west -> secretPassage, secretPassage east -> cellar
        // secretPassage west -> cave, cave east -> secretPassage
        // cave up -> sacrificialSite, and sacrificialSite down -> cave
        // cave south -> beach, and beach north -> cave

        player = new Player(marketsquare); // start game on marketsquare
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
        System.out.println(player.getCurrentRoom().getLongDescription());
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
        switch (commandWord) {
            case "help" -> printHelp();
            case "go" -> goRoom(command);
            case "look" -> look();
            case "take" -> takeItem(command);
            case "drop" -> dropItem(command);
            case "items" -> System.out.println("items command not implemented yet");
            case "injure" ->  injurePlayer();
            case "heal" ->  healPlayer();
            case "eat" -> eatItem(command);
            case "status" -> System.out.println(player.showStatus());
            case "quit" -> wantToQuit = quit(command);
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
        System.out.println("   " + this.parser.showCommands());
    }

    //! Look
    private void look(){
        printRoomInformation();
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
        Room nextRoom =  player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.goTo(nextRoom);
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

    //! Take
    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item pickedItem = player.getCurrentRoom().removeItem(itemName);

        if (pickedItem == null) {
            System.out.println("There is no " + itemName + " in this room.");
            return;
        }

        if (player.takeItem(pickedItem)) {
            System.out.println("You took the " + itemName + ".");
            System.out.println(player.showStatus());
            printRoomInformation();
        } else {
            System.out.println("You cannot take the " + itemName + ". It is too heavy.");
            player.getCurrentRoom().putItem(pickedItem); // put the item back in the room
        }
    }

    //! Drop
    private void dropItem (Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Drop What?");
            return;
        }

        String itemName = command.getSecondWord();

        Item droppedItem = player.dropItem(itemName);

        if(droppedItem == null) {
            System.out.println("You don't have " + itemName + " in your inventory.");
            return;
        }

        player.getCurrentRoom().putItem(droppedItem);
        System.out.println("You dropped the " + itemName + ".");
        System.out.println(player.showStatus());
        printRoomInformation();
    }

    //! Injure
    private void injurePlayer() {
        player.injureEasily();
        System.out.println(player.showStatus());
    }

    //! Heal
    private void healPlayer() {
        player.heal();
        System.out.println(player.showStatus());
    }

    //! Eat
    private void eatItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("eat what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.dropItem(itemName);

        if (item == null) {
            System.out.println("You don't have " + itemName + ".");
            return;
        }

        if (item instanceof Herb) {
            player.heal();
            System.out.println("You ate the herb. You feel better!");
        } else {
            System.out.println("You can't eat that.");
            player.takeItem(item); // put it back
        }

        System.out.println(player.showStatus());
    }
}
