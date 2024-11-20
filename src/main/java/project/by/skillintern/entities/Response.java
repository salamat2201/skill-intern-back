package project.by.skillintern.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "responses")
@Data
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, который откликнулся

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy; // Вакансия, на которую откликнулись

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now(); // Время отклика

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResponseStatus status = ResponseStatus.PENDING; // Статус отклика (ожидание, принят, отклонён)
}
