package modele;


import com.google.maps.model.LatLng;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aguyonnaud
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance; 
    
    @Column(unique=true)
    private String mail;
    @Column(unique=true)
    private String tel;
    private String adresse;
    private String mdp;
    private LatLng coord;
    
    @Embedded
    private ProfilAstral profAst;
    public Client() {
    }

    public Client(String nom, String prenom, String dateNaissance,  String adresse,String tel, String mail, String mdp) throws ParseException {
        
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.adresse = adresse;
        this.mdp = mdp;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
 
        this.dateNaissance = simpleDateFormat.parse(dateNaissance);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", coord=" + coord + '}';
    }

        

    

    public Long getId() {
        return id;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public ProfilAstral getProfAst() {
        return profAst;
    }

    public void setProfAst(ProfilAstral profAst) {
        this.profAst = profAst;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
