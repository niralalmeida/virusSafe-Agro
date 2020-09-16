package com.example.virussafeagro.uitilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.virussafeagro.models.VirusModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceProcess {
    private Context context;
    private static SharedPreferenceProcess sharedPreferenceProcess;

    // for showing On Boarding Screen
    private static SharedPreferences onBoardingSP;

    private static final String FILE_NAME_OnBoarding = "sp_on_boarding";

    private static final String KEY_IS_FIRST_SHOW = "is_first_show";

    // for app password
    private static SharedPreferences appPasswordSP;

    private static final String FILE_NAME_APP_PASSWORD = "sp_app_password";

    private static final String KEY_PASSWORD = "password";
    private static final String KEY_HAS_AUTHENTICATION = "has_authentication";

    // for virus check
    private static SharedPreferences virusCheckSP;

    private static final String FILE_NAME_VIRUS_CHECK_INFO = "sp_virus_check_info";

    private static final String KEY_VIRUS_CHECK_IMAGE = "virus_check_image";


    // for virus info
    private static List<VirusModel> virusModelList;
    private static SharedPreferences virusSP1;
    private static SharedPreferences virusSP2;
    private static SharedPreferences virusSP3;
    private static SharedPreferences virusSP4;
    private static SharedPreferences virusSP5;
    private static SharedPreferences virusSP6;
    private static SharedPreferences virusSP7;
    private static SharedPreferences virusSP8;
    private static SharedPreferences virusSP9;

    private static String FILE_NAME_VIRUS_1 = "sp_virus_1";
    private static String FILE_NAME_VIRUS_2 = "sp_virus_2";
    private static String FILE_NAME_VIRUS_3 = "sp_virus_3";
    private static String FILE_NAME_VIRUS_4 = "sp_virus_4";
    private static String FILE_NAME_VIRUS_5 = "sp_virus_5";
    private static String FILE_NAME_VIRUS_6 = "sp_virus_6";
    private static String FILE_NAME_VIRUS_7 = "sp_virus_7";
    private static String FILE_NAME_VIRUS_8 = "sp_virus_8";
    private static String FILE_NAME_VIRUS_9 = "sp_virus_9";

    private static final String KEY_VIRUS_FULL_NAME = "virus_full_name";
    private static final String KEY_VIRUS_ABBREVIATION = "virus_abbreviation";
    private static final String KEY_VIRUS_DESCRIPTION = "virus_description";
    private static final String KEY_VIRUS_SYMPTOMS = "virus_symptoms";
    private static final String KEY_VIRUS_CAUSES = "virus_causes";
    private static final String KEY_VIRUS_SPREAD = "virus_spread";
    private static final String KEY_VIRUS_PREVENTION = "virus_prevention";
    private static final String KEY_VIRUS_DISTRIBUTION = "virus_distribution";


    private SharedPreferenceProcess(Context context){
        this.context = context;
        onBoardingSP = context.getSharedPreferences(FILE_NAME_OnBoarding, Context.MODE_PRIVATE);
        appPasswordSP = context.getSharedPreferences(FILE_NAME_APP_PASSWORD, Context.MODE_PRIVATE);
        virusCheckSP = context.getSharedPreferences(FILE_NAME_VIRUS_CHECK_INFO, Context.MODE_PRIVATE);
        virusModelList = new ArrayList<>();
        virusSP1 = context.getSharedPreferences(FILE_NAME_VIRUS_1, Context.MODE_PRIVATE);
        virusSP2 = context.getSharedPreferences(FILE_NAME_VIRUS_2, Context.MODE_PRIVATE);
        virusSP3 = context.getSharedPreferences(FILE_NAME_VIRUS_3, Context.MODE_PRIVATE);
        virusSP4 = context.getSharedPreferences(FILE_NAME_VIRUS_4, Context.MODE_PRIVATE);
        virusSP5 = context.getSharedPreferences(FILE_NAME_VIRUS_5, Context.MODE_PRIVATE);
        virusSP6 = context.getSharedPreferences(FILE_NAME_VIRUS_6, Context.MODE_PRIVATE);
        virusSP7 = context.getSharedPreferences(FILE_NAME_VIRUS_7, Context.MODE_PRIVATE);
        virusSP8 = context.getSharedPreferences(FILE_NAME_VIRUS_8, Context.MODE_PRIVATE);
        virusSP9 = context.getSharedPreferences(FILE_NAME_VIRUS_9, Context.MODE_PRIVATE);
    }

    // get SharedPreferenceProcess instance
    public static SharedPreferenceProcess getSharedPreferenceProcessInstance(Context context){
        if (sharedPreferenceProcess == null){
            sharedPreferenceProcess = new SharedPreferenceProcess(context);
        }
        return sharedPreferenceProcess;
    }

    // for on boarding screen show
    public void putOnBoardingIsFirstShow(boolean isFirstShow) {
        onBoardingSP.edit().putBoolean(KEY_IS_FIRST_SHOW, isFirstShow).apply();
    }
    public boolean getOnBoardingIsFirstShow() {
        return onBoardingSP.getBoolean(KEY_IS_FIRST_SHOW, true);
    }

    // for app password check
    public void putHasAuthentication(String yesOrNo) {
        appPasswordSP.edit().putString(KEY_HAS_AUTHENTICATION, yesOrNo).apply();
    }
    public String getHasAuthentication() {
        return appPasswordSP.getString(KEY_HAS_AUTHENTICATION, "");
    }
    public void putAppPassword(String password) {
        appPasswordSP.edit().putString(KEY_PASSWORD, password).apply();
    }
    public String getAppPassword() {
        return appPasswordSP.getString(KEY_PASSWORD, "");
    }

    // for virus image check
    public void putCurrentVirusCheckImage(Bitmap bitmap) {
        SharedPreferences.Editor edit = virusCheckSP.edit();
        String virusCheckImageBitmapString = DataConverter.bitmapToStringConverter(bitmap);
        edit.putString(KEY_VIRUS_CHECK_IMAGE, virusCheckImageBitmapString).apply();
    }
    public Bitmap getCurrentVirusCheckImage(){
        String virusCheckImageBitmapString = virusCheckSP.getString(KEY_VIRUS_CHECK_IMAGE, "");
        return DataConverter.stringToBitmapConverter(virusCheckImageBitmapString);
    }


    // for all virus info
    public List<VirusModel> getVirusModelListFromSP() {
        virusModelList = new ArrayList<>();
        for (int id = 1; id <= 9; id++) {
            VirusModel virusModel = new VirusModel();
            virusModel.setVirusId(id);
            virusModel.setVirusFullName(getSPVirusFullName(id));
            virusModel.setVirusAbbreviation(getSPVirusAbbreviation(id));
            virusModel.setVirusDescription(getSPVirusDescription(id));
            virusModel.setSymptoms(getSPVirusSymptoms(id));
            virusModel.setCauses(getSPVirusCauses(id));
            virusModel.setSpread(getSPVirusSpread(id));
            virusModel.setPrevention(getSPVirusPrevention(id));
            virusModel.setDistribution(getSPDistribution(id));
            virusModelList.add(virusModel);
        }
        return virusModelList;
    }
    public void saveVirusInfoList(List<VirusModel> virusModelListInput) {
        // set the virus model list
        virusModelList = virusModelListInput;
        // clear the details for all the virus SP file
        this.clearVirusDataInFiles();
        // put virus data into virusSPs
        for (VirusModel vm : virusModelList) {
            SharedPreferences virusSP = this.getVirusSPByVirusId(vm.getVirusId());
            virusSP.edit().putString(KEY_VIRUS_FULL_NAME, vm.getVirusFullName()).apply();
            virusSP.edit().putString(KEY_VIRUS_ABBREVIATION, vm.getVirusAbbreviation()).apply();
            virusSP.edit().putString(KEY_VIRUS_DESCRIPTION, vm.getVirusDescription()).apply();
            virusSP.edit().putString(KEY_VIRUS_SYMPTOMS, vm.getSymptoms()).apply();
            virusSP.edit().putString(KEY_VIRUS_CAUSES, vm.getCauses()).apply();
            virusSP.edit().putString(KEY_VIRUS_SPREAD, vm.getSpread()).apply();
            virusSP.edit().putString(KEY_VIRUS_PREVENTION, vm.getPrevention()).apply();
            virusSP.edit().putString(KEY_VIRUS_DISTRIBUTION, vm.getDistribution()).apply();
        }
    }
    // clear the virus data if all the SP file for virus exist
    private void clearVirusDataInFiles() {
        if (this.checkAllFileExistence()) {
            virusSP1.edit().clear().apply();
            virusSP2.edit().clear().apply();
            virusSP3.edit().clear().apply();
            virusSP4.edit().clear().apply();
            virusSP5.edit().clear().apply();
            virusSP6.edit().clear().apply();
            virusSP7.edit().clear().apply();
            virusSP8.edit().clear().apply();
            virusSP9.edit().clear().apply();
        }
    }
    // if exist, clear the data; if not exist, generate a new one
    private boolean checkAllFileExistence() {
        for (VirusModel vm : this.virusModelList) {
            String fileNameForSearch = "sp_virus_" + vm.getVirusId();
            if (!findFileByNameInSPDirectory(fileNameForSearch)) {
                return false;
            }
        }
        return true;
    }
    public boolean findFileByNameInSPDirectory(String fileNameForSearch) {
        String path = "/data/data/" + context.getPackageName() +"/shared_prefs";
        File directory = new File(path);
        File[] files = directory.listFiles();
        int file_count = files.length;
        for (int i = 0; i < file_count; i++){
            String fileName = files[i].getName(); // eg. sp_virus_check_info.xml
            if (fileName.equals(fileNameForSearch)) {
                return true;
            }
        }
        return false;
    }
    // get virus SP by virus id
    private SharedPreferences getVirusSPByVirusId(int virusId) {
        SharedPreferences virusSP = null;
        switch (virusId) {
            case 1:
                virusSP = virusSP1;
                break;
            case 2:
                virusSP = virusSP2;
                break;
            case 3:
                virusSP = virusSP3;
                break;
            case 4:
                virusSP = virusSP4;
                break;
            case 5:
                virusSP = virusSP5;
                break;
            case 6:
                virusSP = virusSP6;
                break;
            case 7:
                virusSP = virusSP7;
                break;
            case 8:
                virusSP = virusSP8;
                break;
            case 9:
                virusSP = virusSP9;
                break;
        }
        return virusSP;
    }
    // get virus data
    public String getSPVirusFullName(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_FULL_NAME, "");
    }
    public String getSPVirusAbbreviation(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_ABBREVIATION, "");
    }
    public String getSPVirusDescription(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_DESCRIPTION, "");
    }
    public String getSPVirusSymptoms(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_SYMPTOMS, "");
    }
    public String getSPVirusCauses(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_CAUSES, "");
    }
    public String getSPVirusSpread(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_SPREAD, "");
    }
    public String getSPVirusPrevention(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_PREVENTION, "");
    }
    public String getSPDistribution(int virusId){
        SharedPreferences virusSP = this.getVirusSPByVirusId(virusId);
        return virusSP.getString(KEY_VIRUS_DISTRIBUTION, "");
    }
}
