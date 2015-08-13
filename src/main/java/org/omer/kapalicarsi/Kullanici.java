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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "KULLANICI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kullanici.findAll", query = "SELECT k FROM Kullanici k"),
    @NamedQuery(name = "Kullanici.findByKulId", query = "SELECT k FROM Kullanici k WHERE k.kulId = :kulId"),
    @NamedQuery(name = "Kullanici.findByAdi", query = "SELECT k FROM Kullanici k WHERE k.adi = :adi"),
    @NamedQuery(name = "Kullanici.findByEmail", query = "SELECT k FROM Kullanici k WHERE k.email = :email"),
    @NamedQuery(name = "Kullanici.findByEmailAndSifre", query = "SELECT k FROM Kullanici k WHERE k.email = :email AND k.sifre = :sifre"),
    @NamedQuery(name = "Kullanici.findBySifre", query = "SELECT k FROM Kullanici k WHERE k.sifre = :sifre"),
    @NamedQuery(name = "Kullanici.findByIsAdmin", query = "SELECT k FROM Kullanici k WHERE k.isAdmin = :isAdmin"),
    @NamedQuery(name = "Kullanici.findByKayitTarih", query = "SELECT k FROM Kullanici k WHERE k.kayitTarih = :kayitTarih")})
public class Kullanici implements Serializable {
    @OneToMany(mappedBy = "kaydedenKullaniciId")
    private Collection<MucevherPiyasa> mucevherPiyasaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KUL_ID")
    private Integer kulId;
    @Size(max = 50)
    @Column(name = "ADI")
    private String adi;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 50)
    @Column(name = "SIFRE")
    private String sifre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;
    @Column(name = "KAYIT_TARIH")
    @Temporal(TemporalType.DATE)
    private Date kayitTarih;

    public Kullanici() {
    }

    public Kullanici(Integer kulId) {
        this.kulId = kulId;
    }

    public Kullanici(Integer kulId, Boolean isAdmin) {
        this.kulId = kulId;
        this.isAdmin = isAdmin;
    }

    public Integer getKulId() {
        return kulId;
    }

    public void setKulId(Integer kulId) {
        this.kulId = kulId;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getKayitTarih() {
        return kayitTarih;
    }

    public void setKayitTarih(Date kayitTarih) {
        this.kayitTarih = kayitTarih;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kulId != null ? kulId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kullanici)) {
            return false;
        }
        Kullanici other = (Kullanici) object;
        if ((this.kulId == null && other.kulId != null) || (this.kulId != null && !this.kulId.equals(other.kulId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.omer.kapalicarsi.Kullanici[ kulId=" + kulId + " ]";
    }

    @XmlTransient
    public Collection<MucevherPiyasa> getMucevherPiyasaCollection() {
        return mucevherPiyasaCollection;
    }

    public void setMucevherPiyasaCollection(Collection<MucevherPiyasa> mucevherPiyasaCollection) {
        this.mucevherPiyasaCollection = mucevherPiyasaCollection;
    }
    
}
