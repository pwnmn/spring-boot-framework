package com.github.pwnmn.server.petstore.service;

import com.github.pwnmn.server.petstore.api.PetApiDelegate;
import com.github.pwnmn.server.petstore.model.ModelApiResponse;
import com.github.pwnmn.server.petstore.model.Pet;
import org.springframework.core.io.Resource;
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
public class PetService implements PetApiDelegate {

    /**
     * Autowire your repositories, mappers and dependencies here. (Constructor injection)
     */
    public PetService() {

    }

    @Override
    public ResponseEntity<Pet> addPet(Pet pet) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByStatus(String status) {
        // TODO: Provide custom implementation
        return null;
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByTags(List<String> tags) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Pet> updatePet(Pet pet) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata, Resource body) {
        // TODO: Provide custom implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
