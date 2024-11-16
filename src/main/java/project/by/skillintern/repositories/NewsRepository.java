package project.by.skillintern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.by.skillintern.entities.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
