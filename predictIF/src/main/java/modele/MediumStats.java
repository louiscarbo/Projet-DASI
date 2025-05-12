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
public class MediumStats {
    private Medium medium;
    private int nbConsultations;

    public MediumStats(Medium medium, int nbConsultations) {
        this.medium = medium;
        this.nbConsultations = nbConsultations;
    }



    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public int getNbConsultations() {
        return nbConsultations;
    }

    public void setNbConsultations(int nbConsultations) {
        this.nbConsultations = nbConsultations;
    }


    @Override
    public String toString() {
        return "MediumStats{" + "medium=" + medium + ", nbConsultations=" + nbConsultations + '}';
    }
    
    
}
