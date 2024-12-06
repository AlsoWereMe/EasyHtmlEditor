package console.constant;

public interface ParserExceptionConstants {
    String UNKNOWN_COMMAND = "Unknown command: ";
    String INIT_WRONG_FORMAT = "\"init\" command should have no argument.";
    String READ_WRONG_FORMAT = "\"read\" command must have the filepath as the only argument.";
    String SAVE_WRONG_FORMAT = "\"save\" command must have the filepath as the only argument.";
    String INSERT_WRONG_FORMAT = "\"insert\" command should have three or four arguments.";
    String APPEND_WRONG_FORMAT = "\"append\" command should have three or four arguments.";
    String PRINT_TREE_WRONG_FORMAT = "\"print-tree\" command should have no argument.";
    String PRINT_INDENT_WRONG_FORMAT = "\"print-indent\" command should have zero or one argument, and the extra argument must be a number.";
    String REDO_WRONG_FORMAT = "\"redo\" command should have no argument.";
    String UNDO_WRONG_FORMAT = "\"undo\" command should have no argument.";
    String EDIT_ID_WRONG_FORMAT = "\"edit-id\" command should have exactly two arguments.";
    String EDIT_TEXT_WRONG_FORMAT = "\"edit-text\" command should have two or three arguments.";
    String DELETE_WRONG_FORMAT = "\"delete\" command should have exactly one argument.";
    String SPELL_CHECK_WRONG_FORMAT = "\"spell-check\" command should have no argument.";
    String NOT_INITIALIZED = "The editor is not initialized so that only \"read\" or \"init\" available.";
}
