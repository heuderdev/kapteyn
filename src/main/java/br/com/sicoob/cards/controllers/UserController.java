package br.com.sicoob.cards.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.sicoob.cards.models.UserModel;
import br.com.sicoob.cards.repositories.IUserRepository;
import br.com.sicoob.cards.utils.ErroObjeto;
import br.com.sicoob.cards.utils.Input;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserRepository userRepository;

    @PutMapping("/update-profile/{id}")
    public ResponseEntity update(@RequestBody UserModel userModel, @PathVariable Long id) {
        var user = this.userRepository.findById(id);

        if (user.isPresent()) {
            // Input.copyNonNullProperties(userModel, user);
            logger.error(user.toString());
            UserModel userNew = user.get();

            userNew.setPassword(userModel.getPassword());
            userNew.setUsername(userModel.getUsername());
            userNew.setEmail(userModel.getEmail());
            userNew.setPermission(userModel.getPermission());

            userNew = this.userRepository.save(userNew);

            return ResponseEntity.status(HttpStatus.OK).body(userNew);

        } else {
            ErroObjeto error = new ErroObjeto("Lamentamos, não conseguimos localizar o usuário que você está procurando em nosso sistema. Por favor, verifique se os detalhes fornecidos estão corretos e tente novamente. Se ainda precisar de ajuda, não hesite em entrar em contato conosco.");
            logger.error(error.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody UserModel userModel) {
        logger.info("create new users");

        var emailExists = this.userRepository.findByEmail(userModel.getEmail());
        var userNameExists = this.userRepository.findByUsername(userModel.getUsername());


        if (emailExists) {
            ErroObjeto error = new ErroObjeto("Desculpe, o e-mail fornecido já existe em nosso banco de dados. Por favor, escolha um email único.");
            System.out.println(emailExists);
            logger.error(error.getMensagem());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (userNameExists) {
            ErroObjeto error = new ErroObjeto("Desculpe, o nome de usuário fornecido já existe em nosso banco de dados. Por favor, escolha um nome de usuário único.");
            logger.error(error.getMensagem());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        String passwordEncrypted = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordEncrypted);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }
}
