/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aguyonnaud
 */
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    
    @Temporal(TemporalType.DATE)
    private Date date; 
    private String etat;
    private String commentaire;
    
    //date de naissance
    
    @Embedded
    private Prediction prediction;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Employe employe;
    
    @ManyToOne
    private Medium medium;

    public Consultation(Client client, Employe employe, Medium medium) {
        this.client = client;
        this.employe = employe;
        this.medium = medium;
        this.etat = "ATTENTE";
        
        this.date = new Date();     
        
    }

    public Consultation() {
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", etat=" + etat + ", client=" + client + ", employe=" + employe + ", medium=" + medium + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
    
    
    
    
}
