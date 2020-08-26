package thegeekmodule.com.readaloud;

import java.util.Locale;

/**
 * Created by Anshul Goel on 26-08-2020.
 */

public class Constants {
    public static Locale[] locales = new Locale[]{Locale.ENGLISH,Locale.GERMAN,Locale.FRENCH};

    public static int getLocaleIndex(String s) {
        if(s.equalsIgnoreCase("English")) return 0;
        else if(s.equalsIgnoreCase("German")) return 1;
        else if(s.equalsIgnoreCase("French")) return 2;
        else return 0;
    }
}
