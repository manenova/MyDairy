package itcelaya.edu.mx.appagenda.Model;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class Contact {

    private String nomContacto;
    private String teleContacto;
    private String emailContacto;

    public Contact(String nomContacto, String teleContacto, String emailContacto) {
        this.nomContacto = nomContacto;
        this.teleContacto = teleContacto;
        this.emailContacto = emailContacto;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getTeleContacto() {
        return teleContacto;
    }

    public void setTeleContacto(String teleContacto) {
        this.teleContacto = teleContacto;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }
}
