package project.by.skillintern.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.by.skillintern.dto.requests.FilterVacancyDTO;
import project.by.skillintern.dto.requests.VacancyDTO;
import project.by.skillintern.dto.responses.VacancyResponseDTO;
import project.by.skillintern.entities.User;
import project.by.skillintern.entities.Vacancy;
import project.by.skillintern.repositories.VacancyRepository;
import project.by.skillintern.repositories.VacancySpecification;
import project.by.skillintern.services.VacancyService;

import java.util.List;
import java.util.Optional;
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

    @Transactional
    @Override
    public List<VacancyResponseDTO> getAllVacancies() {
        return vacancyRepository.findAll().stream().map(this::convertToVacancyResponseDTO).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<VacancyResponseDTO> getVacanciesByEmployer(User employer) {
        return vacancyRepository.findAll().stream()
                .filter(job -> job.getEmployer().equals(employer))
                .toList().stream().map(this::convertToVacancyResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<VacancyResponseDTO> getVacanciesByFilter(FilterVacancyDTO filterVacancyDTO) {
        Specification<Vacancy> specification = VacancySpecification.filterVacancies(filterVacancyDTO);
        return vacancyRepository.findAll(specification).stream().map(this::convertToVacancyResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<VacancyResponseDTO> getAllInternships() {
        return vacancyRepository.findAllWhereLevelIsIntern().stream().map(this::convertToVacancyResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VacancyDTO getVacancyDetail(Long id) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
        if(vacancyOptional.isEmpty()) {
            throw new UsernameNotFoundException("Vacancy not found!");
        }
        VacancyDTO vacancyDTO = convertToVacancyDTO(vacancyOptional.get());
        vacancyDTO.setCompanyName(vacancyOptional.get().getEmployer().getCompanyName());
        return vacancyDTO;
    }

    private VacancyResponseDTO convertToVacancyResponseDTO(Vacancy vacancy) {
        VacancyResponseDTO vacancyResponseDTO = new VacancyResponseDTO();
        vacancyResponseDTO.setCompanyName(vacancy.getEmployer().getCompanyName());
        vacancyResponseDTO.setLocation(vacancy.getLocation());
        vacancyResponseDTO.setExperience(vacancy.getExperience());
        vacancyResponseDTO.setTitle(vacancy.getTitle());
        vacancyResponseDTO.setSalaryStart(vacancy.getSalaryStart());
        vacancyResponseDTO.setSalaryEnd(vacancy.getSalaryEnd());
        vacancyResponseDTO.setId(vacancy.getId());
        return vacancyResponseDTO;
    }
    private VacancyDTO convertToVacancyDTO(Vacancy vacancy) {
        return modelMapper.map(vacancy, VacancyDTO.class);
    }
}
