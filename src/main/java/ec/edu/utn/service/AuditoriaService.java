package ec.edu.utn.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.utn.dto.AuditoriaDTO;
import ec.edu.utn.util.ApiConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class AuditoriaService {

    private static final Logger LOG = Logger.getLogger(AuditoriaService.class.getName());
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

    public List<AuditoriaDTO> listarTodos() {
        try {
            Response response = client
                .target(ApiConfig.BASE_URL + "/auditoria")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ApiConfig.JWT_TOKEN)
                .get();

            if (response.getStatus() != 200) return new ArrayList<>();

            String json = response.readEntity(String.class);
            return mapper.readValue(json, new TypeReference<List<AuditoriaDTO>>() {});

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar auditoria", e);
            return new ArrayList<>();
        }
    }
}
