package pl.lodz.p.pas.service.mapper.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorProp {
    @JsonProperty
    private String item;
    @JsonProperty
    private String message;
}
