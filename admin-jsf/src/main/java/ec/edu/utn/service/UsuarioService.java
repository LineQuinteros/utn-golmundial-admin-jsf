package ec.edu.utn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.utn.dto.LoginDTO;
import ec.edu.utn.dto.UsuarioDTO;
import ec.edu.utn.util.ApiConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
        if (client != null) {
            client.close();
        }
    }

    public UsuarioDTO login(String username, String password) {
        try {
            LoginDTO credenciales = new LoginDTO(username, password);
            String bodyJson = mapper.writeValueAsString(credenciales);

            Response response = client
                .target(ApiConfig.BASE_URL + "/auth/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(bodyJson));

            if (response.getStatus() != 200) {
                return null;
            }

            String json = response.readEntity(String.class);
            return mapper.readValue(json, UsuarioDTO.class);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al iniciar sesión", e);
            return null;
        }
    }
}