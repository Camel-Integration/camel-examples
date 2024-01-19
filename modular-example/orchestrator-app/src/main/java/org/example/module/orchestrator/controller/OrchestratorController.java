package org.example.module.orchestrator.controller;

import org.example.module.orchestrator.model.OrchestratorDto;
import org.example.module.orchestrator.model.OrchestratorResponseDto;
import org.example.module.orchestrator.service.NumberConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orchestrator")
public class OrchestratorController {

    private final NumberConversionService numberConversionService;

    public OrchestratorController(NumberConversionService numberConversionService) {
        this.numberConversionService = numberConversionService;
    }


    @PostMapping("/convertNumberToWords")
    public OrchestratorResponseDto convertNumberToWords(@RequestBody OrchestratorDto orchestratorDto) {
        return numberConversionService.getNumberToWords(orchestratorDto);
    }

    @PostMapping("/convertNumberToDollars")
    public OrchestratorResponseDto convertNumberToDollars(@RequestBody OrchestratorDto orchestratorDto) {
        return numberConversionService.getNumberToDollars(orchestratorDto);
    }
}
