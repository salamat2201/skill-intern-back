package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.services.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/internships")
@Tag(name="Vacancy", description="Взаймодействие со стажировками")
@RequiredArgsConstructor
public class InternshipController {
    private final VacancyService vacancyService;
    @GetMapping("/all")
    @Operation(summary = "Get all internships")
    private ResponseEntity<List<VacancyDTO>> allInternships() {
        return ResponseEntity.ok(vacancyService.getAllInternships());
    }
}
