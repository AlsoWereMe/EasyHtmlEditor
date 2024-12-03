package test.coretests;

import core.Editor;
import core.Parser;
import command.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private static Parser parser;

    @BeforeAll
    public static void setUp() {
        Editor editor = new Editor();
        parser = new Parser(editor);
        System.out.println("Parser Test Begin.");
    }

    @DisplayName("Parse Insert Command Test")
    @Test
    public void testParseInsertCommand() {
        Command command = parser.parseCommand("insert div newDiv after existingDiv");
        assertTrue(command instanceof Insert);
    }

    @DisplayName("Parse Append Command Test")
    @Test
    public void testParseAppendCommand() {
        Command command = parser.parseCommand("append span childDiv root Hello");
        assertTrue(command instanceof Append);
    }

    @DisplayName("Parse Edit ID Command Test")
    @Test
    public void testParseEditIdCommand() {
        Command command = parser.parseCommand("edit-id oldId newId");
        assertTrue(command instanceof EditId);
    }

    @DisplayName("Parse Edit Text Command Test")
    @Test
    public void testParseEditTextCommand() {
        Command command = parser.parseCommand("edit-text elementId NewContent");
        assertTrue(command instanceof EditText);
    }

    @DisplayName("Parse Delete Command Test")
    @Test
    public void testParseDeleteCommand() {
        Command command = parser.parseCommand("delete elementId");
        assertTrue(command instanceof Delete);
    }

    @DisplayName("Parse Spell Check Command Test")
    @Test
    public void testParseSpellCheckCommand() {
        Command command = parser.parseCommand("spell-check");
        assertTrue(command instanceof SpellCheck);
    }

    @DisplayName("Parse Undo Command Test")
    @Test
    public void testParseUndoCommand() {
        Command command = parser.parseCommand("undo");
        assertTrue(command instanceof Undo);
    }

    @DisplayName("Parse Redo Command Test")
    @Test
    public void testParseRedoCommand() {
        Command command = parser.parseCommand("redo");
        assertTrue(command instanceof Redo);
    }

    @DisplayName("Invalid Command Test")
    @Test
    public void testInvalidCommand() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parseCommand("unknown command"));
        assertEquals("Unknown command: unknown", exception.getMessage());
    }

    @DisplayName("Invalid Argument Count Test")
    @Test
    public void testInvalidArgumentCount() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseCommand("insert div id"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseCommand("append div id"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseCommand("edit-id id"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseCommand("delete"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseCommand("spell-check extra"));
    }

    @AfterAll
    public static void end() {
        System.out.println("Parser Test End.");
    }
}
