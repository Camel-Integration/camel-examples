package org.example.cxf.springrest.controller;

import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cxf.springrest.model.NumberDto;
import org.example.cxf.springrest.service.NumberConversionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumberConversionController.class)
class NumberConversionControllerTest {

    static final String NUMBER_TO_WORDS = "/api/number-conversion/convertNumberToWords";
    static final String NUMBER_TO_DOLLARS = "/api/number-conversion/convertNumberToDollars";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NumberConversionService numberConversionService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void convertNumberToWords() throws Exception {
        NumberToWordsResponse response = new NumberToWordsResponse();
        response.setNumberToWordsResult("one hundred twenty-three");

        NumberDto numberDto = new NumberDto();
        numberDto.setNumber("123");
        String json = objectMapper.writeValueAsString(numberDto);


        Mockito.when(numberConversionService.getNumberToWords(numberDto)).thenReturn(response);

        mockMvc.perform(post(NUMBER_TO_WORDS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(response))
                );

        // redundant but for the sake of variation use verify
        verify(numberConversionService).getNumberToWords(numberDto);
    }

    @Test
    void convertNumberToDollars() throws Exception {
        NumberToDollarsResponse response = new NumberToDollarsResponse();
        response.setNumberToDollarsResult("one hundred twenty-three dollars");

        NumberDto numberDto = new NumberDto();
        numberDto.setNumber("123");

        Mockito.when(numberConversionService.getNumberToDollars(numberDto)).thenReturn(response);

        mockMvc.perform(post(NUMBER_TO_DOLLARS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(numberDto)))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(response))
                );

        // redundant but for the sake of variation use verify
        verify(numberConversionService).getNumberToDollars(numberDto);
    }

    @Test
    void shouldReturnBadRequestWhenNumberDtoIsInvalid_convertNumberToWords() throws Exception {
        String json = setupInvalidNumberDto();
        mockMvc.perform(post(NUMBER_TO_WORDS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNumberDtoIsInvalid_convertNumberToDollars() throws Exception {
        String json = setupInvalidNumberDto();
        mockMvc.perform(post(NUMBER_TO_DOLLARS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }



    String setupInvalidNumberDto() throws JsonProcessingException {
        NumberDto numberDto = new NumberDto();
        numberDto.setNumber("invalid number");
        String json = objectMapper.writeValueAsString(numberDto);

        // Returning null is ok as we're not expecting to reach the service layer
        Mockito.when(numberConversionService.getNumberToWords(numberDto)).thenReturn(null);
        Mockito.when(numberConversionService.getNumberToDollars(numberDto)).thenReturn(null);

        return json;
    }

}
