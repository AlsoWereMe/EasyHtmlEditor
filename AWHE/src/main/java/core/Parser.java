package core;

import command.*;

public class Parser {
    private final Editor editor;

    public Parser(Editor editor) {
        this.editor = editor;
    }

    public Command parseCommand(String inputLine) {
        // 解构命令行的输入
        String[] tokens = inputLine.trim().split("\\s+");
        if (tokens.length == 0) {
            throw new IllegalArgumentException("No command entered.");
        }

        String commandName = tokens[0];

        switch (commandName) {
            case "insert":
                return parseInsertCommand(tokens);
            case "append":
                return parseAppendCommand(tokens);
            case "edit-id":
                return parseEditIdCommand(tokens);
            case "edit-text":
                return parseEditTextCommand(tokens);
            case "delete":
                return parseDeleteCommand(tokens);
            case "spell-check":
                return parseSpellCheckCommand(tokens);
            case "undo":
                return new Undo(editor);
            case "redo":
                return new Redo(editor);
            case "print-indent":
                editor.printIndent(tokens);
                return null;
            case "print-tree":
                editor.printTree();
                return null;
            case "read":
                editor.readFile(tokens);
                return null;
            case "save":
                editor.saveFile(tokens);
                return null;
            case "init":
                editor.init();
                return null;
            default:
                throw new IllegalArgumentException("Unknown command: " +
                        commandName);
        }
    }

    private Command parseInsertCommand(String[] tokens) {
        if (tokens.length != 4 && tokens.length != 5) {
            throw new IllegalArgumentException("Invalid insert command.");
        }
        String tagName = tokens[1];
        String idValue = tokens[2];
        String insertLocation = tokens[3];
        String textContent = tokens.length > 4 ? tokens[4] : null;
        return new Insert(editor, tagName, idValue, insertLocation,
                textContent);
    }

    private Command parseAppendCommand(String[] tokens) {
        if (tokens.length != 4 && tokens.length != 5) {
            throw new IllegalArgumentException("Invalid append command.");
        }
        String tagName = tokens[1];
        String idValue = tokens[2];
        String parentElement = tokens[3];
        String textContent = tokens.length > 4 ? tokens[4] : null;
        return new Append(editor, tagName, idValue, parentElement,
                textContent);
    }

    private Command parseEditIdCommand(String[] tokens) {
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid edit-id command.");
        }
        String oldId = tokens[1];
        String newId = tokens[2];
        return new EditId(editor, oldId, newId);
    }

    private Command parseEditTextCommand(String[] tokens) {
        if (tokens.length != 2 && tokens.length != 3) {
            throw new IllegalArgumentException("Invalid edit-text command.");
        }
        String elementId = tokens[1];
        String newTextContent = tokens.length > 2 ? tokens[2] : "";
        return new EditText(editor, elementId, newTextContent);
    }

    private Command parseDeleteCommand(String[] tokens) {
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Invalid delete command.");
        }
        String elementId = tokens[1];
        return new Delete(editor, elementId);
    }

    private Command parseSpellCheckCommand(String[] tokens) {
        if (tokens.length != 1) {
            throw new IllegalArgumentException("Invalid spell-check command.");
        }
        return new SpellCheck(editor);
    }
}
