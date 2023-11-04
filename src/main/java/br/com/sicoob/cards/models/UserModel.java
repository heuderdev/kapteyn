package br.com.sicoob.cards.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Table(name = "users")
@Entity(name = "users")
public class UserModel  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(unique = true, nullable = false)
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Integer permission;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime update_at;

}
