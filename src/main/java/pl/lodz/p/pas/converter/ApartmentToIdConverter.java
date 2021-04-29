package pl.lodz.p.pas.converter;

import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.ApartmentRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
public class ApartmentToIdConverter implements Converter<Apartment>, Serializable {

    @Inject
    private ApartmentRepository apartmentRepository;

    @Override
    public Apartment getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return apartmentRepository.get(UUID.fromString(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Apartment apartment) {
        return apartment.getId().toString();
    }
}
