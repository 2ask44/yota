package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Pojo {
    private Long phone;
    private String locale;
}
