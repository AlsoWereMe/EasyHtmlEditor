package model;

import java.util.HashSet;
import java.util.Set;

public class IdSet {
    // 私有化构造函数，防止外部通过new创建实例
    private IdSet() {
    }

    private static final Set<String> ID_SET = new HashSet<>();

    public static void insertId(String id) {
        ID_SET.add(id);
    }

    public static boolean isIdExists(String id) {
        return ID_SET.contains(id);
    }
}
