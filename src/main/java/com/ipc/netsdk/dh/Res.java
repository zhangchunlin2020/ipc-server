package com.ipc.netsdk.dh;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Res {

    private static ResourceBundle bundle;

    private Res() {
        switchLanguage(LanguageType.Chinese);
    }

    private static class StringBundleHolder {
        private static Res instance = new Res();
    }

    public static Res string() {
        return StringBundleHolder.instance;
    }

    public static enum LanguageType {
        English,
        Chinese
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * \if ENGLISH_LANG
     * Switch between Chinese and English
     * \else
     * 中英文切换
     * \endif
     */
    public void switchLanguage(LanguageType type) {
        switch(type) {
            case Chinese:
                bundle = ResourceBundle.getBundle("res", new Locale("zh", "CN"));
                break;
            case English:
                bundle = ResourceBundle.getBundle("res", new Locale("en", "US"));
                break;
            default:
                break;
        }
    }

}



