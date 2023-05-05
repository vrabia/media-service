package app.vrabia.mediaservice.config;


import app.vrabia.vrcommon.exception.ErrorCodes;
import app.vrabia.vrcommon.exception.VrabiaException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Configuration
public class ClientAuthorizationFilter extends OncePerRequestFilter {

    @Value("${python-client-id}")
    private String pythonClientId;

    private static final String LOAD_COMMUNITIES_IMAGE_ENDPOINT = "/image/communities";
    private static final String CLIENT_ID_HEADER = "Client-Id";

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldValidateAuthorizationHeader(request)) {
            validateAuthorizationHeader(request);
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldValidateAuthorizationHeader(HttpServletRequest request) {
        if("OPTIONS".equals(request.getMethod())) {
            return false;
        }
        return request.getServletPath().equals(LOAD_COMMUNITIES_IMAGE_ENDPOINT);
    }

    private void validateAuthorizationHeader(HttpServletRequest request) {
        String clientIdHeader = request.getHeader(CLIENT_ID_HEADER);
        if (!pythonClientId.equals(clientIdHeader)) {
            throw new VrabiaException(ErrorCodes.UNAUTHORIZED);
        }
    }
}
