package command;

import command.constant.CommandInfoConstants;
import editor.Editor;
import tools.ElementStringTranslator;
import model.HtmlElement;

public class PrintTreeCommand implements ICommand{
    private final Editor editor;

    public PrintTreeCommand (Editor editor){
        this.editor = editor;
    }

    @Override
    public void execute() {
        HtmlElement root = editor.getRoot();
        if (root != null) {
            String result = ElementStringTranslator.toTreeString(root);
            System.out.print(result);
        } else {
            System.out.println(CommandInfoConstants.PRINT_TREE_EMPTY_MESSAGE);
        }
    }

    @Override
    public void undo() {}
}
