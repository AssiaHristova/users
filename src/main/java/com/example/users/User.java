package com.example.users;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @NotBlank(message = "Name is mandatory")
    private String userName;

    private String userAddress;
}
