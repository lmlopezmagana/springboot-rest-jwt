package net.openwebinars.springboot.restjwt.security.jwt.refresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;

}
