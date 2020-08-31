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
    private static List<List[]> choiceQuestionListBackup = new ArrayList<>();
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
        v2.setVirusFullName("TARGET SPOT");
        v2.setVirusAbbreviation("");
        v2.setVirusDescription("• Target spot attacks leaves or stems of tomato plants\n• Also called as leaf spot\n• Scientific Name:\n  Corynespora cassiicola\n• Distribution:\n  Widespread");
        v2.setSymptoms("1. LEAVES:\n  • Brown and black spots appear on leaves, which keeps on spreading\n  • Disease starts on the older leaves\n  • Spread of virus is rapid, and the plant loses its leaves quickly\n2. APPEARANCE:\n  • The spots are oval to angular in shape.\n• They form series of concentric circles\n  • The spots grow up to 10-12 mm\n3. FRUIT:\n  • Difficult to identify target spot on tomato fruit in early stage\n  • Circular spots with velvety black fungal lesion are observed after the diseased tomato ripens");
        v2.setCauses("1. TIMING OF CAUSE:\n  Any time during the growing season\n2. ENVIRONMENTAL CONDITIONS:\n  • Moderate temperatures\n  • High moisture\n3. MEANS OF INFECTION:\n  • Infected seed\n  • Remains of earlier infected plants or host species");
        v2.setPrevention("1. NON-CHEMICAL CONTROL\n  • Remove debris of old plants at the end of growing season\n  • Avoid planting in the areas where diseased plants where located in the past years\n  • Plants should be grown in sunlight with proper air circulation\n  • Avoid crowding of plants\n  • Remove branches from lower part of the plants for better airflow\n  2. CHEMICAL CONTROL\n  • Chemical products used:\n    Mancozeb\n    copper oxychloride\n    chlorothalonil\n  • Plants should be treated when the first spots are seen and should be sprayed at 10-14 days interval for three-four weeks\n  • Should be sprayed on both sides of the leaves");

        v3.setVirusId(3);
        v3.setVirusFullName("BACTERIAL SPOT");
        v3.setVirusAbbreviation("");
        v3.setVirusDescription("• Bacterial spot is considered as one of the devastating diseases\n• It’s impossible to control the disease once present in the plant\n• Scientific name:\n  Xanthomonas vesicatoria\n• Affects both above and ground parts of tomato plants: fruits, stems, leaves, and petioles");
        v3.setSymptoms("1. LEAVES:\n  • Leaves have small (<1/8 inch), brown, circular spots encircling a yellow halo\n  • Sunken upper leaf surface\n  • APPEARANCE:\n    Spots are about 3 mm and usually found on tips and margins of leaves\n2. FRUIT:\n  • Fruit spots are 1⁄4 in inch\n  • Slightly raised, scabby, and brown\n  • Fruit has a waxy white halo surrounding");
        v3.setCauses("1. TIMING OF CAUSE:\n  Any time during the growing season\n2. ENVIRONMENTAL CONDITIONS:\n  • High temperatures\n  • High humidity\n  • Overhead irrigation\n  • Frequent rainfall\n3. MEANS OF INFECTION:\n  • Infected seed");
        v3.setPrevention("1. NON-CHEMICAL CONTROL\n  • Grow plants from infection free seeds\n  • Purchase high quality seeds\n  • Use hot water treatment to kill bacteria in seeds\n  • Avoid high pressure sprays\n  • Rotate between crops\n  • Remove crop debris at the end of the growing season\n  • Disinfect pruners and tools by dipping in commercial sanitizer\n2. CHEMICAL CONTROL\n  • Copper fungicides or\n  • Copper plus mancozeb\n  • Chemicals should be sprayed at 7-10 days interval in the field\n  • Spray early when plants are young");

        v4.setVirusId(4);
        v4.setVirusFullName("TOMATO YELLOW LEAF CURL VIRUS");
        v4.setVirusAbbreviation("TYLCV");
        v4.setVirusDescription("• ItisaDNA virus and belongs to the family of Geminiviridae\n  • Is one of the most damaging pathogens of tomato plants\n  • It is often spread by whiteflies");
        v4.setSymptoms("1. LEAVES:\n  • Leaves are often bent downwards, thick, and stiff\n  • Have leathery texture and are wrinkled\n 2. APPEARANCE:\n  • Plants are severely stunted and will not develop fruits\n  • Small leaves become yellow between the veins\n  • Flowers usually fall off or do not develop at all\n3. FRUIT:\n  • Will not produce fruit\n  • If at all the fruit is produced, it is small, dry, and unsaleable");
        v4.setCauses("MEANS OF INFECTION:\n  • It is not transmitted in seed nor through soil\n  • It is harboured in infected host plants\n  • TYLCV is spready by silverleaf whitefly insect\n  • The whiteflies carry the virus by feeding on the infected host plants\n  • The whiteflies spread the virus to the healthy plants, which shows the symptoms 10-21 days later");
        v4.setPrevention("1. NON-CHEMICAL CONTROL\n  • Manage flies\n  • Remove old crop debris and destroy them\n2. CHEMICAL CONTROL\n  • Silverleaf whiteflies can be bought under control using potent chemicals");

        v5.setVirusId(5);
        v5.setVirusFullName("TOMATO LATE BLIGHT");
        v5.setVirusAbbreviation("");
        v5.setVirusDescription("• Late blight is considered as one of the most serious fungal diseases in tomato plants\n• Scientific name: Phytophthora infestans\n  • Impact:\n  Causes large losses in yields of tomato");
        v5.setSymptoms("1. LEAVES: • Black or brown irregular shape spots, which grow rapidly forming a white cottony growth\n2. APPEARANCE:\n  • Rapid growth of spots and patches on leaves with a furry white growth on the underside\n  • Water soaked appearance in leaf, stem, and fruit lesions\n3. FRUIT:\n  • Dark brown, firm, rots appear on the tomato fruit");
        v5.setCauses("MEANS OF INFECTION:\n  • Infected transplants\n  • Airborne\n  • Virus travel great distances in storms\n  • Favoured by cool and wet weather");
        v5.setPrevention("1. NON-CHEMICAL CONTROL\n  • Rotate crops, avoid planting tomato plants on same land\n  • Destroy diseased plants\n  • Maintain spacing between plants to ensure air movement\n  • Remove branches from lower parts of plants\n2. CHEMICAL CONTROL\n  • Expensive fungicides should be used to maintain control\n  • Spray plants with fungicides if unsure about the symptoms\n  • Preventive sprays should be used before symptoms are seen\n  • Preventive sprays: \n    Copper product\n    chlorothalonil\n    mancozeb\n Systemic products such as strobilurin, dimethomorth, cymoxanil, and metalaxyl");

        v6.setVirusId(6);
        v6.setVirusFullName("LEAF MOLD");
        v6.setVirusAbbreviation("");
        v6.setVirusDescription("• Leaf mold is caused by Passalora fulva\n  • It is found worldwide and occasionally found in on field grown tomatoes\n  • It’s a serious issue in green house grown tomatoes");
        v6.setSymptoms("1. LEAVES:\n  • Pale green or yellowish spots on the upper leaf surface\n  • Olive, green mold is visible on the lower leaf surface\n2. APPEARANCE:\n  • Yellow spots\n  • Irregular patches\n  • Dried leaves\n3. FRUIT:\n  • Dark, leathery, rot at the stem ends are the common symptoms seen on fruits");
        v6.setCauses("MEANS OF INFECTION:\n  • Contaminated seed serves as an initial source of spread\n  • Passalora fulva survives as saprophyte on infected plant debris, as conidia\n  • Conidia can be easily spread by rain and wind\n  • Conidia can also be spread on tools and workers clothes");
        v6.setPrevention("1. NON-CHEMICAL CONTROL\n  • Remove infected lower leaves\n  • Burn or dig remains of infected plants\n  • Pruning to increase ventilation\n2. CHEMICAL CONTROL\n  • Use fungicides to give adequate control\n  • Chemical products which can be used:\n    o mancozeb\n    o copper oxychloride\n    chlorothalonil\n  • Treatment should start when yellow spots are seen\n  • The leaves should be sprayed at an 10-14 day intervals");

        v7.setVirusId(7);
        v7.setVirusFullName("EARLY BLIGHT");
        v7.setVirusAbbreviation("");
        v7.setVirusDescription("• Most common tomato disease\n  • Occurs nearly every season\n  • Usually affects leaves, stems, and fruits\n  • It is caused by fungus named Alternaria solani");
        v7.setSymptoms("1. LEAVES:\n  • Small dark spots are observed near the ground of the plant\n  • Round and brown leaf spots, which grows up to half inch in diameter\n  • Infected leaves turn brown and fall off \n2. APPEARANCE: \n  • Dry brown areas with dark brown concentric circles \n3. FRUIT:\n  • Leathery and black spots on fruits\n  • Infected fruit often falls off the plant");
        v7.setCauses("1. TIMING OF CAUSE:\n  • Evident during early to mid-season\n2. ENVIRONMENTAL CONDITIONS:\n  • Can occur in any type of weather\n  • Favours damp conditions like frequent rains or heavy dews\n  • Strikes after a period of heavy rainfall\n  • Disease develop at moderate to warm temperatures\n3. MEANS OF INFECTION:\n  • Infected seedlings\n  • Diseased debris");
        v7.setPrevention("1. NON-CHEMICAL CONTROL\n  • Use pathogen free seed\n  • Blight remains active for a year, so it is recommended to rotate crops\n  • Avoid overcrowding of plants so that air can pass through the leaves and keep them dry\n  • Water the spoil and not the plants\n  • Destroy infected plants\n2. CHEMICAL CONTROL\n  • Use effective fungicides\n  • Fungicides include:\n    Mancozeb\n    Maneb\n    Chlorothalonil\n    Copper (Kocide)\n    Penthiopyrad\n    Boscalid\n    Pyraclostrobin\n  • Fungicides should be applied at the fruit and reapplied every 7-14 days");

        v8.setVirusId(8);
        v8.setVirusFullName("TWO-SPOTTED SPIDER MITE");
        v8.setVirusAbbreviation("");
        v8.setVirusDescription("• Most common mite species that attacks vegetables especially the tomatoes\n  • Adult spider mites are less than 0.5 mm long with eight legs\n  • Scientific name: Tetranychus urticae\n  • Damage caused by the mites is infrequent and minor\n  • The mites suck individual plant cells");
        v8.setSymptoms("1. LEAVES:\n  • Yellow mottled or stippled areas can be found on underside of leaves\n  • Damage can first be observed at the leaf veins\n  • Distorted leaves\n  • Whitening or spotting of leaves\n2. APPEARANCE:\n  • The feeding of mites on plant cells leads to extensive damage to leaves, fruits and flower\n  • Leaves turn yellow and drop");
        v8.setCauses("ENVIRONMENTAL CONDITIONS:\n  • Mites are favoured by hot dry weather");
        v8.setPrevention("1. NON-CHEMICAL CONTROL\n  • Avoid weedy fields\n  • Avoid over fertilising\n2. CHEMICAL CONTROL\n  • Miticide spray should be applied if mites are present on 50% of the leaves\n  • Insecticides should be applied only as needed\n  • Usage of pesticides is recommended, which will have minimal impact on useful insects\n  • Chemical application should be done at the undersides of the leaves\n  • One effective application of chemical is sufficient\n  • Spraying chemicals to plants should be avoided in hot weathers as they may cause chemical injuries");

        v9.setVirusId(9);
        v9.setVirusFullName("SEPTORIA LEAF SPOT");
        v9.setVirusAbbreviation("");
        v9.setVirusDescription("• Also known as Septoria blight\n• Spreads rapidly and may quickly defoliate and weaken the plants\n• It can be controlled if detected early");
        v9.setSymptoms("1. LEAVES:\n  • Lower leaves are affected first, later it moves up the plant\n  • The spots are smaller in size (1/16 to 18 inches)\n  • Diameter of spots is 1.6 to 3.2 mm in diameter\n  • Usually occurs in older leaves\n  • May also appear on blossoms, calyxes, and stems\n2. APPEARANCE: Leaves appear yellow, then brown and eventually fall off\n3. FRUIT:\n  • The virus rarely affects fruits");
        v9.setCauses("MEANS OF INFECTION:\n  • Lives on fallen tomato plant debris\n  • Spreads by both wind and water\n  • Damp conditions are favourable for virus to develop\n  • Can travel long distances");
        v9.setPrevention("1. NON-CHEMICAL CONTROL\n  • Remove plants with symptoms promptly and destroy infected areas\n  • Use crop rotation\n  • Use disease free seeds\n  • Overhead watering should be avoided\n  • Enough room should be provided for air circulation\n  • Best to plant tomatoes in different location each other\n2. CHEMICAL CONTROL\n  • The plant can be protected by spraying copper oxychloride\n  • Use fungicidal sprays\n  • The infected leaves will not be protected by fungicides, but they will protect new leaves from becoming infected\n  • For effective results the chemicals must be applied at 7-10- day intervals\n  • Most commonly used chemicals:\n    Maneb\n    Chlorothalonil");

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

    public static List<List[]> getVirus1SingleChoiceQuestionModelListBackup() {
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup1 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup1 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup2 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup2 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup3 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup3 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup4 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup4 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup5 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup5 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup6 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup6 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup7 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup7 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup8 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup8 = new ArrayList<>();
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelListBackup9 = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelListBackup9 = new ArrayList<>();

        // v1
        SingleChoiceQuestionModel s11 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s12 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s13 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s14 = new SingleChoiceQuestionModel();
        MultipleChoiceQuestionModel m15 = new MultipleChoiceQuestionModel();

        s11.setChoiceQuestionId(1);
        s11.setSingleChoiceQuestionContent("Are there chemical control strategies available to control the spread of tomato Mosaic Virus (ToMV) ?");
        List<String> so11 = new ArrayList<>();
        so11.add("A. YES");
        so11.add("B. NO");
        s11.setSingleChoiceQuestionOptionList(so11);
        s11.setSingleChoiceQuestionVirusId(1);

        s12.setChoiceQuestionId(2);
        s12.setSingleChoiceQuestionContent("Which disinfectant should be used to sterilise tools after handling infected plants?");
        List<String> so12 = new ArrayList<>();
        so12.add("A. Virkon S");
        so12.add("B. Virkon N");
        so12.add("C. Virkon X");
        so12.add("D. Virkon R");
        s12.setSingleChoiceQuestionOptionList(so12);
        s12.setSingleChoiceQuestionVirusId(1);

        s13.setChoiceQuestionId(3);
        s13.setSingleChoiceQuestionContent("What are the means of infection for Tomato Mosaic Virus (T oMV)?");
        List<String> so13 = new ArrayList<>();
        so13.add("A. INFECTED SEED");
        so13.add("B. CONTAMINATED TOOLS");
        so13.add("C. CONTAMINATED HANDS AND CLOTHES OF WORKERS");
        so13.add("D. ALL OF THE ABOVE");
        s13.setSingleChoiceQuestionOptionList(so13);
        s13.setSingleChoiceQuestionVirusId(1);

        s14.setChoiceQuestionId(3);
        s14.setSingleChoiceQuestionContent("Which of the following environmental conditions influence the symptoms of Tomato Mosaic Virus (ToMV)?");
        List<String> so14 = new ArrayList<>();
        so14.add("A. Temperature");
        so14.add("B. Day length");
        so14.add("C. Intensity of light");
        so14.add("D. All of the Above");
        so14.add("E. None");
        s14.setSingleChoiceQuestionOptionList(so14);
        s14.setSingleChoiceQuestionVirusId(1);

        m15.setChoiceQuestionId(3);
        m15.setMultipleChoiceQuestionContent("What are the common symptoms observed in tomato (fruit)?");
        List<String> mo15 = new ArrayList<>();
        mo15.add("A. BRONZE AND PATCH APPEARANCE");
        mo15.add("B. DISTORTED");
        mo15.add("C. YELLOW BLOTCHES AND NECROTIC SPOTS");
        mo15.add("D. BLUE PATCHES");
        m15.setMultipleChoiceQuestionOptionList(mo15);
        m15.setMultipleChoiceQuestionVirusId(1);

        singleChoiceQuestionModelListBackup1.add(s11);
        singleChoiceQuestionModelListBackup1.add(s12);
        singleChoiceQuestionModelListBackup1.add(s13);
        singleChoiceQuestionModelListBackup1.add(s14);
        multipleChoiceQuestionModelListBackup1.add(m15);

        // v2
        SingleChoiceQuestionModel s21 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s22 = new SingleChoiceQuestionModel();
        MultipleChoiceQuestionModel m23 = new MultipleChoiceQuestionModel();
        MultipleChoiceQuestionModel m24 = new MultipleChoiceQuestionModel();
        MultipleChoiceQuestionModel m25 = new MultipleChoiceQuestionModel();

        s21.setChoiceQuestionId(1);
        s21.setSingleChoiceQuestionContent("Are there chemical control strategies available to control the spread of tomato Mosaic Virus (ToMV) ?");
        List<String> so21 = new ArrayList<>();
        so21.add("A. YES");
        so21.add("B. NO");
        s21.setSingleChoiceQuestionOptionList(so21);
        s21.setSingleChoiceQuestionVirusId(1);
        
        s22.setChoiceQuestionId(1);
        s22.setSingleChoiceQuestionContent("Are there chemical control strategies available to control the spread of tomato Mosaic Virus (ToMV) ?");
        List<String> so22 = new ArrayList<>();
        so22.add("A. YES");
        so22.add("B. NO");
        s22.setSingleChoiceQuestionOptionList(so22);
        s22.setSingleChoiceQuestionVirusId(1);

        m23.setChoiceQuestionId(3);
        m23.setMultipleChoiceQuestionContent("What are the common symptoms observed in tomato (fruit)?");
        List<String> mo23 = new ArrayList<>();
        mo23.add("A. BRONZE AND PATCH APPEARANCE");
        mo23.add("B. DISTORTED");
        mo23.add("C. YELLOW BLOTCHES AND NECROTIC SPOTS");
        mo23.add("D. BLUE PATCHES");
        m23.setMultipleChoiceQuestionOptionList(mo23);
        m23.setMultipleChoiceQuestionVirusId(1);
        
        
        // v3

        // v4

        // v5

        // v6

        // v7

        // v8

        // v9

//
//        s22.setChoiceQuestionId(1);
//        s22.setSingleChoiceQuestionContent("Are there chemical control strategies available to control the spread of tomato Mosaic Virus (ToMV) ?");
//        List<String> so22 = new ArrayList<>();
//        so22.add("A. YES");
//        so22.add("B. NO");
//        s22.setSingleChoiceQuestionOptionList(so22);
//        s22.setSingleChoiceQuestionVirusId(1);
//
//        m23.setChoiceQuestionId(3);
//        m23.setMultipleChoiceQuestionContent("What are the common symptoms observed in tomato (fruit)?");
//        List<String> mo23 = new ArrayList<>();
//        mo23.add("A. BRONZE AND PATCH APPEARANCE");
//        mo23.add("B. DISTORTED");
//        mo23.add("C. YELLOW BLOTCHES AND NECROTIC SPOTS");
//        mo23.add("D. BLUE PATCHES");
//        m23.setMultipleChoiceQuestionOptionList(mo23);
//        m23.setMultipleChoiceQuestionVirusId(1);
//


        List[] choiceQuestionArray1 = new List[]{singleChoiceQuestionModelListBackup1, multipleChoiceQuestionModelListBackup1};
        List[] choiceQuestionArray2 = new List[]{singleChoiceQuestionModelListBackup2, multipleChoiceQuestionModelListBackup2};
        List[] choiceQuestionArray3 = new List[]{singleChoiceQuestionModelListBackup3, multipleChoiceQuestionModelListBackup3};
        List[] choiceQuestionArray4 = new List[]{singleChoiceQuestionModelListBackup4, multipleChoiceQuestionModelListBackup4};
        List[] choiceQuestionArray5 = new List[]{singleChoiceQuestionModelListBackup5, multipleChoiceQuestionModelListBackup5};
        List[] choiceQuestionArray6 = new List[]{singleChoiceQuestionModelListBackup6, multipleChoiceQuestionModelListBackup6};
        List[] choiceQuestionArray7 = new List[]{singleChoiceQuestionModelListBackup7, multipleChoiceQuestionModelListBackup7};
        List[] choiceQuestionArray8 = new List[]{singleChoiceQuestionModelListBackup8, multipleChoiceQuestionModelListBackup8};
        List[] choiceQuestionArray9 = new List[]{singleChoiceQuestionModelListBackup9, multipleChoiceQuestionModelListBackup9};
        choiceQuestionListBackup.add(choiceQuestionArray1);
        choiceQuestionListBackup.add(choiceQuestionArray2);
        choiceQuestionListBackup.add(choiceQuestionArray3);
        choiceQuestionListBackup.add(choiceQuestionArray4);
        choiceQuestionListBackup.add(choiceQuestionArray5);
        choiceQuestionListBackup.add(choiceQuestionArray6);
        choiceQuestionListBackup.add(choiceQuestionArray7);
        choiceQuestionListBackup.add(choiceQuestionArray8);
        choiceQuestionListBackup.add(choiceQuestionArray9);

        return choiceQuestionListBackup;
    }

    public static List<ChoiceQuestionCorrectAnswerModel> getChoiceQuestionCorrectAnswerModelListBackup() {
        return choiceQuestionCorrectAnswerModelListBackup;
    }
}
