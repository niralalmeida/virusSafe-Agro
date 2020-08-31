package com.example.virussafeagro.uitilities;

import android.graphics.Color;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import java.util.ArrayList;
import java.util.List;

public class AppResources {
    public final static int COlOR_RIGHT_ANSWER = Color.rgb(30,132,73);
    public final static int COlOR_WRONG_ANSWER = Color.rgb(192,57,43);
    public final static int COlOR_CORRECT_ANSWER = Color.rgb(46,134,193);
    public final static int COlOR_RESULT_ITEM_RIGHT_BG = Color.rgb(212, 239, 223 );
    public final static int COlOR_RESULT_ITEM_WRONG_BG = Color.rgb(250, 219, 216);

    private static List<VirusModel> virusModelListBackup = new ArrayList<>();
    private static List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup = new ArrayList<>();
    private static List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup = new ArrayList<>();
    private static List<ChoiceQuestionCorrectAnswerModel> choiceQuestionCorrectAnswerModelListBackup = new ArrayList<>();

    public static int getVirusPictureDrawableId(int virusId) {
        int virusDrawablePictureResource = -1;
        switch (virusId){
            case 1:
                virusDrawablePictureResource = R.drawable.one;
                break;
            case 2:
                virusDrawablePictureResource = R.drawable.two;
                break;
            case 3:
                virusDrawablePictureResource = R.drawable.three;
                break;
            case 4:
                virusDrawablePictureResource = R.drawable.four;
                break;
            case 5:
                virusDrawablePictureResource = R.drawable.five;
                break;
            case 6:
                virusDrawablePictureResource = R.drawable.six;
                break;
            case 7:
                virusDrawablePictureResource = R.drawable.seven;
                break;
            case 8:
                virusDrawablePictureResource = R.drawable.eight;
                break;
            case 9:
                virusDrawablePictureResource = R.drawable.nine;
                break;
        }
        return virusDrawablePictureResource;
    }

    public static List<VirusModel> getVirusModelListBackup() {
        VirusModel v1 = new VirusModel();
        VirusModel v2 = new VirusModel();
        VirusModel v3 = new VirusModel();
        VirusModel v4 = new VirusModel();
        VirusModel v5 = new VirusModel();
        VirusModel v6 = new VirusModel();
        VirusModel v7 = new VirusModel();
        VirusModel v8 = new VirusModel();
        VirusModel v9 = new VirusModel();

        v1.setVirusId(1);
        v1.setVirusFullName("TOMATO MOSAIC VIRUS");
        v1.setVirusAbbreviation("ToMV");
        v1.setVirusDescription("- Belongs to the family of Virgaviridae\n- Is a plant pathogenic virus\n- Commonly found in tomatoes and many other plants");
        v1.setSymptoms("1. LEAVES:\n  • Light green mosaic patterns on the leaves\n  • Alternating yellowish and darker green areas\n2. APPEARANCE:\n  Leaves appear to be fern like in appearance and tend to have pointed tips\n3. FRUIT:\n  • Bronze and patchy appearance develops on the fruit\n  • Distorted, yellow blotches, necrotic spots on fruit");
        v1.setCauses("1. TIMING OF CAUSE:\n  Any time during the growing season\n2. ENVIRONMENTAL CONDITIONS:\n  • Environmental conditions influence the symptoms\n  • Influencing factors:\n    Temperature, day length, intensity of light, variety, plant age at the time of infection\n3. MEANS OF INFECTION:\n  • Infected seed\n  • Small number of infected seedlings are enough for virus to spread rapidly\n  • Contaminated tools, hands and clothes of workers");
        v1.setPrevention("1. NON-CHEMICAL CONTROL\n  • Remove plants with symptoms Promptly to prevent the spread of the virus to nearby healthy plants\n  • On handling infected plants, it’s necessary to wash tools and hands in hot soapy water\n  • Tools should be sterilised using a disinfectant (Virkon S)\n  • Avoid growing new plants in close proximity if you suspect your plants are infected\n2. CHEMICAL CONTROL\n  No chemical controls available");

        v2.setVirusId(2);
        v2.setVirusFullName("Target spot");
        v2.setVirusAbbreviation("");
        v2.setVirusDescription("• Target spot attacks leaves or stems of tomato plants\n• Also called as leaf spot\n• Scientific Name:\n  Corynespora cassiicola\n• Distribution:\n  Widespread");
        v2.setSymptoms("");
        v2.setCauses("");
        v2.setPrevention("");

        v3.setVirusId(3);
        v3.setVirusFullName("");
        v3.setVirusAbbreviation("");
        v3.setVirusDescription("");
        v3.setSymptoms("");
        v3.setCauses("");
        v3.setPrevention("");

        v4.setVirusId(4);
        v4.setVirusFullName("");
        v4.setVirusAbbreviation("");
        v4.setVirusDescription("");
        v4.setSymptoms("");
        v4.setCauses("");
        v4.setPrevention("");

        v5.setVirusId(5);
        v5.setVirusFullName("");
        v5.setVirusAbbreviation("");
        v5.setVirusDescription("");
        v5.setSymptoms("");
        v5.setCauses("");
        v5.setPrevention("");

        v6.setVirusId(6);
        v6.setVirusFullName("");
        v6.setVirusAbbreviation("");
        v6.setVirusDescription("");
        v6.setSymptoms("");
        v6.setCauses("");
        v6.setPrevention("");

        v7.setVirusId(7);
        v7.setVirusFullName("");
        v7.setVirusAbbreviation("");
        v7.setVirusDescription("");
        v7.setSymptoms("");
        v7.setCauses("");
        v7.setPrevention("");

        v8.setVirusId(8);
        v8.setVirusFullName("");
        v8.setVirusAbbreviation("");
        v8.setVirusDescription("");
        v8.setSymptoms("");
        v8.setCauses("");
        v8.setPrevention("");

        v9.setVirusId(9);
        v9.setVirusFullName("");
        v9.setVirusAbbreviation("");
        v9.setVirusDescription("");
        v9.setSymptoms("");
        v9.setCauses("");
        v9.setPrevention("");

        virusModelListBackup.add(v1);
        virusModelListBackup.add(v2);
        virusModelListBackup.add(v3);
        virusModelListBackup.add(v4);
        virusModelListBackup.add(v5);
        virusModelListBackup.add(v6);
        virusModelListBackup.add(v7);
        virusModelListBackup.add(v8);
        virusModelListBackup.add(v9);

        return virusModelListBackup;
    }

    private static void addDescriptionForVirus() {
        for (VirusModel v : virusModelListBackup) {

        }
    }

    private static void addCausesForVirus() {
        for (VirusModel v : virusModelListBackup) {

        }
    }

    public static List<SingleChoiceQuestionModel> getSingleChoiceQuestionModelListBackup() {
        return singleChoiceQuestionModelListBackup;
    }

    public static List<MultipleChoiceQuestionModel> getMultipleChoiceQuestionModelListBackup() {
        return multipleChoiceQuestionModelListBackup;
    }

    public static List<ChoiceQuestionCorrectAnswerModel> getChoiceQuestionCorrectAnswerModelListBackup() {
        return choiceQuestionCorrectAnswerModelListBackup;
    }
}
