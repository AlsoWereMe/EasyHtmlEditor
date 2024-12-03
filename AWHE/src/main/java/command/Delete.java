package command;

import core.Editor;
import model.HtmlElement;

public class Delete implements Command {
    private final Editor editor;
    private final String elementId;

    private HtmlElement element;
    private HtmlElement parent;
    private int index;

    public Delete(Editor editor, String elementId) {
        this.editor = editor;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        element = editor.findElement(elementId);
        if (element == null) {
            throw new IllegalArgumentException("Element not found: " +
                    elementId);
        }
        parent = element.getParent();
        if (parent == null) {
            throw new IllegalArgumentException("Cannot delete root element");
        }
        index = parent.getChildren().indexOf(element);
        parent.removeChild(element);
    }

    @Override
    public void undo() {
        parent.getChildren().add(index, element);
        element.updateParent(parent);
    }
}
