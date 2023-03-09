package io.contino.transformer.repositories;

import io.contino.transformer.models.Address;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomerAddressRepository {

    private Map<String, Address> store = Map.of(
    "12345678910", new Address("21 Baker Street", "", "W1U 8EQ", "London"),
    "12345678911", new Address("10 Downing Street", "", "SW1A 2AB", "London"),
    "12345678912", new Address("3 Abbey Rd", "", "NW8 9AY", "London")
    );

    public Address findByCustomerId(String customerId){
        return store.get(customerId);
    }
}
