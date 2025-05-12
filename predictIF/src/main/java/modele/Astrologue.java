/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.persistence.Entity;

/**
 *
 * @author aguyonnaud
 */
@Entity
public class Astrologue extends Medium {
    
    private String formation;
    private String promotion;

    public Astrologue(String nom, String genre, String presentation,String formation, String promotion) {
        super(nom, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public Astrologue() {
    }
    

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }


  
}