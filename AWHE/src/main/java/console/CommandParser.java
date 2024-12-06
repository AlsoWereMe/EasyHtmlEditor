package console;

import command.*;
import console.constant.*;
import editor.Editor;
import exception.InvalidCommandException;

import java.util.Scanner;

public class CommandParser {
    private final Editor editor;

    public CommandParser(Editor editor) {
        this.editor = editor;
    }

    public ICommand parseCommand() {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine().trim();
        String[] tokens = inputLine.trim().split("\\s+");
        if (tokens.length == 0) {
            throw new InvalidCommandException(ConsoleInfoConstants.NOTHING_TYPED);
        }
        if (editor.isInitialized()) {
            return parseProgressingCommand(tokens);
        }
        return parseInitOrReadCommand(tokens);
    }

    private ICommand parseProgressingCommand(String[] tokens) {
        String commandName = tokens[0];
        return switch (commandName) {
            case CommandNameConstants.INSERT -> parseInsertCommand(tokens);
            case CommandNameConstants.APPEND -> parseAppendCommand(tokens);
            case CommandNameConstants.EDIT_ID -> parseEditIdCommand(tokens);
            case CommandNameConstants.EDIT_TEXT -> parseEditTextCommand(tokens);
            case CommandNameConstants.DELETE -> parseDeleteCommand(tokens);
            case CommandNameConstants.SPELL_CHECK -> parseSpellCheckCommand(tokens);
            case CommandNameConstants.UNDO -> parseUndoCommand(tokens);
            case CommandNameConstants.REDO -> parseRedoCommand(tokens);
            case CommandNameConstants.PRINT_INDENT -> parsePrintIndentCommand(tokens);
            case CommandNameConstants.PRINT_TREE -> parsePrintTreeCommand(tokens);
            case CommandNameConstants.SAVE -> parseSaveCommand(tokens);
            default -> throw new InvalidCommandException(ParserExceptionConstants.UNKNOWN_COMMAND + commandName);
        };
    }

    private ICommand parseInitOrReadCommand(String[] tokens) {
        String commandName = tokens[0];
        return switch (commandName) {
            case CommandNameConstants.READ -> parseReadCommand(tokens);
            case CommandNameConstants.INIT -> parseInitCommand(tokens);
            default -> throw new InvalidCommandException(ParserExceptionConstants.NOT_INITIALIZED);
        };
    }

    private void validateArguments(String[] tokens, int... requiredLengths) {
        for (int requiredLength : requiredLengths) {
            if (tokens.length == requiredLength) {
                return;
            }
        }
        throw new InvalidCommandException(getWrongFormatMessage(tokens[0]));
    }

    private String getWrongFormatMessage(String commandName) {
        // 根据命令名返回对应的错误信息常量
        return switch (commandName) {
            case CommandNameConstants.INIT -> ParserExceptionConstants.INIT_WRONG_FORMAT;
            case CommandNameConstants.SAVE -> ParserExceptionConstants.SAVE_WRONG_FORMAT;
            case CommandNameConstants.READ -> ParserExceptionConstants.READ_WRONG_FORMAT;
            case CommandNameConstants.INSERT -> ParserExceptionConstants.INSERT_WRONG_FORMAT;
            case CommandNameConstants.APPEND -> ParserExceptionConstants.APPEND_WRONG_FORMAT;
            case CommandNameConstants.PRINT_TREE -> ParserExceptionConstants.PRINT_TREE_WRONG_FORMAT;
            case CommandNameConstants.PRINT_INDENT -> ParserExceptionConstants.PRINT_INDENT_WRONG_FORMAT;
            case CommandNameConstants.REDO -> ParserExceptionConstants.REDO_WRONG_FORMAT;
            case CommandNameConstants.UNDO -> ParserExceptionConstants.UNDO_WRONG_FORMAT;
            case CommandNameConstants.EDIT_ID -> ParserExceptionConstants.EDIT_ID_WRONG_FORMAT;
            case CommandNameConstants.EDIT_TEXT -> ParserExceptionConstants.EDIT_TEXT_WRONG_FORMAT;
            case CommandNameConstants.DELETE -> ParserExceptionConstants.DELETE_WRONG_FORMAT;
            case CommandNameConstants.SPELL_CHECK -> ParserExceptionConstants.SPELL_CHECK_WRONG_FORMAT;
            default -> ParserExceptionConstants.UNKNOWN_COMMAND + commandName;
        };
    }

    private ICommand parseInitCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.INIT_ARGUMENTS_COUNT_GROUND);
        return new InitCommand(editor);
    }

    private ICommand parseSaveCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.SAVE_ARGUMENTS_COUNT_GROUND);
        String filepath = tokens[1];
        return new SaveCommand(editor, filepath);
    }

    private ICommand parseReadCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.READ_ARGUMENTS_COUNT_GROUND);
        String filepath = tokens[1];
        return new ReadCommand(editor, filepath);
    }

    private ICommand parsePrintTreeCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.PRINT_TREE_ARGUMENTS_COUNT_GROUND);
        return new PrintTreeCommand(editor);
    }

    private ICommand parsePrintIndentCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.PRINT_INDENT_ARGUMENTS_COUNT_GROUND, CommandArgumentsConstants.PRINT_INDENT_ARGUMENTS_COUNT_GROUND + 1);
        int indentSize = CommandConfigConstants.DEFAULT_INDENT_SIZE;
        if (tokens.length == 2) {
            try {
                indentSize = Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException(ParserExceptionConstants.PRINT_INDENT_WRONG_FORMAT);
            }
        }
        return new PrintIndentCommand(editor, indentSize);
    }

    private ICommand parseRedoCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.REDO_ARGUMENTS_COUNT_GROUND);
        return new RedoCommand(editor);
    }

    private ICommand parseUndoCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.UNDO_ARGUMENTS_COUNT_GROUND);
        return new UndoCommand(editor);
    }

    private ICommand parseInsertCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.INSERT_ARGUMENTS_COUNT_GROUND, CommandArgumentsConstants.INSERT_ARGUMENTS_COUNT_GROUND + 1);
        String tagName = tokens[1];
        String idValue = tokens[2];
        String insertLocation = tokens[3];
        String textContent = tokens.length > 4 ? tokens[4] : null;
        return new InsertCommand(editor, tagName, idValue, insertLocation, textContent);
    }

    private ICommand parseAppendCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.APPEND_ARGUMENTS_COUNT_GROUND, CommandArgumentsConstants.APPEND_ARGUMENTS_COUNT_GROUND + 1);
        String tagName = tokens[1];
        String idValue = tokens[2];
        String parentElement = tokens[3];
        String textContent = tokens.length > 4 ? tokens[4] : null;
        return new AppendCommand(editor, tagName, idValue, parentElement, textContent);
    }

    private ICommand parseEditIdCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.EDIT_ID_ARGUMENTS_COUNT_GROUND);
        String oldId = tokens[1];
        String newId = tokens[2];
        return new EditIdCommand(editor, oldId, newId);
    }

    private ICommand parseEditTextCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.EDIT_TEXT_ARGUMENTS_COUNT_GROUND, CommandArgumentsConstants.EDIT_TEXT_ARGUMENTS_COUNT_GROUND + 1);
        String elementId = tokens[1];
        String newTextContent = tokens.length > 2 ? tokens[2] : "";
        return new EditTextCommand(editor, elementId, newTextContent);
    }

    private ICommand parseDeleteCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.DELETE_ARGUMENTS_COUNT_GROUND);
        String elementId = tokens[1];
        return new DeleteCommand(editor, elementId);
    }

    private ICommand parseSpellCheckCommand(String[] tokens) {
        validateArguments(tokens, CommandArgumentsConstants.SPELL_CHECK_ARGUMENTS_COUNT_GROUND);
        return new SpellCheckCommand(editor);
    }
}