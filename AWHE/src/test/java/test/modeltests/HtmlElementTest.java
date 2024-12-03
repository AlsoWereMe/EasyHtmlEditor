package test.modeltests;

import model.HtmlElement;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlElementTest {

    private HtmlElement parentElement;
    private HtmlElement childElement;
    private HtmlElement grandChildElement;

    @BeforeAll
    public static void init() {
        System.out.println("HtmlElement Test Start");
    }

    @BeforeEach
    public void setUp() {
        // 初始化层级结构的 HTML 元素
        parentElement = new HtmlElement("div", "parent", "Parent Content");
        childElement = new HtmlElement("p", "child", "Child Content");
        grandChildElement = new HtmlElement("span", "grandchild", "Grandchild Content");

        // 设置父子关系
        childElement.insertChild(grandChildElement);
        parentElement.insertChild(childElement);
    }

    @DisplayName("Add Child Element Test")
    @Test
    public void testAddChildElement() {
        assertEquals(1, parentElement.getChildren().size());
        assertEquals(childElement, parentElement.getChildren().get(0));
    }

    @DisplayName("Remove Child Element Test")
    @Test
    public void testRemoveChildElement() {
        parentElement.removeChild(childElement);
        assertTrue(parentElement.getChildren().isEmpty());
    }

    @DisplayName("Set Text Content Test")
    @Test
    public void testSetTextContent() {
        parentElement.updateTextContent("Updated Parent Content");
        assertEquals("Updated Parent Content", parentElement.getTextContent());
    }

    @DisplayName("Set and Get ID Test")
    @Test
    public void testSetAndGetId() {
        parentElement.updateId("newParentId");
        assertEquals("newParentId", parentElement.getId());
    }

    @DisplayName("Find Element Test")
    @Test
    public void testFindElement() {
        // 在层级结构中查找元素
        assertEquals(childElement, parentElement.getSingleChildById("child"));
        assertEquals(grandChildElement, parentElement.getSingleChildById("grandchild"));
        assertNull(parentElement.getSingleChildById("nonExistentId"));
    }

    @DisplayName("Check ID Exists Test")
    @Test
    public void testIdExists() {
        assertTrue(parentElement.isIdExistsInTree("parent"));
        assertTrue(parentElement.isIdExistsInTree("child"));
        assertTrue(parentElement.isIdExistsInTree("grandchild"));
        assertFalse(parentElement.isIdExistsInTree("nonExistentId"));
    }

    @DisplayName("Indented HTML String Test")
    @Test
    public void testToIndentedString() {
        String expectedIndentedString =
                "<div id=\"parent\">\n" +
                        "  Parent Content\n" +
                        "  <p id=\"child\">\n" +
                        "    Child Content\n" +
                        "    <span id=\"grandchild\">Grandchild Content</span>\n" +
                        "  </p>\n" +
                        "</div>\n";

        assertEquals(expectedIndentedString, parentElement.toIndentedString(0, 2));
    }

    @DisplayName("HTML String Test")
    @Test
    public void testToHtmlString() {
        String expectedHtmlString =
                "<div id=\"parent\">Parent Content" +
                        "<p id=\"child\">Child Content" +
                        "<span id=\"grandchild\">Grandchild Content</span>" +
                        "</p></div>";

        assertEquals(expectedHtmlString, parentElement.toHtmlString());
    }
}
