package com.nastyastrel.pancakes.rest;

import com.nastyastrel.pancakes.model.pancake.PancakeDto;
import com.nastyastrel.pancakes.model.request.PancakeRequest;
import com.nastyastrel.pancakes.service.pancake.PancakeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PancakeRestController {
    private final PancakeService pancakeService;

    @GetMapping("/pancakes")
    public List<PancakeDto> findAll() {
        return pancakeService.findAll();
    }

    @GetMapping("/pancakes/{pancakeId}")
    @ResponseStatus(HttpStatus.OK)
    public PancakeDto findById(@PathVariable Long pancakeId) {
        return pancakeService.findById(pancakeId);
    }

    @PostMapping("/pancakes")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPancake(@RequestBody PancakeRequest pancakeRequest) {
        pancakeService.save(pancakeRequest);
    }

    @PutMapping("/pancakes/{pancakeId}")
    @ResponseStatus(HttpStatus.OK)
    public PancakeDto updatePancake(@PathVariable Long pancakeId, @RequestBody PancakeRequest pancakeRequest) {
        return pancakeService.updatePancake(pancakeId, pancakeRequest);
    }

    @DeleteMapping("/pancakes/{pancakeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePancake(@PathVariable Long pancakeId) {
        pancakeService.deletePancake(pancakeId);
    }
}
