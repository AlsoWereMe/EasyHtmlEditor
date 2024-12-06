package command;

import command.constant.CommandExceptionConstants;
import exception.InvalidCommandException;
import model.HtmlElement;
import editor.Editor;

import java.util.Objects;

public class InsertCommand implements ICommand {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String insertLocationId;
    private final String textContent;

    private HtmlElement newHtmlElement;
    private HtmlElement parent;

    public InsertCommand(Editor editor, String tagName, String idValue,
                         String insertLocationId, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocationId = insertLocationId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        if (editor.isIdExists(idValue)) {
            throw new InvalidCommandException(CommandExceptionConstants.ID_ALREADY_EXIST + idValue);
        }
        if (!editor.isIdExists(insertLocationId)) {
            throw new InvalidCommandException(CommandExceptionConstants.LOCATION_NOT_FOUND + insertLocationId);
        }
        HtmlElement insertLocation = editor.findElement(insertLocationId);
        parent = insertLocation.getParent();
            if (Objects.isNull(parent)) {
            throw new InvalidCommandException(CommandExceptionConstants.INSERT_BEFORE_ROOT);
        }
        newHtmlElement = new HtmlElement(tagName, idValue, textContent);
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