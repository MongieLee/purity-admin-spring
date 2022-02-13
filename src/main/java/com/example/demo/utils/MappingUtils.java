package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 */
public class MappingUtils {
    public static Map<String, Object> asMap(Object... args) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            result.put(args[i].toString(), args[i + 1]);
        }
        return result;
    }
}
