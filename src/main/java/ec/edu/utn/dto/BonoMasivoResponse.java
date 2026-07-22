package ec.edu.utn.dto;

public class BonoMasivoResponse {
    private String mensaje;
    private int usuariosBeneficiados;

    public BonoMasivoResponse() {}

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public int getUsuariosBeneficiados() { return usuariosBeneficiados; }
    public void setUsuariosBeneficiados(int usuariosBeneficiados) {
        this.usuariosBeneficiados = usuariosBeneficiados;
    }
}
