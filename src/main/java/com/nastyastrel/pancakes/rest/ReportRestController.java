package com.nastyastrel.pancakes.rest;

import com.nastyastrel.pancakes.model.pancake.IngredientDto;
import com.nastyastrel.pancakes.service.report.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @GetMapping("/reports")
    public List<IngredientDto> findTheMostPopularOrderedIngredients(@RequestParam int month) {
        return reportService.findTheMostPopularOrderedIngredients(month);
    }

    @GetMapping("/reports/healthy")
    public List<IngredientDto> findTheMostPopularOrderedHealthyIngredients(@RequestParam int month) {
        return reportService.findTheMostPopularOrderedHealthyIngredients(month);
    }
}
