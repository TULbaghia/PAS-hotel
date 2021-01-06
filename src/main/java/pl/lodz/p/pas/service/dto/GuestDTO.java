package pl.lodz.p.pas.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.pas.model.user.Guest;

import java.util.List;

public class GuestDTO {

    private final ObjectMapper objectMapper;

    public GuestDTO() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public String writeAsString(Class<?> views, Guest guest) throws JsonProcessingException {
        return objectMapper.writerWithView(views).writeValueAsString(guest);
    }

    public String writeAsString(Class<?> views, List<Guest> list) throws JsonProcessingException {
        return objectMapper.writerWithView(views).writeValueAsString(list);
    }
}
