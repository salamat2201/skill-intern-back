package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.dto.responses.VacancyResponseDTO;
import project.by.skillintern.services.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/internship")
@CrossOrigin(origins = "*")
@Tag(name="Internship", description="Взаймодействие со стажировками")
@RequiredArgsConstructor
public class InternshipController {
    private final VacancyService vacancyService;
    @GetMapping("/all")
    @Operation(summary = "Get all internships. All Users(Token керек емес)")
    private ResponseEntity<List<VacancyResponseDTO>> allInternships() {
        return ResponseEntity.ok(vacancyService.getAllInternships());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get internship detail by id. All Users(Токен керек емес)")
    private ResponseEntity<VacancyDTO> internshipDetail(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyDetail(id));
    }
}
