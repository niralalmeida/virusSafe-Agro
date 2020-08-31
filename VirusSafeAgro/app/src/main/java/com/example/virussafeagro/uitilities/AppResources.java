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

    public static List<VirusModel> virusModelListBackup = new ArrayList<>();
    public static List<List[]> choiceQuestionListBackup = new ArrayList<>();
    public static List<List<ChoiceQuestionCorrectAnswerModel>> choiceQuestionCorrectAnswerModelListBackup = new ArrayList<>();

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

    public static List[] getVirusChoiceQuestionModelListBackup(int index) {
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
        s11.setSingleChoiceQuestionContent("Q1. Are there chemical control strategies available to control the spread of tomato Mosaic Virus (ToMV) ?");
        List<String> so11 = new ArrayList<>();
        so11.add("A. YES");
        so11.add("B. NO");
        s11.setSingleChoiceQuestionOptionList(so11);
        s11.setSingleChoiceQuestionVirusId(1);

        s12.setChoiceQuestionId(2);
        s12.setSingleChoiceQuestionContent("Q2. Which disinfectant should be used to sterilise tools after handling infected plants?");
        List<String> so12 = new ArrayList<>();
        so12.add("A. Virkon S");
        so12.add("B. Virkon N");
        so12.add("C. Virkon X");
        so12.add("D. Virkon R");
        s12.setSingleChoiceQuestionOptionList(so12);
        s12.setSingleChoiceQuestionVirusId(1);

        s13.setChoiceQuestionId(3);
        s13.setSingleChoiceQuestionContent("Q3. What are the means of infection for Tomato Mosaic Virus (T oMV)?");
        List<String> so13 = new ArrayList<>();
        so13.add("A. INFECTED SEED");
        so13.add("B. CONTAMINATED TOOLS");
        so13.add("C. CONTAMINATED HANDS AND CLOTHES OF WORKERS");
        so13.add("D. ALL OF THE ABOVE");
        s13.setSingleChoiceQuestionOptionList(so13);
        s13.setSingleChoiceQuestionVirusId(1);

        s14.setChoiceQuestionId(4);
        s14.setSingleChoiceQuestionContent("Q4. Which of the following environmental conditions influence the symptoms of Tomato Mosaic Virus (ToMV)?");
        List<String> so14 = new ArrayList<>();
        so14.add("A. Temperature");
        so14.add("B. Day length");
        so14.add("C. Intensity of light");
        so14.add("D. All of the Above");
        so14.add("E. None");
        s14.setSingleChoiceQuestionOptionList(so14);
        s14.setSingleChoiceQuestionVirusId(1);

        m15.setChoiceQuestionId(5);
        m15.setMultipleChoiceQuestionContent("Q1. What are the common symptoms observed in tomato (fruit)?");
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

        s21.setChoiceQuestionId(6);
        s21.setSingleChoiceQuestionContent("Q1. The target spots grow up to");
        List<String> so21 = new ArrayList<>();
        so21.add("A. 10-12 mm");
        so21.add("B. 12-13 mm");
        so21.add("C. 11-12 mm");
        so21.add("D. 07- 09 mm");
        s21.setSingleChoiceQuestionOptionList(so21);
        s21.setSingleChoiceQuestionVirusId(2);
        
        s22.setChoiceQuestionId(7);
        s22.setSingleChoiceQuestionContent("Q2. What are the means of infection for target spots in tomato plants?");
        List<String> so22 = new ArrayList<>();
        so22.add("A. Infected seed");
        so22.add("B. Remains of earlier infected plants or host species");
        so22.add("C. ALL OF THE ABOVE");
        s22.setSingleChoiceQuestionOptionList(so22);
        s22.setSingleChoiceQuestionVirusId(2);

        m23.setChoiceQuestionId(8);
        m23.setMultipleChoiceQuestionContent("Q1. Which of the following chemical products can be used to control spread target spots in tomato plants ?");
        List<String> mo23 = new ArrayList<>();
        mo23.add("A. Mancozeb");
        mo23.add("B. copper oxychloride");
        mo23.add("C. chlorothalonil");
        mo23.add("D. Virkon S");
        m23.setMultipleChoiceQuestionOptionList(mo23);
        m23.setMultipleChoiceQuestionVirusId(2);

        m24.setChoiceQuestionId(9);
        m24.setMultipleChoiceQuestionContent("Q2. What are the common symptoms observed in tomato (fruit)?");
        List<String> mo24 = new ArrayList<>();
        mo24.add("A. Brown and black spots");
        mo24.add("B. series of concentric circles");
        mo24.add("C. Blotches and necrotic spots");
        mo24.add("D. All of the above");
        m24.setMultipleChoiceQuestionOptionList(mo24);
        m24.setMultipleChoiceQuestionVirusId(2);

        m25.setChoiceQuestionId(10);
        m25.setMultipleChoiceQuestionContent("Q3. Target spot attacks which area of the plant first?");
        List<String> mo25 = new ArrayList<>();
        mo25.add("A. Leaves");
        mo25.add("B. Stems");
        mo25.add("C. Roots");
        mo25.add("D. Fruit");
        m25.setMultipleChoiceQuestionOptionList(mo25);
        m25.setMultipleChoiceQuestionVirusId(2);

        singleChoiceQuestionModelListBackup2.add(s21);
        singleChoiceQuestionModelListBackup2.add(s22);
        multipleChoiceQuestionModelListBackup2.add(m23);
        multipleChoiceQuestionModelListBackup2.add(m24);
        multipleChoiceQuestionModelListBackup2.add(m25);

        // v3
        SingleChoiceQuestionModel s31 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s32 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s33 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s34 = new SingleChoiceQuestionModel();
        MultipleChoiceQuestionModel m35 = new MultipleChoiceQuestionModel();

        s31.setChoiceQuestionId(11);
        s31.setSingleChoiceQuestionContent("Q1. What are the common symptoms observed in tomato (fruit)?");
        List<String> so31 = new ArrayList<>();
        so31.add("A. Fruit has waxy white halo surrounding");
        so31.add("B. Yellowblotches and necrotic spots");
        so31.add("C. Bluepatches");
        so31.add("D. All of the above");
        s31.setSingleChoiceQuestionOptionList(so31);
        s31.setSingleChoiceQuestionVirusId(3);

        s32.setChoiceQuestionId(12);
        s32.setSingleChoiceQuestionContent("Q2. How wide are the bacterial spots on the tomato leaves?");
        List<String> so32 = new ArrayList<>();
        so32.add("A. 3mm");
        so32.add("B. 7mm");
        so32.add("C. 4mm");
        so32.add("D. 2mm");
        s32.setSingleChoiceQuestionOptionList(so32);
        s32.setSingleChoiceQuestionVirusId(3);

        s33.setChoiceQuestionId(13);
        s33.setSingleChoiceQuestionContent("Q3. What are the environmental conditions that influence the spread of bacterial spots?");
        List<String> so33 = new ArrayList<>();
        so33.add("A. High temperatures");
        so33.add("B. High humidity");
        so33.add("C. Overhead irrigation");
        so33.add("D. Frequent rainfall");
        so33.add("E. All of the above");
        s33.setSingleChoiceQuestionOptionList(so33);
        s33.setSingleChoiceQuestionVirusId(3);

        s34.setChoiceQuestionId(14);
        s34.setSingleChoiceQuestionContent("Q4. Bacterial spots affect which of the following parts of the plant?");
        List<String> so34 = new ArrayList<>();
        so34.add("A. Fruits");
        so34.add("B. Stems");
        so34.add("C. Leaves");
        so34.add("D. Petioles");
        so34.add("E. All of the above");
        s34.setSingleChoiceQuestionOptionList(so34);
        s34.setSingleChoiceQuestionVirusId(3);

        m35.setChoiceQuestionId(15);
        m35.setMultipleChoiceQuestionContent("Q1. Which of the following chemical products can be used to control spread bacterial spots in tomato plants ?");
        List<String> mo35 = new ArrayList<>();
        mo35.add("A. Copper plus mancozeb");
        mo35.add("B. Copper fungicides");
        mo35.add("C. Chlorothalonil");
        mo35.add("D. Virkon S");
        m35.setMultipleChoiceQuestionOptionList(mo35);
        m35.setMultipleChoiceQuestionVirusId(3);

        singleChoiceQuestionModelListBackup3.add(s31);
        singleChoiceQuestionModelListBackup3.add(s32);
        singleChoiceQuestionModelListBackup3.add(s33);
        singleChoiceQuestionModelListBackup3.add(s34);
        multipleChoiceQuestionModelListBackup3.add(m35);

        // v4
        SingleChoiceQuestionModel s41 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s42 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s43 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s44 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s45 = new SingleChoiceQuestionModel();

        s41.setChoiceQuestionId(16);
        s41.setSingleChoiceQuestionContent("Q1. The genome of tomato yellow curl virus is made up of:");
        List<String> so41 = new ArrayList<>();
        so41.add("A. DNA");
        so41.add("B. RNA");
        so41.add("C. DNA AND RNA");
        so41.add("D. Neither RNA nor DNA");
        s41.setSingleChoiceQuestionOptionList(so41);
        s41.setSingleChoiceQuestionVirusId(4);

        s42.setChoiceQuestionId(17);
        s42.setSingleChoiceQuestionContent("Q2. What are the common symptoms observed in tomato (fruit)?");
        List<String> so42 = new ArrayList<>();
        so42.add("A. Small fruit");
        so42.add("B. Dry fruit");
        so42.add("C. All of the above");
        s42.setSingleChoiceQuestionOptionList(so42);
        s42.setSingleChoiceQuestionVirusId(4);

        s43.setChoiceQuestionId(18);
        s43.setSingleChoiceQuestionContent("Q3. Tomato yellow curl virus is spread by which fly?");
        List<String> so43 = new ArrayList<>();
        so43.add("A. Silverleaf whitefly");
        so43.add("B. Aleurothrixus");
        so43.add("C. Greenhouse whitefly");
        so43.add("D. Dialeurodes");
        so43.add("E. All of the above");
        s43.setSingleChoiceQuestionOptionList(so43);
        s43.setSingleChoiceQuestionVirusId(4);

        s44.setChoiceQuestionId(19);
        s44.setSingleChoiceQuestionContent("Q4. Tomato yellow leaf curl virus is not transmitted in seed and through soil");
        List<String> so44 = new ArrayList<>();
        so44.add("A. True");
        so44.add("B. False");
        s44.setSingleChoiceQuestionOptionList(so44);
        s44.setSingleChoiceQuestionVirusId(4);

        s45.setChoiceQuestionId(20);
        s45.setSingleChoiceQuestionContent("Q5. What are the common symptoms observed of yellow leaf curl virus in tomato leaves??");
        List<String> so45 = new ArrayList<>();
        so45.add("A. Leathery texture");
        so45.add("B. Wrinkled leaves");
        so45.add("C. Leaves bent downwards");
        so45.add("D. Yellow between veins");
        so45.add("E. All of the above");
        s45.setSingleChoiceQuestionOptionList(so45);
        s45.setSingleChoiceQuestionVirusId(4);

        singleChoiceQuestionModelListBackup4.add(s41);
        singleChoiceQuestionModelListBackup4.add(s42);
        singleChoiceQuestionModelListBackup4.add(s43);
        singleChoiceQuestionModelListBackup4.add(s44);
        singleChoiceQuestionModelListBackup4.add(s45);

        // v5
        SingleChoiceQuestionModel s51 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s52 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s53 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s54 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s55 = new SingleChoiceQuestionModel();

        s51.setChoiceQuestionId(21);
        s51.setSingleChoiceQuestionContent("Q1. What are the common symptoms of late blight observed in tomato (fruit)?");
        List<String> so51 = new ArrayList<>();
        so51.add("A. Small fruit");
        so51.add("B. Dark brown firm rots");
        so51.add("C. All of the above");
        so51.add("D. None");
        s51.setSingleChoiceQuestionOptionList(so51);
        s51.setSingleChoiceQuestionVirusId(5);

        s52.setChoiceQuestionId(22);
        s52.setSingleChoiceQuestionContent("Q2. Preventive sprays should not be used before symptoms are seen");
        List<String> so52 = new ArrayList<>();
        so52.add("A. True");
        so52.add("B. False");
        s52.setSingleChoiceQuestionOptionList(so52);
        s52.setSingleChoiceQuestionVirusId(5);

        s53.setChoiceQuestionId(23);
        s53.setSingleChoiceQuestionContent("Q3. Tomato late blight is Airborne");
        List<String> so53 = new ArrayList<>();
        so53.add("A. True");
        so53.add("B. False");
        s53.setSingleChoiceQuestionOptionList(so53);
        s53.setSingleChoiceQuestionVirusId(5);

        s54.setChoiceQuestionId(24);
        s54.setSingleChoiceQuestionContent("Q4. Tomato late blight is favoured by wet and cool weather");
        List<String> so54 = new ArrayList<>();
        so54.add("A. True");
        so54.add("B. False");
        s54.setSingleChoiceQuestionOptionList(so54);
        s54.setSingleChoiceQuestionVirusId(5);

        s55.setChoiceQuestionId(25);
        s55.setSingleChoiceQuestionContent("Q5. Which of the following preventive sprays can be used to prevent the spread of the virus?");
        List<String> so55 = new ArrayList<>();
        so55.add("A. Copper product");
        so55.add("B. chlorothalonil");
        so55.add("C. mancozeb");
        so55.add("D. All of the above");
        s55.setSingleChoiceQuestionOptionList(so55);
        s55.setSingleChoiceQuestionVirusId(5);

        singleChoiceQuestionModelListBackup5.add(s51);
        singleChoiceQuestionModelListBackup5.add(s52);
        singleChoiceQuestionModelListBackup5.add(s53);
        singleChoiceQuestionModelListBackup5.add(s54);
        singleChoiceQuestionModelListBackup5.add(s55);

        // v6
        SingleChoiceQuestionModel s61 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s62 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s63 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s64 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s65 = new SingleChoiceQuestionModel();

        s61.setChoiceQuestionId(26);
        s61.setSingleChoiceQuestionContent("Q1. In tomato leaf mold virus, pale green or yellowish spots are present on:");
        List<String> so61 = new ArrayList<>();
        so61.add("A. Upper leaf surface");
        so61.add("B. Lower leaf surface");
        s61.setSingleChoiceQuestionOptionList(so61);
        s61.setSingleChoiceQuestionVirusId(6);

        s62.setChoiceQuestionId(27);
        s62.setSingleChoiceQuestionContent("Q2. Leaf mold is a serious issue in on- field grown tomatoes:");
        List<String> so62 = new ArrayList<>();
        so62.add("A. True");
        so62.add("B. False");
        s62.setSingleChoiceQuestionOptionList(so62);
        s62.setSingleChoiceQuestionVirusId(6);

        s63.setChoiceQuestionId(28);
        s63.setSingleChoiceQuestionContent("Q3. In tomato leaf mold virus, Olive, green mold is visible on:");
        List<String> so63 = new ArrayList<>();
        so63.add("A. Upper leaf surface");
        so63.add("B. Lower leaf surface");
        s63.setSingleChoiceQuestionOptionList(so63);
        s63.setSingleChoiceQuestionVirusId(6);

        s64.setChoiceQuestionId(29);
        s64.setSingleChoiceQuestionContent("Q4. Conidia can be easily spread by rain and wind");
        List<String> so64 = new ArrayList<>();
        so64.add("A. True");
        so64.add("B. False");
        s64.setSingleChoiceQuestionOptionList(so64);
        s64.setSingleChoiceQuestionVirusId(6);

        s65.setChoiceQuestionId(30);
        s65.setSingleChoiceQuestionContent("Q5. The leaves should be sprayed at an 10-14 day intervals, when yellow spots are seen");
        List<String> so65 = new ArrayList<>();
        so65.add("A. True");
        so65.add("B. False");
        s65.setSingleChoiceQuestionOptionList(so65);
        s65.setSingleChoiceQuestionVirusId(6);

        singleChoiceQuestionModelListBackup6.add(s61);
        singleChoiceQuestionModelListBackup6.add(s62);
        singleChoiceQuestionModelListBackup6.add(s63);
        singleChoiceQuestionModelListBackup6.add(s64);
        singleChoiceQuestionModelListBackup6.add(s65);

        // v7
        SingleChoiceQuestionModel s71 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s72 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s73 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s74 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s75 = new SingleChoiceQuestionModel();

        s71.setChoiceQuestionId(31);
        s71.setSingleChoiceQuestionContent("Q1. In early blight, spots grow up to half inch in diameter");
        List<String> so71 = new ArrayList<>();
        so71.add("A. True");
        so71.add("B. False");
        s71.setSingleChoiceQuestionOptionList(so71);
        s71.setSingleChoiceQuestionVirusId(7);

        s72.setChoiceQuestionId(32);
        s72.setSingleChoiceQuestionContent("Q2. What are the common symptoms observed in tomato (fruit)?");
        List<String> so72 = new ArrayList<>();
        so72.add("A. Bronze and patch appearance");
        so72.add("B. Distorted");
        so72.add("C. Yellow blotches and necrotic spots");
        so72.add("D. Leathery and black spots");
        s72.setSingleChoiceQuestionOptionList(so72);
        s72.setSingleChoiceQuestionVirusId(7);

        s73.setChoiceQuestionId(33);
        s73.setSingleChoiceQuestionContent("Q3. Early blight can occur in any type of weather");
        List<String> so73 = new ArrayList<>();
        so73.add("A. True");
        so73.add("B. False");
        s73.setSingleChoiceQuestionOptionList(so73);
        s73.setSingleChoiceQuestionVirusId(7);

        s74.setChoiceQuestionId(34);
        s74.setSingleChoiceQuestionContent("Q4. Early blight commonly affects:");
        List<String> so74 = new ArrayList<>();
        so74.add("A. Leaves");
        so74.add("B. Stems");
        so74.add("C. Fruits");
        so74.add("D. ALL OF THE ABOVE");
        s74.setSingleChoiceQuestionOptionList(so74);
        s74.setSingleChoiceQuestionVirusId(7);

        s75.setChoiceQuestionId(35);
        s75.setSingleChoiceQuestionContent("Q5. Early blight Strikes after a period of heavy rainfall");
        List<String> so75 = new ArrayList<>();
        so75.add("A. True");
        so75.add("B. False");
        s75.setSingleChoiceQuestionOptionList(so75);
        s75.setSingleChoiceQuestionVirusId(7);

        singleChoiceQuestionModelListBackup7.add(s71);
        singleChoiceQuestionModelListBackup7.add(s72);
        singleChoiceQuestionModelListBackup7.add(s73);
        singleChoiceQuestionModelListBackup7.add(s74);
        singleChoiceQuestionModelListBackup7.add(s75);

        // v8
        SingleChoiceQuestionModel s81 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s82 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s83 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s84 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s85 = new SingleChoiceQuestionModel();

        s81.setChoiceQuestionId(36);
        s81.setSingleChoiceQuestionContent("Q1. Miticide spray should be applied if mites are present on at least 10% of the leaves");
        List<String> so81 = new ArrayList<>();
        so81.add("A. True");
        so81.add("B. False");
        s81.setSingleChoiceQuestionOptionList(so81);
        s81.setSingleChoiceQuestionVirusId(8);

        s82.setChoiceQuestionId(37);
        s82.setSingleChoiceQuestionContent("Q2. One effective application of chemical is sufficient");
        List<String> so82 = new ArrayList<>();
        so82.add("A. True");
        so82.add("B. False");
        s82.setSingleChoiceQuestionOptionList(so82);
        s82.setSingleChoiceQuestionVirusId(8);

        s83.setChoiceQuestionId(38);
        s83.setSingleChoiceQuestionContent("Q3. Adult spider mites are 0.8 mm in size");
        List<String> so83 = new ArrayList<>();
        so83.add("A. True");
        so83.add("B. False");
        s83.setSingleChoiceQuestionOptionList(so83);
        s83.setSingleChoiceQuestionVirusId(8);

        s84.setChoiceQuestionId(39);
        s84.setSingleChoiceQuestionContent("Q4. Insecticides should be applied even if there are no mites present on leaves");
        List<String> so84 = new ArrayList<>();
        so84.add("A. True");
        so84.add("B. False");
        s84.setSingleChoiceQuestionOptionList(so84);
        s84.setSingleChoiceQuestionVirusId(8);

        s85.setChoiceQuestionId(40);
        s85.setSingleChoiceQuestionContent("Q5. Spraying chemicals to plants should be avoided in hot weathers as they may cause chemical injuries to plants");
        List<String> so85 = new ArrayList<>();
        so85.add("A. True");
        so85.add("B. False");
        s85.setSingleChoiceQuestionOptionList(so85);
        s85.setSingleChoiceQuestionVirusId(8);

        singleChoiceQuestionModelListBackup8.add(s81);
        singleChoiceQuestionModelListBackup8.add(s82);
        singleChoiceQuestionModelListBackup8.add(s83);
        singleChoiceQuestionModelListBackup8.add(s84);
        singleChoiceQuestionModelListBackup8.add(s85);
        
        // v9
        SingleChoiceQuestionModel s91 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s92 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s93 = new SingleChoiceQuestionModel();
        SingleChoiceQuestionModel s94 = new SingleChoiceQuestionModel();
        MultipleChoiceQuestionModel m95 = new MultipleChoiceQuestionModel();

        s91.setChoiceQuestionId(41);
        s91.setSingleChoiceQuestionContent("Q1. Fungicidal spray will protect infected leaves");
        List<String> so91 = new ArrayList<>();
        so91.add("A. True");
        so91.add("B. False");
        s91.setSingleChoiceQuestionOptionList(so91);
        s91.setSingleChoiceQuestionVirusId(9);

        s92.setChoiceQuestionId(42);
        s92.setSingleChoiceQuestionContent("Q2. Which are the most commonly used chemicals to treat plants from Septoria blight");
        List<String> so92 = new ArrayList<>();
        so92.add("A. Virkon S");
        so92.add("B. Virkon N");
        so92.add("C. Maneb");
        so92.add("D. Chlorothalonil");
        s92.setSingleChoiceQuestionOptionList(so92);
        s92.setSingleChoiceQuestionVirusId(9);

        s93.setChoiceQuestionId(43);
        s93.setSingleChoiceQuestionContent("Q3. For effective results the chemicals must be applied at 7-10-day intervals");
        List<String> so93 = new ArrayList<>();
        so93.add("A. True");
        so93.add("B. False");
        s93.setSingleChoiceQuestionOptionList(so93);
        s93.setSingleChoiceQuestionVirusId(9);

        s94.setChoiceQuestionId(44);
        s94.setSingleChoiceQuestionContent("Q4. Damp conditions are favourable for virus to develop");
        List<String> so94 = new ArrayList<>();
        so94.add("A. True");
        so94.add("B. False");
        s94.setSingleChoiceQuestionOptionList(so94);
        s94.setSingleChoiceQuestionVirusId(9);

        m95.setChoiceQuestionId(45);
        m95.setMultipleChoiceQuestionContent("Q1. Diameter of Septoria leaf spot is about:");
        List<String> mo95 = new ArrayList<>();
        mo95.add("A. 1.6 to 3.2 mm");
        mo95.add("B. 1.0 to 1.2 mm");
        mo95.add("C. 1.3 to 1.5 mm");
        mo95.add("D. None of the above");
        m95.setMultipleChoiceQuestionOptionList(mo95);
        m95.setMultipleChoiceQuestionVirusId(9);

        singleChoiceQuestionModelListBackup9.add(s91);
        singleChoiceQuestionModelListBackup9.add(s92);
        singleChoiceQuestionModelListBackup9.add(s93);
        singleChoiceQuestionModelListBackup9.add(s94);
        multipleChoiceQuestionModelListBackup9.add(m95);

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

        return choiceQuestionListBackup.get(index);
    }

    public static List<ChoiceQuestionCorrectAnswerModel> getChoiceQuestionCorrectAnswerModelListBackup(int index) {
        List<ChoiceQuestionCorrectAnswerModel> list1 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list2 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list3 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list4 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list5 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list6 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list7 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list8 = new ArrayList<>();
        List<ChoiceQuestionCorrectAnswerModel> list9 = new ArrayList<>();

        // v1
        ChoiceQuestionCorrectAnswerModel ans11 = new ChoiceQuestionCorrectAnswerModel(1);
        ChoiceQuestionCorrectAnswerModel ans12 = new ChoiceQuestionCorrectAnswerModel(5);
        ChoiceQuestionCorrectAnswerModel ans13 = new ChoiceQuestionCorrectAnswerModel(2);
        ChoiceQuestionCorrectAnswerModel ans14 = new ChoiceQuestionCorrectAnswerModel(3);
        ChoiceQuestionCorrectAnswerModel ans15 = new ChoiceQuestionCorrectAnswerModel(4);

        List<String> oans11 = new ArrayList<>();
        oans11.add("B");
        ans11.setCorrectAnswerList(oans11);
        ans11.setChoiceQuestionType("single");
        List<String> oans12 = new ArrayList<>();
        oans12.add("A");
        oans12.add("B");
        oans12.add("C");
        ans12.setCorrectAnswerList(oans12);
        ans12.setChoiceQuestionType("multiple");
        List<String> oans13 = new ArrayList<>();
        oans13.add("A");
        ans13.setCorrectAnswerList(oans13);
        ans13.setChoiceQuestionType("single");
        List<String> oans14 = new ArrayList<>();
        oans14.add("D");
        ans14.setCorrectAnswerList(oans14);
        ans14.setChoiceQuestionType("single");
        List<String> oans15 = new ArrayList<>();
        oans15.add("D");
        ans15.setCorrectAnswerList(oans15);
        ans15.setChoiceQuestionType("single");

        list1.add(ans11);
        list1.add(ans12);
        list1.add(ans13);
        list1.add(ans14);
        list1.add(ans15);

        // v2
        ChoiceQuestionCorrectAnswerModel ans21 = new ChoiceQuestionCorrectAnswerModel(8);
        ChoiceQuestionCorrectAnswerModel ans22 = new ChoiceQuestionCorrectAnswerModel(9);
        ChoiceQuestionCorrectAnswerModel ans23 = new ChoiceQuestionCorrectAnswerModel(6);
        ChoiceQuestionCorrectAnswerModel ans24 = new ChoiceQuestionCorrectAnswerModel(7);
        ChoiceQuestionCorrectAnswerModel ans25 = new ChoiceQuestionCorrectAnswerModel(10);

        List<String> oans21 = new ArrayList<>();
        oans21.add("A");
        oans21.add("B");
        oans21.add("C");
        ans21.setCorrectAnswerList(oans21);
        ans21.setChoiceQuestionType("multiple");
        List<String> oans22 = new ArrayList<>();
        oans22.add("A");
        oans22.add("B");
        ans22.setCorrectAnswerList(oans22);
        ans22.setChoiceQuestionType("multiple");
        List<String> oans23 = new ArrayList<>();
        oans23.add("A");
        ans23.setCorrectAnswerList(oans23);
        ans23.setChoiceQuestionType("single");
        List<String> oans24 = new ArrayList<>();
        oans24.add("C");
        ans24.setCorrectAnswerList(oans24);
        ans24.setChoiceQuestionType("single");
        List<String> oans25 = new ArrayList<>();
        oans25.add("A");
        oans25.add("B");
        ans25.setCorrectAnswerList(oans25);
        ans25.setChoiceQuestionType("multiple");

        list2.add(ans21);
        list2.add(ans22);
        list2.add(ans23);
        list2.add(ans24);
        list2.add(ans25);
        
        // v3
        ChoiceQuestionCorrectAnswerModel ans31 = new ChoiceQuestionCorrectAnswerModel(12);
        ChoiceQuestionCorrectAnswerModel ans32 = new ChoiceQuestionCorrectAnswerModel(13);
        ChoiceQuestionCorrectAnswerModel ans33 = new ChoiceQuestionCorrectAnswerModel(14);
        ChoiceQuestionCorrectAnswerModel ans34 = new ChoiceQuestionCorrectAnswerModel(15);
        ChoiceQuestionCorrectAnswerModel ans35 = new ChoiceQuestionCorrectAnswerModel(11);

        List<String> oans31 = new ArrayList<>();
        oans31.add("A");
        oans31.add("B");
        ans31.setCorrectAnswerList(oans31);
        ans31.setChoiceQuestionType("multiple");
        List<String> oans32 = new ArrayList<>();
        oans32.add("A");
        ans32.setCorrectAnswerList(oans32);
        ans32.setChoiceQuestionType("single");
        List<String> oans33 = new ArrayList<>();
        oans33.add("A");
        ans33.setCorrectAnswerList(oans33);
        ans33.setChoiceQuestionType("single");
        List<String> oans34 = new ArrayList<>();
        oans34.add("E");
        ans34.setCorrectAnswerList(oans34);
        ans34.setChoiceQuestionType("single");
        List<String> oans35 = new ArrayList<>();
        oans35.add("E");
        ans35.setCorrectAnswerList(oans35);
        ans35.setChoiceQuestionType("single");

        list3.add(ans31);
        list3.add(ans32);
        list3.add(ans33);
        list3.add(ans34);
        list3.add(ans35);
        
        // v4
        ChoiceQuestionCorrectAnswerModel ans41 = new ChoiceQuestionCorrectAnswerModel(16);
        ChoiceQuestionCorrectAnswerModel ans42 = new ChoiceQuestionCorrectAnswerModel(17);
        ChoiceQuestionCorrectAnswerModel ans43 = new ChoiceQuestionCorrectAnswerModel(18);
        ChoiceQuestionCorrectAnswerModel ans44 = new ChoiceQuestionCorrectAnswerModel(19);
        ChoiceQuestionCorrectAnswerModel ans45 = new ChoiceQuestionCorrectAnswerModel(20);

        List<String> oans41 = new ArrayList<>();
        oans41.add("A");
        ans41.setCorrectAnswerList(oans41);
        ans41.setChoiceQuestionType("single");
        List<String> oans42 = new ArrayList<>();
        oans42.add("C");
        ans42.setCorrectAnswerList(oans42);
        ans42.setChoiceQuestionType("single");
        List<String> oans43 = new ArrayList<>();
        oans43.add("A");
        ans43.setCorrectAnswerList(oans43);
        ans43.setChoiceQuestionType("single");
        List<String> oans44 = new ArrayList<>();
        oans44.add("A");
        ans44.setCorrectAnswerList(oans44);
        ans44.setChoiceQuestionType("single");
        List<String> oans45 = new ArrayList<>();
        oans45.add("E");
        ans45.setCorrectAnswerList(oans45);
        ans45.setChoiceQuestionType("single");

        list4.add(ans41);
        list4.add(ans42);
        list4.add(ans43);
        list4.add(ans44);
        list4.add(ans45);

        // v5
        ChoiceQuestionCorrectAnswerModel ans51 = new ChoiceQuestionCorrectAnswerModel(21);
        ChoiceQuestionCorrectAnswerModel ans52 = new ChoiceQuestionCorrectAnswerModel(22);
        ChoiceQuestionCorrectAnswerModel ans53 = new ChoiceQuestionCorrectAnswerModel(23);
        ChoiceQuestionCorrectAnswerModel ans54 = new ChoiceQuestionCorrectAnswerModel(24);
        ChoiceQuestionCorrectAnswerModel ans55 = new ChoiceQuestionCorrectAnswerModel(25);

        List<String> oans51 = new ArrayList<>();
        oans51.add("B");
        ans51.setCorrectAnswerList(oans51);
        ans51.setChoiceQuestionType("single");
        List<String> oans52 = new ArrayList<>();
        oans52.add("B");
        ans52.setCorrectAnswerList(oans52);
        ans52.setChoiceQuestionType("single");
        List<String> oans53 = new ArrayList<>();
        oans53.add("A");
        ans53.setCorrectAnswerList(oans53);
        ans53.setChoiceQuestionType("single");
        List<String> oans54 = new ArrayList<>();
        oans54.add("A");
        ans54.setCorrectAnswerList(oans54);
        ans54.setChoiceQuestionType("single");
        List<String> oans55 = new ArrayList<>();
        oans55.add("D");
        ans55.setCorrectAnswerList(oans55);
        ans55.setChoiceQuestionType("single");

        list5.add(ans51);
        list5.add(ans52);
        list5.add(ans53);
        list5.add(ans54);
        list5.add(ans55);

        // v6
        ChoiceQuestionCorrectAnswerModel ans61 = new ChoiceQuestionCorrectAnswerModel(26);
        ChoiceQuestionCorrectAnswerModel ans62 = new ChoiceQuestionCorrectAnswerModel(27);
        ChoiceQuestionCorrectAnswerModel ans63 = new ChoiceQuestionCorrectAnswerModel(28);
        ChoiceQuestionCorrectAnswerModel ans64 = new ChoiceQuestionCorrectAnswerModel(29);
        ChoiceQuestionCorrectAnswerModel ans65 = new ChoiceQuestionCorrectAnswerModel(30);

        List<String> oans61 = new ArrayList<>();
        oans61.add("A");
        ans61.setCorrectAnswerList(oans61);
        ans61.setChoiceQuestionType("single");
        List<String> oans62 = new ArrayList<>();
        oans62.add("B");
        ans62.setCorrectAnswerList(oans62);
        ans62.setChoiceQuestionType("single");
        List<String> oans63 = new ArrayList<>();
        oans63.add("B");
        ans63.setCorrectAnswerList(oans63);
        ans63.setChoiceQuestionType("single");
        List<String> oans64 = new ArrayList<>();
        oans64.add("A");
        ans64.setCorrectAnswerList(oans64);
        ans64.setChoiceQuestionType("single");
        List<String> oans65 = new ArrayList<>();
        oans65.add("A");
        ans65.setCorrectAnswerList(oans65);
        ans65.setChoiceQuestionType("single");

        list6.add(ans61);
        list6.add(ans62);
        list6.add(ans63);
        list6.add(ans64);
        list6.add(ans65);

        // v7
        ChoiceQuestionCorrectAnswerModel ans71 = new ChoiceQuestionCorrectAnswerModel(31);
        ChoiceQuestionCorrectAnswerModel ans72 = new ChoiceQuestionCorrectAnswerModel(32);
        ChoiceQuestionCorrectAnswerModel ans73 = new ChoiceQuestionCorrectAnswerModel(33);
        ChoiceQuestionCorrectAnswerModel ans74 = new ChoiceQuestionCorrectAnswerModel(34);
        ChoiceQuestionCorrectAnswerModel ans75 = new ChoiceQuestionCorrectAnswerModel(35);

        List<String> oans71 = new ArrayList<>();
        oans71.add("A");
        ans71.setCorrectAnswerList(oans71);
        ans71.setChoiceQuestionType("single");
        List<String> oans72 = new ArrayList<>();
        oans72.add("D");
        ans72.setCorrectAnswerList(oans72);
        ans72.setChoiceQuestionType("single");
        List<String> oans73 = new ArrayList<>();
        oans73.add("A");
        ans73.setCorrectAnswerList(oans73);
        ans73.setChoiceQuestionType("single");
        List<String> oans74 = new ArrayList<>();
        oans74.add("D");
        ans74.setCorrectAnswerList(oans74);
        ans74.setChoiceQuestionType("single");
        List<String> oans75 = new ArrayList<>();
        oans75.add("A");
        ans75.setCorrectAnswerList(oans75);
        ans75.setChoiceQuestionType("single");

        list7.add(ans71);
        list7.add(ans72);
        list7.add(ans73);
        list7.add(ans74);
        list7.add(ans75);

        // v8
        ChoiceQuestionCorrectAnswerModel ans81 = new ChoiceQuestionCorrectAnswerModel(36);
        ChoiceQuestionCorrectAnswerModel ans82 = new ChoiceQuestionCorrectAnswerModel(37);
        ChoiceQuestionCorrectAnswerModel ans83 = new ChoiceQuestionCorrectAnswerModel(38);
        ChoiceQuestionCorrectAnswerModel ans84 = new ChoiceQuestionCorrectAnswerModel(39);
        ChoiceQuestionCorrectAnswerModel ans85 = new ChoiceQuestionCorrectAnswerModel(40);

        List<String> oans81 = new ArrayList<>();
        oans81.add("B");
        ans81.setCorrectAnswerList(oans81);
        ans81.setChoiceQuestionType("single");
        List<String> oans82 = new ArrayList<>();
        oans82.add("A");
        ans82.setCorrectAnswerList(oans82);
        ans82.setChoiceQuestionType("single");
        List<String> oans83 = new ArrayList<>();
        oans83.add("B");
        ans83.setCorrectAnswerList(oans83);
        ans83.setChoiceQuestionType("single");
        List<String> oans84 = new ArrayList<>();
        oans84.add("B");
        ans84.setCorrectAnswerList(oans84);
        ans84.setChoiceQuestionType("single");
        List<String> oans85 = new ArrayList<>();
        oans85.add("A");
        ans85.setCorrectAnswerList(oans85);
        ans85.setChoiceQuestionType("single");

        list8.add(ans81);
        list8.add(ans82);
        list8.add(ans83);
        list8.add(ans84);
        list8.add(ans85);

        // v9
        ChoiceQuestionCorrectAnswerModel ans91 = new ChoiceQuestionCorrectAnswerModel(41);
        ChoiceQuestionCorrectAnswerModel ans92 = new ChoiceQuestionCorrectAnswerModel(45);
        ChoiceQuestionCorrectAnswerModel ans93 = new ChoiceQuestionCorrectAnswerModel(42);
        ChoiceQuestionCorrectAnswerModel ans94 = new ChoiceQuestionCorrectAnswerModel(43);
        ChoiceQuestionCorrectAnswerModel ans95 = new ChoiceQuestionCorrectAnswerModel(44);

        List<String> oans91 = new ArrayList<>();
        oans91.add("B");
        ans91.setCorrectAnswerList(oans91);
        ans91.setChoiceQuestionType("single");
        List<String> oans92 = new ArrayList<>();
        oans92.add("C");
        oans92.add("D");
        ans92.setCorrectAnswerList(oans92);
        ans92.setChoiceQuestionType("multiple");
        List<String> oans93 = new ArrayList<>();
        oans93.add("A");
        ans93.setCorrectAnswerList(oans93);
        ans93.setChoiceQuestionType("single");
        List<String> oans94 = new ArrayList<>();
        oans94.add("A");
        ans94.setCorrectAnswerList(oans94);
        ans94.setChoiceQuestionType("single");
        List<String> oans95 = new ArrayList<>();
        oans95.add("A");
        ans95.setCorrectAnswerList(oans95);
        ans95.setChoiceQuestionType("single");

        list9.add(ans91);
        list9.add(ans92);
        list9.add(ans93);
        list9.add(ans94);
        list9.add(ans95);

        choiceQuestionCorrectAnswerModelListBackup.add(list1);
        choiceQuestionCorrectAnswerModelListBackup.add(list2);
        choiceQuestionCorrectAnswerModelListBackup.add(list3);
        choiceQuestionCorrectAnswerModelListBackup.add(list4);
        choiceQuestionCorrectAnswerModelListBackup.add(list5);
        choiceQuestionCorrectAnswerModelListBackup.add(list6);
        choiceQuestionCorrectAnswerModelListBackup.add(list7);
        choiceQuestionCorrectAnswerModelListBackup.add(list8);
        choiceQuestionCorrectAnswerModelListBackup.add(list9);
        return choiceQuestionCorrectAnswerModelListBackup.get(index);
    }
}
