/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import modele.Astrologue;
import modele.Cartomancien;
import modele.Client;
import modele.ClientStats;
import modele.Consultation;
import modele.Employe;
import modele.EmployeStat;
import modele.Medium;
import modele.MediumStats;
import modele.Spirite;

/**
 *
 * @author aguyonnaud
 */
public class ConsultationDAO {

    public static void create(Consultation c) {
        JpaUtil.obtenirContextePersistance().persist(c);
    }

    public static Consultation save(Consultation c) {
        return JpaUtil.obtenirContextePersistance().merge(c);
    }

    public static List<Consultation> findAll() {
        String s = "SELECT c FROM Consultation c ORDER BY c.date";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        return query.getResultList();
    }

    public static Consultation findById(long id) {
        String s = "SELECT c FROM Consultation c WHERE c.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        query.setParameter("id", id);
        return (Consultation) query.getResultList().get(0);
    }

    public static List<ClientStats> findClientsPerEmployee(Employe e)
    {
    
        String s = "SELECT c.client, COUNT(c) FROM Consultation c WHERE c.employe = :employe GROUP BY c.client";
        //s = "SELECT c.consultations, COUNT(c) FROM Employe e WHERE c.employe = :employe GROUP BY c.client";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class);
        query.setParameter("employe", e);
        

        List<Object[]> results = query.getResultList();
        List<ClientStats> stats = new ArrayList<>();

        for (Object[] row : results) {
            Client client = (Client) row[0];
            Long nbConsultations = (Long) row[1];
            stats.add(new ClientStats(client, nbConsultations));
        }

        return stats;
    }

    public static List<ClientStats> findNbConsultationsClient() {

        String s = "SELECT c.client, COUNT(c) FROM Consultation c GROUP BY c.client";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class);

        List<Object[]> results = query.getResultList();
        List<ClientStats> stats = new ArrayList<>();

        for (Object[] row : results) {
            Client client = (Client) row[0];
            Long nbConsultations = (Long) row[1];
            stats.add(new ClientStats(client, nbConsultations));
        }

        return stats;
    }

    public static List<Consultation> find(Long clientID, boolean spirite, boolean astro, boolean carto, String nomMedium, String dateString) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date  = simpleDateFormat.parse(dateString);
        String s = "SELECT c FROM Consultation c";
        boolean dateRenseignee = false;
        boolean where = false;
        if (spirite || astro || carto) {
            s += " WHERE TYPE(c.medium) IN ("; //à modifier : on accède aux attributs, pas aux tables
            where = true;

            if (spirite) {
                s = s + ":spirite";
                if (astro || carto) {
                    s += ", ";
                }
            }

            if (astro) {
                s = s + ":astro";
                if (carto) {
                    s = s + ", ";

                }

            }
            if (carto) {
                s = s + ":carto";

            }

            s += ")";

        }
        if (!"".equals(nomMedium)) {
            if (!where) {
                s += " WHERE ";
                where = true;
            } else {
                s += " AND ";
            }
            s += ("c.medium.nom = '" + nomMedium + "'");
        }

        if (clientID != 0) {
            if (!where) {
                s += " WHERE ";
                where = true;
            } else {
                s += " AND ";
            }
            s += (" c.client.id = " + clientID);
        }
        Date defaut;
        
        if (!where) {
            s += " WHERE ";
             where = true;
        } else {
            s += " AND ";
        }
        dateRenseignee = true;
        
        s += (" c.date >= :date");

        s += (" ORDER BY c.date desc");
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        if (spirite) {
            query.setParameter("spirite", Spirite.class);
        }
        if (astro) {
            query.setParameter("astro", Astrologue.class);
        }
        if (carto) {
            query.setParameter("carto", Cartomancien.class);
        }
        if (dateRenseignee) {
            query.setParameter("date", date);
        }
        return query.getResultList();
    }
    
    public static Consultation findNext(Employe e)
    {
        //amelioration : nb_consultations ce mois
        String s = "SELECT c FROM Consultation c WHERE c.etat = 'ATTENTE' AND c.employe= :employe";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        query.setParameter("employe", e);
        if (query.getResultList().size() == 0) {
            return null;
        }
        
        
        Consultation c = (Consultation) query.getResultList().get(0);
        return c;
    }
    
    //public static List<Client> clientsParIdEmploye(Long Id)

}
