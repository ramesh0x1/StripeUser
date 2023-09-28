package Stripe.Customer.Controller;

import Stripe.Customer.ApiResponse.APIResponse;
import Stripe.Customer.dto.CustomerDto;
import Stripe.Customer.service.CustomerService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    @Autowired
    CustomerService customerService;

    @GetMapping("/key")
    public String getKey() {
        return "key  :" + stripeSecretKey;
    }


    @Bean
    public void initStripe() {
        Stripe.apiKey = stripeSecretKey;
    }
//    @PostMapping("/create/token")
//    public PaymentMethod getToken() throws StripeException {
//
//        return customerService.createToken();
//
//    }

    @PostMapping("/create/customer")
    public CustomerDto createCustomer(@RequestBody CustomerDto data) throws StripeException {

        CustomerDto customerDto = customerService.createCustomer(data);
        return customerDto;

    }

    @PostMapping("create/paymentMethod")
    public ResponseEntity<APIResponse> createPaymentMethod() {
     return    customerService.createPaymentMethod();

    }


    @PostMapping("create/paymentIntent")
        public ResponseEntity<APIResponse> createPaymentIntent() throws StripeException {
      return      customerService.createPaymentIntent();
    }

    @PostMapping("confirm/paymentMethod")
    public ResponseEntity<APIResponse> confirmPaymentMethod() throws StripeException {
       return  customerService.confirmPaymentMethod();
    }

//    @PostMapping("/charge")
//    public ResponseEntity<APIResponse> confirmCharge() throws StripeException {
//        return  customerService.confirmCharge();
//    }
}

