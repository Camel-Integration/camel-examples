package org.example.module.orchestrator.service;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToWords;
import org.apache.camel.ProducerTemplate;
import org.example.module.numberconversion.model.NumberConversionDataType;
import org.example.module.numberconversion.route.NumberConversionRoutes;
import org.example.module.orchestrator.exception.CamelRequestException;
import org.example.module.orchestrator.model.OrchestratorDto;
import org.example.module.orchestrator.model.OrchestratorResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class NumberConversionService {
    private final ProducerTemplate producerTemplate;
    private final String NUMBER_TO_WORDS_ROUTE = NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRoute();
    private final String NUMBER_TO_DOLLARS_ROUTE = NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRoute();

    public NumberConversionService(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public OrchestratorResponseDto getNumberToWords(OrchestratorDto input) {
        try {
            // Transform the input to the library's data type
            NumberConversionDataType data = new NumberConversionDataType();
            NumberToWords numberToWords = new NumberToWords();
            numberToWords.setUbiNum(new BigInteger(input.getNumber()));
            data.setNumberToWordsInputType(numberToWords);

            // invoke the route from the library
            NumberConversionDataType numberConversionDataType = producerTemplate.requestBody(NUMBER_TO_WORDS_ROUTE, data, NumberConversionDataType.class);
            return new OrchestratorResponseDto(input.getNumber(), numberConversionDataType.getNumberToWordsOutputType().getNumberToWordsResult(), null);

        } catch (Exception e) {
            throw new CamelRequestException(e.getCause().getMessage());
        }
    }

    public OrchestratorResponseDto getNumberToDollars(OrchestratorDto input) {
        try {
            NumberConversionDataType data = new NumberConversionDataType();
            NumberToDollars numberToDollars = new NumberToDollars();
            numberToDollars.setDNum(new BigDecimal(input.getNumber()));
            data.setNumberToDollarsInputType(numberToDollars);

            NumberConversionDataType numberConversionDataType = producerTemplate.requestBody(NUMBER_TO_DOLLARS_ROUTE, data, NumberConversionDataType.class);
            return new OrchestratorResponseDto(input.getNumber(), null, numberConversionDataType.getNumberToDollarsOutputType().getNumberToDollarsResult());
        } catch (Exception e) {
            throw new CamelRequestException(e.getCause().getMessage());
        }
    }
}
