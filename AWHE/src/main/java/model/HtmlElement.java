package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlsoWereMe
 *
 */
public class HtmlElement {


    private String tagName;
    private String id;
    private String textContent;
    private HtmlElement parent;
    private final List<HtmlElement> children;

    public HtmlElement(String tagName, String id, String textContent) {
        this.tagName = tagName;
        this.id = id;
        this.textContent = textContent;
        this.children = new ArrayList<>();
    }

    public String getTagName() { return tagName; }
    public String getId() { return id; }
    public String getTextContent() { return textContent; }
    public List<HtmlElement> getChildren() { return children; }
    public HtmlElement getParent() { return parent; }
    public void updateTagName(String tagName) { this.tagName = tagName; }
    public void updateId(String id) { this.id = id; }
    public void updateTextContent(String textContent) { this.textContent = textContent; }
    public void updateParent(HtmlElement parent) { this.parent = parent; }

    public void insertChild(HtmlElement child) {
        children.add(child);
        child.updateParent(this);
    }

    public void removeChild(HtmlElement child) {
        children.remove(child);
        child.updateParent(null);
    }

    public HtmlElement getSingleChildById(String id) {
        if (this.id != null && this.id.equals(id)) {
            return this;
        }
        for (HtmlElement child : children) {
            HtmlElement result = child.getSingleChildById(id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public boolean isIdExistsInTree(String id) {
        if (this.id != null && this.id.equals(id)) {
            return true;
        }
        for (HtmlElement child : children) {
            if (child.isIdExistsInTree(id)) {
                return true;
            }
        }
        return false;
    }

    public String toIndentedString(int indentLevel, int indentSize) {
        StringBuilder sb = new StringBuilder();
        StringBuilder indent = new StringBuilder();

        // 根据缩进等级和缩进大小生成缩进字符串
        for (int i = 0; i < indentLevel * indentSize; i++) {
            indent.append(" ");
        }

        // 开始标签
        sb.append(indent).append("<").append(tagName);
        if (id != null) {
            sb.append(" id=\"").append(id).append("\"");
        }
        sb.append(">");



        if (!children.isEmpty()) {
            // 如果有子元素，则添加换行并递归打印子元素
            sb.append("\n");
            if (textContent != null && !textContent.isEmpty()) {
                sb.append(indent);
                for (int i = 0; i < indentSize; i++) {
                    sb.append(" ");
                }
                sb.append(textContent).append("\n");
            }
            for (HtmlElement child : children) {
                sb.append(child.toIndentedString(indentLevel + 1, indentSize));
            }
            // 结束标签缩进
            sb.append(indent);
        } else if (textContent != null && !textContent.isEmpty()) {
            // 如果没有子元素但有文本内容，在同一行显示结束标签
            sb.append(textContent).append("</").append(tagName).append(">\n");
            return sb.toString(); // 提前返回，避免额外换行
        }

        // 结束标签（用于有子元素或没有文本内容的情况）
        sb.append("</").append(tagName).append(">\n");

        return sb.toString();
    }


    // 生成用于保存的 HTML 字符串
    public String toHtmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tagName);

        if (id != null) {
            sb.append(" id=\"").append(id).append("\"");
        }
        sb.append(">");
        if (textContent != null && !textContent.isEmpty()) {
            sb.append(textContent);
        }
        for (HtmlElement child : children) {
            sb.append(child.toHtmlString());
        }
        sb.append("</").append(tagName).append(">");
        return sb.toString();
    }
}
