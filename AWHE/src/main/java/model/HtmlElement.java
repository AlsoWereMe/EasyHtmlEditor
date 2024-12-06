package model;

import model.constant.HtmlElementConfigConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlsoWereMe
 *
 */
public class HtmlElement {
    private String tagName;
    private String id;
    private boolean idVisible;
    private String textContent;
    private HtmlElement parent;
    private final List<HtmlElement> children;

    public HtmlElement(String tagName, String id, String textContent) {
        this.tagName = tagName;
        this.id = id;
        this.idVisible = true;
        this.textContent = textContent;
        this.parent = null;
        this.children = new ArrayList<>();
        this.init();
    }

    public HtmlElement(String tagName, String id) {
        this.tagName = tagName;
        this.id = id;
        this.idVisible = HtmlElementConfigConstants.VISIBLE_ID;
        this.textContent = HtmlElementConfigConstants.DEFAULT_TEXT_CONTENT;
        this.parent = null;
        this.children = new ArrayList<>();
        this.init();
    }

    public HtmlElement(String tagName, String id, boolean idVisible) {
        this.tagName = tagName;
        this.id = id;
        this.idVisible = idVisible;
        this.textContent = HtmlElementConfigConstants.DEFAULT_TEXT_CONTENT;
        this.parent = null;
        this.children = new ArrayList<>();
        this.init();
    }

    private void init() {
        ElementMap.insertElement(id, this);
    }

    public void setIdVisible(boolean idVisible) {
        this.idVisible = idVisible;
    }

    public boolean isIdVisible() {
        return idVisible;
    }

    public String getTagName() {
        return tagName;
    }

    public String getId() {
        return id;
    }

    public String getTextContent() {
        return textContent == null ? "" : textContent;
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

    public HtmlElement lastChild() {
        return children.getLast();
    }

    public HtmlElement getParent() {
        return parent;
    }

    public void updateTagName(String tagName) {
        this.tagName = tagName;
    }

    public void updateId(String id) {
        this.id = id;
    }

    public void updateTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void updateParent(HtmlElement parent) {
        this.parent = parent;
    }

    public void insertChild(HtmlElement child) {
        children.add(child);
        child.updateParent(this);
    }

    public void removeChild(HtmlElement child) {
        children.remove(child);
        child.updateParent(null);
        ElementMap.removeElement(child.getId());
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}
