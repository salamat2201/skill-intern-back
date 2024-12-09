package project.by.skillintern.services;

import project.by.skillintern.dto.responses.MyResponsesDTO;
import project.by.skillintern.dto.responses.ResponseDTO;
import project.by.skillintern.entities.ResponseStatus;

import java.util.List;

public interface ResponseService {
    void createResponse(Long vacancyId);
    List<ResponseDTO> getResponsesForEmployer(Long vacancyId);
    void updateResponseStatus(Long responseId, ResponseStatus status);
    List<MyResponsesDTO> getResponsesForUser();
    List<MyResponsesDTO> getResponses(ResponseStatus status);
}
