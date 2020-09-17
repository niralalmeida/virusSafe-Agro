package com.example.virussafeagro.uitilities;

import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.VirusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyJsonParser {

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

    public static List<NewsModel> newsListJsonParser(String resultText) throws JSONException {
        List<NewsModel> newsModelList = new ArrayList<>();
        if(resultText.substring(0,1).equals("{")) {
            JSONObject resultTextJsonObject = new JSONObject(resultText);
            Iterator<String> resultKeys = resultTextJsonObject.keys();
            // check "items" key
            while (resultKeys.hasNext()) {
                String keyString = resultKeys.next();
                // find "items" key
                if (keyString.equals("items")) {
                    // get "items" json array
                    JSONArray newsListJsonArray = resultTextJsonObject.getJSONArray("items");
                    int listSize = newsListJsonArray.length();
                    for (int i = 0; i < listSize; i++) {
                        JSONObject newsJsonObject = newsListJsonArray.getJSONObject(i);

                        // check keys existence
                        boolean hasTitle = false;
                        boolean hasPressTime = false;
                        boolean hasSnippet = false;
                        boolean hasAuthor = false;
                        boolean hasURL = false;
                        Iterator<String> itemKeys = newsJsonObject.keys();
                        while(itemKeys.hasNext()){
                            String itemKeyString = itemKeys.next();
                            if (itemKeyString.equals("title")){
                                hasTitle = true;
                            }
                            if (itemKeyString.equals("article:published_time")){
                                hasPressTime = true;
                            }
                            if (itemKeyString.equals("og:description")){
                                hasSnippet = true;
                            }
                            if (itemKeyString.equals("author")){
                                hasAuthor = true;
                            }
                            if (itemKeyString.equals("og:url")){
                                hasURL = true;
                            }
                        }

                        // news title
                        String newsTitle = "";
                        if (hasTitle){
                            newsTitle = newsJsonObject.getString("title");
                        }

                        // news press time
                        String newsPressTime = "";
                        if (hasPressTime){
                            newsPressTime = newsJsonObject.getString("article:published_time");
                        }

                        // news author
                        String newsAuthor = "";
                        if (hasAuthor){
                            newsAuthor = newsJsonObject.getString("author");
                        }

                        // news Snippet
                        String newsSnippet = "";
                        if (hasSnippet){
                            newsSnippet = newsJsonObject.getString("og:description");
                        }

                        // news URL
                        String newsURL = "";
                        if (hasURL){
                            newsURL = newsJsonObject.getString("og:url");
                        }

                        NewsModel newsModel = new NewsModel();
                        newsModel.setNewsId(i + 1); // id
                        newsModel.setNewsTitle(newsTitle); // tile
                        newsModel.setNewsPressTime(newsPressTime); // time
                        newsModel.setNewsAuthor(newsAuthor); // author
                        newsModel.setNewsSnippet(newsSnippet); // snippet
                        newsModel.setNewsURL(newsURL); // URL

                        newsModelList.add(newsModel);
                    }
                }
            }
        }
        return newsModelList;
    }
}
