/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tovrestws;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hoy
 */
@Entity
@Table(name = "CHOICEQUESTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choicequestion.findAll", query = "SELECT c FROM Choicequestion c")
    , @NamedQuery(name = "Choicequestion.findByChoiceQuestionId", query = "SELECT c FROM Choicequestion c WHERE c.choiceQuestionId = :choiceQuestionId")
    , @NamedQuery(name = "Choicequestion.findByChoiceQuestionContent", query = "SELECT c FROM Choicequestion c WHERE c.choiceQuestionContent = :choiceQuestionContent")
    , @NamedQuery(name = "Choicequestion.findByChoiceQuestionType", query = "SELECT c FROM Choicequestion c WHERE c.choiceQuestionType = :choiceQuestionType")
    , @NamedQuery(name = "Choicequestion.findByAnswer", query = "SELECT c FROM Choicequestion c WHERE c.answer = :answer")})
public class Choicequestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOICE_QUESTION_ID")
    private Integer choiceQuestionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "CHOICE_QUESTION_CONTENT")
    private String choiceQuestionContent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOICE_QUESTION_TYPE")
    private Character choiceQuestionType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ANSWER")
    private String answer;
    @JoinColumn(name = "CHOICE_QUESTION_VIRUS_ID", referencedColumnName = "VIRUS_ID")
    @ManyToOne(optional = false)
    private Virus choiceQuestionVirusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "choiceOptionQuestionId")
    private Collection<Choiceoption> choiceoptionCollection;

    public Choicequestion() {
    }

    public Choicequestion(Integer choiceQuestionId) {
        this.choiceQuestionId = choiceQuestionId;
    }

    public Choicequestion(Integer choiceQuestionId, String choiceQuestionContent, Character choiceQuestionType, String answer) {
        this.choiceQuestionId = choiceQuestionId;
        this.choiceQuestionContent = choiceQuestionContent;
        this.choiceQuestionType = choiceQuestionType;
        this.answer = answer;
    }

    public Integer getChoiceQuestionId() {
        return choiceQuestionId;
    }

    public void setChoiceQuestionId(Integer choiceQuestionId) {
        this.choiceQuestionId = choiceQuestionId;
    }

    public String getChoiceQuestionContent() {
        return choiceQuestionContent;
    }

    public void setChoiceQuestionContent(String choiceQuestionContent) {
        this.choiceQuestionContent = choiceQuestionContent;
    }

    public Character getChoiceQuestionType() {
        return choiceQuestionType;
    }

    public void setChoiceQuestionType(Character choiceQuestionType) {
        this.choiceQuestionType = choiceQuestionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Virus getChoiceQuestionVirusId() {
        return choiceQuestionVirusId;
    }

    public void setChoiceQuestionVirusId(Virus choiceQuestionVirusId) {
        this.choiceQuestionVirusId = choiceQuestionVirusId;
    }

    @XmlTransient
    public Collection<Choiceoption> getChoiceoptionCollection() {
        return choiceoptionCollection;
    }

    public void setChoiceoptionCollection(Collection<Choiceoption> choiceoptionCollection) {
        this.choiceoptionCollection = choiceoptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choiceQuestionId != null ? choiceQuestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choicequestion)) {
            return false;
        }
        Choicequestion other = (Choicequestion) object;
        if ((this.choiceQuestionId == null && other.choiceQuestionId != null) || (this.choiceQuestionId != null && !this.choiceQuestionId.equals(other.choiceQuestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tovrestws.Choicequestion[ choiceQuestionId=" + choiceQuestionId + " ]";
    }
    
}
