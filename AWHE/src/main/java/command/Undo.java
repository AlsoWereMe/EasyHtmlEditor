package command;

import core.Editor;

public class Undo implements Command {
    private final Editor editor;

    public Undo(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.undo();
    }

    @Override
    public void undo() {
        // 撤销操作无法再撤销
    }
}
