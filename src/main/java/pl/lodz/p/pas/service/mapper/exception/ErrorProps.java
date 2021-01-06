package pl.lodz.p.pas.service.mapper.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorProps {
    @JsonProperty
    private String status;
    @JsonProperty
    private List<ErrorProp> errorMessage = new ArrayList<>();

    public ErrorProps(String status, Collection<? extends ConstraintViolation<?>> collection) {
        this.status = status;

        collection.forEach(x -> errorMessage.add(
                new ErrorProp(StringUtils.substringAfterLast(x.getPropertyPath().toString(), '.'),
                        x.getMessage())));
    }

    public ErrorProps(String status, String message) {
        this.status = status;

        errorMessage.add(new ErrorProp("", message));
    }
}
