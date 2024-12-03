package command;

import core.Editor;
import model.HtmlElement;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class SpellCheck implements Command {
    private final Editor editor;
    private final JLanguageTool langTool;

    public SpellCheck(Editor editor) {
        this.editor = editor;
        this.langTool = new JLanguageTool(new AmericanEnglish());
    }

    @Override
    public void execute() {
        HtmlElement root = editor.getRoot(); // 获取根元素
        boolean error = checkElement(root);
        if (!error) {
            System.out.println("Nothing wrong.");
        }
    }

    private boolean checkElement(HtmlElement element) {
        boolean error = false;
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            try {
                List<RuleMatch> matches = langTool.check(element.getTextContent());
                if (!matches.isEmpty()) {
                    System.out.println("Spell check warnings for element ID: " + element.getId());
                    for (RuleMatch match : matches) {
                        int fromPos = match.getFromPos();
                        int toPos = match.getToPos();

                        // 打印错误的起始和结束位置
                        System.out.println("Potential error from position " + fromPos +
                                " to position " + toPos + ": " + match.getMessage());
                        System.out.println("Suggested correction(s): " +
                                match.getSuggestedReplacements());
                        error = true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error during spell check: " + e.getMessage());
            }
        }
        // 递归检查子元素
        for (HtmlElement child : element.getChildren()) {
            checkElement(child);
        }
        return error;
    }


    @Override
    public void undo() {
        // SpellCheck 命令无需撤销操作
    }
}
