package project.by.skillintern.repositories;
import org.springframework.data.jpa.domain.Specification;
import project.by.skillintern.entities.Vacancy;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VacancySpecification {
    public static Specification<Vacancy> filterVacancies(String[] levels, String[] companies, String[] technologies, String employmentType, Boolean remoteWork) {
        return (Root<Vacancy> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Фильтр по уровню
            if (levels != null && levels.length > 0 && Arrays.stream(levels).anyMatch(l -> l != null && !l.isEmpty())) {
                predicates.add(root.get("level").in((Object[]) levels));
            }

            if (companies != null && companies.length > 0 && Arrays.stream(companies).anyMatch(c -> c != null && !c.isEmpty())) {
                predicates.add(root.get("employer").get("companyName").in((Object[]) companies));
            }

            if (technologies != null && technologies.length > 0 && Arrays.stream(technologies).anyMatch(t -> t != null && !t.isEmpty())) {
                predicates.add(root.get("profession").in((Object[]) technologies));
            }

            if (employmentType != null && !employmentType.isEmpty()) {
                predicates.add(cb.equal(root.get("operatingMode"), employmentType));
            }

            // Фильтр по удалённой работе
            if (remoteWork != null) {
                predicates.add(cb.equal(root.get("remoteWork"), remoteWork));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

