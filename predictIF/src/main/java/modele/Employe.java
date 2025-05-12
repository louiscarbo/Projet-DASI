/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author aguyonnaud
 */
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    
    
    private String nom;
    private String prenom;
    
    private String genre;
    
    
    
    
    @Column(unique=true)
    private String mail;
    @Column(unique=true)
    private String tel;
    private String mdp;
    
    private boolean dispo;
    private int nb_consult;
    
    @OneToMany
    private List<Consultation> consultations;

    
    public Employe(String nom, String prenom, String genre, String mail, String tel, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.mail = mail;
        this.tel = tel;
        this.mdp = mdp;
        this.nb_consult=0;
        this.dispo=true;
        
    }

    public Employe() {
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public int getNb_consult() {
        return nb_consult;
    }

    public void setNb_consult(int nb_consult) {
        this.nb_consult = nb_consult;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "Employe{" + "nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", dispo=" + dispo + "}\n";
    }

    
    
    
    
    
    
}
