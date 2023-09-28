package Stripe.Customer.dto;

import com.google.gson.annotations.JsonAdapter;
import lombok.Data;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;

@Data
public class CustomerDto {
    private String id;
    private String name;
    private String email;


}
