package project.by.skillintern.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.by.skillintern.dto.requests.FilterVacancyDTO;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.entities.User;
import project.by.skillintern.entities.Vacancy;
import project.by.skillintern.repositories.VacancyRepository;
import project.by.skillintern.repositories.VacancySpecification;
import project.by.skillintern.services.VacancyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public void createVacancy(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }
    @Override
    public List<VacancyDTO> getAllVacancies() {
        return vacancyRepository.findAll().stream().map(this::convertToVacancyDTO).collect(Collectors.toList());
    }
    @Override
    public List<VacancyDTO> getVacanciesByEmployer(User employer) {
        return vacancyRepository.findAll().stream()
                .filter(job -> job.getEmployer().equals(employer))
                .toList().stream().map(this::convertToVacancyDTO).collect(Collectors.toList());
    }

    @Override
    public List<VacancyDTO> getVacanciesByFilter(FilterVacancyDTO filterVacancyDTO) {
        Specification<Vacancy> specification = VacancySpecification.filterVacancies(filterVacancyDTO);
        return vacancyRepository.findAll(specification).stream().map(this::convertToVacancyDTO).collect(Collectors.toList());
    }

    @Override
    public List<VacancyDTO> getAllInternships() {
        return vacancyRepository.findAllWhereLevelIsIntern().stream().map(this::convertToVacancyDTO).collect(Collectors.toList());
    }

    private VacancyDTO convertToVacancyDTO(Vacancy vacancy) {
        return modelMapper.map(vacancy, VacancyDTO.class);
    }
}
