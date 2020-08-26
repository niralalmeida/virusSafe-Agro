package com.example.virussafeagro.uitilities;

import com.example.virussafeagro.entities.Virus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Virus> virusInfoListJsonParser(String resultText) throws JSONException {
        List<Virus> virusInfoList = new ArrayList<>();
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

                Virus virus = new Virus(virusId, virusFullName, virusAbbreviation, symptoms, spread, prevention, distribution, null);
                virusInfoList.add(virus);
            }
        }
        return virusInfoList;
    }

}
