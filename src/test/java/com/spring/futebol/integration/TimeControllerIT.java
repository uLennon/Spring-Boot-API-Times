package com.spring.futebol.integration;

import com.spring.futebol.domain.FutUser;
import com.spring.futebol.domain.Time;
import com.spring.futebol.repository.FutUserRepository;
import com.spring.futebol.repository.TimeRepository;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.util.TimeCreator;
import com.spring.futebol.util.TimePostRequestBodyCreator;
import com.spring.futebol.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private FutUserRepository futUserRepository;
    private static final FutUser user = FutUser.builder()
            .name("lennon")
            .password("$2a$10$wBAnWfIRoSqwZcsyZdc1Y.ot969nYA.FXUSNCvdotB5zclIxReYvm")
            .username("lennon")
            .authorities("USER")
            .build();

    @TestConfiguration
    @Lazy
    static class  Config{
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}")int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("lennon","test");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }
    @Test
    @DisplayName("Page Lista de times")
    void list_ReturnsListOfTimesInsidePageObject() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        String exceptedName = TimeCreator.createValidTime().getName();
        PageableResponse<Time> timePage = testRestTemplate.exchange("/times", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Time>>() {
        }).getBody();
        Assertions.assertThat(timePage).isNotNull();
        Assertions.assertThat(timePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timePage.toList().get(0).getName()).isEqualTo(exceptedName);
    }


    @Test
    @DisplayName("Lista de times")
    void listAll_ReturnsListOfTimesInsidePageObject() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        String exceptedName = TimeCreator.createValidTime().getName();
        List<Time> times = testRestTemplate.exchange("/times/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Time>>() {
        }).getBody();
        Assertions.assertThat(times).isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("Retorna o time pelo ID")
    void findById_ReturnsTimes() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        Long expectedId = savedTime.getId();
        Time time = testRestTemplate.getForObject("/times/{id}",Time.class,expectedId);
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Retorna o time pelo nome")
    void findByName_ReturnsListOfTimes() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        String expectedName = TimeCreator.createValidTime().getName();
        String url = String.format("/times/find?nome=%s",expectedName);
        List<Time> times = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Time>>() {
        }).getBody();
        Assertions.assertThat(times)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(times.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Retorno vazio do time")
    void findByName_ReturnsEmptyListOfTimes() {
        futUserRepository.save(user);
        List<Time> times = testRestTemplate.exchange("/times/find?nome=gg", HttpMethod.GET, null, new ParameterizedTypeReference<List<Time>>() {
        }).getBody();
        Assertions.assertThat(times)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("salva o time")
    void save_ReturnsTimes() {
        futUserRepository.save(user);
        TimePostRequestBody timePostRequestBody = TimePostRequestBodyCreator.createTimePostRequestBody();
        ResponseEntity<Time> time = testRestTemplate.postForEntity("/times", timePostRequestBody, Time.class);
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getBody()).isNotNull();
        Assertions.assertThat(time.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Atualiza o time")
    void replace_UpdateTime() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        savedTime.setName("Novo time");
        ResponseEntity<Void> entity = testRestTemplate.exchange("/times",HttpMethod.PUT,new HttpEntity<>(savedTime),Void.class);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Deleta o time")
    void delete_RemoveTime() {
        futUserRepository.save(user);
        Time savedTime = timeRepository.save(TimeCreator.createTimeTobeSaved());
        ResponseEntity<Void> entity = testRestTemplate.exchange("/times/{id}",HttpMethod.DELETE,null,Void.class,savedTime.getId());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
