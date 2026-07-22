package ec.edu.utn.util;

public class ApiConfig {

    // API de Estadísticas (WildFly + PostgreSQL). Sin barra final: los servicios agregan su ruta.
    //public static final String BASE_URL = "http://10.247.187.94:8080/servicio-estadisticas/api";

        public static final String BASE_URL = "http://localhost:8080/servicio-estadisticas/api";


    // Token JWT del administrador, obtenido tras el login.
    public static String JWT_TOKEN = null;

    private ApiConfig() {}
}