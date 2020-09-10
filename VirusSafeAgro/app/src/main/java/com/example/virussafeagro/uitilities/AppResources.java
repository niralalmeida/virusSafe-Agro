package com.example.virussafeagro.uitilities;

import android.graphics.Color;

import com.example.virussafeagro.R;

public class AppResources {
    public final static int COlOR_RIGHT_ANSWER = Color.rgb(30,132,73);
    public final static int COlOR_WRONG_ANSWER = Color.rgb(192,57,43);
    public final static int COlOR_CORRECT_ANSWER = Color.rgb(46,134,193);
    public final static int COlOR_RESULT_ITEM_RIGHT_BG = Color.rgb(212, 239, 223 );
    public final static int COlOR_RESULT_ITEM_WRONG_BG = Color.rgb(250, 219, 216);

    public final static String FRAGMENT_TAG_HOME = "home";
    public final static String FRAGMENT_TAG_VIRUS_INFO = "virus_info";
    public final static String FRAGMENT_TAG_VIRUS_CHECK = "virus_check";
    public final static String FRAGMENT_TAG_VIRUS_QUIZ = "virus_quiz";
    public final static String FRAGMENT_TAG_MORE = "more";
    public final static String FRAGMENT_TAG_ABOUT = "about";
    public final static String FRAGMENT_TAG_VIRUS_DETAIL = "virus_detail";
    public final static String FRAGMENT_TAG_VIRUS_QUIZ_QUESTION = "virus_quiz_question";
    public final static String FRAGMENT_TAG_VIRUS_QUIZ_RESULT = "virus_quiz_result";
    public final static String FRAGMENT_TAG_VIRUS_CHECK_RESULT = "virus_check_result";


    public static int getVirusPictureDrawableId(int virusId) {
        int virusDrawablePictureResource = -1;
        switch (virusId){
            case 1:
                virusDrawablePictureResource = R.drawable.one;
                break;
            case 2:
                virusDrawablePictureResource = R.drawable.two;
                break;
            case 3:
                virusDrawablePictureResource = R.drawable.three;
                break;
            case 4:
                virusDrawablePictureResource = R.drawable.four;
                break;
            case 5:
                virusDrawablePictureResource = R.drawable.five;
                break;
            case 6:
                virusDrawablePictureResource = R.drawable.six;
                break;
            case 7:
                virusDrawablePictureResource = R.drawable.seven;
                break;
            case 8:
                virusDrawablePictureResource = R.drawable.eight;
                break;
            case 9:
                virusDrawablePictureResource = R.drawable.nine;
                break;
        }
        return virusDrawablePictureResource;
    }

    public static int getVirusIdByVirusFullName(String virusFullName) {
        int virusId = 0;
        switch (virusFullName) {
            case "TOMATO MOSAIC VIRUS":
                virusId = 1;
                break;
            case "TARGET SPOT":
                virusId = 2;
                break;
            case "BACTERIAL SPOT":
                virusId = 3;
                break;
            case "TOMATO YELLOW LEAF CURL VIRUS":
                virusId = 4;
                break;
            case "TOMATO LATE BLIGHT":
                virusId = 5;
                break;
            case "LEAF MOLD":
                virusId = 6;
                break;
            case "EARLY BLIGHT":
                virusId = 7;
                break;
            case "TWO-SPOTTED SPIDER MITE":
                virusId = 8;
                break;
            case "SEPTORIA LEAF SPOT":
                virusId = 9;
                break;
        }
        return virusId;
    }
}
