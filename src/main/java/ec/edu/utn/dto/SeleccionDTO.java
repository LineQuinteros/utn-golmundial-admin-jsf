package ec.edu.utn.dto;

public class SeleccionDTO {
    private Long id;
    private String nombre;
    private String codigoFifa;
    private String grupo;
    private String confederacion;
    private Boolean esAnfitrion;

    public SeleccionDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoFifa() { return codigoFifa; }
    public void setCodigoFifa(String codigoFifa) { this.codigoFifa = codigoFifa; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getConfederacion() { return confederacion; }
    public void setConfederacion(String confederacion) { this.confederacion = confederacion; }

    public Boolean getEsAnfitrion() { return esAnfitrion; }
    public void setEsAnfitrion(Boolean esAnfitrion) { this.esAnfitrion = esAnfitrion; }
}
