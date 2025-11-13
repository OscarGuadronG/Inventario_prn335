package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named("idiomaBean")
@SessionScoped
public class IdiomaBean implements Serializable {

    private String idioma = "es";

    public String getIdioma() {return idioma;}

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Locale getLocale() {
        return new Locale(idioma);
    }

    public void cambiarIdioma() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(getLocale());
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(getLocale());
    }
}
