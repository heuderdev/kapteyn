package br.com.sicoob.cards.controllers;

import br.com.sicoob.cards.models.UsufructuariesModel;
import br.com.sicoob.cards.repositories.UsufructuariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    private UsufructuariesRepository _repository;

    @GetMapping
    public ResponseEntity<?> GetMappingPerson() {
        return ResponseEntity.status(HttpStatus.CREATED).body(_repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetMappingPersonById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(_repository.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> PostMappingPerson(@RequestBody UsufructuariesModel usufructuariesModel) throws Exception {

        var personExist = _repository.findByEmail(usufructuariesModel.getEmail());
        logger.error(String.valueOf(personExist));
        logger.error(usufructuariesModel.getEmail());
        if (personExist) {
            throw new Exception("this person already exists.");
        } else {
            _repository.save(usufructuariesModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> PutMappingPerson(@PathVariable("id") Long id, @RequestBody UsufructuariesModel usufructuariesModel) throws Exception {

        var p = _repository.findById(id);

        if (p.isPresent()) {
            var personSave = p.get();

            personSave.setEmail(usufructuariesModel.getEmail());
            personSave.setLogin(usufructuariesModel.getLogin());
            personSave.setPassword(usufructuariesModel.getPassword());
            personSave.setPermission(usufructuariesModel.getPermission());
            _repository.save(personSave);
            return ResponseEntity.status(HttpStatus.CREATED).body("");

        } else {
            throw new Exception("Person not found.");
        }
    }

    @DeleteMapping("/{id}")
    public void DeleteMappingPersonById(@PathVariable("id") Long id) {
        _repository.deleteById(id);
    }

}
