package command;

import command.constant.CommandExceptionConstants;
import editor.Editor;
import exception.InvalidCommandException;
import model.HtmlElement;

import java.util.Objects;

public class DeleteCommand implements ICommand {
    private final Editor editor;
    private final String elementId;

    private HtmlElement element;
    private HtmlElement parent;
    private int index;

    public DeleteCommand(Editor editor, String elementId) {
        this.editor = editor;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        if (!editor.isIdExists(elementId)) {
            throw new InvalidCommandException(CommandExceptionConstants.ELEMENT_NOT_FOUND + elementId);
        }
        element = editor.findElement(elementId);
        parent = element.getParent();
        if (Objects.isNull(parent)) {
            throw new InvalidCommandException(CommandExceptionConstants.DELETE_ROOT);
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
