package project.by.skillintern.dto.responses;

import lombok.Data;

@Data
public class VacancyResponseDTO {
    private Long id;
    private String title;
    private String location;
    private Long salaryStart;
    private Long salaryEnd;
    private Integer experience;
    private String companyName;
}
