package gitlab.com.pwnmn.filter;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import gitlab.com.pwnmn.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Set tenant and username in MDC.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class MDCFilter extends OncePerRequestFilter {

    private static final String TENANT_PARAMETER = "tenant";
    private static final String USERNAME_PARAMETER = "username";
    private final JwtUtil jwtUtil;

    public MDCFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> businessPartner = jwtUtil.extractClaimFromToken(
                request.getHeader(HttpHeaders.AUTHORIZATION),
                TENANT_PARAMETER
        );

        // Get tenant from token
        if(businessPartner.isPresent()) {
            MDC.put(TENANT_PARAMETER, businessPartner.get());
            // Else get it from the path parameter
        } else {
            final Map<String, String> pathVariables = (Map<String, String>) request
                    .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if(pathVariables != null && pathVariables.containsKey(TENANT_PARAMETER)) {
                MDC.put(TENANT_PARAMETER, pathVariables.get(TENANT_PARAMETER));
            }
        }

        Optional<String> username = jwtUtil.extractClaimFromToken(
                request.getHeader(HttpHeaders.AUTHORIZATION),
                USERNAME_PARAMETER
        );

        if(username.isPresent()) {
            MDC.put(USERNAME_PARAMETER, username.get());
        }


        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TENANT_PARAMETER);
            MDC.remove(USERNAME_PARAMETER);
        }
    }
}