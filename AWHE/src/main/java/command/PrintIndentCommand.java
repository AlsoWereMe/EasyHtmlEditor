package command;

import command.constant.CommandInfoConstants;
import editor.Editor;
import tools.ElementStringTranslator;
import model.HtmlElement;

public class PrintIndentCommand implements ICommand{
    private final Editor editor;
    private final int indentSize;
    public PrintIndentCommand(Editor editor, int indentSize) {
        this.editor = editor;
        this.indentSize = indentSize;
    }

    @Override
    public void execute() {
        HtmlElement root = editor.getRoot();
        if (root != null) {
            String result = ElementStringTranslator.toIndentedString(0, indentSize, root);
            System.out.print(result);
        } else {
            System.out.println(CommandInfoConstants.PRINT_INDENT_EMPTY_MESSAGE);
        }
    }

    @Override
    public void undo() {}
}

