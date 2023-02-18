package com.spring.futebol.repository;

import com.spring.futebol.domain.Time;
import com.spring.futebol.util.TimeCreator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
@Log4j2
@DataJpaTest
@DisplayName("Teste para Time Repository")
class TimeRepositoryTest {
    @Autowired
    private TimeRepository timeRepository;

    @Test
    @DisplayName("Time criado com sucesso")
    void save_PersistTime(){
        Time time = TimeCreator.createTimeTobeSaved();
        Time timeSaved = this.timeRepository.save(time);
        Assertions.assertThat(timeSaved).isNotNull();
        Assertions.assertThat(timeSaved.getName()).isEqualTo(time.getName());
    }
    @Test
    @DisplayName("Time atualizado com sucesso")
    void save_UpdatePersistTime(){
        Time time = TimeCreator.createValidUpdatedTime();
        time.setName("CRB");
        Time timeUpdated = this.timeRepository.save(time);
        Assertions.assertThat(timeUpdated).isNotNull();
        Assertions.assertThat(timeUpdated.getName()).isEqualTo(time.getName());
    }
    @Test
    @DisplayName("Time removido com sucesso")
    void delete_RemovePersistTime(){
        Time time = TimeCreator.createTimeTobeSaved();
        Time timeSaved = this.timeRepository.save(time);
        this.timeRepository.delete(timeSaved);
        Optional<Time> timeOpt = this.timeRepository.findById(timeSaved.getId());
        Assertions.assertThat(timeOpt.isEmpty());
    }
    @Test
    @DisplayName("Lista de times encontrado pelo nome com sucesso")
    void findByName_ReturnListOfTime(){
        Time time = TimeCreator.createTimeTobeSaved();
        String nome = time.getName();
        Time timeSaved = this.timeRepository.save(time);
        List<Time> times = this.timeRepository.findByName(nome);

        Assertions.assertThat(times).isNotEmpty().contains(timeSaved);
    }

    @Test
    @DisplayName("Nome vazio ")
    void save_ThrowsConstraintValidationException(){
        Time time = new Time();
        Assertions.assertThatThrownBy(()->this.timeRepository.save(time))
                .isInstanceOf(ConstraintViolationException.class);
    }
}