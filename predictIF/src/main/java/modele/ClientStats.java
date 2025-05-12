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
public class ClientStats {

    private Client client;
    private Long nbConsultations;

    public ClientStats(Client client, Long nbConsultations) {
        this.client = client;
        this.nbConsultations = nbConsultations;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getNbConsultations() {
        return nbConsultations;
    }

    public void setNbConsultations(Long nbClients) {
        this.nbConsultations = nbConsultations;
    }

    @Override
    public String toString() {
        return "ClientStats{" + "client=" + client + ", nbConsultations=" + nbConsultations + '}';
    }
    
    
}
