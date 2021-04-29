package pl.lodz.p.pas.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;

import javax.ws.rs.core.Response;
import java.util.List;

public class Mapper {

    private final ObjectMapper objectMapper;

    public Mapper() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public String writeAsString(Class<?> views, Object object) {
        try {
            return objectMapper.writerWithView(views).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("", e.getMessage()));
        }
    }

    public String writeAsString(Class<?> views, List<Object> objects) {
        try {
            return objectMapper.writerWithView(views).writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("", e.getMessage()));
        }
    }
}
