package ec.edu.utn.security;

import ec.edu.utn.bean.LoginBean;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import java.io.IOException;

public class AccesoPhaseListener implements PhaseListener {
    private static final long serialVersionUID = 1L;

    @Override
    public PhaseId getPhaseId() { return PhaseId.RESTORE_VIEW; }

    @Override
    public void beforePhase(PhaseEvent event) {}

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        if (context.getViewRoot() == null) return;

        String viewId = context.getViewRoot().getViewId();

        boolean requiereAdmin = viewId.startsWith("/admin/");
        boolean requiereCliente = viewId.startsWith("/cliente/");

        if (!requiereAdmin && !requiereCliente) return;

        Application app = context.getApplication();
        LoginBean loginBean = app.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);

        try {
            if (!loginBean.isAutenticado()) {
                redirigir(context, "/login.xhtml");
                return;
            }
            if (requiereAdmin && !loginBean.isAdministrador()) {
                redirigir(context, loginBean.isCliente() ? "/cliente/inicio.xhtml" : "/login.xhtml");
                return;
            }
            if (requiereCliente && !loginBean.isCliente()) {
                redirigir(context, loginBean.isAdministrador() ? "/admin/inicio.xhtml" : "/login.xhtml");
                return;
            }
        } catch (IOException e) {}
    }

    private void redirigir(FacesContext context, String ruta) throws IOException {
        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + ruta);
        context.responseComplete();
    }
}
