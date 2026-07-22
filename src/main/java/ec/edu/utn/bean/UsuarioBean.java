package ec.edu.utn.bean;

import ec.edu.utn.dto.UsuarioDTO;
import ec.edu.utn.dto.BonoMasivoResponse;
import ec.edu.utn.service.UsuarioService;
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
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private List<UsuarioDTO> usuarios = new ArrayList<>();
    private UsuarioDTO usuarioSeleccionado = new UsuarioDTO();
/*
    @PostConstruct
    public void init() {
        cargarUsuariosMock();
    }

    private void cargarUsuariosMock() {
        usuarios.clear();
        UsuarioDTO u1 = new UsuarioDTO();
        u1.setId(1L);
        u1.setUsername("admin");
        u1.setNombre("Administrador");
        u1.setEmail("admin@utn.edu.ec");
        u1.setRol("ADMINISTRADOR");
        u1.setSaldo(0.0);
        usuarios.add(u1);

        UsuarioDTO u2 = new UsuarioDTO();
        u2.setId(2L);
        u2.setUsername("jperez");
        u2.setNombre("Juan Pérez");
        u2.setEmail("jperez@utn.edu.ec");
        u2.setRol("CLIENTE");
        u2.setSaldo(0.0);
        usuarios.add(u2);

        UsuarioDTO u3 = new UsuarioDTO();
        u3.setId(3L);
        u3.setUsername("mgomez");
        u3.setNombre("María Gómez");
        u3.setEmail("mgomez@utn.edu.ec");
        u3.setRol("CLIENTE");
        u3.setSaldo(0.0);
        usuarios.add(u3);
    }
*/


@PostConstruct
public void init() {
    cargarUsuarios();
}

public void cargarUsuarios() {
    usuarios = usuarioService.listarTodos();
}
    public String prepararEditar(UsuarioDTO usuario) {
        this.usuarioSeleccionado = usuario;
        return "/admin/usuarios/editar?faces-redirect=true";
    }

    public String guardar() {
        // Actualizar el usuario en la lista (en memoria)
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuarioSeleccionado.getId())) {
                usuarios.get(i).setNombre(usuarioSeleccionado.getNombre());
                usuarios.get(i).setRol(usuarioSeleccionado.getRol());
                break;
            }
        }
        mostrarMensaje("Usuario actualizado correctamente (modo demo).", FacesMessage.SEVERITY_INFO);
        return "/admin/usuarios?faces-redirect=true";
    }

    public String cancelar() {
        usuarioSeleccionado = new UsuarioDTO();
        return "/admin/usuarios?faces-redirect=true";
    }

    public void otorgarBonoMasivo() {
        int contador = 0;
        for (UsuarioDTO u : usuarios) {
            if ("CLIENTE".equals(u.getRol()) && (u.getSaldo() == null || u.getSaldo() == 0)) {
                u.setSaldo(u.getSaldo() + 1);
                contador++;
            }
        }
        if (contador > 0) {
            mostrarMensaje("Bono masivo otorgado a " + contador + " clientes.", FacesMessage.SEVERITY_INFO);
        } else {
            mostrarMensaje("No hay clientes con saldo 0.", FacesMessage.SEVERITY_WARN);
        }
    }

    private void mostrarMensaje(String texto, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, texto, null));
    }

    public List<UsuarioDTO> getUsuarios() { return usuarios; }
    public void setUsuarios(List<UsuarioDTO> usuarios) { this.usuarios = usuarios; }
    public UsuarioDTO getUsuarioSeleccionado() { return usuarioSeleccionado; }
    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) { this.usuarioSeleccionado = usuarioSeleccionado; }
}
