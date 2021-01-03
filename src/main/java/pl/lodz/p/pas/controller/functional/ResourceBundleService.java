package pl.lodz.p.pas.controller.functional;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import java.util.PropertyResourceBundle;

@RequestScoped
public class ResourceBundleService {

    @Produces
    public static PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context == null ? null : context.getApplication().evaluateExpressionGet(context, "#{msg}", PropertyResourceBundle.class);
    }

}
