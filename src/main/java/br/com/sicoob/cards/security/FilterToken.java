package br.com.sicoob.cards.security;

import br.com.sicoob.cards.repositories.UsufructuariesRepository;
import br.com.sicoob.cards.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private UsufructuariesRepository usufructuariesRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token;

        var authorizationHeader =   this.recoverToken(request);


        if(authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ","");
            var subject = this.tokenService.getSubject(token);
            var person = usufructuariesRepository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(person,
                    null, person.getAuthorities());
        }


        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
