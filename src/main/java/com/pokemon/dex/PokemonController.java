package com.pokemon.dex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private List<Pokemon> pokemons = new ArrayList<>();
    private int proximoId = 1;

    @GetMapping
    private List<Pokemon> listar() {
        return pokemons;
    }

    @GetMapping("/{id}")
    private Pokemon descrever(@PathVariable int id) {
        Pokemon pokemon = buscarPokemonPorId(id);

        if(pokemon != null) {
            return pokemon;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void inserir(@RequestBody Pokemon pokemon) {
        pokemon.setId(proximoId);
        proximoId++;
        pokemons.add(pokemon);
    }

    @PutMapping("/{id}")
    private void atualizar(@PathVariable int id, @RequestBody Pokemon pokemon) {
        Pokemon pokemonEncontrado = buscarPokemonPorId(id);

        if(pokemonEncontrado != null) {
            pokemonEncontrado.atualizar(pokemon);
            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    private void remover(@PathVariable int id) {
        Pokemon pokemon = buscarPokemonPorId(id);

        if(pokemon != null) {
            pokemons.remove(pokemon);
            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private Pokemon buscarPokemonPorId(int id) {
        for(Pokemon pokemon : pokemons) {
            if(pokemon.getId() == id) {
                return pokemon;
            }
        }

        return null;
    }
}
