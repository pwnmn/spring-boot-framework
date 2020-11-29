package gitlab.com.pwnmn.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor to output execution time.
 */
@Slf4j
public class ExecutionTimeInterceptor extends HandlerInterceptorAdapter {
    /**
     * Captures the start time of the request
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @param handler  Method handler
     * @return We will always return true here
     * @throws Exception any exception thrown
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("API : Method {} | URL {}", request.getMethod(), request.getServletPath());
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * Captures the end time of the request and calculated the execution time based on start and end time
     *
     * @param request      HTTP request
     * @param response     HTTP response
     * @param handler      Method handler
     * @param modelAndView not used
     * @throws Exception any exception thrown
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        log.info("API: Method {} | URL {} | Execution time: {}", request.getMethod(), request.getRequestURI(), executeTime);
    }
}
