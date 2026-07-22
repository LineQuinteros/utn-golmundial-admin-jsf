package ec.edu.utn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.utn.dto.LoginDTO;
import ec.edu.utn.dto.UsuarioDTO;
import ec.edu.utn.dto.BonoMasivoResponse;
import ec.edu.utn.util.ApiConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UsuarioService {

    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());
    private Client client;
    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        mapper = new ObjectMapper();
    }

    @PreDestroy
    public void destroy() {
        if (client != null) client.close();
    }

    // ============ LOGIN ============
    public UsuarioDTO login(String username, String password) {
        try {
            LoginDTO credenciales = new LoginDTO(username, password);
            String bodyJson = mapper.writeValueAsString(credenciales);

            Response response = client
                .target(ApiConfig.BASE_URL + "/auth/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(bodyJson));

            if (response.getStatus() != 200) return null;

            String json = response.readEntity(String.class);
            return mapper.readValue(json, UsuarioDTO.class);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al iniciar sesión", e);
            return null;
        }
    }

    // ============ LISTAR TODOS LOS USUARIOS ============
    public List<UsuarioDTO> listarTodos() {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/usuarios")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .get();

            if (response.getStatus() != 200) return new ArrayList<>();

            String json = response.readEntity(String.class);
            List<UsuarioDTO> usuarios = mapper.readValue(json, new TypeReference<List<UsuarioDTO>>() {});

            // Si el backend no devuelve saldo, lo seteamos en 0
            for (UsuarioDTO u : usuarios) {
                if (u.getSaldo() == null) {
                    u.setSaldo(0.0);
                }
            }

            return usuarios;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar usuarios", e);
            return new ArrayList<>();
        }
    }

    // ============ ACTUALIZAR USUARIO ============
    public boolean actualizar(UsuarioDTO usuario) {
        try {
            String json = mapper.writeValueAsString(usuario);

            Response response = client
                .target(ApiConfig.BASE_URL + "/usuarios/" + usuario.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .put(Entity.json(json));

            return response.getStatus() == 200;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al actualizar usuario", e);
            return false;
        }
    }

    // ============ OTORGAR BONO DIARIO A UN USUARIO ============
    public boolean otorgarBonoDiario(Long idUsuario) {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/usuarios/" + idUsuario + "/bono-diario")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .post(Entity.json(null));

            return response.getStatus() == 200;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al otorgar bono diario", e);
            return false;
        }
    }

    // ============ OTORGAR BONO MASIVO (A TODOS LOS QUE TIENEN SALDO 0) ============
    public BonoMasivoResponse otorgarBonoMasivo() {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/usuarios/bono-masivo")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .post(Entity.json(null));

            if (response.getStatus() != 200) {
                return null;
            }

            String json = response.readEntity(String.class);
            return mapper.readValue(json, BonoMasivoResponse.class);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al otorgar bono masivo", e);
            return null;
        }
    }
}

