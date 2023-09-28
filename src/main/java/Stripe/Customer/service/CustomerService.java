package Stripe.Customer.service;

import Stripe.Customer.ApiResponse.APIResponse;
import Stripe.Customer.Response.PaymentIntentResponse;
import Stripe.Customer.dto.CustomerDto;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {


    @Value("${stripe.api.secretKey}")
    private String stripeApiKey;

    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("name", customerDto.getName());
        params.put("email", customerDto.getEmail());
        params.put("description", "my customer");
//        Session session=Session.create(params);
        try {
            Customer customer = Customer.create(params);
            customerDto.setId(customer.getId());
            System.out.println(customer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return customerDto;
    }


//    public PaymentMethod createToken() throws StripeException {
//
//        Stripe.apiKey = "sk_test_51Nu8OjSGIdv1eLogRSaBrxovnGZXNpvXmZAZC6tfw6s41OUXkpzDtQUXXzEIXJp4sHE9d5XRwPmAtt8kcOMJ2Sja00zsjba2Lo";
//        String cc = "4242424242424242";
//        String month = "9";
//        String year = "2027";
//        String cvc = "314";
//        Map<String, Object> card = new HashMap<>();
//        card.put("number", cc);
//        card.put("exp_month", month);
//        card.put("exp_year", year);
//        card.put("cvc", cvc);
//        Map<String, Object> params = new HashMap<>();
//        params.put("type","card");
//        params.put("card", card);
//
//
//        PaymentMethod paymentMethod = PaymentMethod.create(params);
//  return  paymentMethod;
//    }

    public ResponseEntity<APIResponse> createPaymentMethod() {

        Stripe.apiKey = stripeApiKey;

        Map<String, Object> card = new HashMap<>();
//        card.put("number", "4242424242424242");
//        card.put("exp_month", 12);
//
//        card.put("exp_year", 2034);
//        card.put("cvc", "314");
        card.put("token", "tok_1NvBTlSGIdv1eLogeGaZSZib");
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);
//        params.put("customer","cus_OiKixQGMB6HMjH");

        try {
            PaymentMethod paymentMethod =
                    PaymentMethod.create(params);
            PaymentIntentResponse paymentIntentResponse = new PaymentIntentResponse();
            paymentIntentResponse.setPaymentIntentId(paymentMethod.getId());
            paymentIntentResponse.setMethod(paymentMethod.getObject());
//            paymentIntentResponse.setStatus(paymentMethod.getS());

            System.out.println(paymentMethod);
            return APIResponse.success("payment method :", paymentIntentResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.errorBadRequest("something occurred");
        }
    }

    public  ResponseEntity<APIResponse> createPaymentIntent() throws StripeException {

        Stripe.apiKey = stripeApiKey;
        Map<String, Object> automaticPaymentMethods =
                new HashMap<>();
        automaticPaymentMethods.put("enabled", true);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 50000);
        params.put("currency", "usd");
        params.put("customer","cus_OiKixQGMB6HMjH");

        params.put(
                "automatic_payment_methods",
                automaticPaymentMethods
        );
        try {
            PaymentIntent paymentIntent =
                    PaymentIntent.create(params);
            PaymentIntentResponse paymentIntentResponse= new PaymentIntentResponse();
            paymentIntentResponse.setPaymentIntentId(paymentIntent.getId());
            paymentIntentResponse.setMethod(paymentIntent.getObject());
            paymentIntentResponse.setCustomer(paymentIntent.getCustomer());
            paymentIntentResponse.setStatus(paymentIntent.getStatus());
            return APIResponse.success(" paymentIntent :",paymentIntentResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResponse.errorBadRequest("something occurred");
        }

        public  ResponseEntity<APIResponse> confirmPaymentMethod() throws StripeException {

            Stripe.apiKey = stripeApiKey;
        // To create a PaymentIntent for confirmation, see our guide at: https://stripe.com/docs/payments/payment-intents/creating-payment-intents#creating-for-automatic
            PaymentIntent paymentIntent =
                    PaymentIntent.retrieve(
                            "pi_3NvBcASGIdv1eLog1xlQi5P6"
                    );
            Map<String, Object> params = new HashMap<>();
            params.put("payment_method","pm_1NvBYlSGIdv1eLogfPGUGyuS");
            params.put("return_url","https://www.google.com/search?q=my+url&oq=my+url&aqs=chrome..69i57j0i512l9.7586j0j7&sourceid=chrome&ie=UTF-8");
            try {
            PaymentIntent updatedPaymentIntent =
              paymentIntent.confirm(params);
            PaymentIntentResponse paymentIntentResponse= new PaymentIntentResponse();
            paymentIntentResponse.setPaymentIntentId(updatedPaymentIntent.getId());
            paymentIntentResponse.setMethod(updatedPaymentIntent.getPaymentMethod());
            paymentIntentResponse.setCustomer(updatedPaymentIntent.getCustomer());
            paymentIntentResponse.setStatus(updatedPaymentIntent.getStatus());
      return APIResponse.success("updated paymentIntent method :",paymentIntentResponse);
     }catch (Exception e){
      e.printStackTrace();;
   }
            return APIResponse.errorBadRequest("something occurred");
    }

//    public ResponseEntity<APIResponse> confirmCharge() {
//        String chargeId = null;
//        try {
//            Stripe.apiKey = stripeApiKey;
//            String token="tok_1NvC3KSGIdv1eLogT1LesRtd";
//            Map<String, Object> chargeParams = new HashMap<>();
//            chargeParams.put("description", "Charge for " + "ram2@gmail.com");
//            chargeParams.put("currency", "usd");
//            chargeParams.put("amount", 10000);
//            chargeParams.put("source", token);
//            Charge charge = Charge.create(chargeParams);
//            PaymentIntentResponse paymentIntentResponse= new PaymentIntentResponse();
//            paymentIntentResponse.setPaymentIntentId(charge.getId());
//            paymentIntentResponse.setMethod(charge.getPaymentMethod());
//            paymentIntentResponse.setCustomer(charge.getCustomer());
//            paymentIntentResponse.setStatus(charge.getStatus());
//            return APIResponse.success("charge",paymentIntentResponse);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//       return  APIResponse.errorBadRequest("something cause on charge");
//    }
}







