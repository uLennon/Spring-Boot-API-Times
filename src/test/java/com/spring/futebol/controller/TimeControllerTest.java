package com.spring.futebol.controller;

import com.spring.futebol.domain.Time;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.request.TimePutRequestBody;
import com.spring.futebol.service.TimeService;
import com.spring.futebol.util.TimeCreator;
import com.spring.futebol.util.TimePostRequestBodyCreator;
import com.spring.futebol.util.TimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TimeControllerTest {
    @InjectMocks
    private TimeController timeController;
    @Mock
    private TimeService timeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Time> timePage = new PageImpl<>(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(timePage);
        BDDMockito.when(timeServiceMock.listAllNonPageable())
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.findByIdOrThrowsBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.when(timeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.save(ArgumentMatchers.any(TimePostRequestBody.class)))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.doNothing().when(timeServiceMock).replace(ArgumentMatchers.any(TimePutRequestBody.class));
        BDDMockito.doNothing().when(timeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Lista de times")
    void list_ReturnsListOfTimesInsidePageObject() {
        String exceptedName = TimeCreator.createValidTime().getName();
        Page<Time> timePage = timeController.list(null).getBody();
        Assertions.assertThat(timePage).isNotNull();
        Assertions.assertThat(timePage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Lista de times")
    void listAll_ReturnsListOfTimesInsidePageObject() {
        String exceptedName = TimeCreator.createValidTime().getName();
        List<Time> times = timeController.listAll().getBody();
        Assertions.assertThat(times)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("Retorna o time pelo ID")
    void findById_ReturnsTimes() {
        Long expectedId = TimeCreator.createValidTime().getId();
        Time time = timeController.findById(1).getBody();
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Retorna o time pelo nome")
    void findByName_ReturnsListOfTimes() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> times = timeController.findByName("time").getBody();
        Assertions.assertThat(times)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Retorno vazio do time")
    void findByName_ReturnsEmptyListOfTimes() {
        BDDMockito.when(timeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> times = timeController.findByName("time").getBody();
        Assertions.assertThat(times)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("salva o time")
    void save_ReturnsTimes() {
        Time time = timeController.save(TimePostRequestBodyCreator.createTimePostRequestBody()).getBody();
        Assertions.assertThat(time).isNotNull().isEqualTo(TimeCreator.createValidTime());
    }

    @Test
    @DisplayName("Atualiza o time")
    void replace_UpdateTime() {
        Assertions.assertThatCode(() -> timeController.replace(TimePutRequestBodyCreator.createTimePutRequestBody()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = timeController.replace(TimePutRequestBodyCreator.createTimePutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Deleta o time")
    void delete_RemoveTime() {
        Assertions.assertThatCode(() -> timeController.delete(1))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = timeController.delete(1);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}