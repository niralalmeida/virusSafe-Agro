package com.example.virussafeagro.uitilities;

import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonParser {

    public static List<VirusModel> virusInfoListJsonParser(String resultText) throws JSONException {
        List<VirusModel> virusModelInfoList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray virusInfoListJsonArray = new JSONArray(resultText);
            int listSize = virusInfoListJsonArray.length();
            for (int i = 0; i < listSize; i++) {
                JSONObject virusJsonObject = virusInfoListJsonArray.getJSONObject(i);

                int virusId = virusJsonObject.getInt("virusId");
                String virusFullName = virusJsonObject.getString("virusFullName");
                String virusAbbreviation = virusJsonObject.getString("virusAbbreviation");
                String virusDescription = virusJsonObject.getString("virusDescription");
                String symptoms = virusJsonObject.getString("symptoms");
                String causes = virusJsonObject.getString("causes");
                String spread = virusJsonObject.getString("spread");
                String prevention = virusJsonObject.getString("prevention");
                String virusDistribution = virusJsonObject.getString("virusDistribution");

                VirusModel virusModel = new VirusModel(virusId, virusFullName, virusAbbreviation, virusDescription, symptoms, causes, spread, prevention, virusDistribution, null);
                virusModelInfoList.add(virusModel);
            }
        }
        return virusModelInfoList;
    }

    public static List<SingleChoiceQuestionModel>  singleChoiceQuestionModelListJsonParser(String resultText) throws JSONException{
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray questionListJsonArray = new JSONArray(resultText);
            int arrayLength = questionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject questionJsonObject = questionListJsonArray.getJSONObject(i);

                int choiceQuestionId = questionJsonObject.getInt("choiceQuestionId");
                String choiceQuestionContent = questionJsonObject.getString("choiceQuestionContent");
                String choiceQuestionTypeLetter = questionJsonObject.getString("choiceQuestionType");
                if (choiceQuestionTypeLetter.equals("s")){ // single choice
                    SingleChoiceQuestionModel singleChoiceQuestionModel = new SingleChoiceQuestionModel();
                    singleChoiceQuestionModel.setChoiceQuestionId(choiceQuestionId);
                    singleChoiceQuestionModel.setSingleChoiceQuestionContent(choiceQuestionContent);
                    singleChoiceQuestionModelList.add(singleChoiceQuestionModel);
                }
            }
        }
        return singleChoiceQuestionModelList;
    }

    public static List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListJsonParser(String resultText) throws JSONException{
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray questionListJsonArray = new JSONArray(resultText);
            int arrayLength = questionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject questionJsonObject = questionListJsonArray.getJSONObject(i);

                int choiceQuestionId = questionJsonObject.getInt("choiceQuestionId");
                String choiceQuestionContent = questionJsonObject.getString("choiceQuestionContent");
                String choiceQuestionTypeLetter = questionJsonObject.getString("choiceQuestionType");
                if (choiceQuestionTypeLetter.equals("m")){ // multiple choice
                    MultipleChoiceQuestionModel multipleChoiceQuestionModel = new MultipleChoiceQuestionModel();
                    multipleChoiceQuestionModel.setChoiceQuestionId(choiceQuestionId);
                    multipleChoiceQuestionModel.setMultipleChoiceQuestionContent(choiceQuestionContent);
                    multipleChoiceQuestionModelList.add(multipleChoiceQuestionModel);
                }
            }
        }
        return multipleChoiceQuestionModelList;
    }

    public static List<String> singleChoiceOptionListJsonParser(String resultText) throws JSONException{
        List<String> singleOptionList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray optionListJsonArray = new JSONArray(resultText);
            int arrayLength = optionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject optionJsonObject = optionListJsonArray.getJSONObject(i);

                String choiceOptionContent = optionJsonObject.getString("choiceOptionContent");
                String choiceOptionLabel = optionJsonObject.getString("choiceOptionLabel").toUpperCase();
                String choiceOption = choiceOptionLabel + ". " + choiceOptionContent;
                singleOptionList.add(choiceOption);
            }
        }
        return singleOptionList;
    }

    public static List<String> multipleChoiceOptionListJsonParser(String resultText) throws JSONException{
        List<String> multipleOptionList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray optionListJsonArray = new JSONArray(resultText);
            int arrayLength = optionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject optionJsonObject = optionListJsonArray.getJSONObject(i);

                String choiceOptionContent = optionJsonObject.getString("choiceOptionContent");
                String choiceOptionLabel = optionJsonObject.getString("choiceOptionLabel").toUpperCase();
                String choiceOption = choiceOptionLabel + ". " + choiceOptionContent;
                multipleOptionList.add(choiceOption);
            }
        }
        return multipleOptionList;
    }

    public static List<ChoiceQuestionCorrectAnswerModel> choiceQuestionAnswerListJsonParser(String resultText) throws JSONException{
        List<ChoiceQuestionCorrectAnswerModel> correctAnswersList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray answerListJsonArray = new JSONArray(resultText);
            int arrayLength = answerListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject answerJsonObject = answerListJsonArray.getJSONObject(i);

                // question id
                int choiceQuestionId = answerJsonObject.getInt("choiceQuestionId");
                // question type
                String choiceQuestionTypeLetter = answerJsonObject.getString("choiceQuestionType");
                String choiceQuestionType = "";
                if (choiceQuestionTypeLetter.equals("s")){ // single choice
                    choiceQuestionType = "single";
                } else { // multiple choice
                    choiceQuestionType = "multiple";
                }

                // answer
                String answer = answerJsonObject.getString("answer");
                List<String> answerList = new ArrayList<>();
                for (int k = 0; k < answer.length(); k++) {
                    String answerItem = answer.substring(k, k + 1);
                    answerList.add(answerItem);
                }
                ChoiceQuestionCorrectAnswerModel choiceQuestionCorrectAnswerModel = new ChoiceQuestionCorrectAnswerModel();
                choiceQuestionCorrectAnswerModel.setChoiceQuestionId(choiceQuestionId);
                choiceQuestionCorrectAnswerModel.setChoiceQuestionType(choiceQuestionType);
                choiceQuestionCorrectAnswerModel.setCorrectAnswerList(answerList);
                correctAnswersList.add(choiceQuestionCorrectAnswerModel);
            }
        }
        return correctAnswersList;
    }

    public static String imageCheckFeedbackJsonParser(String resultText) throws JSONException {
        String feedBack = "";
        if(!resultText.equals("[]")){
            JSONObject predictionJsonObject = new JSONObject(resultText);
            Iterator<String> keysIterator = predictionJsonObject.keys();
            if (keysIterator.next().equals("prediction")) {
                feedBack = predictionJsonObject.getString("prediction");
            } else {
                feedBack = "error";
            }
        }
        return feedBack;
    }
}
