package command;

import command.constant.CommandExceptionConstants;
import editor.Editor;
import model.HtmlElement;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class SpellCheckCommand implements ICommand {
    private final static String ID_SPELL_CHECK_WARNING = "Spell check warnings for element ID: ";
    private final static String BEGIN_POSITION = "Potential error from position ";
    private final static String END_POSITION = " to position ";
    private final static String SUGGESTED_CORRECTION = "Suggested correction(s): ";
    private final static String ALL_OK = "Nothing wrong.";

    private final Editor editor;
    private final JLanguageTool langTool;

    public SpellCheckCommand(Editor editor) {
        this.editor = editor;
        this.langTool = new JLanguageTool(new AmericanEnglish());
    }

    private boolean checkElement(HtmlElement element) {
        boolean error = false;
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            try {
                List<RuleMatch> matches = langTool.check(element.getTextContent());
                if (!matches.isEmpty()) {
                    System.out.println( ID_SPELL_CHECK_WARNING + element.getId());
                    for (RuleMatch match : matches) {
                        int fromPos = match.getFromPos();
                        int toPos = match.getToPos();
                        System.out.println(BEGIN_POSITION + fromPos + END_POSITION + toPos + "\n"
                            + match.getMessage() + "\n"
                            + SUGGESTED_CORRECTION + match.getSuggestedReplacements());
                        error = true;
                    }
                }
            } catch (IOException e) {
                System.err.println(CommandExceptionConstants.SPELL_CHECK_ERROR);
            }
        }
        // 递归检查子元素
        for (HtmlElement child : element.getChildren()) {
            checkElement(child);
        }
        return error;
    }

    @Override
    public void execute() {
        HtmlElement root = editor.getRoot();
        if (!checkElement(root)) {
            System.out.println(ALL_OK);
        }
    }

    @Override
    public void undo() {}
}
