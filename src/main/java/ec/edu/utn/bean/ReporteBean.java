package ec.edu.utn.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ReporteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public int getTotalSelecciones() { return 0; }
    public int getTotalPartidos() { return 0; }
    public int getTotalGrupos() { return 0; }
    public int getTotalUsuarios() { return 0; }
    public int getTotalMonedas() { return 0; }
    public int getTotalPredicciones() { return 0; }
}
