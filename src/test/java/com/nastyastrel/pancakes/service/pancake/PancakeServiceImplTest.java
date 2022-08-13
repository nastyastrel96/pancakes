package com.nastyastrel.pancakes.service.pancake;

import com.nastyastrel.pancakes.exception.*;
import com.nastyastrel.pancakes.model.order.Order;
import com.nastyastrel.pancakes.model.pancake.*;
import com.nastyastrel.pancakes.model.request.PancakeRequest;
import com.nastyastrel.pancakes.repository.IngredientRepository;
import com.nastyastrel.pancakes.repository.PancakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PancakeServiceImplTest {
    @Mock
    private PancakeRepository pancakeRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    private PancakeServiceImpl pancakeService;

    @BeforeEach
    void setUp() {
        pancakeService = new PancakeServiceImpl(pancakeRepository, ingredientRepository);
    }

    @Test
    void itShouldFindAllPancakesAndReturnPancakesDto() {
        //given
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        List<Pancake> pancakes = List.of(pancake1, pancake2);
        PancakeDto pancakeDto1 = PancakeDto.from(pancake1);
        PancakeDto pancakeDto2 = PancakeDto.from(pancake2);
        List<PancakeDto> pancakeDtoListToCalculateDiscount = List.of(pancakeDto1, pancakeDto2);
        when(pancakeRepository.findAll()).thenReturn(pancakes);

        //when
        var answer = pancakeService.findAll();

        //then
        assertEquals(pancakeDtoListToCalculateDiscount, answer);
    }

    @Test
    void itShouldFindPancakeById() {
        //given
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));

        //when
        var answer = pancakeService.findById(1L);

        //then
        assertEquals(PancakeDto.from(pancake), answer);
    }

    @Test
    void itShouldThrowExceptionIfPancakeIdIsIncorrect() {
        //given
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.empty());

        //then
        Throwable exception = assertThrows(NoSuchIdPancakeException.class, () -> pancakeService.findById(1L));
        assertEquals("Pancake with id = 1 is not found", exception.getMessage());
    }

    @Test
    void itShoulsThrowExceptionIfPancakeRequestDoesNotHaveIngredients() {
        //given
        PancakeRequest pancakeRequest = new PancakeRequest(Collections.emptyList());

        //then
        Throwable exception = assertThrows(NotValidPancakeForCreationException.class, () -> pancakeService.save(pancakeRequest));
        assertEquals("Pancake is not valid for creation. Valid pancake should have at least 1 ingredient", exception.getMessage());
    }

    @Test
    void itShouldSavePancake() {
        //given
        PancakeRequest pancakeRequest = new PancakeRequest(List.of(1L, 2L, 3L));
        Ingredient ingredient1 = new Ingredient(1L, "Egg", 20.0, Category.BASE, true);
        Ingredient ingredient2 = new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true);
        Ingredient ingredient3 = new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true);
        Pancake pancake = new Pancake(1L, new HashSet<>(), null);
        when(ingredientRepository.findById(eq(1L))).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(eq(2L))).thenReturn(Optional.of(ingredient2));
        when(ingredientRepository.findById(eq(3L))).thenReturn(Optional.of(ingredient3));
        pancake.addIngredient(ingredient1);
        pancake.addIngredient(ingredient2);
        pancake.addIngredient(ingredient3);
        when(pancakeRepository.save(any())).thenAnswer(call -> {
            Pancake pancakeArgument = call.getArgument(0);
            pancakeArgument.setPancakeId(1L);
            return pancakeArgument;
        });

        //when
        pancakeService.save(pancakeRequest);

        //then
        verify(pancakeRepository).save(pancake);
    }

    @Test
    void itShouldUpdatePancake() {
        //given
        Long pancakeId = 1L;
        PancakeRequest pancakeRequest = new PancakeRequest(List.of(1L, 2L));
        Ingredient ingredient1 = new Ingredient(1L, "Egg", 20.0, Category.BASE, true);
        Ingredient ingredient2 = new Ingredient(2L, "Nutella", 50.0, Category.FILLING, true);
        Set<Ingredient> ingredients = Set.of(
                ingredient1,
                ingredient2,
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));
        when(ingredientRepository.findById(eq(1L))).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(eq(2L))).thenReturn(Optional.of(ingredient2));

        Set<Ingredient> updatedIngredients = Set.of(ingredient1, ingredient2);
        pancake.setIngredients(updatedIngredients);

        //when
        var answer = pancakeService.updatePancake(pancakeId, pancakeRequest);

        //then
        assertEquals(PancakeDto.from(pancake), answer);
    }

    @Test
    void itShouldThrowExceptionIfPancakeRequestDoesNotHaveIngredients() {
        //given
        Long pancakeId = 1L;
        PancakeRequest pancakeRequest = new PancakeRequest(Collections.emptyList());

        //then
        Throwable exception = assertThrows(NotValidPancakeForCreationException.class, () -> pancakeService.updatePancake(pancakeId, pancakeRequest));
        assertEquals("Pancake is not valid for creation. Valid pancake should have at least 1 ingredient", exception.getMessage());
    }

    @Test
    void itShouldThrowExceptionIfPancakeIsAttachedToAnyOrder() {
        //given
        Long pancakeId = 1L;
        PancakeRequest pancakeRequest = new PancakeRequest(List.of(1L, 2L));
        Ingredient ingredient1 = new Ingredient(1L, "Egg", 20.0, Category.BASE, true);
        Ingredient ingredient2 = new Ingredient(2L, "Nutella", 50.0, Category.FILLING, true);
        Set<Ingredient> ingredients = Set.of(
                ingredient1,
                ingredient2,
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, new Order());
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));

        //then
        Throwable exception = assertThrows(NotVacantPancakeException.class, () -> pancakeService.updatePancake(pancakeId, pancakeRequest));
        assertEquals("At first you need to create pancakes and then to add them to the order", exception.getMessage());
    }

    @Test
    void itShouldDeletePancakeIfPancakeIdIsCorrect() {
        //given
        Long pancakeId = 1L;
        PancakeRequest pancakeRequest = new PancakeRequest(List.of(1L, 2L));
        Ingredient ingredient1 = new Ingredient(1L, "Egg", 20.0, Category.BASE, true);
        Ingredient ingredient2 = new Ingredient(2L, "Nutella", 50.0, Category.FILLING, true);
        Set<Ingredient> ingredients = Set.of(
                ingredient1,
                ingredient2,
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));

        //when
        pancakeService.deletePancake(pancakeId);

        //then
        verify(pancakeRepository).deleteById(1L);
    }

    @Test
    void itShouldDeletePancakeIfPancakeIdIsIncorrect() {
        //given
        Long pancakeId = 1L;
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.empty());

        //then
        Throwable exception = assertThrows(NoSuchIdPancakeException.class, () -> pancakeService.deletePancake(pancakeId));
        assertEquals("Pancake with id = 1 is not found", exception.getMessage());
    }

    @Test
    void itShouldAddPancakesToOrder() {
        //given
        Long userId = 1L;
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        List<Pancake> pancakes = List.of(pancake1, pancake2);
        Order order = new Order(1L, new ArrayList<>(), " ", LocalDateTime.of(2021, 9, 20, 21, 48, 22), userId);

        //when
        pancakeService.addPancakesToOrder(pancakes, order);

        //then
        verify(pancakeRepository).save(new Pancake(pancake1.getPancakeId(), pancake1.getIngredients(), order));
        verify(pancakeRepository).save(new Pancake(pancake2.getPancakeId(), pancake2.getIngredients(), order));
    }

    @Test
    void itShouldBeValidPancake() {
        //given
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.FILLING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));

        //when
        var answer = pancakeService.hasValidIngredients(1L);

        //then
        assertTrue(answer);
    }

    @Test
    void itShouldNotBeValidPancake() {
        //given
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.BASE, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FILLING, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake));

        //when
        var answer = pancakeService.hasValidIngredients(1L);

        //then
        assertFalse(answer);
    }

    @Test
    void itShouldBeHealthyPancake() {
        //given
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.BASE, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FILLING, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        PancakeDto pancakeDto = PancakeDto.from(pancake);

        //when
        var answer = pancakeService.isHealthyPancake(pancakeDto);

        //then
        assertTrue(answer);
    }

    @Test
    void itShouldNotBeHealthyPancake() {
        //given
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.BASE, false),
                new Ingredient(3L, "Raspberry", 150.0, Category.FILLING, true)
        );
        Pancake pancake = new Pancake(1L, ingredients, null);
        PancakeDto pancakeDto = PancakeDto.from(pancake);

        //when
        var answer = pancakeService.isHealthyPancake(pancakeDto);

        //then
        assertFalse(answer);
    }

    @Test
    void itShouldBeVacantPancakes() {
        //given
        List<Long> pancakesId = List.of(1L, 2L);
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, null);
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake1));
        when(pancakeRepository.findById(eq(2L))).thenReturn(Optional.of(pancake2));

        //when
        var answer = pancakeService.checkIfPancakeIsVacant(pancakesId);

        //then
        assertTrue(answer);
    }

    @Test
    void itShouldNotBeVacantPancakes() {
        //given
        List<Long> pancakesId = List.of(1L, 2L);
        Set<Ingredient> ingredients1 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, true),
                new Ingredient(3L, "Raspberry", 150.0, Category.FRUIT, true)
        );
        Set<Ingredient> ingredients2 = Set.of(
                new Ingredient(1L, "Egg", 20.0, Category.BASE, true),
                new Ingredient(2L, "Nutella", 50.0, Category.TOPPING, false)
        );
        Pancake pancake1 = new Pancake(1L, ingredients1, null);
        Pancake pancake2 = new Pancake(2L, ingredients2, new Order());
        when(pancakeRepository.findById(eq(1L))).thenReturn(Optional.of(pancake1));
        when(pancakeRepository.findById(eq(2L))).thenReturn(Optional.of(pancake2));

        //when
        var answer = pancakeService.checkIfPancakeIsVacant(pancakesId);

        //then
        assertFalse(answer);
    }
}