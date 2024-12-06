package command;

import command.constant.CommandInfoConstants;
import editor.Editor;
import tools.ElementStringTranslator;
import model.HtmlElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveCommand implements ICommand{
    private final Editor editor;
    private final String filepath;

    public SaveCommand(Editor editor, String filepath) {
        this.editor = editor;
        this.filepath = filepath;
    }

    @Override
    public void execute() {
        try {
            HtmlElement root = editor.getRoot();
            String htmlContent = ElementStringTranslator.toHtmlString(root);
            Path path = Paths.get(filepath);
            Files.writeString(path, htmlContent);
            editor.reset();
        } catch (IOException e) {
            System.out.println(CommandInfoConstants.SAVE_FAILED_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void undo() {}
}
