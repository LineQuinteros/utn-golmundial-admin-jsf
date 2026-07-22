package ec.edu.utn.dto;
import java.util.Date;

public class PartidoDTO {
    private Long id;

    private String fase;
    private String grupo;
    private String seleccionLocal;
    private String seleccionVisitante;
private String fecha;

    private String sede;
    private String estado;
    private Integer golesLocal;
    private Integer golesVisitante;

    public PartidoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFase() { return fase; }
    public void setFase(String fase) { this.fase = fase; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getSeleccionLocal() { return seleccionLocal; }
    public void setSeleccionLocal(String seleccionLocal) { this.seleccionLocal = seleccionLocal; }

    public String getSeleccionVisitante() { return seleccionVisitante; }
    public void setSeleccionVisitante(String seleccionVisitante) { this.seleccionVisitante = seleccionVisitante; }

public String getFecha() { return fecha; }
public void setFecha(String fecha) { this.fecha = fecha; }
    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }

    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }
}
