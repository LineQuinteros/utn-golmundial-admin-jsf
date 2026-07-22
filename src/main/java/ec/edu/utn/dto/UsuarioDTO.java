package ec.edu.utn.dto;

public class UsuarioDTO {
    private Long id;
    private String token;
    private String username;
    private String nombre;
    private String email;
    private String rol;
    private Double saldo;


    public UsuarioDTO() {}

    public UsuarioDTO(String token, String username, String nombre, String rol) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

}
