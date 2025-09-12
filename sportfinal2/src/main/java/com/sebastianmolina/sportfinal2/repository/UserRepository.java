package com.sebastianmolina.sportfinal2.repository;

import com.sebastianmolina.sportfinal2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // estos booleanos miran si ya hay un registro con esos datos al ingresar
    boolean existsByEmail(String email);
    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);

    // estos son para actualizar, pues buscan si otro id ya tiene ese dato
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsByFirstNameAndIdNot(String firstName, Integer id);
    boolean existsByLastNameAndIdNot(String lastName, Integer id);
}
