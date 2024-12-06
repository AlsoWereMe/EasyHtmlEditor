package command;

import command.constant.CommandInfoConstants;
import constant.HtmlTagConstants;
import editor.Editor;
import model.HtmlElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ReadCommand implements ICommand{
    private final Editor editor;
    private final String filepath;

    public ReadCommand(Editor editor, String filepath) {
        this.editor = editor;
        this.filepath = filepath;
    }

    private HtmlElement convertJsoupElementToHtmlElement(Element jsoupElement) {
        String tagName = jsoupElement.tagName();
        String id = jsoupElement.id();
        if (id.isEmpty()
                && (HtmlTagConstants.HTML_TAG_STRING.equals(tagName)
                || HtmlTagConstants.HEAD_TAG_STRING.equals(tagName)
                || HtmlTagConstants.TITLE_TAG_STRING.equals(tagName)
                || HtmlTagConstants.BODY_TAG_STRING.equals(tagName))) {
            id = tagName;
        }

        String textContent = jsoupElement.ownText();
        HtmlElement element = new HtmlElement(tagName, id, textContent);

        for (Element child : jsoupElement.children()) {
            HtmlElement childElement = convertJsoupElementToHtmlElement(child);
            element.insertChild(childElement);
        }

        return element;
    }

    @Override
    public void execute() {
        try {
            Path path = Paths.get(filepath);
            String htmlContent = Files.readString(path);
            Document doc = Jsoup.parse(htmlContent);
            editor.reset();
            HtmlElement root = convertJsoupElementToHtmlElement(Objects.requireNonNull(doc.body().parent()));
            editor.updateRoot(root);
            editor.setInitialized();
        } catch (IOException e) {
            System.out.println(CommandInfoConstants.READ_FAILED_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void undo() {}
}
