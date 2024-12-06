package command;

import command.constant.CommandExceptionConstants;
import editor.Editor;
import exception.InvalidCommandException;
import model.HtmlElement;

public class EditIdCommand implements ICommand {
    private final Editor editor;
    private final String oldId;
    private final String newId;

    private HtmlElement element;

    public EditIdCommand(Editor editor, String oldId, String newId) {
        this.editor = editor;
        this.oldId = oldId;
        this.newId = newId;
    }

    @Override
    public void execute() {
        if (editor.isIdExists(newId)) {
            throw new InvalidCommandException(CommandExceptionConstants.ID_ALREADY_EXIST + newId);
        }
        if (!editor.isIdExists(oldId)) {
            throw new InvalidCommandException(CommandExceptionConstants.ELEMENT_NOT_FOUND + oldId);
        }
        element = editor.findElement(oldId);
        element.updateId(newId);
    }

    @Override
    public void undo() {
        element.updateId(oldId);
    }
}
