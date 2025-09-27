package iut.cse.trade_square_chat.security;

import iut.cse.trade_square_chat.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    JwtService jwtService;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes){

        if(request instanceof ServletServerHttpRequest servletRequest){
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            System.out.println(httpServletRequest.getRequestURI());
            String authHeader = httpServletRequest.getHeader("Authorization");

            if(authHeader != null && authHeader.startsWith("Bearer ")){
                System.out.println(authHeader);
                String token = authHeader.substring(7);

                if(jwtService.isTokenValid(token)){
                    attributes.put("token", token);
                    return true;
                }
            }
            else System.out.println("Authorization header not found");
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
