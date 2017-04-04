package itcelaya.edu.mx.appagenda.Model;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

/*Modelo Base de datos*/
public class Evento {

    private int idEvento;
    private String nomEvento;
    private String desEvento;
    private String fechaEvento;

    public Evento(int idEvento, String nomEvento, String desEvento, String fechaEvento) {
        this.idEvento = idEvento;
        this.nomEvento = nomEvento;
        this.desEvento = desEvento;
        this.fechaEvento = fechaEvento;
    }

    public Evento() {
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomEvento() {
        return nomEvento;
    }

    public void setNomEvento(String nomEvento) {
        this.nomEvento = nomEvento;
    }

    public String getDesEvento() {
        return desEvento;
    }

    public void setDesEvento(String desEvento) {
        this.desEvento = desEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
