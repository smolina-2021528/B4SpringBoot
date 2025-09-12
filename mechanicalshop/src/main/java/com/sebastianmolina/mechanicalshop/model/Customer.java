package com.sebastianmolina.mechanicalshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no tiene que estar vacio")
    @Column(name = "full_name", unique = true)
    private String fullName;

    @NotBlank(message = "El teléfono no tiene que estar vacio")
    @Column(name = "phone", unique = true)
    private String phone;

    @NotBlank(message = "El correo no tiene que estar vacio")
    @Pattern(
            regexp = ".*(@gmail\\.com|@outlook\\.com|@yahoo\\.com|@kinal\\.edu\\.gt)$",
            message = "El correo debe terminar en @gmail.com, @outlook.com, @yahoo.com o @kinal.edu.gt"
    )
    @Column(name = "email", unique = true)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
