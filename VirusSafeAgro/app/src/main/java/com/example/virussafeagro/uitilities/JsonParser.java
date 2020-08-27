package com.example.virussafeagro.uitilities;

import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                String symptoms = virusJsonObject.getString("symptoms");
                String spread = virusJsonObject.getString("spread");
                String prevention = virusJsonObject.getString("prevention");
                String distribution = virusJsonObject.getString("distribution");

                VirusModel virusModel = new VirusModel(virusId, virusFullName, virusAbbreviation, symptoms, spread, prevention, distribution, null);
                virusModelInfoList.add(virusModel);
            }
        }
        return virusModelInfoList;
    }

    public static List[] virusTwoTypeQuestionArrayJsonParser(String resultText) throws JSONException{
        List[] virusTwoTypeQuestionArray = new List[2];
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
        if(!resultText.equals("[]")){
            JSONArray questionListJsonArray = new JSONArray(resultText);
            int arrayLength = questionListJsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject questionJsonObject = questionListJsonArray.getJSONObject(i);

                int choiceQuestionId = questionJsonObject.getInt("choiceQuestionId");
                String choiceQuestionContent = questionJsonObject.getString("choiceQuestionContent");
                int choiceQuestionTypeNo = questionJsonObject.getInt("choiceQuestionType");
                if (choiceQuestionTypeNo == 115){ // single choice
                    SingleChoiceQuestionModel singleChoiceQuestionModel = new SingleChoiceQuestionModel();
                    singleChoiceQuestionModel.setChoiceQuestionId(choiceQuestionId);
                    singleChoiceQuestionModel.setSingleChoiceQuestionContent(choiceQuestionContent);
                    singleChoiceQuestionModelList.add(singleChoiceQuestionModel);
                } else { // multiple choice
                    MultipleChoiceQuestionModel multipleChoiceQuestionModel = new MultipleChoiceQuestionModel();
                    multipleChoiceQuestionModel.setChoiceQuestionId(choiceQuestionId);
                    multipleChoiceQuestionModel.setMultipleChoiceQuestionContent(choiceQuestionContent);
                    multipleChoiceQuestionModelList.add(multipleChoiceQuestionModel);
                }
            }
        }
        virusTwoTypeQuestionArray[0] = singleChoiceQuestionModelList;
        virusTwoTypeQuestionArray[1] = multipleChoiceQuestionModelList;
        return virusTwoTypeQuestionArray;
    }

}
