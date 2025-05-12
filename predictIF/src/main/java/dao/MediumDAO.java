/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import modele.Astrologue;
import modele.Cartomancien;
import modele.Client;

import modele.Medium;
import modele.MediumStats;
import modele.Spirite;

/**
 *
 * @author aguyonnaud
 */
public class MediumDAO {

    public static void create(Medium c) {
        JpaUtil.obtenirContextePersistance().persist(c);
    }

    public static Medium save(Medium e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }

    public static List<Medium> findAll() {
        String s = "SELECT c FROM Medium c ORDER BY c.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
    
    
    public static List<MediumStats> findNbConsultationsMedium() {

        String s = "SELECT c, c.nbConsultations FROM Medium c ORDER BY c.nbConsultations DESC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class);

        List<Object[]> results = query.getResultList();
        List<MediumStats> stats = new ArrayList<>();

        for (Object[] row : results) {
            Medium medium = (Medium) row[0];
            int nbConsultations = (int) row[1];
            stats.add(new MediumStats(medium, nbConsultations));
        }

        return stats;
    }


    public static List<Medium> find(boolean Spirite, boolean Astrologue, boolean Carto, String nomMedium, String genre) {
        String s = "SELECT m FROM Medium m";
        boolean where = false;
        if (Spirite || Astrologue || Carto) {
            s += " WHERE TYPE(m) IN ("; //à modifier : on accède aux attributs, pas aux tables
            where = true;

            if (Spirite) {
                s = s + ":spirite";
                if (Astrologue || Carto) {
                    s += ", ";
                }
            }

            if (Astrologue) {
                s = s + ":astro";
                if (Carto) {
                    s = s + ", ";

                }

            }
            if (Carto) {
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
            s += ("m.nom = '" + nomMedium + "'");
        }

        if (!"".equals(genre)) {
            if (!where) {
                s += " WHERE ";
            } else {
                s += " AND ";
            }
            s += (" m.genre = '" + genre + "'");
        }

        s += (" ORDER BY m.nbConsultations desc");

        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        if (Spirite)
        {
            query.setParameter("spirite", Spirite.class);
        }
        if (Astrologue)
        {
            query.setParameter("astro", Astrologue.class);
        }
        if (Carto)
        {
            query.setParameter("carto", Cartomancien.class);
        }
        return query.getResultList();
    
    }
    
    public static Medium findById(Long id) { //faire find direct
        String s = "SELECT c FROM Medium c WHERE c.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Medium.class);
        query.setParameter("id", id);
        return (Medium) query.getResultList().get(0);
    }

}
