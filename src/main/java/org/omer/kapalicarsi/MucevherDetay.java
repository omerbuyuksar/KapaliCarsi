/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "MUCEVHER_DETAY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MucevherDetay.findAll", query = "SELECT m FROM MucevherDetay m"),
    @NamedQuery(name = "MucevherDetay.findByMucId", query = "SELECT m FROM MucevherDetay m WHERE m.mucId = :mucId"),
    @NamedQuery(name = "MucevherDetay.findByAdi", query = "SELECT m FROM MucevherDetay m WHERE m.adi = :adi"),
    @NamedQuery(name = "MucevherDetay.findByKayittarih", query = "SELECT m FROM MucevherDetay m WHERE m.kayittarih = :kayittarih")})
public class MucevherDetay implements Serializable {
    @Lob
    @Column(name = "RESIM")
    private byte [] resim;
    @OneToMany(mappedBy = "kaydedilenMucevherId")
    private Collection<MucevherPiyasa> mucevherPiyasaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MUC_ID")
    private Integer mucId;
    @Size(max = 50)
    @Column(name = "ADI")
    private String adi;
    @Column(name = "KAYITTARIH")
    @Temporal(TemporalType.DATE)
    private Date kayittarih;
    

    public MucevherDetay() {
    }

    public MucevherDetay(Integer mucId) {
        this.mucId = mucId;
    }

    public Integer getMucId() {
        return mucId;
    }

    public void setMucId(Integer mucId) {
        this.mucId = mucId;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Date getKayittarih() {
        return kayittarih;
    }

    public void setKayittarih(Date kayittarih) {
        this.kayittarih = kayittarih;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mucId != null ? mucId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MucevherDetay)) {
            return false;
        }
        MucevherDetay other = (MucevherDetay) object;
        if ((this.mucId == null && other.mucId != null) || (this.mucId != null && !this.mucId.equals(other.mucId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.omer.kapalicarsi.MucevherDetay[ mucId=" + mucId + " ]";
    }


    @XmlTransient
    public Collection<MucevherPiyasa> getMucevherPiyasaCollection() {
        return mucevherPiyasaCollection;
    }

    public void setMucevherPiyasaCollection(Collection<MucevherPiyasa> mucevherPiyasaCollection) {
        this.mucevherPiyasaCollection = mucevherPiyasaCollection;
    }

    public Serializable getResim() {
        return resim;
    }

    public void setResim(byte [] resim) {
        this.resim = resim;
    }
    
}
