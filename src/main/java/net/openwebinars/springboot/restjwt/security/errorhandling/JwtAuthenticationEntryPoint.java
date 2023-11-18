package net.openwebinars.springboot.restjwt.security.errorhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //private final ObjectMapper objectMapper;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

       /*response.setStatus(HttpStatus.UNAUTHORIZED.value());
       response.setHeader("WWW-Authenticate", "Bearer");
       response.setContentType("application/json");

       response.getWriter()
               .println(objectMapper.writeValueAsString(
                       Map.of("error", authException.getMessage())
               ));
       */
        resolver.resolveException(request, response, null, authException);
    }
}
