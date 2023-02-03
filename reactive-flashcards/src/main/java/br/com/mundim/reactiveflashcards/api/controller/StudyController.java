package br.com.mundim.reactiveflashcards.api.controller;

import br.com.mundim.reactiveflashcards.api.controller.request.StudyRequest;
import br.com.mundim.reactiveflashcards.api.controller.response.QuestionResponse;
import br.com.mundim.reactiveflashcards.api.mapper.StudyMapper;
import br.com.mundim.reactiveflashcards.domain.service.StudyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("studies")
@Slf4j
@AllArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final StudyMapper studyMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest request){
        return studyService.start(studyMapper.toDocument(request))
                .doFirst(() -> log.info("===== try to create a study with follow request {}", request))
                .map(document -> studyMapper.toResponse(document.getLastQuestionPending()));
    }

}
