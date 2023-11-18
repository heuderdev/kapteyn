package br.com.sicoob.cards.controllers;


import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.sicoob.cards.models.UsufructuariesModel;
import br.com.sicoob.cards.repositories.UserRepository;
import br.com.sicoob.cards.utils.ErroObjeto;
import br.com.sicoob.cards.utils.Input;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UsufructuariesModel>> getAll() {
        List<UsufructuariesModel> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getOne(@PathVariable(value = "id") Long id) {
        var user = this.userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> PostMappingCreate(@Valid @RequestBody UsufructuariesModel usufructuariesModel) {
        var emailExists = this.userRepository.findByEmail(usufructuariesModel.getEmail());
        var userNameExists = this.userRepository.findByUsername(usufructuariesModel.getUsername());
        if (emailExists) {
            ErroObjeto error = new ErroObjeto("Sorry, the email you provided already exists in our database. Please choose a unique email.");
            logger.error(error.getMensagem());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (userNameExists) {
            ErroObjeto error = new ErroObjeto("Sorry, the username you provided already exists in our database. Please choose a unique username.");
            logger.error(error.getMensagem());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        String passwordEncrypted = BCrypt.withDefaults().hashToString(12, usufructuariesModel.getPassword().toCharArray());
        usufructuariesModel.setPassword(passwordEncrypted);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(usufructuariesModel));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {

        Optional<UsufructuariesModel> userO = this.userRepository.findById(id);

        if (userO.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");

        this.userRepository.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted!");
    }

    @PatchMapping("/users")
    public ResponseEntity<?> update(@RequestBody UsufructuariesModel dto) {

        //UsufructuariesModel  usufructuariesModel  = this.userRepository.queryBuscarUsuarioPorId(dto.getId());

        //logger.error(usufructuariesModel.toString());
        //var newUser = this.userRepository.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
