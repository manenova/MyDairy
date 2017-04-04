package itcelaya.edu.mx.appagenda.Model;

import java.util.ArrayList;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class ViewContact {

    private int idContact;
    private String nomContact;
    private ArrayList<String> teleContact = new ArrayList<>();
    private ArrayList<String> emailContact = new ArrayList<>();


    public ViewContact(int idContact, String nomContact, ArrayList<String> teleContact, ArrayList<String> emailContact) {
        this.idContact = idContact;
        this.nomContact = nomContact;
        this.teleContact = teleContact;
        this.emailContact = emailContact;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public ArrayList<String> getTeleContact() {
        return teleContact;
    }

    public void setTeleContact(ArrayList<String> teleContact) {
        this.teleContact = teleContact;
    }

    public ArrayList<String> getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(ArrayList<String> emailContact) {
        this.emailContact = emailContact;
    }
}
