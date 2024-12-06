package command;

import command.constant.CommandInfoConstants;
import editor.Editor;

public class UndoCommand implements ICommand {
    private final Editor editor;

    public UndoCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        if (editor.canUndo()) {
            ICommand command = editor.getNeedUndoCommand();
            command.undo();
            editor.insertCommandForRedo(command);
        } else {
            System.out.println(CommandInfoConstants.UNDO_EMPTY_MESSAGE);
        }
    }

    @Override
    public void undo() {}
}
