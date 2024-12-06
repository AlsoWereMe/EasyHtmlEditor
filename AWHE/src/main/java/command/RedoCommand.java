package command;

import command.constant.CommandInfoConstants;
import editor.Editor;

public class RedoCommand implements ICommand {
    private final Editor editor;

    public RedoCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        if (editor.canRedo()) {
            ICommand command = editor.getNeedRedoCommand();
            command.execute();
            editor.insertCommandForUndo(command);
        } else {
            System.out.println(CommandInfoConstants.REDO_EMPTY_MESSAGE);
        }
    }

    @Override
    public void undo() {}
}
