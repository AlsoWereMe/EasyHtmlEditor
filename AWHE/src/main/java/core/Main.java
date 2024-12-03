package core;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Simple HTML Editor.");
        System.out.println("Please enter 'read filepath' or 'init' to start.");

        boolean initialized = false;

        // 初始阶段，只接受 'read' 或 'init' 命令
        while (!initialized) {
            System.out.print("> ");
            String inputLine = scanner.nextLine().trim();

            // 处理 'read' 和 'init' 命令
            if (inputLine.startsWith("read ")) {
                editor.executeCommand(inputLine);
                initialized = true; // 设置为 true，允许继续使用其他命令
            } else if (inputLine.equalsIgnoreCase("init")) {
                editor.executeCommand("init");
                initialized = true; // 设置为 true，允许继续使用其他命令
            } else {
                System.out.println("Invalid command. Please enter 'read filepath' or 'init' to start.");
            }
        }

        while (true) {
            System.out.print("> ");
            String inputLine = scanner.nextLine();
            if ("exit".equalsIgnoreCase(inputLine)) {
                break;
            }
            editor.executeCommand(inputLine);
        }

        scanner.close();
        System.out.println("Exiting the editor. Goodbye!");
    }
}

