/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi;

/**
 *
 * @author pc
 */
public class Piyasa {
    private String mucevher;
    private double fiyat;
    private String kaydedenKullanıcıAdi;
    private String kaydedenKullaniciEmail;

    
    Piyasa(){
        
    }
    Piyasa (String mucevher, double fiyat,String kaydedenKullanıcıAdi,String kaydedenKullaniciEmail){
        this.mucevher = mucevher;
        this.fiyat = fiyat;
        this.kaydedenKullanıcıAdi = kaydedenKullanıcıAdi;
        this.kaydedenKullaniciEmail = kaydedenKullaniciEmail;
        
    }
    
    
    public String getMucevher() {
        return mucevher;
    }

    public void setMucevher(String mucevher) {
        this.mucevher = mucevher;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public String getKaydedenKullanıcıAdi() {
        return kaydedenKullanıcıAdi;
    }

    public void setKaydedenKullanıcıAdi(String kaydedenKullanıcıAdi) {
        this.kaydedenKullanıcıAdi = kaydedenKullanıcıAdi;
    }

    public String getKaydedenKullaniciEmail() {
        return kaydedenKullaniciEmail;
    }

    public void setKaydedenKullaniciEmail(String kaydedenKullaniciEmail) {
        this.kaydedenKullaniciEmail = kaydedenKullaniciEmail;
    }
    
    
    
    
    
    
    
}
