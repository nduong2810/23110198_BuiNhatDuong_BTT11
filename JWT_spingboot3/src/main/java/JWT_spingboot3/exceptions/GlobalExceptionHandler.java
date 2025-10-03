package JWT_spingboot3.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ex.printStackTrace(); // có thể gửi log lên tool observability

        ProblemDetail pd;

        if (ex instanceof BadCredentialsException) {
            pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            pd.setProperty("description", "The username or password is incorrect");
            return pd;
        }
        if (ex instanceof AccountStatusException) {
            pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            pd.setProperty("description", "The account is locked");
            return pd;
        }
        if (ex instanceof AccessDeniedException) {
            pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            pd.setProperty("description", "You are not authorized to access this resource");
            return pd;
        }
        if (ex instanceof SignatureException) {
            pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            pd.setProperty("description", "The JWT signature is invalid");
            return pd;
        }
        if (ex instanceof ExpiredJwtException) {
            pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            pd.setProperty("description", "The JWT token has expired");
            return pd;
        }

        pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
        pd.setProperty("description", "Unknown internal server error.");
        return pd;
    }
}