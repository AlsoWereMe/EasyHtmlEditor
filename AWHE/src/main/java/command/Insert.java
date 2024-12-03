package command;

import model.HtmlElement;
import core.Editor;
public class Insert implements Command {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String insertLocationId;
    private final String textContent;

    private HtmlElement newHtmlElement;
    private HtmlElement parent;

    public Insert(Editor editor, String tagName, String idValue,
                         String insertLocationId, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocationId = insertLocationId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        if (editor.idExists(idValue)) {
            throw new IllegalArgumentException("ID already exists: " + idValue);
        }
        HtmlElement insertLocation = editor.findElement(insertLocationId);
        if (insertLocation == null) {
            throw new IllegalArgumentException("Insert location not found: " +
                    insertLocationId);
        }
        newHtmlElement = new HtmlElement(tagName, idValue, textContent);
        parent = insertLocation.getParent();
        if (parent == null) {
            throw new IllegalArgumentException("Cannot insert before root element");
        }
        int index = parent.getChildren().indexOf(insertLocation);
        parent.getChildren().add(index, newHtmlElement);
        newHtmlElement.updateParent(parent);
    }

    @Override
    public void undo() {
        parent.getChildren().remove(newHtmlElement);
        newHtmlElement.updateParent(null);
    }
}