/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aguyonnaud
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Medium {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String nom;
    private String genre;
    private String presentation;
    private int nbConsultations;

    public Medium() {
    }
    
    

    public Medium(String nom, String genre, String presentation) {
        this.nom = nom;
        this.genre = genre;
        this.presentation = presentation;
        this.nbConsultations = 0;
    }

    public int getNbConsultations() {
        return nbConsultations;
    }

    public void setNbConsultations(int nbConsultations) {
        this.nbConsultations = nbConsultations;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    @Override
    public String toString() {
        return "Medium{" + "nom=" + nom + ", genre=" + genre + ", nbConsultations=" + nbConsultations + "}\n";
    }
    
    
    
    
    
}
