package project.by.skillintern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.by.skillintern.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
    @Query("SELECT v FROM Vacancy v WHERE v.level = 'Intern'")
    List<Vacancy> findAllWhereLevelIsIntern();

}
