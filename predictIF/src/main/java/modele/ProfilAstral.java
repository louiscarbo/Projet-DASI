/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author aguyonnaud
 */
@Embeddable
public class ProfilAstral {
    

    private String zodiaque;
    private String sgnChinois;
    private String couleur;
    private String animal;

    public ProfilAstral(String zodiaque, String sgnChinois, String couleur, String animal) {
        this.zodiaque = zodiaque;
        this.sgnChinois = sgnChinois;
        this.couleur = couleur;
        this.animal = animal;
    }

    public ProfilAstral() {
    }

    
    

    @Override
    public String toString() {
        return "ProfilAstral{" +  ", zodiaque=" + zodiaque + ", sgnChinois=" + sgnChinois + ", couleur=" + couleur + ", animal=" + animal + '}';
    }

    

    

    public String getZodiaque() {
        return zodiaque;
    }

    public void setZodiaque(String zodiaque) {
        this.zodiaque = zodiaque;
    }

    public String getSgnChinois() {
        return sgnChinois;
    }

    public void setSgnChinois(String sgnChinois) {
        this.sgnChinois = sgnChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    
    
}
