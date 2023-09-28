package Stripe.Customer.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentIntentResponse {

    private String paymentIntentId;

    private String method;

    private String customer;

    private String status;


}
