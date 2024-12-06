package command;

import command.constant.CommandExceptionConstants;
import editor.Editor;
import exception.InvalidCommandException;
import model.HtmlElement;

public class AppendCommand implements ICommand {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String parentId;
    private final String textContent;
    private HtmlElement newHtmlElement;
    private HtmlElement parent;

    public AppendCommand(Editor editor, String tagName, String idValue, String parentId, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentId = parentId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        if (editor.isIdExists(idValue)) {
            throw new InvalidCommandException(CommandExceptionConstants.ID_ALREADY_EXIST + idValue);
        }
        if (!editor.isIdExists(parentId)) {
            throw new InvalidCommandException(CommandExceptionConstants.PARENT_NOT_FOUND + parentId);
        }
        parent = editor.findElement(parentId);
        newHtmlElement = new HtmlElement(tagName, idValue, textContent);
        parent.insertChild(newHtmlElement);
    }

    @Override
    public void undo() {
        parent.removeChild(newHtmlElement);
    }
}

