package lekkit.scev.gui.util;

import java.util.HashMap;

import net.minecraft.util.StatCollector;

public class LocaleUtil {
    private static HashMap<String, String> stringCache = new HashMap<String, String>();
    private static int cacheRenew = 0;

    public static boolean canTranslate(String key) {
        if (cachedTranslate(key) != null) {
            cacheUse();
            return true;
        }
        return StatCollector.canTranslate(key);
    }

    public static String translate(String key) {
        String translation = cachedTranslate(key);
        if (translation != null) {
            cacheUse();
            return translation;
        }
        translation = key;
        if (canTranslate(translation)) {
            translation = StatCollector.translateToLocal(key);
        }
        cachePut(key, translation);
        return translation;
    }

    public static String translateText(String text) {
        String translation = cachedTranslate(text);
        if (translation != null) {
            cacheUse();
            return translation;
        }

        String result = "";
        while (text.length() > 0) {
            String key = getKey(text, false);
            text = text.substring(key.length());
            if (key.length() > 0) {
                result += translate(key);
            }

            String trailing = getKey(text, true);
            text = text.substring(trailing.length());
            result += trailing;
        }

        cachePut(text, result);

        return result;
    }

    private static String cachedTranslate(String key) {
        return stringCache.get(key);
    }

    private static void cachePut(String key, String translation) {
        cacheUse();
        stringCache.put(key, translation);
    }

    private static void cacheUse() {
        if (cacheRenew++ > 100) {
            stringCache.clear();
        }
    }

    private static boolean isKeyChar(char c) {
        return c == '.' || c == '_'
            || (c >= 'a' && c <= 'z')
            || (c >= 'A' && c <= 'Z')
            || (c >= '0' && c <= '9');
    }

    private static String getKey(String text, boolean trailing) {
        int i = 0;
        while ((i < text.length()) && (isKeyChar(text.charAt(i)) != trailing)) {
            i++;
        }
        if (i > 0) {
            return text.substring(0, i);
        }
        return "";
    }
}
