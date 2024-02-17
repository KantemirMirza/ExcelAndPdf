package com.kani.excelandpdf.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotEmpty(message = "{NotEmpty.employee.firstName}")
    private String firstName;

    @NotEmpty(message = "{NotEmpty.employee.lastName}")
    private String lastName;

    @NotEmpty(message = "{NotEmpty.employee.email}")
    @Email(message = "{Email.employee.email}")
    private String email;

    @NotNull(message = "{typeMisMatch.employee.phone}")
    private int phone;

    @NotEmpty(message = "{typeMisMatch.employee.gender}")
    private String gender;

    @NotNull(message = "{typeMisMatch.employee.salary}")
    private double salary;

    @NotNull(message = "{NotNull.employee.date}")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
