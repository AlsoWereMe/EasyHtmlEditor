package model;

import java.util.HashMap;
import java.util.Map;

public class ElementMap {
    private ElementMap() {
    }

    private static final Map<String, HtmlElement> ELEMENT_MAP = new HashMap<>();

    public static void insertElement(String id, HtmlElement element) {
        ELEMENT_MAP.put(id, element);
    }

    public static HtmlElement findElement(String id) {
        return ELEMENT_MAP.get(id);
    }

    public static void removeElement(String id) {
        ELEMENT_MAP.remove(id);
    }
}
