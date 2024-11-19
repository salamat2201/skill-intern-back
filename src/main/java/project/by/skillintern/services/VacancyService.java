package project.by.skillintern.services;

import project.by.skillintern.dto.requests.FilterVacancyDTO;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.dto.responses.VacancyResponseDTO;
import project.by.skillintern.entities.User;
import project.by.skillintern.entities.Vacancy;

import java.util.List;

public interface VacancyService {
    void createVacancy(Vacancy vacancy);
    List<VacancyResponseDTO> getAllVacancies();
    List<VacancyResponseDTO> getVacanciesByEmployer(User employer);
    List<VacancyResponseDTO> getVacanciesByFilter(FilterVacancyDTO filterVacancyDTO);
    List<VacancyResponseDTO> getAllInternships();
    VacancyDTO getVacancyDetail(Long id);
}
