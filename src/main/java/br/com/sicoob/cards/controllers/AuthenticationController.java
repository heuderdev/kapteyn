package br.com.sicoob.cards.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.sicoob.cards.dto.Login;
import br.com.sicoob.cards.models.UsufructuariesModel;
import br.com.sicoob.cards.repositories.UsufructuariesRepository;
import br.com.sicoob.cards.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsufructuariesRepository _repository;
    @GetMapping("/")
    public ResponseEntity GetMappingAuth() {
        return ResponseEntity.ok("MESAGE");
    }
    @PostMapping("/login")
    public String login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.login(), login.password());
        Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        var person = (UsufructuariesModel) authenticate.getPrincipal();

        return tokenService.gerarToken(person);
    }

    @PostMapping("/register")
    public ResponseEntity<?> PostMappingRegisterAuth(@RequestBody UsufructuariesModel usufructuariesModel) {
        String passwordEncrypted = BCrypt.withDefaults().hashToString(12, usufructuariesModel.getPassword().toCharArray());
        usufructuariesModel.setPassword(passwordEncrypted);

        return ResponseEntity.status(HttpStatus.CREATED).body(_repository.save(usufructuariesModel));
    }
}
