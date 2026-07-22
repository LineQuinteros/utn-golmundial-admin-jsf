package ec.edu.utn.bean;

import ec.edu.utn.dto.SeleccionDTO;
import ec.edu.utn.service.SeleccionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class SeleccionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SeleccionService seleccionService;

    private List<SeleccionDTO> selecciones = new ArrayList<>();
    private SeleccionDTO seleccionSeleccionada = new SeleccionDTO();

    @PostConstruct
    public void init() {
        cargarSelecciones();
    }


/*
    public void cargarSelecciones() {
        selecciones.clear();

        SeleccionDTO s1 = new SeleccionDTO();
        s1.setId(1L);
        s1.setNombre("Argentina");
        s1.setCodigoFifa("ARG");
        s1.setGrupo("A");
        s1.setConfederacion("Conmebol");
        s1.setEsAnfitrion(false);
        selecciones.add(s1);

        SeleccionDTO s2 = new SeleccionDTO();
        s2.setId(2L);
        s2.setNombre("Brasil");
        s2.setCodigoFifa("BRA");
        s2.setGrupo("B");
        s2.setConfederacion("Conmebol");
        s2.setEsAnfitrion(false);
        selecciones.add(s2);

        SeleccionDTO s3 = new SeleccionDTO();
        s3.setId(3L);
        s3.setNombre("México");
        s3.setCodigoFifa("MEX");
        s3.setGrupo("A");
        s3.setConfederacion("Concacaf");
        s3.setEsAnfitrion(true);
        selecciones.add(s3);

        SeleccionDTO s4 = new SeleccionDTO();
        s4.setId(4L);
        s4.setNombre("España");
        s4.setCodigoFifa("ESP");
        s4.setGrupo("C");
        s4.setConfederacion("UEFA");
        s4.setEsAnfitrion(false);
        selecciones.add(s4);

        SeleccionDTO s5 = new SeleccionDTO();
        s5.setId(5L);
        s5.setNombre("Francia");
        s5.setCodigoFifa("FRA");
        s5.setGrupo("D");
        s5.setConfederacion("UEFA");
        s5.setEsAnfitrion(false);
        selecciones.add(s5);
    }
*/


public void cargarSelecciones() {
    selecciones = seleccionService.listarTodos();
}
    public String prepararEditar(SeleccionDTO seleccion) {
        this.seleccionSeleccionada = seleccion;
        return "/admin/selecciones/editar?faces-redirect=true";
    }

    public String guardar() {
        // Actualizar en memoria
        for (int i = 0; i < selecciones.size(); i++) {
            if (selecciones.get(i).getId().equals(seleccionSeleccionada.getId())) {
                selecciones.set(i, seleccionSeleccionada);
                break;
            }
        }
        mostrarMensaje("Selección actualizada correctamente (modo demo).", FacesMessage.SEVERITY_INFO);
        return "/admin/selecciones?faces-redirect=true";
    }



public String guardarNuevo() {
    SeleccionDTO creado = seleccionService.crear(seleccionSeleccionada);
    if (creado != null) {
        mostrarMensaje("Selección creada correctamente.", FacesMessage.SEVERITY_INFO);
    } else {
        mostrarMensaje("Error al crear la selección.", FacesMessage.SEVERITY_ERROR);
    }
    cargarSelecciones();
    return "/admin/selecciones?faces-redirect=true";
}

public String prepararNuevo() {
    seleccionSeleccionada = new SeleccionDTO();
    return "/admin/selecciones/nuevo?faces-redirect=true";
}

    public String cancelar() {
        seleccionSeleccionada = new SeleccionDTO();
        return "/admin/selecciones?faces-redirect=true";
    }

    private void mostrarMensaje(String texto, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, texto, null));
    }

    public List<SeleccionDTO> getSelecciones() { return selecciones; }
    public void setSelecciones(List<SeleccionDTO> selecciones) { this.selecciones = selecciones; }
    public SeleccionDTO getSeleccionSeleccionada() { return seleccionSeleccionada; }
    public void setSeleccionSeleccionada(SeleccionDTO seleccionSeleccionada) { this.seleccionSeleccionada = seleccionSeleccionada; }
}
