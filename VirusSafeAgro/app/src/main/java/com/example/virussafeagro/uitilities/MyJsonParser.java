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
    public final static String CONNECTION_ERROR_MESSAGE = "Fail to connect to the server! Something wrong with the network!";

    public static List<VirusModel> virusInfoListJsonParser(String resultText) throws JSONException {
        List<VirusModel> virusModelInfoList = new ArrayList<>();
        // check network connection
        if (resultText.isEmpty()){
            VirusModel virusModel = new VirusModel(CONNECTION_ERROR_MESSAGE);
            virusModelInfoList.add(virusModel);
        } else {
            if (!resultText.equals("[]")) {
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
        }
        return virusModelInfoList;
    }

    public static List<ChoiceQuestionModel> choiceQuestionModelListJsonParser(String resultText) throws JSONException{
        List<ChoiceQuestionModel> quizQuestionModelList = new ArrayList<>();

        if (resultText.isEmpty()){
            ChoiceQuestionModel choiceQuestionModel = new ChoiceQuestionModel(CONNECTION_ERROR_MESSAGE);
            quizQuestionModelList.add(choiceQuestionModel);
        } else {
            if (!resultText.equals("[]")) {
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
                    if (choiceQuestionTypeLetter.equals("s")) { // single choice
                        choiceQuestionType = "single";
                        correctAnswerList.add(answer);
                    } else { // multiple choice
                        for (int k = 0; k < answer.length(); k++) {
                            String answerItem = answer.substring(k, k + 1);
                            correctAnswerList.add(answerItem);
                        }
                        choiceQuestionType = "multiple";
                    }

                    // question explanation
                    String explanation = questionJsonObject.getString("explanation");

                    // create the choiceQuestionModel and add it into quizQuestionModelList
                    ChoiceQuestionModel choiceQuestionModel = new ChoiceQuestionModel(choiceQuestionId, choiceQuestionType, choiceQuestionContent, correctAnswerList, explanation);
                    quizQuestionModelList.add(choiceQuestionModel);
                }
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
        if(!resultText.equals("[]")){
            JSONArray nutrientInfoListJsonArray = new JSONArray(resultText);
            int listSize = nutrientInfoListJsonArray.length();
            for (int i = 0; i < listSize; i++) {
                JSONObject nutrientJsonObject = nutrientInfoListJsonArray.getJSONObject(i);

                int nutrientId = nutrientJsonObject.getInt("nutrientId");
                String nutrientName = nutrientJsonObject.getString("nutrientName");
                String nutrientSymptoms = nutrientJsonObject.getString("nutrientSymptoms");
                String nutrientReasons = nutrientJsonObject.getString("nutrientReasons");
                String nutrientFactory = nutrientJsonObject.getString("nutrientFactory");
                String nutrientCorrectionMethod = nutrientJsonObject.getString("nutrientCorrectionMethod");

                NutrientModel nutrientModel = new NutrientModel(nutrientId, nutrientName, nutrientSymptoms, nutrientReasons, nutrientFactory, nutrientCorrectionMethod);
                nutrientModelList.add(nutrientModel);
            }
        }
        return nutrientModelList;
    }

    public static List<NewsModel> newsListJsonParser(String resultText) throws JSONException {
        List<NewsModel> newsModelList = new ArrayList<>();
        if(resultText.substring(0,1).equals("{")) {
            JSONObject resultTextJsonObject = new JSONObject(resultText);

            // check "items" key
            Iterator<String> resultKeys = resultTextJsonObject.keys();
            while (resultKeys.hasNext()) {
                String keyString = resultKeys.next();
                // find "items" key
                if (keyString.equals("items")) {
                    // get "items" json array
                    JSONArray newsItemListJsonArray = resultTextJsonObject.getJSONArray("items");
                    int listSize = newsItemListJsonArray.length();
                    for (int i = 0; i < listSize; i++) {
                        JSONObject newsJsonObject = newsItemListJsonArray.getJSONObject(i);

                        // check "pagemap" key
                        Iterator<String> itemKeys = newsJsonObject.keys();
                        while (itemKeys.hasNext()) {
                            String itemKeyString = itemKeys.next();
                            // find "items" key
                            if (itemKeyString.equals("pagemap")) {
                                // get "items" json array
                                JSONObject pageMapJsonObject = newsJsonObject.getJSONObject("pagemap");

                                // check "metatags" key
                                Iterator<String> pageMapKeys = pageMapJsonObject.keys();
                                while (pageMapKeys.hasNext()) {
                                    String pageMapKeyString = pageMapKeys.next();
                                    // find "metatags" key
                                    if (pageMapKeyString.equals("metatags")) {
                                        // get "metatags" json array and object
                                        JSONArray metaTagsJsonArray = pageMapJsonObject.getJSONArray("metatags");
                                        JSONObject metaTagsJsonObject = metaTagsJsonArray.getJSONObject(0);

                                        // check keys existence
                                        boolean hasTitle = false;
                                        boolean hasPressTime = false;
                                        boolean hasSnippet = false;
                                        boolean hasAuthor = false;
                                        boolean hasURL = false;
                                        Iterator<String> metaTagsKeys = metaTagsJsonObject.keys();
                                        while(metaTagsKeys.hasNext()){
                                            String metaTagsKeyString = metaTagsKeys.next();
                                            if (metaTagsKeyString.equals("title")){
                                                hasTitle = true;
                                            }
                                            if (metaTagsKeyString.equals("article:published_time")){
                                                hasPressTime = true;
                                            }
                                            if (metaTagsKeyString.equals("og:description")){
                                                hasSnippet = true;
                                            }
                                            if (metaTagsKeyString.equals("author")){
                                                hasAuthor = true;
                                            }
                                            if (metaTagsKeyString.equals("og:url")){
                                                hasURL = true;
                                            }
                                        }

                                        if (hasTitle && hasAuthor && hasPressTime && hasSnippet && hasURL) {
                                            // title
                                            String newsTitle = metaTagsJsonObject.getString("title");
                                            // news press time
                                            String newsPressTime = metaTagsJsonObject.getString("article:published_time");
                                            // news author
                                            String newsAuthor = metaTagsJsonObject.getString("author");
                                            // news Snippet
                                            String newsSnippet = metaTagsJsonObject.getString("og:description");
                                            // news URL
                                            String newsURL = metaTagsJsonObject.getString("og:url");

                                            NewsModel newsModel = new NewsModel();
                                            newsModel.setNewsId(i + 1); // id
                                            newsModel.setNewsTitle(newsTitle); // tile
                                            newsModel.setNewsPressTime(newsPressTime); // time
                                            newsModel.setNewsAuthor(newsAuthor); // author
                                            newsModel.setNewsSnippet(newsSnippet); // snippet
                                            newsModel.setNewsURL(newsURL); // URL

                                            newsModelList.add(newsModel);
                                        }

                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return newsModelList;
    }
}
