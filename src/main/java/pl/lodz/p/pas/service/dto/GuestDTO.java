package pl.lodz.p.pas.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;

import javax.ws.rs.core.Response;
import java.util.List;

public class GuestDTO {

    private final ObjectMapper objectMapper;

    public GuestDTO() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public String writeAsString(Class<?> views, User guest) {
        try {
            return objectMapper.writerWithView(views).writeValueAsString(guest);
        } catch (JsonProcessingException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("", e.getMessage()));
        }
    }

    public String writeAsString(Class<?> views, List<User> list) {
        try {
            return objectMapper.writerWithView(views).writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("", e.getMessage()));
        }
    }
}
