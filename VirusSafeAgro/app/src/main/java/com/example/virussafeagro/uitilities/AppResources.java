package com.example.virussafeagro.uitilities;

import android.graphics.Color;

import com.example.virussafeagro.R;

public class AppResources {
    public final static int COlOR_RIGHT_ANSWER = Color.rgb(30,132,73);
    public final static int COlOR_WRONG_ANSWER = Color.rgb(192,57,43);
    public final static int COlOR_CORRECT_ANSWER = Color.rgb(46,134,193);
    public final static int COlOR_RESULT_ITEM_RIGHT_BG = Color.rgb(212, 239, 223 );
    public final static int COlOR_RESULT_ITEM_WRONG_BG = Color.rgb(250, 219, 216);

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
}
