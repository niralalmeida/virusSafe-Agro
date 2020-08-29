package tovrestws;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tovrestws.Choiceoption;
import tovrestws.Virus;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-29T08:55:36")
@StaticMetamodel(Choicequestion.class)
public class Choicequestion_ { 

    public static volatile CollectionAttribute<Choicequestion, Choiceoption> choiceoptionCollection;
    public static volatile SingularAttribute<Choicequestion, Virus> choiceQuestionVirusId;
    public static volatile SingularAttribute<Choicequestion, Integer> choiceQuestionId;
    public static volatile SingularAttribute<Choicequestion, String> answer;
    public static volatile SingularAttribute<Choicequestion, Character> choiceQuestionType;
    public static volatile SingularAttribute<Choicequestion, String> choiceQuestionContent;

}