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
@Table(name = "VIRUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Virus.findAll", query = "SELECT v FROM Virus v")
    , @NamedQuery(name = "Virus.findByVirusId", query = "SELECT v FROM Virus v WHERE v.virusId = :virusId")
    , @NamedQuery(name = "Virus.findByVirusFullName", query = "SELECT v FROM Virus v WHERE v.virusFullName = :virusFullName")
    , @NamedQuery(name = "Virus.findByVirusAbbreviation", query = "SELECT v FROM Virus v WHERE v.virusAbbreviation = :virusAbbreviation")
    , @NamedQuery(name = "Virus.findBySymptoms", query = "SELECT v FROM Virus v WHERE v.symptoms = :symptoms")
    , @NamedQuery(name = "Virus.findBySpread", query = "SELECT v FROM Virus v WHERE v.spread = :spread")
    , @NamedQuery(name = "Virus.findByPrevention", query = "SELECT v FROM Virus v WHERE v.prevention = :prevention")
    , @NamedQuery(name = "Virus.findByDistribution", query = "SELECT v FROM Virus v WHERE v.distribution = :distribution")})
public class Virus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIRUS_ID")
    private Integer virusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VIRUS_FULL_NAME")
    private String virusFullName;
    @Size(max = 255)
    @Column(name = "VIRUS_ABBREVIATION")
    private String virusAbbreviation;
    @Size(max = 1500)
    @Column(name = "SYMPTOMS")
    private String symptoms;
    @Size(max = 1500)
    @Column(name = "SPREAD")
    private String spread;
    @Size(max = 1500)
    @Column(name = "PREVENTION")
    private String prevention;
    @Size(max = 1500)
    @Column(name = "DISTRIBUTION")
    private String distribution;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "choiceQuestionVirusId")
    private Collection<Choicequestion> choicequestionCollection;

    public Virus() {
    }

    public Virus(Integer virusId) {
        this.virusId = virusId;
    }

    public Virus(Integer virusId, String virusFullName) {
        this.virusId = virusId;
        this.virusFullName = virusFullName;
    }

    public Integer getVirusId() {
        return virusId;
    }

    public void setVirusId(Integer virusId) {
        this.virusId = virusId;
    }

    public String getVirusFullName() {
        return virusFullName;
    }

    public void setVirusFullName(String virusFullName) {
        this.virusFullName = virusFullName;
    }

    public String getVirusAbbreviation() {
        return virusAbbreviation;
    }

    public void setVirusAbbreviation(String virusAbbreviation) {
        this.virusAbbreviation = virusAbbreviation;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    @XmlTransient
    public Collection<Choicequestion> getChoicequestionCollection() {
        return choicequestionCollection;
    }

    public void setChoicequestionCollection(Collection<Choicequestion> choicequestionCollection) {
        this.choicequestionCollection = choicequestionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (virusId != null ? virusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Virus)) {
            return false;
        }
        Virus other = (Virus) object;
        if ((this.virusId == null && other.virusId != null) || (this.virusId != null && !this.virusId.equals(other.virusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tovrestws.Virus[ virusId=" + virusId + " ]";
    }
    
}
