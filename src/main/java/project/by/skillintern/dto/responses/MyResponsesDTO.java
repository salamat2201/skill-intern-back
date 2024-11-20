package project.by.skillintern.dto.responses;

import lombok.Data;
import project.by.skillintern.entities.ResponseStatus;

@Data
public class MyResponsesDTO {private Long id;
    private String title;
    private String location;
    private Long salaryStart;
    private Long salaryEnd;
    private Integer experience;
    private String companyName;
    private ResponseStatus responseStatus;
    public MyResponsesDTO(Long id, String title, String location, Long salaryStart, Long salaryEnd, Integer experience, String companyName, ResponseStatus responseStatus) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.salaryStart = salaryStart;
        this.salaryEnd = salaryEnd;
        this.experience = experience;
        this.companyName = companyName;
        this.responseStatus = responseStatus;
    }
}
