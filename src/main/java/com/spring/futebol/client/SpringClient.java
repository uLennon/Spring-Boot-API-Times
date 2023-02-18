package com.spring.futebol.client;

import com.spring.futebol.domain.Time;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Time> entity = new RestTemplate().getForEntity("http://localhost:8080/times/8", Time.class);
        log.info(entity);
        ResponseEntity<Object> exchange = new RestTemplate().exchange("http://localhost:8080/times/all", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());

        Time timeBahia = Time.builder().name("bahia").build();
        Time timeSaved = new RestTemplate().postForObject("http://localhost:8080/times", timeBahia, Time.class);
        log.info("saved time {}", timeSaved);
    }
}
