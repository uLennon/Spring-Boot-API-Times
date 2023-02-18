package com.spring.futebol.service;

import com.spring.futebol.domain.Time;
import com.spring.futebol.exception.BadRequestException;
import com.spring.futebol.mapper.TimeMapper;
import com.spring.futebol.repository.TimeRepository;
import com.spring.futebol.request.TimePostRequestBody;
import com.spring.futebol.request.TimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public Page<Time> listAll(Pageable pageable) {
        return timeRepository.findAll(pageable);
    }

    public List<Time> listAllNonPageable() {
        return timeRepository.findAll();
    }

    public List<Time> findByName(String nome) {
        return timeRepository.findByName(nome);
    }

    public Time findByIdOrThrowsBadRequestException(long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Time nao encontrado"));
    }

    public Time save(TimePostRequestBody timePostRequestBody) {
        return timeRepository.save(TimeMapper.INSTANCE.toTime(timePostRequestBody));
    }

    public void delete(long id) {
        timeRepository.delete(findByIdOrThrowsBadRequestException(id));
    }

    public void replace(TimePutRequestBody timePutRequestBody) {
        Time savedTime = findByIdOrThrowsBadRequestException(timePutRequestBody.getId());
        Time time = TimeMapper.INSTANCE.toTime(timePutRequestBody);
        time.setId(savedTime.getId());
        timeRepository.save(time);
    }


}
