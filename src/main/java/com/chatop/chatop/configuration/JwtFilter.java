package com.chatop.chatop.configuration;

import com.chatop.chatop.services.JWTService;
import com.chatop.chatop.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Service
public class JwtFilter  extends OncePerRequestFilter {

    private UserService userService;
    private JWTService jwtService;
    public JwtFilter(UserService userService, JWTService jwtService ) {
        this.userService = userService;
        this.jwtService = jwtService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Boolean IsTokenExpired = true;

        final String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){

            token = authorization.substring(7);

            IsTokenExpired = jwtService.isTokenExpired(token);

            username = jwtService.extractUsername(token);

        }
        if(!IsTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails =  userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(String.valueOf(SecurityContextHolder.getContext()));
        }
        filterChain.doFilter(request, response);
    }
}
