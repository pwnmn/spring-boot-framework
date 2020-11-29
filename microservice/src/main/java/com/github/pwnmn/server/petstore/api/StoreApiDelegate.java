package com.github.pwnmn.server.petstore.api;

import com.github.pwnmn.server.petstore.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link StoreApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-11-28T18:27:51.349294+07:00[Asia/Bangkok]")

public interface StoreApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /store/order/{orderId} : Delete purchase order by ID
     * For valid response try integer IDs with value &lt; 1000. Anything above 1000 or nonintegers will generate API errors
     *
     * @param orderId ID of the order that needs to be deleted (required)
     * @return Invalid ID supplied (status code 400)
     *         or Order not found (status code 404)
     * @see StoreApi#deleteOrder
     */
    default ResponseEntity<Void> deleteOrder(Long orderId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /store/inventory : Returns pet inventories by status
     * Returns a map of status codes to quantities
     *
     * @return successful operation (status code 200)
     * @see StoreApi#getInventory
     */
    default ResponseEntity<Map<String, Integer>> getInventory() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /store/order/{orderId} : Find purchase order by ID
     * For valid response try integer IDs with value &lt;&#x3D; 5 or &gt; 10. Other values will generated exceptions
     *
     * @param orderId ID of order that needs to be fetched (required)
     * @return successful operation (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or Order not found (status code 404)
     * @see StoreApi#getOrderById
     */
    default ResponseEntity<Order> getOrderById(Long orderId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"petId\" : 198772, \"quantity\" : 7, \"id\" : 10, \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\", \"complete\" : true, \"status\" : \"approved\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<order> <id>10</id> <petId>198772</petId> <quantity>7</quantity> <shipDate>2000-01-23T04:56:07.000Z</shipDate> <status>approved</status> <complete>true</complete> </order>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /store/order : Place an order for a pet
     * Place a new order in the store
     *
     * @param order  (optional)
     * @return successful operation (status code 200)
     *         or Invalid input (status code 405)
     * @see StoreApi#placeOrder
     */
    default ResponseEntity<Order> placeOrder(Order order) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"petId\" : 198772, \"quantity\" : 7, \"id\" : 10, \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\", \"complete\" : true, \"status\" : \"approved\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
