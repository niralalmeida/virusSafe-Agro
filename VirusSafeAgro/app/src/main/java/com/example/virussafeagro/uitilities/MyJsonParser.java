package com.example.virussafeagro.uitilities;

import android.graphics.Bitmap;

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

    // get data to store viruses into virus model list
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

    // get data to store questions into question model list
    public static List<ChoiceQuestionModel> choiceQuestionModelListJsonParser(String resultText) throws JSONException{
        List<ChoiceQuestionModel> quizQuestionModelList = new ArrayList<>();
        // check network connection
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

    // get data to store question options into question model list
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

//                // test - image base64-bitmap
//                String imageBase64 = optionJsonObject.getString("choiceOptionImageBinaryCode");
//                // test
//                Bitmap bitmap = DataConverter.stringToBitmapConverter(imageBase64);

                // create a ChoiceOptionModel and add it into the optionModelList
                ChoiceOptionModel choiceOption = new ChoiceOptionModel(optionId, choiceOptionLabel, choiceOptionContent);
                // test
//                choiceOption.setChoiceOptionImage(bitmap);
                optionModelList.add(choiceOption);
            }
        }
        return optionModelList;
    }

    public static String questionImageJsonParser(String resultText) throws JSONException{
        String imageString = "";
        if(!resultText.equals("[]")){
            JSONArray imageResultJsonArray = new JSONArray(resultText);
            JSONObject imageResultJsonObject = imageResultJsonArray.getJSONObject(0);
            imageString = imageResultJsonObject.getString("choiceQuestionImageBinaryCode");
        }
        return imageString;
    }

    public static String optionImageJsonParser(String resultText) throws JSONException{
        String imageString = "";
        if(!resultText.equals("[]")){
            JSONArray imageResultJsonArray = new JSONArray(resultText);
            JSONObject imageResultJsonObject = imageResultJsonArray.getJSONObject(0);
            imageString = imageResultJsonObject.getString("choiceOptionImageBinaryCode");
        }
        return imageString;
    }

    // get data to store question images into question model list
    public static List<ChoiceQuestionModel> choiceQuestionModelListForImageJsonParser(String resultText, List<ChoiceQuestionModel> quizQuestionModelList) throws JSONException{
        // test
        System.out.println("result text" + resultText);
        // check network connection
        if (resultText.isEmpty()){
            // add a new question model into the list and set the message into it
            ChoiceQuestionModel choiceQuestionModel = new ChoiceQuestionModel(CONNECTION_ERROR_MESSAGE);
            quizQuestionModelList.add(choiceQuestionModel);
        } else {
            if (resultText.substring(0,1).equals("{") && (!resultText.equals("{}"))) {
                // get result Json Object
                JSONObject resultJsonObject = new JSONObject(resultText);
                // check "QUESTION 1" to "QUESTION 5" keys
                Iterator<String> resultKeys = resultJsonObject.keys();
                while (resultKeys.hasNext()) {
                    String resultKeyString = resultKeys.next();
                    // find questions' keys
                    for (int questionNo = 1; questionNo <= 5; questionNo++) {
                        if (resultKeyString.equals("QUESTION " + questionNo)) {
                            // get question json object
                            JSONObject questionJsonObject = resultJsonObject.getJSONObject("QUESTION " + questionNo);
                            // get question json object keys
                            Iterator<String> questionKeys = questionJsonObject.keys();
                            // check "question" and "options" key
                            while (questionKeys.hasNext()){
                                String questionKeyString = questionKeys.next();
                                if (questionKeyString.equals("question")){ // find "question" key
                                    // get question image json array
                                    JSONArray questionImageJsonArray = questionJsonObject.getJSONArray("question");
                                    // get question image URLs
                                    List<String> questionImageURLs = new ArrayList<>();
                                    for (int i = 0; i < questionImageJsonArray.length(); i++) {
                                        // store the image URL into the URL list
                                        questionImageURLs.add(questionImageJsonArray.getString(i));
                                    }
                                    // store the URL list for each question model
                                    quizQuestionModelList.get(questionNo - 1).setImageURLList(questionImageURLs);
                                } else if (questionKeyString.equals("options")){ // find "options" key
                                    // get option image json array
                                    JSONArray optionImageJsonArray = questionJsonObject.getJSONArray("options");
                                    // get option image URLs
                                    for (int i = 0; i < optionImageJsonArray.length(); i++) {
                                        // store the option image URL for each option model
                                        quizQuestionModelList
                                                .get(questionNo - 1) // get the question model
                                                .getChoiceQuestionOptionList() // get the option list
                                                .get(i) // get the option
                                                .setChoiceOptionImageURL(optionImageJsonArray.getString(i)); // set the option image URL
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //
        System.out.println("");
        return quizQuestionModelList;
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
        // check network connection
        if (resultText.isEmpty()){
            NutrientModel nutrientModel = new NutrientModel(CONNECTION_ERROR_MESSAGE);
            nutrientModelList.add(nutrientModel);
        } else {
            if (!resultText.equals("[]")) {
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
        }
        return nutrientModelList;
    }

    public static List<NewsModel> newsListJsonParser(String resultText) throws JSONException {
        List<NewsModel> newsModelList = new ArrayList<>();
        // check network connection
        if (resultText.isEmpty()){
            NewsModel newsModel = new NewsModel(CONNECTION_ERROR_MESSAGE);
            newsModelList.add(newsModel);
        } else {
            if (resultText.substring(0, 1).equals("{")) {
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

                            // create a news model
                            NewsModel newsModel = new NewsModel();

                            // check "pagemap" key
                            Iterator<String> itemKeys = newsJsonObject.keys();
                            while (itemKeys.hasNext()) {
                                String itemKeyString = itemKeys.next();
                                // find "items" key
                                if (itemKeyString.equals("pagemap")) {
                                    // get "items" json array
                                    JSONObject pageMapJsonObject = newsJsonObject.getJSONObject("pagemap");

                                    // check "metatags" and "cse_image" key
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
                                            while (metaTagsKeys.hasNext()) {
                                                String metaTagsKeyString = metaTagsKeys.next();
                                                if (metaTagsKeyString.equals("title")) {
                                                    hasTitle = true;
                                                }
                                                if (metaTagsKeyString.equals("article:published_time")) {
                                                    hasPressTime = true;
                                                }
                                                if (metaTagsKeyString.equals("og:description")) {
                                                    hasSnippet = true;
                                                }
                                                if (metaTagsKeyString.equals("author")) {
                                                    hasAuthor = true;
                                                }
                                                if (metaTagsKeyString.equals("og:url")) {
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

                                                newsModel.setNewsId(i + 1); // id
                                                newsModel.setNewsTitle(newsTitle); // tile
                                                newsModel.setNewsPressTime(newsPressTime); // time
                                                newsModel.setNewsAuthor(newsAuthor); // author
                                                newsModel.setNewsSnippet(newsSnippet); // snippet
                                                newsModel.setNewsURL(newsURL); // URL
                                            }
                                        } else if (pageMapKeyString.equals("cse_image")){
                                            // find "cse_image" key
                                            // get "cse_image" json array and object
                                            JSONArray cseImageJsonArray = pageMapJsonObject.getJSONArray("cse_image");
                                            JSONObject cseImageJsonObject = cseImageJsonArray.getJSONObject(0);
                                            // check "src" key
                                            Iterator<String> cseImageKeys = cseImageJsonObject.keys();
                                            while(cseImageKeys.hasNext()){
                                                // find "src" key
                                                if (cseImageKeys.next().equals("src")){
                                                    // image URL
                                                    String imageURL = cseImageJsonObject.getString("src");
                                                    newsModel.setNewsImageURL(imageURL);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                            }

                            // add the news model into the list
                            if (newsModel.getNewsTitle() != null) {
                                newsModelList.add(newsModel);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return newsModelList;
    }
}
