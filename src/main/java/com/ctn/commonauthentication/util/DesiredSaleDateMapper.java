package com.ctn.commonauthentication.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DesiredSaleDateMapper {

    private static final Map<String, String> MAPPING = new HashMap<>();

    static {
        MAPPING.put("1ヵ月以内", "1ヵ月以内");
        MAPPING.put("2ヵ月以内", "2ヵ月以内");
        MAPPING.put("3ヵ月以内", "3ヵ月以内");
        MAPPING.put("6ヵ月以内", "6ヵ月以内");
        MAPPING.put("1年以内", "1年以内");
        MAPPING.put("1年以上", "1年以上");
        MAPPING.put("不明", "不明");

        MAPPING.put("1ヶ月以内", "1ヵ月以内");
        MAPPING.put("2ヶ月以内", "2ヵ月以内");
        MAPPING.put("3ヶ月以内", "3ヵ月以内");
        MAPPING.put("6ヶ月以内", "6ヵ月以内");
        MAPPING.put("1ヶ月", "1ヵ月以内");
        MAPPING.put("2ヶ月", "2ヵ月以内");
        MAPPING.put("3ヶ月", "3ヵ月以内");
        MAPPING.put("6ヶ月", "6ヵ月以内");
        MAPPING.put("1年", "1年以内");
        MAPPING.put("半年以内", "6ヵ月以内");
        MAPPING.put("半年", "6ヵ月以内");

        MAPPING.put("未定", "不明");
        MAPPING.put("すぐ", "1ヵ月以内");
        MAPPING.put("急ぎ", "1ヵ月以内");
        MAPPING.put("わからない", "不明");
        MAPPING.put("分からない", "不明");
        MAPPING.put("", "不明");
        MAPPING.put(null, "不明");
    }

    public String mapToStandardValue(String dbValue) {
        if (dbValue == null || dbValue.trim().isEmpty()) {
            return "不明";
        }

        String normalized = dbValue.trim();

        if (MAPPING.containsKey(normalized)) {
            return MAPPING.get(normalized);
        }

        if (normalized.contains("1") && normalized.contains("月")) {
            return "1ヵ月以内";
        } else if (normalized.contains("2") && normalized.contains("月")) {
            return "2ヵ月以内";
        } else if (normalized.contains("3") && normalized.contains("月")) {
            return "3ヵ月以内";
        } else if (normalized.contains("6") && normalized.contains("月") || normalized.contains("半年")) {
            return "6ヵ月以内";
        } else if (normalized.contains("1") && normalized.contains("年") && !normalized.contains("以上")) {
            return "1年以内";
        } else if (normalized.contains("以上")) {
            return "1年以上";
        }

        return "不明";
    }
}
