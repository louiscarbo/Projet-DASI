/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author aguyonnaud
 */
@Embeddable
public class Prediction {
    

    
    
    private String sante; 
    private String amour;
    private String travail;

    public Prediction(String sante, String amour, String travail) {
        this.sante = sante;
        this.amour = amour;
        this.travail = travail;
    }

    public Prediction() {
    }


    public String getSante() {
        return sante;
    }

    public void setSante(String sante) {
        this.sante = sante;
    }

    public String getAmour() {
        return amour;
    }

    public void setAmour(String amour) {
        this.amour = amour;
    }

    public String getTravail() {
        return travail;
    }

    public void setTravail(String travail) {
        this.travail = travail;
    }
    
    
    
}
