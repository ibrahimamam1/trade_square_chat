package iut.cse.trade_square_chat.services;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    public boolean isTokenValid(String token) {
        System.out.println("Token is valid");
        return true;
    }
}
