package com.nastyastrel.pancakes.repository;

import com.nastyastrel.pancakes.model.pancake.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query(value = "SELECT ingredients.id, ingredients.name, ingredients.price, ingredients.category, ingredients.healthy\n" +
            "FROM ingredients\n" +
            "         INNER JOIN pancake_ingredient pi on ingredients.id = pi.ingredients_id\n" +
            "         INNER JOIN pancakes p on p.id = pi.pancakes_id\n" +
            "         INNER JOIN orders o on o.id = p.orders_id\n" +
            "WHERE EXTRACT(month from creation_date) = :month\n" +
            "group by ingredients.id\n" +
            "having count(ingredients.id) = (SELECT count(ingredients.id)\n" +
            "                      FROM ingredients\n" +
            "                               INNER JOIN pancake_ingredient pi on ingredients.id = pi.ingredients_id\n" +
            "                               INNER JOIN pancakes p on p.id = pi.pancakes_id\n" +
            "                               INNER JOIN orders o on o.id = p.orders_id\n" +
            "                      WHERE EXTRACT(month from creation_date) = :month\n" +
            "                      group by ingredients.id\n" +
            "                      order by count(ingredients.id) desc\n" +
            "                      limit 1)", nativeQuery = true)
    List<Ingredient> findTheMostPopularIngredient(@Param("month") int month);

    @Query(value = "SELECT ingredients.id, ingredients.name, ingredients.price, ingredients.category, ingredients.healthy\n" +
            "FROM ingredients\n" +
            "         INNER JOIN pancake_ingredient pi on ingredients.id = pi.ingredients_id\n" +
            "         INNER JOIN pancakes p on p.id = pi.pancakes_id\n" +
            "         INNER JOIN orders o on o.id = p.orders_id\n" +
            "WHERE EXTRACT(month from creation_date) = :month AND ingredients.healthy = true\n" +
            "group by ingredients.id\n" +
            "having count(ingredients.id) = (SELECT count(ingredients.id)\n" +
            "                      FROM ingredients\n" +
            "                               INNER JOIN pancake_ingredient pi on ingredients.id = pi.ingredients_id\n" +
            "                               INNER JOIN pancakes p on p.id = pi.pancakes_id\n" +
            "                               INNER JOIN orders o on o.id = p.orders_id\n" +
            "                      WHERE EXTRACT(month from creation_date) = :month AND ingredients.healthy = true\n" +
            "                      group by ingredients.id\n" +
            "                      order by count(ingredients.id) desc\n" +
            "                      limit 1)", nativeQuery = true)
    List<Ingredient> findTheMostPopularHealthyIngredient(@Param("month") int month);
}
