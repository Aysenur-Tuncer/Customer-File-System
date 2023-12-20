package jwttoken;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class TokenService {


        public String generateToken(String name) {
            Set<String> roles = new HashSet<>(Arrays.asList("admin"));

            return Jwt.issuer("jwttoken")
                    .subject("resources")
                    .groups(roles)
                    .claim(Claims.preferred_username.name(), name)
                    .expiresAt(System.currentTimeMillis()+3600)
                    .sign();
        }

}