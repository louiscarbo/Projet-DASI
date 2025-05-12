/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author daaquinohur
 */
public class EmployeStat {
    private Employe employe;
    private int nbConsultations;

    public EmployeStat(Employe employe, int nbConsultations) {
        this.employe = employe;
        this.nbConsultations = nbConsultations;
    }

    public Employe getEmploye() {
        return employe;
    }

    public int getNbConsultations() {
        return nbConsultations;
    }

    @Override
    public String toString() {
        return "EmployeStat{" + "employe=" + employe + ", nbConsultations=" + nbConsultations + '}';
    }
}

