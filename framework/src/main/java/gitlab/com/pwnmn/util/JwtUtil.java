package gitlab.com.pwnmn.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {

    /**
     * Extracts a top level claim from the raw JWT token.
     *
     * @param authorization
     * @param claim
     * @return
     */
    public Optional<String> extractClaimFromToken(String authorization, String claim) {
        var jwtToken = extractJwtToken(authorization);

        Optional<String> result = Optional.empty();
        if(jwtToken.isPresent()) {
            try {
                DecodedJWT jwt = JWT.decode(jwtToken.get());
                result = Optional.ofNullable(jwt.getClaim(claim).asString());
            } catch(JWTDecodeException exception) {
                log.error("Could not decode JWT claim to determine realm with root cause", exception);
            }
        }

        return result;
    }

    /**
     * Extracts the raw JWT token from the 'authroziation' header.
     *
     * @param authorization
     * @return
     */
    public Optional<String> extractJwtToken(String authorization) {
        var authHeaderOpt = Optional.ofNullable(authorization);
        String authHeader;
        if (authHeaderOpt.isPresent()) {
            authHeader = authHeaderOpt.get();
        } else {
            log.trace("Authorization header not present");
            return Optional.empty();
        }

        final String space = " ";
        String[] splitAuth = authHeader.split(space);
        if(splitAuth.length == 2) {
            return Optional.of(splitAuth[1]);
        } else {
            return Optional.empty();
        }
    }
}
