package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.by.skillintern.dto.requests.FilterVacancyDTO;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.entities.User;
import project.by.skillintern.entities.Vacancy;
import project.by.skillintern.services.UserService;
import project.by.skillintern.services.VacancyService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacancies")
@CrossOrigin(origins = "*")
@Tag(name="Vacancy", description="Взаймодействие с вакансиями")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/add")
    @Operation(summary = "Add a new vacancy. Only Employers.")
    @ApiResponse(responseCode = "202", description = "Vacancy created successfully!")
    private ResponseEntity<?> addNewVacancy(@RequestBody @Valid VacancyDTO vacancyDTO, BindingResult bindingResult) {
        User currentUser = userService.getUserByUsername(userService.getCurrentUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        Vacancy vacancy = convertToVacancy(vacancyDTO);
        vacancy.setEmployer(currentUser);
        vacancyService.createVacancy(vacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vacancy created successfully!");
    }
    @GetMapping("/all")
    @Operation(summary = "Get all vacancies. All Users(Токен керек емес)")
    @ApiResponse(responseCode = "200")
    private ResponseEntity<List<VacancyDTO>> all() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }
    @GetMapping("/by-filter")
    @Operation(summary = "Get vacancies by filter. ")
    private ResponseEntity<List<VacancyDTO>> vacanciesByFilter(@RequestBody FilterVacancyDTO filterVacancyDTO) {
        return ResponseEntity.ok(vacancyService.getVacanciesByFilter(filterVacancyDTO));
    }
    @GetMapping("/my-vacancies")
    @Operation(summary = "Get my vacancies. All Users(Токен керек емес)")
    public ResponseEntity<List<VacancyDTO>> getMyVacancies() {
        User currentUser = userService.getUserByUsername(userService.getCurrentUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(vacancyService.getVacanciesByEmployer(currentUser));
    }

    private Vacancy convertToVacancy(VacancyDTO vacancyDTO) {
        return modelMapper.map(vacancyDTO, Vacancy.class);
    }
}
