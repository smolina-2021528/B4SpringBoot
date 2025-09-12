package com.sebastianmolina.sportfinal2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

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
            regexp = ".*(@gmail\\.com|@kinal\\.edu\\.gt|@yahoo\\.com|@outlook\\.com)$", // aqui miro el final del correo, pudiendo solo ser las opciones que yo deje
            message = "El correo debe terminar en @gmail.com, @kinal.edu.gt, @yahoo.com o @outlook.com"
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
