package com.github.pwnmn.server.petstore.service;

import com.github.pwnmn.server.petstore.api.StoreApiDelegate;
import com.github.pwnmn.server.petstore.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This service is the actual implementation and is autowired automatically instead of the
 * delegate. If this service is not provided, the default delegate will be used returning not implemented.
 *
 * The methods here override the default implementations in the delegate. Because the delegate is an interface
 * with default implementations, some of the methods can be implemented at a later time.
 *
 * This separates the implementation of the REST endpoints from the actual API definition. In case the API changes,
 * the classes in the api and model packages can be regenerated independent of the implementation.
 */
@Service
public class StoreService implements StoreApiDelegate {

    /**
     * Autowire your repositories, mappers and dependencies here. (Constructor injection)
     */
    public StoreService() {

    }

    @Override
    public ResponseEntity<Void> deleteOrder(Long orderId) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Map<String, Integer>> getInventory() {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long orderId) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Order> placeOrder(Order order) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
