package itcelaya.edu.mx.appagenda.Model;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

/*Modelo Base de datos*/
public class Contactos {

    private int idContact;
    private int idEvento;
    private String nombContact;
    private String telContact;
    private String emailContact;

    public Contactos(int idContact, int idEvento, String nombContact, String telContact, String emailContact) {
        this.idContact = idContact;
        this.idEvento = idEvento;
        this.nombContact = nombContact;
        this.telContact = telContact;
        this.emailContact = emailContact;
    }

    public Contactos(int idContact, String nombContact, String telContact, String emailContact) {
        this.idContact = idContact;
        this.nombContact = nombContact;
        this.telContact = telContact;
        this.emailContact = emailContact;
    }

    public Contactos() {

    }

    public int getIdContact() {
        return idContact;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public String getNombContact() {
        return nombContact;
    }

    public String getTelContact() {
        return telContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public void setNombContact(String nombContact) {
        this.nombContact = nombContact;
    }

    public void setTelContact(String telContact) {
        this.telContact = telContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}
