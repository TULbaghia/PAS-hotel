package pl.lodz.p.pas.service.mapper.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorProps {
    @JsonProperty
    private List<ErrorProp> errorMessage = new ArrayList<>();

    public ErrorProps(Collection<? extends ConstraintViolation<?>> collection, boolean z) {
        collection.forEach(x -> errorMessage.add(
                new ErrorProp(StringUtils.substringAfterLast(x.getPropertyPath().toString(), '.'),
                        x.getMessage())));
    }

    public ErrorProps(Collection<ErrorProp> collection) {
        errorMessage = new ArrayList<>(collection);
    }

    public ErrorProps(String message) {
        errorMessage.add(new ErrorProp("", message));
    }
}
