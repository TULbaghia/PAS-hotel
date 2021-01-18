package pl.lodz.p.pas.converter;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("pl.lodz.p.pas.converter.localDateTimeConverter")
public class DateTimeToStringConverter  implements Converter<DateTime> {

    @Override
    public DateTime getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return DateTime.parse(s, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, DateTime dateTime) {
        DateTimeFormatter dmf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dmf.print(dateTime);
    }
}
