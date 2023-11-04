package br.com.sicoob.cards.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FilterToken.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;

        var authorizationHeader = request.getHeader("Authorization");
        logger.info(request.toString());

        if(authorizationHeader != null) {
//            token = authorizationHeader.replace("Bearer ", "");
//            var subject = this.tokenService.getSubject(token);
//
//            var usuario = this.usuarioRepository.findByLogin(subject);
//
//            var authentication = new UsernamePasswordAuthenticationToken(usuario,
//                    null, usuario.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}