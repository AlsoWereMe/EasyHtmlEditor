package command;

import core.Editor;

public class Redo implements Command {
    private final Editor editor;

    public Redo(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.redo();
    }

    @Override
    public void undo() {
        // 重做操作无法再撤销
    }
}
