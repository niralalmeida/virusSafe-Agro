package tovrestws;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tovrestws.Choicequestion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-01T03:54:10")
@StaticMetamodel(Virus.class)
public class Virus_ { 

    public static volatile SingularAttribute<Virus, String> symptoms;
    public static volatile SingularAttribute<Virus, Integer> virusId;
    public static volatile SingularAttribute<Virus, String> virusFullName;
    public static volatile SingularAttribute<Virus, String> virusAbbreviation;
    public static volatile CollectionAttribute<Virus, Choicequestion> choicequestionCollection;
    public static volatile SingularAttribute<Virus, String> distribution;
    public static volatile SingularAttribute<Virus, String> prevention;
    public static volatile SingularAttribute<Virus, String> spread;

}