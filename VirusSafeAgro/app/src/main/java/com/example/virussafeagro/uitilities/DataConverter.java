package com.example.virussafeagro.uitilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.NutrientCorrectionMethodModel;
import com.example.virussafeagro.models.NutrientFactorModel;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.NutrientReasonModel;
import com.example.virussafeagro.models.NutrientSymptomModel;
import com.example.virussafeagro.models.VirusCauseModel;
import com.example.virussafeagro.models.VirusDescriptionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.models.VirusPreventionModel;
import com.example.virussafeagro.models.VirusSymptomModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataConverter {

    public static Bitmap stringToBitmapConverter(String s){
        Bitmap bitmap = null;
        if ((s != null) && (!s.isEmpty())){
            byte[] byteArray= Base64.decode(s, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        }
        return bitmap;
    }

    public static String bitmapToStringConverter(Bitmap b){
        String bitmapStr = "";
        if (b != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bitmapStr = android.util.Base64.encodeToString(bos.toByteArray(), android.util.Base64.DEFAULT);
        }
        return bitmapStr;
    }

    public static Bitmap drawableImageToBitmap(FragmentActivity fragmentActivity, int drawableId) {
        InputStream is = fragmentActivity.getResources().openRawResource(drawableId);
        return BitmapFactory.decodeStream(is);
    }

    public static boolean isSameImage(BitmapDrawable imageViewDrawable, FragmentActivity fragmentActivity, int drawableId){
        Drawable.ConstantState drawableCs = fragmentActivity.getResources().getDrawable(drawableId).getConstantState();
        return imageViewDrawable.getConstantState().equals(drawableCs);
    }

    public static Drawable getDrawableById(FragmentActivity fragmentActivity, int drawableId) {
        return fragmentActivity.getResources().getDrawable(drawableId);
    }

    public static String checkResultVirusRawNameToDisplayFormat(String resultVirusRawFullName) {
        String processedNameWithNoUnderline = resultVirusRawFullName.trim().replace('_', ' ');
        return processedNameWithNoUnderline;
    }

    public static String checkResultVirusRawNameToUpperCaseWithSpace(String resultVirusRawFullName) {
        String processedNameWithNoUnderline = resultVirusRawFullName.trim().replace('_', ' ');
        return processedNameWithNoUnderline.toUpperCase();
    }

    public static String newsTimeToStandardFormat(String originalTimeString, String originalTimePattern, String targetTimePattern) {
        DateFormat df = new SimpleDateFormat(originalTimePattern);  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date date;
        String time = "";
        if (originalTimeString != null) {
            try {
                date = df.parse(originalTimeString);
                SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                Date date1 = df1.parse(date.toString());
                DateFormat df2 = new SimpleDateFormat(targetTimePattern);
                time = df2.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public static Date getDateByString(String time) {
        Date date = null;
        if(time == null) {
            return date;
        }

        String pattern = "dd MMMM yyyy, HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getShortTime(String time) {
        String shortString = null;
        long now = Calendar.getInstance().getTimeInMillis();
        Date date = getDateByString(time);
        if(date == null) {
            return null;
        }
        long delTime = (now - date.getTime()) / 1000;
        if (delTime > 365 * 24 * 60 * 60) {
            int year = (int) (delTime / (365 * 24 * 60 * 60));
            shortString =  year > 1 ? year + " years ago" : year + " year ago";
        } else if (delTime > 24 * 60 * 60 * 30) {
            int month = (int) (delTime / (24 * 60 * 60 * 30));
            shortString =  month > 1 ? month + " months ago" : month + " month ago";
        }else if (delTime > 24 * 60 * 60) {
            int day = (int) (delTime / (24 * 60 * 60));
            shortString =  day > 1 ? day + " days ago" : day + " day ago";
        } else if (delTime > 60 * 60) {
            int hour = (int) (delTime / (60 * 60));
            shortString =  hour > 1 ? hour + " hours ago" : hour + " hour ago";
        } else if (delTime > 60) {
            int minute = (int) (delTime / (60));
            shortString = minute > 1 ? minute + " minutes ago" : minute + " minute ago";
        } else if (delTime > 1) {
            shortString = delTime + " seconds ago";
        } else {
            shortString = "1 second ago";
        }
        return shortString;
    }

    // for searching virus
    public static List<String> virusModelInfoListToVirusStringInfoList(List<VirusModel> virusModelInfoList) {
        List<String> VirusStringInfoList = new ArrayList<>();
        for (VirusModel virusModel : virusModelInfoList) {
            StringBuilder virusModelStringBuilder = new StringBuilder();
            virusModelStringBuilder.append(virusModel.getVirusId()).append("#").append(virusModel.getVirusFullName()).append("#").append(virusModel.getVirusAbbreviation());
            // add description
            for (VirusDescriptionModel virusDescriptionModel : virusModel.getVirusDescriptionModelList()) {
                virusModelStringBuilder.append("#").append(virusDescriptionModel.getDesContent());
            }
            // add symptom
            for (VirusSymptomModel virusSymptomModel : virusModel.getVirusSymptomModelList()) {
                virusModelStringBuilder.append("#").append(virusSymptomModel.getSymContent());
            }
            // add cause
            for (VirusCauseModel virusCauseModel : virusModel.getVirusCauseModelList()) {
                virusModelStringBuilder.append("#").append(virusCauseModel.getCauseContent());
            }
            // add prevention
            for (VirusPreventionModel virusPreventionModel : virusModel.getVirusPreventionModelList()) {
                virusModelStringBuilder.append("#").append(virusPreventionModel.getPreContent());
            }
            VirusStringInfoList.add(virusModelStringBuilder.toString());
        }
        return VirusStringInfoList;
    }

    // for searching nutrient
    public static List<String> nutrientModelListToNutrientStringList(List<NutrientModel> nutrientModelList) {
        List<String> NutrientStringList = new ArrayList<>();
        for (NutrientModel nutrientModel : nutrientModelList) {
            StringBuilder nutrientModelStringBuilder = new StringBuilder();
            nutrientModelStringBuilder.append(nutrientModel.getNutrientId()).append("#").append(nutrientModel.getNutrientName());
            // add symptom
            for (NutrientSymptomModel nutrientSymptomModel : nutrientModel.getNutrientSymptomList()) {
                nutrientModelStringBuilder.append("#").append(nutrientSymptomModel.getSymContent());
            }
            // add reason
            for (NutrientReasonModel nutrientReasonModel : nutrientModel.getNutrientReasonList()) {
                nutrientModelStringBuilder.append("#").append(nutrientReasonModel.getReasonContent());
            }
            // add factor
            for (NutrientFactorModel nutrientFactorModel : nutrientModel.getNutrientFactorList()) {
                nutrientModelStringBuilder.append("#").append(nutrientFactorModel.getFactorContent());
            }
            // add correction method
            for (NutrientCorrectionMethodModel nutrientCorrectionMethodModel : nutrientModel.getNutrientCorrectionMethodList()) {
                nutrientModelStringBuilder.append("#").append(nutrientCorrectionMethodModel.getMethodContent());
            }
            NutrientStringList.add(nutrientModelStringBuilder.toString());
        }
        return NutrientStringList;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getQuizQuestionId(int questionNo) {
        int id = -1;
        switch (questionNo){
            case 1:
                id = R.id.question_1_quiz;
                break;
            case 2:
                id = R.id.question_2_quiz;
                break;
            case 3:
                id = R.id.question_3_quiz;
                break;
            case 4:
                id = R.id.question_4_quiz;
                break;
            case 5:
                id = R.id.question_5_quiz;
                break;
        }
        return id;
    }
}
