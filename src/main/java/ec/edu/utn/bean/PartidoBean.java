package ec.edu.utn.bean;

import ec.edu.utn.dto.PartidoDTO;
import ec.edu.utn.service.PartidoService;
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
public class PartidoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PartidoService partidoService;

    private List<PartidoDTO> partidos = new ArrayList<>();
    private PartidoDTO partidoSeleccionado = new PartidoDTO();
    private Integer golesLocal;
    private Integer golesVisitante;
    private String estado;

    @PostConstruct
    public void init() {
        cargarPartidos();
    }

public void cargarPartidos() {
    partidos = partidoService.listarTodos();
}
    public String prepararNuevo() {
        partidoSeleccionado = new PartidoDTO();
        golesLocal = null;
        golesVisitante = null;
        estado = "PROGRAMADO";
        return "/admin/partidos/nuevo?faces-redirect=true";
    }

    public String guardarNuevo() {
        partidoSeleccionado.setGolesLocal(golesLocal);
        partidoSeleccionado.setGolesVisitante(golesVisitante);
        partidoSeleccionado.setEstado(estado);

        PartidoDTO creado = partidoService.crear(partidoSeleccionado);
        if (creado != null) {
            mostrarMensaje("Partido creado correctamente.", FacesMessage.SEVERITY_INFO);
        } else {
            mostrarMensaje("Error al crear el partido.", FacesMessage.SEVERITY_ERROR);
        }
        cargarPartidos();
        return "/admin/partidos?faces-redirect=true";
    }

    public String prepararEditar(PartidoDTO partido) {
        this.partidoSeleccionado = partido;
        this.golesLocal = partido.getGolesLocal();
        this.golesVisitante = partido.getGolesVisitante();
        this.estado = partido.getEstado();
        System.out.println("EDITANDO: " + partido.getSeleccionLocal() + " vs " + partido.getSeleccionVisitante());
        return "/admin/partidos/editar?faces-redirect=true";
    }

    public String guardar() {
        // Actualizar el partido en la lista (en memoria)
        for (int i = 0; i < partidos.size(); i++) {
            if (partidos.get(i).getId().equals(partidoSeleccionado.getId())) {
                partidos.get(i).setGolesLocal(golesLocal);
                partidos.get(i).setGolesVisitante(golesVisitante);
                partidos.get(i).setEstado(estado);
                break;
            }
        }
        mostrarMensaje("Resultado guardado correctamente (modo demo).", FacesMessage.SEVERITY_INFO);
        return "/admin/partidos?faces-redirect=true";
    }

    public String cancelar() {
        partidoSeleccionado = new PartidoDTO();
        golesLocal = null;
        golesVisitante = null;
        estado = null;
        return "/admin/partidos?faces-redirect=true";
    }

    private void mostrarMensaje(String texto, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, texto, null));
    }

    public List<PartidoDTO> getPartidos() { return partidos; }
    public void setPartidos(List<PartidoDTO> partidos) { this.partidos = partidos; }
    public PartidoDTO getPartidoSeleccionado() { return partidoSeleccionado; }
    public void setPartidoSeleccionado(PartidoDTO partidoSeleccionado) { this.partidoSeleccionado = partidoSeleccionado; }
    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }
    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
