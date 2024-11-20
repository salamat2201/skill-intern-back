package project.by.skillintern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.by.skillintern.entities.Response;
import project.by.skillintern.entities.ResponseStatus;
import project.by.skillintern.entities.User;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByVacancy_Employer(User employer);
    List<Response> findByUser(User user);
    List<Response> findByUserAndStatus(User user, ResponseStatus status);
}
