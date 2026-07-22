package ec.edu.utn.bean;

import ec.edu.utn.dto.GrupoDTO;
import ec.edu.utn.service.GrupoService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GrupoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private GrupoService grupoService;

    private List<GrupoDTO> grupos = new ArrayList<>();

    @PostConstruct
    public void init() {
        cargarGrupos();
    }

    public void cargarGrupos() {
        grupos = grupoService.listarTodos();
    }

    public List<GrupoDTO> getGrupos() { return grupos; }
    public void setGrupos(List<GrupoDTO> grupos) { this.grupos = grupos; }
}
