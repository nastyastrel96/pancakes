package com.nastyastrel.pancakes.service.report;

import com.nastyastrel.pancakes.model.pancake.Category;
import com.nastyastrel.pancakes.model.pancake.Ingredient;
import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(ReportConfiguration.class)
public class ReportServiceIntegrationTest {
    @Autowired
    private ReportService reportService;

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.3"));

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void itShouldFindTheMostPopularOrderedIngredients() {
        //when
        var answer = reportService.findTheMostPopularOrderedIngredients(8);
        Ingredient ingredient = new Ingredient(1L, "Egg", 20.0, Category.BASE, false);
        IngredientDto ingredientDto = IngredientDto.from(ingredient);

        //then
        assertEquals(List.of(ingredientDto), answer);
    }

    @Test
    void itShouldFindTheMostPopularOrderedHealthyIngredients() {
        //when
        var answer = reportService.findTheMostPopularOrderedHealthyIngredients(8);
        Ingredient ingredient = new Ingredient(3L,"Raspberry", 150.0, Category.FRUIT, true);
        IngredientDto ingredientDto = IngredientDto.from(ingredient);

        //then
        assertEquals(List.of(ingredientDto), answer);
    }
}
