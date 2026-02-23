package com.ctn.commonauthentication.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColorMapper {

    // Allowed color values
    private static final String WHITE = "ホワイト";
    private static final String BLACK = "ブラック";
    private static final String SILVER = "シルバー";
    private static final String RED = "レッド";
    private static final String ORANGE = "オレンジ";
    private static final String GREEN = "グリーン";
    private static final String BLUE = "ブルー";
    private static final String BROWN = "ブラウン";
    private static final String YELLOW = "イエロー";
    private static final String PINK = "ピンク";
    private static final String PEARL = "パール";
    private static final String PURPLE = "パープル";
    private static final String GOLD = "ゴールド";
    private static final String GRAY = "グレー";
    private static final String UNKNOWN = "不明";

    private static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        // White variations
        COLOR_MAP.put("ホワイト", WHITE);
        COLOR_MAP.put("白", WHITE);
        COLOR_MAP.put("white", WHITE);
        COLOR_MAP.put("しろ", WHITE);
        COLOR_MAP.put("ホワイトパール", WHITE);
        COLOR_MAP.put("パールホワイト", WHITE);

        // Black variations
        COLOR_MAP.put("ブラック", BLACK);
        COLOR_MAP.put("黒", BLACK);
        COLOR_MAP.put("black", BLACK);
        COLOR_MAP.put("くろ", BLACK);
        COLOR_MAP.put("ブラックマイカ", BLACK);

        // Silver variations
        COLOR_MAP.put("シルバー", SILVER);
        COLOR_MAP.put("銀", SILVER);
        COLOR_MAP.put("silver", SILVER);
        COLOR_MAP.put("ぎん", SILVER);
        COLOR_MAP.put("シルバーメタリック", SILVER);

        // Red variations
        COLOR_MAP.put("レッド", RED);
        COLOR_MAP.put("赤", RED);
        COLOR_MAP.put("red", RED);
        COLOR_MAP.put("あか", RED);
        COLOR_MAP.put("レッドマイカ", RED);

        // Orange variations
        COLOR_MAP.put("オレンジ", ORANGE);
        COLOR_MAP.put("orange", ORANGE);
        COLOR_MAP.put("オレンジメタリック", ORANGE);

        // Green variations
        COLOR_MAP.put("グリーン", GREEN);
        COLOR_MAP.put("緑", GREEN);
        COLOR_MAP.put("green", GREEN);
        COLOR_MAP.put("みどり", GREEN);
        COLOR_MAP.put("グリーンメタリック", GREEN);

        // Blue variations
        COLOR_MAP.put("ブルー", BLUE);
        COLOR_MAP.put("青", BLUE);
        COLOR_MAP.put("blue", BLUE);
        COLOR_MAP.put("あお", BLUE);
        COLOR_MAP.put("ブルーメタリック", BLUE);
        COLOR_MAP.put("ネイビー", BLUE);
        COLOR_MAP.put("紺", BLUE);

        // Brown variations
        COLOR_MAP.put("ブラウン", BROWN);
        COLOR_MAP.put("茶", BROWN);
        COLOR_MAP.put("brown", BROWN);
        COLOR_MAP.put("ちゃ", BROWN);
        COLOR_MAP.put("ブラウンメタリック", BROWN);
        COLOR_MAP.put("ベージュ", BROWN);

        // Yellow variations
        COLOR_MAP.put("イエロー", YELLOW);
        COLOR_MAP.put("黄", YELLOW);
        COLOR_MAP.put("yellow", YELLOW);
        COLOR_MAP.put("き", YELLOW);
        COLOR_MAP.put("イエローメタリック", YELLOW);

        // Pink variations
        COLOR_MAP.put("ピンク", PINK);
        COLOR_MAP.put("pink", PINK);
        COLOR_MAP.put("ピンクメタリック", PINK);

        // Pearl variations
        COLOR_MAP.put("パール", PEARL);
        COLOR_MAP.put("pearl", PEARL);
        COLOR_MAP.put("真珠", PEARL);

        // Purple variations
        COLOR_MAP.put("パープル", PURPLE);
        COLOR_MAP.put("紫", PURPLE);
        COLOR_MAP.put("purple", PURPLE);
        COLOR_MAP.put("むらさき", PURPLE);
        COLOR_MAP.put("バイオレット", PURPLE);

        // Gold variations
        COLOR_MAP.put("ゴールド", GOLD);
        COLOR_MAP.put("金", GOLD);
        COLOR_MAP.put("gold", GOLD);
        COLOR_MAP.put("きん", GOLD);
        COLOR_MAP.put("ゴールドメタリック", GOLD);

        // Gray variations
        COLOR_MAP.put("グレー", GRAY);
        COLOR_MAP.put("灰", GRAY);
        COLOR_MAP.put("gray", GRAY);
        COLOR_MAP.put("grey", GRAY);
        COLOR_MAP.put("はい", GRAY);
        COLOR_MAP.put("グレーメタリック", GRAY);
        COLOR_MAP.put("ダークグレー", GRAY);
        COLOR_MAP.put("ライトグレー", GRAY);
    }

    public static String mapColor(String inputColor) {
        if (inputColor == null || inputColor.trim().isEmpty()) {
            return UNKNOWN;
        }

        String normalized = inputColor.trim().toLowerCase();

        String mapped = COLOR_MAP.get(normalized);
        if (mapped != null) {
            return mapped;
        }

        mapped = COLOR_MAP.get(inputColor.trim());
        if (mapped != null) {
            return mapped;
        }

        for (Map.Entry<String, String> entry : COLOR_MAP.entrySet()) {
            if (normalized.contains(entry.getKey().toLowerCase()) ||
                    inputColor.trim().contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return UNKNOWN;
    }

    public static Set<String> getValidColors() {
        return Set.of(WHITE, BLACK, SILVER, RED, ORANGE, GREEN, BLUE,
                BROWN, YELLOW, PINK, PEARL, PURPLE, GOLD, GRAY, UNKNOWN);
    }
}
