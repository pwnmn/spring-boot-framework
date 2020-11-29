package gitlab.com.pwnmn.config;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Keeps tenant information in a ThreadLocal (Per request)
 */
@Slf4j
public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
        log.trace("Set tenant for hibernate to {}", tenant);
        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}