package core;

import command.*;
import model.HtmlElement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Objects;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Editor {
    private HtmlElement root;
    private final Parser parser;
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    private boolean canRedo;

    public Editor() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.parser = new Parser(this);
        this.canRedo = true;
    }

    // 执行用户命令
    public void executeCommand(String inputLine) {
        try {
            Command command = parser.parseCommand(inputLine);
            if (command != null) {
                if (command instanceof Undo || command instanceof Redo) {
                    command.execute();
                } else {
                    command.execute();
                    undoStack.push(command);
                    if (canRedo) {
                        redoStack.clear();
                    }
                    canRedo = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 撤销操作
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            canRedo = true;
        } else {
            System.out.println("No actions to undo.");
        }
    }

    // 重做操作
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            canRedo = true;
        } else {
            System.out.println("No actions to redo.");
        }
    }

    // 检查 ID 是否存在
    public boolean idExists(String id) {
        if (root != null) {
            return root.isIdExistsInTree(id);
        }
        return false;
    }

    // 根据 ID 查找元素
    public HtmlElement findElement(String id) {
        if (root != null) {
            return root.getSingleChildById(id);
        }
        return null;
    }

    // 初始化编辑器
    public void init() {
        this.root = new HtmlElement("html", "html", null);

        HtmlElement head = new HtmlElement("head", "head", null);
        HtmlElement title = new HtmlElement("title", "title", null);
        head.insertChild(title);

        HtmlElement body = new HtmlElement("body", "body", null);

        root.insertChild(head);
        root.insertChild(body);

        undoStack.clear();
        redoStack.clear();
        canRedo = false;

        System.out.println("Editor initialized with empty HTML template.");
    }

    // 读取 HTML 文件
    public void readFile(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Invalid read command.");
            return;
        }
        String filepath = tokens[1];
        try {
            String htmlContent = new String(
                    Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
            Document doc = Jsoup.parse(htmlContent);
            this.root = convertJsoupElementToHtmlElement(Objects.requireNonNull(doc.body().parent()));
            undoStack.clear();
            redoStack.clear();
            canRedo = false;
            System.out.println("File read successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // 将 Jsoup 的 Element 转换为 HtmlElement
    private HtmlElement convertJsoupElementToHtmlElement(Element jsoupElement) {
        String tagName = jsoupElement.tagName();
        String id = jsoupElement.id();
        if (id.isEmpty()) {
            if (tagName.equals("html") || tagName.equals("head") ||
                    tagName.equals("title") || tagName.equals("body")) {
                id = tagName;
            } else {
                id = null;
            }
        }

        String textContent = jsoupElement.ownText();
        HtmlElement element = new HtmlElement(tagName, id, textContent);

        for (Element child : jsoupElement.children()) {
            HtmlElement childElement = convertJsoupElementToHtmlElement(child);
            element.insertChild(childElement);
        }

        return element;
    }

    // 保存 HTML 文件
    public void saveFile(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Invalid save command.");
            return;
        }
        String filepath = tokens[1];
        try {
            String htmlContent = generateHtmlString(root);
            Files.write(Paths.get(filepath),
                    htmlContent.getBytes(StandardCharsets.UTF_8));
            undoStack.clear();
            redoStack.clear();
            canRedo = false;

            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // 生成 HTML 字符串
    private String generateHtmlString(HtmlElement element) {
        return element.toHtmlString();
    }

    // 显示缩进格式的 HTML
    public void printIndent(String[] tokens) {
        int indentSize = 2;
        if (tokens.length == 2) {
            try {
                indentSize = Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid indent size, using default value 2.");
            }
        }
        if (root != null) {
            String result = root.toIndentedString(0, indentSize);
            System.out.print(result);
        } else {
            System.out.println("No content to display.");
        }
    }

    // 显示树型结构
    public void printTree() {
        if (root != null) {
            printTreeRecursive(root, "", true);
        } else {
            System.out.println("No content to display.");
        }
    }

    private void printTreeRecursive(HtmlElement element, String prefix, boolean isLast) {
        // 打印当前元素的标签和 ID
        System.out.print(prefix + (isLast ? "└── " : "├── "));
        System.out.print(element.getTagName());
        System.out.println();

        // 更新前缀
        prefix += isLast ? "    " : "│   ";

        // 递归打印子元素
        for (int i = 0; i < element.getChildren().size(); i++) {
            HtmlElement child = element.getChildren().get(i);
            boolean lastChild = (i == element.getChildren().size() - 1);

            // 打印文本内容（如果有的话）
            if (i == 0 && element.getTextContent() != null && !element.getTextContent().isEmpty()) {
                System.out.print(prefix + "└── ");
                System.out.println(element.getTextContent());
            }

            // 递归打印子元素
            printTreeRecursive(child, prefix, lastChild);
        }
    }


    // 设置根元素（用于测试）
    public void setRoot(HtmlElement root) {
        this.root = root;
    }

    // 获取根元素
    public HtmlElement getRoot() {
        return this.root;
    }
}
