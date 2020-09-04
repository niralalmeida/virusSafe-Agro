/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tovrestws;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hoy
 */
@Entity
@Table(name = "CHOICEOPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choiceoption.findAll", query = "SELECT c FROM Choiceoption c")
    , @NamedQuery(name = "Choiceoption.findByChoiceOptionId", query = "SELECT c FROM Choiceoption c WHERE c.choiceOptionId = :choiceOptionId")
    , @NamedQuery(name = "Choiceoption.findByChoiceOptionLabel", query = "SELECT c FROM Choiceoption c WHERE c.choiceOptionLabel = :choiceOptionLabel")
    , @NamedQuery(name = "Choiceoption.findByChoiceOptionContent", query = "SELECT c FROM Choiceoption c WHERE c.choiceOptionContent = :choiceOptionContent")})
public class Choiceoption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOICE_OPTION_ID")
    private Integer choiceOptionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOICE_OPTION_LABEL")
    private Character choiceOptionLabel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "CHOICE_OPTION_CONTENT")
    private String choiceOptionContent;
    @JoinColumn(name = "CHOICE_OPTION_QUESTION_ID", referencedColumnName = "CHOICE_QUESTION_ID")
    @ManyToOne(optional = false)
    private Choicequestion choiceOptionQuestionId;

    public Choiceoption() {
    }

    public Choiceoption(Integer choiceOptionId) {
        this.choiceOptionId = choiceOptionId;
    }

    public Choiceoption(Integer choiceOptionId, Character choiceOptionLabel, String choiceOptionContent) {
        this.choiceOptionId = choiceOptionId;
        this.choiceOptionLabel = choiceOptionLabel;
        this.choiceOptionContent = choiceOptionContent;
    }

    public Integer getChoiceOptionId() {
        return choiceOptionId;
    }

    public void setChoiceOptionId(Integer choiceOptionId) {
        this.choiceOptionId = choiceOptionId;
    }

    public Character getChoiceOptionLabel() {
        return choiceOptionLabel;
    }

    public void setChoiceOptionLabel(Character choiceOptionLabel) {
        this.choiceOptionLabel = choiceOptionLabel;
    }

    public String getChoiceOptionContent() {
        return choiceOptionContent;
    }

    public void setChoiceOptionContent(String choiceOptionContent) {
        this.choiceOptionContent = choiceOptionContent;
    }

    public Choicequestion getChoiceOptionQuestionId() {
        return choiceOptionQuestionId;
    }

    public void setChoiceOptionQuestionId(Choicequestion choiceOptionQuestionId) {
        this.choiceOptionQuestionId = choiceOptionQuestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choiceOptionId != null ? choiceOptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choiceoption)) {
            return false;
        }
        Choiceoption other = (Choiceoption) object;
        if ((this.choiceOptionId == null && other.choiceOptionId != null) || (this.choiceOptionId != null && !this.choiceOptionId.equals(other.choiceOptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tovrestws.Choiceoption[ choiceOptionId=" + choiceOptionId + " ]";
    }
    
}
