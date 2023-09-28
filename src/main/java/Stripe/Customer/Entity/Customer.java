package Stripe.Customer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="customers")
@NoArgsConstructor
public class Customer {

    @Id
    private String id;

    private String name;

    @Column(unique = true)
    private  String email;

    private String password;

}
