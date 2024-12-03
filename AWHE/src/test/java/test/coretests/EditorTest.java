package test.coretests;

import core.Editor;
import model.HtmlElement;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditorTest {
    private Editor editor;
    private static Path tempFilePath;

    @BeforeAll
    public static void init() throws IOException {
        System.out.println("Editor Test Start");
        // 创建临时文件用于保存和读取测试
        tempFilePath = Files.createTempFile("testFile", ".html");
    }

    @BeforeEach
    public void setUp() {
        editor = new Editor();
        HtmlElement rootElement = new HtmlElement("html", "root", "");
        editor.setRoot(rootElement);
    }

    @DisplayName("Execute Command Test")
    @Test
    @Order(1)
    public void testExecuteCommandBasic() {
        editor.executeCommand("append div testDiv root Hello");
        HtmlElement element = editor.findElement("testDiv");

        assertNotNull(element);
        assertEquals("div", element.getTagName());
        assertEquals("Hello", element.getTextContent());
    }

    @DisplayName("Undo Command Test")
    @Test
    @Order(2)
    public void testUndoCommand() {
        editor.executeCommand("append div undoTest root Hello");
        editor.executeCommand("undo");
        assertNull(editor.findElement("undoTest"));
    }

    @DisplayName("Redo Command Test")
    @Test
    @Order(3)
    public void testRedoCommand() {
        editor.executeCommand("append div redoTest root Hello");
        editor.executeCommand("undo");
        assertNull(editor.findElement("redoTest"));

        editor.executeCommand("redo");
        assertNotNull(editor.findElement("redoTest"));
    }

    @DisplayName("Save File Test")
    @Test
    @Order(4)
    public void testSaveFile() {
        editor.executeCommand("append div saveTest root Test_Content");

        // 执行保存文件操作
        editor.saveFile(new String[]{"save", tempFilePath.toString()});
        assertTrue(Files.exists(tempFilePath));

        // 验证文件内容是否符合预期
        try {
            String fileContent = new String(Files.readAllBytes(tempFilePath), StandardCharsets.UTF_8);
            assertTrue(fileContent.contains("<div id=\"saveTest\">Test_Content</div>"));
        } catch (IOException e) {
            fail("Error reading saved file: " + e.getMessage());
        }
    }

    @DisplayName("Read File Test")
    @Test
    @Order(5)
    public void testReadFile() {
        // 确保文件中有内容
        String initialHtmlContent = "<html><div id=\"readTest\">Read Content</div></html>";
        try {
            Files.write(tempFilePath, initialHtmlContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            fail("Error writing initial content to temp file: " + e.getMessage());
        }

        // 读取文件内容到编辑器
        editor.readFile(new String[]{"read", tempFilePath.toString()});
        HtmlElement readElement = editor.findElement("readTest");

        // 验证读取内容是否正确
        assertNotNull(readElement);
        assertEquals("div", readElement.getTagName());
        assertEquals("Read Content", readElement.getTextContent());
    }

    @AfterAll
    public static void end() throws IOException {
        System.out.println("All Editor Tests Completed.");
        Files.deleteIfExists(tempFilePath);
    }
}
