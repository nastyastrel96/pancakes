package com.nastyastrel.pancakes.rest;

import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.model.request.IngredientRequest;
import com.nastyastrel.pancakes.service.ingredient.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class IngredientRestController {
    private final IngredientService ingredientService;

    @GetMapping("/ingredients")
    public List<IngredientDto> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDto findById(@PathVariable Long ingredientId) {
        return ingredientService.findById(ingredientId);
    }

    @PostMapping("/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody IngredientRequest ingredientRequest) {
        ingredientService.save(ingredientRequest);
    }

    @PutMapping("/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDto updateIngredient(@PathVariable Long ingredientId, @RequestBody IngredientRequest ingredientRequest) {
        return ingredientService.updateIngredient(ingredientId, ingredientRequest);
    }

    @DeleteMapping("/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }
}
