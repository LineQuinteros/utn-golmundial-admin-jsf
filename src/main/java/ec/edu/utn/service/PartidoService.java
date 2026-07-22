package ec.edu.utn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.utn.dto.PartidoDTO;
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
public class PartidoService {

    private static final Logger LOG = Logger.getLogger(PartidoService.class.getName());
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

    public List<PartidoDTO> listarTodos() {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/partidos")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .get();

            if (response.getStatus() != 200) return new ArrayList<>();

            String json = response.readEntity(String.class);
            return mapper.readValue(json, new TypeReference<List<PartidoDTO>>() {});

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar partidos", e);
            return new ArrayList<>();
        }
    }

    public PartidoDTO buscarPorId(Long id) {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/partidos/" + id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .get();

            if (response.getStatus() != 200) return null;

            String json = response.readEntity(String.class);
            return mapper.readValue(json, PartidoDTO.class);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al buscar partido", e);
            return null;
        }
    }

    public boolean actualizar(PartidoDTO partido) {
        try {
            String json = mapper.writeValueAsString(partido);

            Response response = client
                .target(ApiConfig.BASE_URL + "/partidos/" + partido.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .put(Entity.json(json));

            return response.getStatus() == 200;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al actualizar partido", e);
            return false;
        }
    }

    public PartidoDTO crear(PartidoDTO partido) {
        try {
            String json = mapper.writeValueAsString(partido);

            Response response = client
                .target(ApiConfig.BASE_URL + "/partidos")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .post(Entity.json(json));

            if (response.getStatus() != 201) return null;

            String jsonResponse = response.readEntity(String.class);
            return mapper.readValue(jsonResponse, PartidoDTO.class);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al crear partido", e);
            return null;
        }
    }



    public boolean eliminar(Long id) {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/partidos/" + id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .delete();

            return response.getStatus() == 204;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al eliminar partido", e);
            return false;
        }
    }
}
