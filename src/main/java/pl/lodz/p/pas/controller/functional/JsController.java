package pl.lodz.p.pas.controller.functional;

import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@RequestScoped
@Named
public class JsController implements Serializable {
    private final Map<String, String> jsMap = new HashMap<>();

    public String getJs() {
        return new JSONObject(jsMap).toString();
    }

    @PostConstruct
    public void initMap() {
        ResourceBundle rs = ResourceBundleService.getBundle();
        rs.keySet().stream().filter(x -> x.startsWith("JS_")).forEach(x -> jsMap.put(x, rs.getString(x)));
    }
}
