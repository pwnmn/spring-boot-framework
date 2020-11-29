package com.github.pwnmn.server.petstore.service;

import com.github.pwnmn.server.petstore.api.UserApiDelegate;
import com.github.pwnmn.server.petstore.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class UserService implements UserApiDelegate {

    /**
     * Autowire your repositories, mappers and dependencies here. (Constructor injection)
     */
    public UserService() {

    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<User> createUsersWithListInput(List<User> user) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String username) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<User> getUserByName(String username) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<String> loginUser(String username, String password) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> logoutUser() {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> updateUser(String username, User user) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
