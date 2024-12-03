package command;

import core.Editor;
import model.HtmlElement;

public class Append implements Command {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String parentId;
    private final String textContent;
    private HtmlElement newHtmlElement;
    private HtmlElement parent;

    public Append(Editor editor, String tagName, String idValue,
                         String parentId, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentId = parentId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        if (editor.idExists(idValue)) {
            throw new IllegalArgumentException("ID already exists: " + idValue);
        }
        parent = editor.findElement(parentId);
        if (parent == null) {
            throw new IllegalArgumentException("Parent element not found: " +
                    parentId);
        }
        newHtmlElement = new HtmlElement(tagName, idValue, textContent);
        parent.insertChild(newHtmlElement);
    }

    @Override
    public void undo() {
        parent.removeChild(newHtmlElement);
    }
}

