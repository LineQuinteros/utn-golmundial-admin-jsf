package ec.edu.utn.bean;

import ec.edu.utn.dto.AuditoriaDTO;
import ec.edu.utn.service.AuditoriaService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AuditoriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AuditoriaService auditoriaService;

    private List<AuditoriaDTO> registros = new ArrayList<>();

    @PostConstruct
    public void init() {
        cargarAuditoria();
    }

    public void cargarAuditoria() {
        registros = auditoriaService.listarTodos();
    }

    public List<AuditoriaDTO> getRegistros() { return registros; }
    public void setRegistros(List<AuditoriaDTO> registros) { this.registros = registros; }
}
