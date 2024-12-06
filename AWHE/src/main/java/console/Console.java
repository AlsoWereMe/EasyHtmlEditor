package console;

import command.ICommand;
import console.constant.ConsoleInfoConstants;
import editor.Editor;
import exception.InvalidCommandException;

public class Console {
    public static void main(String[] args) {
        Editor editor = new Editor();
        CommandParser parser = new CommandParser(editor);
        System.out.println(ConsoleInfoConstants.WELCOME);

        while (true) {
            System.out.print(ConsoleInfoConstants.CONSOLE_PROMPT);
            try {
                ICommand command = parser.parseCommand();
                editor.action(command);
            } catch (InvalidCommandException e) {
                System.out.println(ConsoleInfoConstants.WRONG_INPUT);
                System.out.println(e.getMessage());
            }
        }
    }
}

