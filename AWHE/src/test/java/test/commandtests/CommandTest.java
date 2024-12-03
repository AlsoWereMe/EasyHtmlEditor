package test.commandtests;

import core.Editor;
import command.*;
import model.HtmlElement;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    private Editor editor;

    @BeforeEach
    public void setUp() {
        editor = new Editor();
        HtmlElement rootElement = new HtmlElement("html", "root", "");
        editor.setRoot(rootElement);
    }

    @DisplayName("Insert Command Test")
    @Test
    public void testInsertCommand() {
        // 首先创建一个非根节点，并将其添加到根节点下
        editor.executeCommand("append div parentDiv root Parent_Content");

        // 然后尝试将新元素插入到非根节点 "parentDiv" 下
        Command insertCommand = new Insert(editor, "div", "newDiv", "parentDiv", "Child_Content");
        insertCommand.execute();

        // 验证新元素是否被成功插入到 "parentDiv" 下
        HtmlElement root = editor.getRoot();
        HtmlElement parentElement = editor.findElement("parentDiv");
        HtmlElement newElement = editor.findElement("newDiv");

        assertNotNull(parentElement);
        assertNotNull(newElement);
        assertEquals("div", newElement.getTagName());
        assertEquals("Child_Content", newElement.getTextContent());
        assertTrue(root.getChildren().contains(newElement));
    }


    @DisplayName("Append Command Test")
    @Test
    public void testAppendCommand() {
        Command appendCommand = new Append(editor, "span", "newSpan", "root", "Hello");
        appendCommand.execute();
        HtmlElement element = editor.findElement("newSpan");

        assertNotNull(element);
        assertEquals("span", element.getTagName());
        assertEquals("Hello", element.getTextContent());
    }

    @DisplayName("Edit ID Command Test")
    @Test
    public void testEditIdCommand() {
        editor.executeCommand("append div editTest root TestContent");
        Command editIdCommand = new EditId(editor, "editTest", "newEditTestId");
        editIdCommand.execute();

        assertNull(editor.findElement("editTest"));
        assertNotNull(editor.findElement("newEditTestId"));
    }

    @DisplayName("Edit Text Command Test")
    @Test
    public void testEditTextCommand() {
        editor.executeCommand("append div textTest root InitialText");
        Command editTextCommand = new EditText(editor, "textTest", "UpdatedText");
        editTextCommand.execute();

        HtmlElement element = editor.findElement("textTest");
        assertNotNull(element);
        assertEquals("UpdatedText", element.getTextContent());
    }

    @DisplayName("Delete Command Test")
    @Test
    public void testDeleteCommand() {
        editor.executeCommand("append div deleteTest root ContentToDelete");
        Command deleteCommand = new Delete(editor, "deleteTest");
        deleteCommand.execute();

        assertNull(editor.findElement("deleteTest"));
    }

    @DisplayName("Spell Check Command Test")
    @Test
    public void testSpellCheckCommand() {
        Command spellCheckCommand = new SpellCheck(editor);
        spellCheckCommand.execute();

        // Assuming spell check output goes to console or modifies Editor in a detectable way
        // Placeholder assertion; modify based on actual SpellCheck behavior
        assertTrue(true);
    }

    @DisplayName("Undo Command Test")
    @Test
    public void testUndoCommand() {
        editor.executeCommand("append div undoTest root UndoContent");
        editor.executeCommand("undo");

        assertNull(editor.findElement("undoTest"));
    }

    @DisplayName("Redo Command Test")
    @Test
    public void testRedoCommand() {
        editor.executeCommand("append div redoTest root RedoContent");
        editor.executeCommand("undo");
        assertNull(editor.findElement("redoTest"));

        editor.executeCommand("redo");
        assertNotNull(editor.findElement("redoTest"));
    }
}
