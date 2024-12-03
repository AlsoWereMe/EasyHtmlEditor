package command;

import core.Editor;
import model.HtmlElement;

public class EditId implements Command {
    private final Editor editor;
    private final String oldId;
    private final String newId;

    private HtmlElement element;

    public EditId(Editor editor, String oldId, String newId) {
        this.editor = editor;
        this.oldId = oldId;
        this.newId = newId;
    }

    @Override
    public void execute() {
        if (editor.idExists(newId)) {
            throw new IllegalArgumentException("ID already exists: " + newId);
        }
        element = editor.findElement(oldId);
        if (element == null) {
            throw new IllegalArgumentException("Element not found: " + oldId);
        }
        element.updateId(newId);
    }

    @Override
    public void undo() {
        element.updateId(oldId);
    }
}
