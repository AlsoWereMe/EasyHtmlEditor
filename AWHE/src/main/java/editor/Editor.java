package editor;

import command.*;
import model.ElementMap;
import model.HtmlElement;
import model.IdSet;

import java.util.Stack;

public class Editor {
    private HtmlElement root;
    private boolean initialized;
    private final Stack<ICommand> undoStack;
    private final Stack<ICommand> redoStack;

    public Editor() {
        this.root = null;
        this.initialized = false;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void reset() {
        undoStack.clear();
        redoStack.clear();
        root = null;
    }

    public void setInitialized() {
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public HtmlElement getRoot() {
        return root;
    }

    public void updateRoot(HtmlElement rootElement) {
        this.root = rootElement;
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    public ICommand getNeedUndoCommand() {
        return undoStack.pop();
    }

    public void insertCommandForUndo(ICommand command) {
        undoStack.push(command);
    }

    public ICommand getNeedRedoCommand() {
        return redoStack.pop();
    }

    public void insertCommandForRedo(ICommand command) {
        redoStack.push(command);
    }

    public void action(ICommand command) {
        command.execute();
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            undoStack.push(command);
        }
    }

    public HtmlElement findElement(String id) {
        return ElementMap.findElement(id);
    }

    public boolean isIdExists(String idValue) {
        return IdSet.isIdExists(idValue);
    }
}
