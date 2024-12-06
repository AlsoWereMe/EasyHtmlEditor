package command;

import command.constant.CommandExceptionConstants;
import editor.Editor;
import exception.InvalidCommandException;
import model.HtmlElement;

public class EditTextCommand implements ICommand {
    private final Editor editor;
    private final String elementId;
    private final String newTextContent;

    private HtmlElement element;
    private String oldTextContent;

    public EditTextCommand(Editor editor, String elementId,
                           String newTextContent) {
        this.editor = editor;
        this.elementId = elementId;
        this.newTextContent = newTextContent;
    }

    @Override
    public void execute() {
        if (!editor.isIdExists(elementId)) {
            throw new InvalidCommandException(CommandExceptionConstants.ELEMENT_NOT_FOUND + elementId);
        }
        element = editor.findElement(elementId);
        oldTextContent = element.getTextContent();
        element.updateTextContent(newTextContent);
    }

    @Override
    public void undo() {
        element.updateTextContent(oldTextContent);
    }

}
