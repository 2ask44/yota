package pojos;
import lombok.Data;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PhonePOJOResponse {
    private Long phone;
    private String locale;
}
