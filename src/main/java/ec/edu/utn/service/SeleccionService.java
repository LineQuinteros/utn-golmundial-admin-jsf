package ec.edu.utn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.utn.dto.SeleccionDTO;
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
public class SeleccionService {

    private static final Logger LOG = Logger.getLogger(SeleccionService.class.getName());
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

    public List<SeleccionDTO> listarTodos() {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/selecciones")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .get();

            if (response.getStatus() != 200) return new ArrayList<>();

            String json = response.readEntity(String.class);
            return mapper.readValue(json, new TypeReference<List<SeleccionDTO>>() {});

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar selecciones", e);
            return new ArrayList<>();
        }
    }


public SeleccionDTO crear(SeleccionDTO seleccion) {
    try {
        String json = mapper.writeValueAsString(seleccion);

        Response response = client
            .target(ApiConfig.BASE_URL + "/selecciones")
            .request(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
            .post(Entity.json(json));

        if (response.getStatus() != 201) return null;

        String jsonResponse = response.readEntity(String.class);
        return mapper.readValue(jsonResponse, SeleccionDTO.class);

    } catch (Exception e) {
        LOG.log(Level.SEVERE, "Error al crear selección", e);
        return null;
    }
}


    public boolean actualizar(SeleccionDTO seleccion) {
        try {
            String json = mapper.writeValueAsString(seleccion);

            Response response = client
                .target(ApiConfig.BASE_URL + "/selecciones/" + seleccion.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .put(Entity.json(json));

            return response.getStatus() == 200;

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al actualizar seleccion", e);
            return false;
        }
    }
}
