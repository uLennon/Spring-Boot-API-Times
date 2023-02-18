package com.spring.futebol.controller;

import com.spring.futebol.domain.Time;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.request.TimePutRequestBody;
import com.spring.futebol.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Lista todos os times paginado",description = "O padrao da paginacao e 20, use o parametro do tamanho para alterar o valor padrao")
    public ResponseEntity<Page<Time>> list(Pageable pageable) {
        return  ResponseEntity.ok(timeService.listAll(pageable));
    }
    @GetMapping(path = "/all")
    @Operation(summary = "Lista todos os times")
    public ResponseEntity<List<Time>> listAll() {
        return  ResponseEntity.ok(timeService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Procura o time pelo id")
    public ResponseEntity<Time> findById(@PathVariable long id) {
        return  ResponseEntity.ok(timeService.findByIdOrThrowsBadRequestException(id)) ;
    }

    @GetMapping(path = "by-id/{id}")
    @Operation(summary = "Procura o time pelo id, necessita de autorizacao")
    public ResponseEntity<Time> findByIdAuthenticationPrincipal(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return  ResponseEntity.ok(timeService.findByIdOrThrowsBadRequestException(id)) ;
    }

    @GetMapping(path = "/find")
    @Operation(summary = "Procura o time pelo nome")
    public ResponseEntity<List<Time>> findByName(@RequestParam String nome) {
        return  ResponseEntity.ok(timeService.findByName(nome)) ;
    }

    @PostMapping
    @Operation(summary = "Salva um time")
    public ResponseEntity<Time> save(@RequestBody @Valid  TimePostRequestBody timePostRequestBody){
        return new ResponseEntity<>(timeService.save(timePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Exclui um time pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operacao concluida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quando o time nao existe no banco de dados")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Altera um time")
    public ResponseEntity<Void> replace(@RequestBody TimePutRequestBody timePutRequestBody){
        timeService.replace(timePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
