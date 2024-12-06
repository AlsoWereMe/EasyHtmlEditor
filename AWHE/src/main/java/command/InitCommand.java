package command;

import command.constant.CommandInfoConstants;
import constant.HtmlTagConstants;
import editor.Editor;
import model.HtmlElement;
import model.constant.HtmlElementConfigConstants;

public class InitCommand implements ICommand{

    private final Editor editor;
    public InitCommand(Editor editor) {
        this.editor = editor;
    }
    @Override
    public void execute() {
        HtmlElement root = new HtmlElement(HtmlTagConstants.HTML_TAG_STRING, HtmlTagConstants.HTML_TAG_STRING, HtmlElementConfigConstants.INVISIBLE_ID);
        HtmlElement head = new HtmlElement(HtmlTagConstants.HEAD_TAG_STRING, HtmlTagConstants.HEAD_TAG_STRING, HtmlElementConfigConstants.INVISIBLE_ID);
        HtmlElement title = new HtmlElement(HtmlTagConstants.TITLE_TAG_STRING, HtmlTagConstants.TITLE_TAG_STRING, HtmlElementConfigConstants.INVISIBLE_ID);
        HtmlElement body = new HtmlElement(HtmlTagConstants.BODY_TAG_STRING, HtmlTagConstants.BODY_TAG_STRING, HtmlElementConfigConstants.INVISIBLE_ID);
        head.insertChild(title);
        root.insertChild(head);
        root.insertChild(body);

        editor.reset();
        editor.updateRoot(root);
        editor.setInitialized();
        System.out.println(CommandInfoConstants.INIT_SUCCESS_MESSAGE);
    }

    @Override
    public void undo() {}
}
