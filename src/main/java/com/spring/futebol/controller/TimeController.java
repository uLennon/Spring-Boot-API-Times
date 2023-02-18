package com.spring.futebol.controller;

import com.spring.futebol.domain.Time;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.request.TimePutRequestBody;
import com.spring.futebol.service.TimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("times")
@Log4j2
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<Page<Time>> list(Pageable pageable) {
        return  ResponseEntity.ok(timeService.listAll(pageable));
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<Time>> listAll() {
        return  ResponseEntity.ok(timeService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Time> findById(@PathVariable long id) {
        return  ResponseEntity.ok(timeService.findByIdOrThrowsBadRequestException(id)) ;
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Time> findByIdAuthenticationPrincipal(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return  ResponseEntity.ok(timeService.findByIdOrThrowsBadRequestException(id)) ;
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Time>> findByName(@RequestParam String nome) {
        return  ResponseEntity.ok(timeService.findByName(nome)) ;
    }

    @PostMapping
    public ResponseEntity<Time> save(@RequestBody @Valid  TimePostRequestBody timePostRequestBody){
        return new ResponseEntity<>(timeService.save(timePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TimePutRequestBody timePutRequestBody){
        timeService.replace(timePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
