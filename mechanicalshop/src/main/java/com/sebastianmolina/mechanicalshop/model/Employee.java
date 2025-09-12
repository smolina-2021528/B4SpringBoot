package com.sebastianmolina.mechanicalshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no tiene que estar vacio")
    @Column(name = "first_name", unique = true)
    private String firstName;

    @NotBlank(message = "El apellido no tiene que estar vacio")
    @Column(name = "last_name", unique = true)
    private String lastName;

    @NotBlank(message = "El correo no tiene que estar vacio")
    @Pattern(
            regexp = ".*(@gmail\\.com|@outlook\\.com|@yahoo\\.com|@kinal\\.edu\\.gt)$",
            message = "El correo tiene que terminar en @gmail.com, @outlook.com, @yahoo.com o @kinal.edu.gt"
    )
    @Column(name = "email", unique = true)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
