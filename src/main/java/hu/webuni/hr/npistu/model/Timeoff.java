package hu.webuni.hr.npistu.model;

import hu.webuni.hr.npistu.enums.TimeoffStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Timeoff {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @ToString.Exclude
    private Employee employee;
    @ManyToOne
    @ToString.Exclude
    private Employee approver;

    private LocalDate started;
    private LocalDate ended;

    @Enumerated(EnumType.STRING)
    private TimeoffStatus status;

    @CreationTimestamp
    @ReadOnlyProperty
    private LocalDateTime createdAt;

    public Timeoff(Employee employee, Employee approver, LocalDate started, LocalDate ended, TimeoffStatus status) {
        this.employee = employee;
        this.approver = approver;
        this.started = started;
        this.ended = ended;
        this.status = status;
    }
}
