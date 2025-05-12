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
public class Spirite extends Medium {
    
    private String support;

    public Spirite(String nom, String genre, String presentation ,String support) {
        super(nom, genre, presentation);
        this.support = support;
    }

    public Spirite() {
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
  
}
