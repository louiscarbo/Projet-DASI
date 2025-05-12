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
public class Cartomancien extends Medium {
    
    
    

    public Cartomancien(String nom, String genre, String presentation ) {
        super(nom, genre, presentation);
    }

    public Cartomancien() {
    }

  
}
