package command;

import core.Editor;
import model.HtmlElement;

public class EditText implements Command {
    private final Editor editor;
    private final String elementId;
    private final String newTextContent;

    private HtmlElement element;
    private String oldTextContent;

    public EditText(Editor editor, String elementId,
                           String newTextContent) {
        this.editor = editor;
        this.elementId = elementId;
        this.newTextContent = newTextContent;
    }

    @Override
    public void execute() {
        element = editor.findElement(elementId);
        if (element == null) {
            throw new IllegalArgumentException("Element not found: " +
                    elementId);
        }
        oldTextContent = element.getTextContent();
        element.updateTextContent(newTextContent);
    }

    @Override
    public void undo() {
        element.updateTextContent(oldTextContent);
    }

}
