package org.example.module.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrchestratorResponseDto {
    String inputNumber;
    String numberToWordsResult;
    String numberToDollarsResult;
}
