package tools;

import model.HtmlElement;
import model.constant.PrintConstants;

public class ElementStringTranslator {
    public static String toIndentedString(int indentLevel, int indentSize, HtmlElement htmlElement) {
        StringBuilder res = new StringBuilder();
        StringBuilder indent = new StringBuilder();

        // Initialize indent format.
        int indentSpacesCount = indentLevel * indentSize;
        indent.append(PrintConstants.SPACE.repeat(indentSpacesCount));

        String tagName = htmlElement.getTagName();
        String id = htmlElement.getId();

        // Append the string which corresponding to first line in html element.
        res.append(indent).append(PrintConstants.LEFT_ANGLE_BRACKET).append(tagName);
        if (htmlElement.isIdVisible() && id != null) {
            res.append(PrintConstants.LEFT_ID_FORMAT).append(id).append(PrintConstants.RIGHT_ID_FORMAT);
        }
        res.append(PrintConstants.RIGHT_ANGLE_BRACKET);

        // If there are children, change the line then append text and recursively call function.
        String textContent = htmlElement.getTextContent();
        if (htmlElement.hasChildren()) {
            // Firstly, change line.
            res.append("\n");
            // Second, append text.
            if (!textContent.isEmpty()) {
                res.append(indent).append(PrintConstants.SPACE.repeat(indentSize)).append(textContent).append("\n");
            }
            // The last, call function to recursive.
            for (HtmlElement child : htmlElement.getChildren()) {
                res.append(toIndentedString(indentLevel + 1, indentSize, child));
            }
            res.append(indent);
        } else {
            // Otherwise, do not change line and append text directly.
            res.append(textContent);
        }

        res.append(PrintConstants.LEFT_END_TAG_FORMAT).append(tagName)
                .append(PrintConstants.RIGHT_END_TAG_FORMAT);

        return res.toString();
    }

    /**
     * 开始构建树状结构字符串的入口方法
     *
     * @param root 树的根节点
     * @return 构建好的树状结构字符串
     */
    public static String toTreeString(HtmlElement root) {
        StringBuilder sb = new StringBuilder();
        getTreeStructure(root, sb, "", true);
        return sb.toString();
    }

    /**
     * 打印HtmlElement树的结构
     *
     * @param element     根元素
     * @param res          StringBuilder对象，用于构建最终的字符串
     * @param prefix      前缀字符串，用于表示当前节点的层级
     * @param isLastChild 是否是兄弟节点中的最后一个节点
     */
    private static void getTreeStructure(HtmlElement element, StringBuilder res, String prefix, boolean isLastChild) {
        // 根据是否是最后一个子节点来决定使用哪种符号
        String connector = isLastChild ? PrintConstants.RIGHT_ANGLE_LINE_SYMBOL : PrintConstants.T_PIPE_SYMBOL;
        res.append(prefix).append(connector);

        // 拼接标签名，如果可见则拼接ID
        String tagAndId = element.getTagName();
        if (element.isIdVisible() && element.getId() != null) {
            tagAndId += PrintConstants.ID_PREFIX + element.getId();
        }
        res.append(tagAndId);

        // 如果有文本内容，则追加
        String textContent = element.getTextContent();
        if (!textContent.isEmpty()) {
            res.append("\n").append(prefix);

            if (!isLastChild) {
                res.append(PrintConstants.VERTICAL_BAR_SYMBOL);
            } else {
                res.append(PrintConstants.SPACE.repeat(PrintConstants.DEFAULT_INDENT_SIZE));
            }

            if (!element.hasChildren()) {
                res.append(PrintConstants.RIGHT_ANGLE_LINE_SYMBOL).append(textContent);
            } else {
                res.append(PrintConstants.T_PIPE_SYMBOL).append(textContent);
            }
        }
        res.append("\n");

        // 遍历子节点
        for (HtmlElement child : element.getChildren()) {
            boolean isLast = child == element.lastChild();
            StringBuilder childPrefix = new StringBuilder(prefix);
            if(isLastChild) {
                childPrefix.append(PrintConstants.SPACE.repeat(PrintConstants.DEFAULT_INDENT_SIZE));
            } else {
                childPrefix.append(PrintConstants.VERTICAL_BAR_SYMBOL);
            }
            getTreeStructure(child, res, childPrefix.toString(), isLast);
        }
    }

    public static String toHtmlString(HtmlElement htmlElement) {
        StringBuilder res = new StringBuilder();
        String tagName = htmlElement.getTagName();
        String id = htmlElement.getId();
        String textContent = htmlElement.getTextContent();
        res.append(PrintConstants.LEFT_ANGLE_BRACKET).append(tagName);

        if (id != null) {
            res.append(PrintConstants.LEFT_ID_FORMAT).append(id).append(PrintConstants.RIGHT_ID_FORMAT);
        }
        res.append(PrintConstants.RIGHT_ANGLE_BRACKET);
        if (textContent != null && !textContent.isEmpty()) {
            res.append(textContent);
        }
        for (HtmlElement child : htmlElement.getChildren()) {
            res.append(toHtmlString(child));
        }
        // Html字符串末尾不需要换行
        res.append(PrintConstants.LEFT_END_TAG_FORMAT).append(tagName).append(PrintConstants.RIGHT_ANGLE_BRACKET);
        return res.toString();
    }
}
