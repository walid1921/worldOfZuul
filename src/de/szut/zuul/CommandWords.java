package de.szut.zuul;

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "take", "drop", "items", "status", "injure", "heal", "eat"
    };

    public CommandWords()
    {
        // Constructor - initialise the command words. nothing to do at the moment...
    }

    // Check whether a given String is a valid command word.
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false; // the string was not found in the commands
    }

    public String showAll() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String command : validCommands){
            stringBuilder.append(command).append(" "); // add a space after each command for better readability
        }
        return stringBuilder.toString();
    }
}
