package com.example.virussafeagro.uitilities;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
}
