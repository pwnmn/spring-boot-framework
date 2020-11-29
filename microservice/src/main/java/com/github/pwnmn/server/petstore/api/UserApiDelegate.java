package com.github.pwnmn.server.petstore.api;

import com.github.pwnmn.server.petstore.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

/**
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-11-28T18:27:51.349294+07:00[Asia/Bangkok]")

public interface UserApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /user : Create user
     * This can only be done by the logged in user.
     *
     * @param user Created user object (optional)
     * @return successful operation (status code 200)
     * @see UserApi#createUser
     */
    default ResponseEntity<User> createUser(User user) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"John\", \"lastName\" : \"James\", \"password\" : \"12345\", \"userStatus\" : 1, \"phone\" : \"12345\", \"id\" : 10, \"email\" : \"john@email.com\", \"username\" : \"theUser\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <id>10</id> <username>theUser</username> <firstName>John</firstName> <lastName>James</lastName> <email>john@email.com</email> <password>12345</password> <phone>12345</phone> <userStatus>1</userStatus> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /user/createWithList : Creates list of users with given input array
     * Creates list of users with given input array
     *
     * @param user  (optional)
     * @return Successful operation (status code 200)
     *         or successful operation (status code 200)
     * @see UserApi#createUsersWithListInput
     */
    default ResponseEntity<User> createUsersWithListInput(List<User> user) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"John\", \"lastName\" : \"James\", \"password\" : \"12345\", \"userStatus\" : 1, \"phone\" : \"12345\", \"id\" : 10, \"email\" : \"john@email.com\", \"username\" : \"theUser\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <id>10</id> <username>theUser</username> <firstName>John</firstName> <lastName>James</lastName> <email>john@email.com</email> <password>12345</password> <phone>12345</phone> <userStatus>1</userStatus> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /user/{username} : Delete user
     * This can only be done by the logged in user.
     *
     * @param username The name that needs to be deleted (required)
     * @return Invalid username supplied (status code 400)
     *         or User not found (status code 404)
     * @see UserApi#deleteUser
     */
    default ResponseEntity<Void> deleteUser(String username) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/{username} : Get user by user name
     *
     * @param username The name that needs to be fetched. Use user1 for testing.  (required)
     * @return successful operation (status code 200)
     *         or Invalid username supplied (status code 400)
     *         or User not found (status code 404)
     * @see UserApi#getUserByName
     */
    default ResponseEntity<User> getUserByName(String username) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"John\", \"lastName\" : \"James\", \"password\" : \"12345\", \"userStatus\" : 1, \"phone\" : \"12345\", \"id\" : 10, \"email\" : \"john@email.com\", \"username\" : \"theUser\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <id>10</id> <username>theUser</username> <firstName>John</firstName> <lastName>James</lastName> <email>john@email.com</email> <password>12345</password> <phone>12345</phone> <userStatus>1</userStatus> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/login : Logs user into the system
     *
     * @param username The user name for login (optional)
     * @param password The password for login in clear text (optional)
     * @return successful operation (status code 200)
     *         or Invalid username/password supplied (status code 400)
     * @see UserApi#loginUser
     */
    default ResponseEntity<String> loginUser(String username,
        String password) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /user/logout : Logs out current logged in user session
     *
     * @return successful operation (status code 200)
     * @see UserApi#logoutUser
     */
    default ResponseEntity<Void> logoutUser() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /user/{username} : Update user
     * This can only be done by the logged in user.
     *
     * @param username name that need to be deleted (required)
     * @param user Update an existent user in the store (optional)
     * @return successful operation (status code 200)
     * @see UserApi#updateUser
     */
    default ResponseEntity<Void> updateUser(String username,
        User user) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
