package ec.edu.utn.bean;

import ec.edu.utn.dto.UsuarioDTO;
import ec.edu.utn.service.UsuarioService;
import ec.edu.utn.util.ApiConfig;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private String username;
    private String password;
    private UsuarioDTO usuarioActual;

    public String ingresar() {
        UsuarioDTO usuario = usuarioService.login(username, password);

        if (usuario == null) {
            mostrarMensaje("Usuario o contraseña incorrectos.", FacesMessage.SEVERITY_ERROR);
            return null;
        }

        ApiConfig.JWT_TOKEN = usuario.getToken();
        this.usuarioActual = usuario;
        this.password = null;

        if ("ADMINISTRADOR".equals(usuario.getRol())) {
            return "/admin/inicio?faces-redirect=true";
        } else {
            return "/cliente/inicio?faces-redirect=true";
        }
    }

    public String salir() {
        this.usuarioActual = null;
        ApiConfig.JWT_TOKEN = null;
        this.username = null;
        this.password = null;
        return "/login?faces-redirect=true";
    }

    public boolean isAutenticado() { return usuarioActual != null; }
    public boolean isAdministrador() { return usuarioActual != null && "ADMINISTRADOR".equals(usuarioActual.getRol()); }
    public boolean isCliente() { return usuarioActual != null && "CLIENTE".equals(usuarioActual.getRol()); }

    private void mostrarMensaje(String texto, FacesMessage.Severity severidad) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, texto, null));
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UsuarioDTO getUsuarioActual() { return usuarioActual; }
}
