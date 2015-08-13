/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "MUCEVHER_PIYASA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MucevherPiyasa.findAll", query = "SELECT m FROM MucevherPiyasa m"),
    @NamedQuery(name = "MucevherPiyasa.findByMucPyId", query = "SELECT m FROM MucevherPiyasa m WHERE m.mucPyId = :mucPyId"),
    @NamedQuery(name = "MucevherPiyasa.findByFiyat", query = "SELECT m FROM MucevherPiyasa m WHERE m.fiyat = :fiyat")})
public class MucevherPiyasa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MUC_PY_ID")
    private Integer mucPyId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FIYAT")
    private Double fiyat;
    @JoinColumn(name = "KAYDEDEN_KULLANICI_ID", referencedColumnName = "KUL_ID")
    @ManyToOne
    private Kullanici kaydedenKullaniciId;
    @JoinColumn(name = "KAYDEDILEN_MUCEVHER_ID", referencedColumnName = "MUC_ID")
    @ManyToOne
    private MucevherDetay kaydedilenMucevherId;

    public MucevherPiyasa() {
    }

    public MucevherPiyasa(Integer mucPyId) {
        this.mucPyId = mucPyId;
    }

    public Integer getMucPyId() {
        return mucPyId;
    }

    public void setMucPyId(Integer mucPyId) {
        this.mucPyId = mucPyId;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public Kullanici getKaydedenKullaniciId() {
        return kaydedenKullaniciId;
    }

    public void setKaydedenKullaniciId(Kullanici kaydedenKullaniciId) {
        this.kaydedenKullaniciId = kaydedenKullaniciId;
    }

    public MucevherDetay getKaydedilenMucevherId() {
        return kaydedilenMucevherId;
    }

    public void setKaydedilenMucevherId(MucevherDetay kaydedilenMucevherId) {
        this.kaydedilenMucevherId = kaydedilenMucevherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mucPyId != null ? mucPyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MucevherPiyasa)) {
            return false;
        }
        MucevherPiyasa other = (MucevherPiyasa) object;
        if ((this.mucPyId == null && other.mucPyId != null) || (this.mucPyId != null && !this.mucPyId.equals(other.mucPyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.omer.kapalicarsi.MucevherPiyasa[ mucPyId=" + mucPyId + " ]";
    }
    
}
