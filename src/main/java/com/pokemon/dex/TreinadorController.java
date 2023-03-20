package com.pokemon.dex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/treinadores")
public class TreinadorController {
    @Autowired
    TreinadorRepository repository;

    @GetMapping
    private Iterable<Treinador> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    private Treinador descrever(@PathVariable int id) {
        Optional<Treinador> optional = repository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void inserir(@RequestBody Treinador treinador) {
        repository.save(treinador);
    }

    @PutMapping("/{id}")
    private void atualizar(@PathVariable int id, @RequestBody Treinador treinador) {
        Optional<Treinador> optional = repository.findById(id);

        if(optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Treinador encontrado = optional.get();
        encontrado.atualizar(treinador);

        repository.save(encontrado);
    }

    @DeleteMapping("/{id}")
    private void remover(@PathVariable int id) {
        Optional<Treinador> treinador = repository.findById(id);

        if(treinador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repository.delete(treinador.get());
    }
}
