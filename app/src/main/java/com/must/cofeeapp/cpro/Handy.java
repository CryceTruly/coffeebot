package com.must.cofeeapp.cpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import java.util.Locale;

public class Handy {
    private static final String TAG = "Handy";
    //----------------SORTING HING ISSUES ----------------------


    public static long fitnessNumber() {
        long x = System.currentTimeMillis();

        long y = -x;


        return y;
    }

    public static Double fitnessPoint(Double d) {
        Double x = d;

        Double y = -x;


        return y;
    }


    //--------------------REFRESHES THE GALLERY TO SHOW THE SAVED PCTURES--------------------//
    public static void scanFile(Context context, Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }

    //--------------------REFRESHES THE GALLERY TO SHOW THE SAVED PCTURES--------------------//
    public static boolean isAlphnuemeric(String string) {
        return string.matches("[A-Za-z0-9]+");


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //----------------CAPITALISES LETTERS-----------JUST LIKE PHP UCWORDS--------------//
    public static String getTrimmedName(String sentence) {

        String c = (sentence != null) ? sentence.trim() : "";
        String[] words = c.split(" ");
        String result = "";
        for (String w : words) {
            result += (w.length() > 1 ? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w) + " ";
        }
        return result.trim();

    }

    //------------CAPITALISES FIRST,ONCE IN SENTENCE
    public static String capitalize(final String line) {

        String c = (line != null) ? line.trim() : "";
        try {
            return Character.toUpperCase(c.charAt(0)) + c.substring(1);
        } catch (StringIndexOutOfBoundsException e) {
            return " ";
        }
    }

}