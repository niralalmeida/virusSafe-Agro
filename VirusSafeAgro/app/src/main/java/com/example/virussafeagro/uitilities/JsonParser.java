package com.example.virussafeagro.uitilities;

import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.NutrientModel;
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

    public static List<ChoiceQuestionModel> choiceQuestionModelListJsonParser(String resultText) throws JSONException{
        List<ChoiceQuestionModel> quizQuestionModelList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray questionListJsonArray = new JSONArray(resultText);
            int arrayLength = questionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject questionJsonObject = questionListJsonArray.getJSONObject(i);

                // question id
                int choiceQuestionId = questionJsonObject.getInt("choiceQuestionId");

                // question content
                String choiceQuestionContent = questionJsonObject.getString("choiceQuestionContent");

                // question type and answer
                String choiceQuestionTypeLetter = questionJsonObject.getString("choiceQuestionType");
                String choiceQuestionType = "";
                String answer = questionJsonObject.getString("answer");
                List<String> correctAnswerList = new ArrayList<>();
                if (choiceQuestionTypeLetter.equals("s")){ // single choice
                    choiceQuestionType = "single";
                    correctAnswerList.add(answer);
                } else { // multiple choice
                    for (int k = 0; k < answer.length(); k++) {
                        String answerItem = answer.substring(k, k + 1);
                        correctAnswerList.add(answerItem);
                    }
                    choiceQuestionType = "multiple";
                }

                // create the choiceQuestionModel and add it into quizQuestionModelList
                ChoiceQuestionModel choiceQuestionModel = new ChoiceQuestionModel(choiceQuestionId, choiceQuestionType, choiceQuestionContent, correctAnswerList);
                quizQuestionModelList.add(choiceQuestionModel);
            }
        }
        return quizQuestionModelList;
    }

    public static List<ChoiceOptionModel> choiceOptionListJsonParser(String resultText) throws JSONException{
        List<ChoiceOptionModel> optionModelList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray optionListJsonArray = new JSONArray(resultText);
            int arrayLength = optionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject optionJsonObject = optionListJsonArray.getJSONObject(i);

                // option id
                int optionId = optionJsonObject.getInt("choiceOptionId");

                // option content
                String choiceOptionContent = optionJsonObject.getString("choiceOptionContent");

                // option label
                String choiceOptionLabel = optionJsonObject.getString("choiceOptionLabel").toUpperCase();

                // create a ChoiceOptionModel and add it into the optionModelList
                ChoiceOptionModel choiceOption = new ChoiceOptionModel(optionId, choiceOptionLabel, choiceOptionContent);
                optionModelList.add(choiceOption);
            }
        }
        return optionModelList;
    }

    public static String imageCheckFeedbackJsonParser(String resultText) throws JSONException {
        String feedBack = "";
        if(!resultText.equals("[]")){
            JSONObject predictionJsonObject = new JSONObject(resultText);
            Iterator<String> keysIterator = predictionJsonObject.keys();
            if (keysIterator.next().equals("prediction")) {
                feedBack = predictionJsonObject.getString("prediction");
            } else {
                feedBack = "json error";
            }
        }
        return feedBack;
    }

    public static List<NutrientModel> nutrientListJsonParser(String resultText) throws JSONException {
        List<NutrientModel> nutrientModelList = new ArrayList<>();
//        if(!resultText.equals("[]")){
//            JSONArray virusInfoListJsonArray = new JSONArray(resultText);
//            int listSize = virusInfoListJsonArray.length();
//            for (int i = 0; i < listSize; i++) {
//                JSONObject virusJsonObject = virusInfoListJsonArray.getJSONObject(i);
//
//                int virusId = virusJsonObject.getInt("virusId");
//                String virusFullName = virusJsonObject.getString("virusFullName");
//                String virusAbbreviation = virusJsonObject.getString("virusAbbreviation");
//                String virusDescription = virusJsonObject.getString("virusDescription");
//                String symptoms = virusJsonObject.getString("symptoms");
//                String causes = virusJsonObject.getString("causes");
//                String spread = virusJsonObject.getString("spread");
//                String prevention = virusJsonObject.getString("prevention");
//                String virusDistribution = virusJsonObject.getString("virusDistribution");
//
//                VirusModel virusModel = new VirusModel(virusId, virusFullName, virusAbbreviation, virusDescription, symptoms, causes, spread, prevention, virusDistribution, null);
//                virusModelInfoList.add(virusModel);
//            }
//        }
        return nutrientModelList;
    }
}
