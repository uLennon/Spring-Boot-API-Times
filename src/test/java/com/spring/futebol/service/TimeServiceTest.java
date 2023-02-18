package com.spring.futebol.service;

import com.spring.futebol.domain.Time;
import com.spring.futebol.exception.BadRequestException;
import com.spring.futebol.repository.TimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class TimeServiceTest {
    @InjectMocks
    private TimeService timeService;
    @Mock
    private TimeRepository timeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Time> timePage = new PageImpl<>(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(timePage);
        BDDMockito.when(timeRepositoryMock.findAll())
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.save(ArgumentMatchers.any(Time.class)))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.doNothing().when(timeRepositoryMock).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Lista de times")
    void listAll_ReturnsListOfTimesInsidePageObject() {
        Page<Time> timePage = timeService.listAll(PageRequest.of(1,1));
        Assertions.assertThat(timePage).isNotNull();
        Assertions.assertThat(timePage.toList())
                .isNotEmpty()
                .hasSize(1);
    }


    @Test
    @DisplayName("Lista nao page de times")
    void listAllNonPageable_ReturnsListOfTimesInsidePageObject() {
        String exceptedName = TimeCreator.createValidTime().getName();
        List<Time> times = timeService.listAllNonPageable();
        Assertions.assertThat(times)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("Retorna o time pelo ID")
    void findByIdOrThrowsBadRequestException_ReturnsTimes() {
        Long expectedId = TimeCreator.createValidTime().getId();
        Time time = timeService.findByIdOrThrowsBadRequestException(1);
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("Excecao quando o time nao e encontrado")
    void findByIdOrThrowsBadRequestException_ThrowsBadRequestExceptionTimes() {
     BDDMockito.when(timeRepositoryMock.findById(ArgumentMatchers.anyLong()))
             .thenReturn(Optional.empty());
     Assertions.assertThatExceptionOfType(BadRequestException.class)
             .isThrownBy(()->timeService.findByIdOrThrowsBadRequestException(1));
    }


    @Test
    @DisplayName("Retorna o time pelo nome")
    void findByName_ReturnsListOfTimes() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> times = timeService.findByName("time");
        Assertions.assertThat(times)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Retorno vazio do time")
    void findByName_ReturnsEmptyListOfTimes() {
        BDDMockito.when(timeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        List<Time> times = timeService.findByName("time");
        Assertions.assertThat(times)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("salva o time")
    void save_ReturnsTimes() {
        Time time = timeService.save(TimePostRequestBodyCreator.createTimePostRequestBody());
        Assertions.assertThat(time).isNotNull().isEqualTo(TimeCreator.createValidTime());
    }

    @Test
    @DisplayName("Atualiza o time")
    void replace_UpdateTime() {
        Assertions.assertThatCode(() -> timeService.replace(TimePutRequestBodyCreator.createTimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deleta o time")
    void delete_RemoveTime() {
        Assertions.assertThatCode(() -> timeService.delete(1))
                .doesNotThrowAnyException();
    }
}