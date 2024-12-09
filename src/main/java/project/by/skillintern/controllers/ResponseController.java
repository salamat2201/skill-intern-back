package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.by.skillintern.dto.responses.MyResponsesDTO;
import project.by.skillintern.dto.responses.ResponseDTO;
import project.by.skillintern.entities.ResponseStatus;
import project.by.skillintern.services.ResponseService;
import project.by.skillintern.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/responses")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final UserService userService;
    @PostMapping("/create")
    @Operation(summary = "Create response for vacancy. Only Users")
    public ResponseEntity<String> createResponse(@RequestParam Long vacancyId){
        responseService.createResponse(vacancyId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Response created successfully");
    }
    @GetMapping("/forMyVacancy")
    @Operation(summary = "Get all responses for Employer Vacancy. Only Employers")
    public ResponseEntity<List<ResponseDTO>> getResponsesForEmployer(@RequestParam Long vacancyId) {
        List<ResponseDTO> responses = responseService.getResponsesForEmployer(vacancyId);
        return ResponseEntity.ok(responses);
    }
    @PatchMapping("/status/{responseId}")
    @Operation(summary = "Обновить статус респонса")
    public ResponseEntity<String> updateResponseStatus(
            @PathVariable Long responseId,
            @RequestParam ResponseStatus status) {
        responseService.updateResponseStatus(responseId, status);
        return ResponseEntity.status(HttpStatus.OK).body("Status updated successfully!");
    }
    @GetMapping("/my")
    @Operation(summary = "Get my responses. Only Users")
    public ResponseEntity<List<MyResponsesDTO>> myResponses() {
        return ResponseEntity.ok(responseService.getResponsesForUser());
    }
    @GetMapping("/my/accepted")
    @Operation(summary = "Get my accepted responses. Only Users")
    public ResponseEntity<List<MyResponsesDTO>> getAcceptedResponses() {
        return ResponseEntity.ok(responseService.getResponses(ResponseStatus.ACCEPTED));
    }

    @GetMapping("/my/rejected")
    @Operation(summary = "Get my rejected responses. Only Users")
    public ResponseEntity<List<MyResponsesDTO>> getRejectedResponses() {
        return ResponseEntity.ok(responseService.getResponses(ResponseStatus.REJECTED));
    }

    @GetMapping("/my/pending")
    @Operation(summary = "Get my pending responses. Only Users")
    public ResponseEntity<List<MyResponsesDTO>> getPendingResponses() {
        return ResponseEntity.ok(responseService.getResponses(ResponseStatus.PENDING));
    }
}
