package com.sebastianmolina.sportfinal2.service;

import com.sebastianmolina.sportfinal2.model.User;
import com.sebastianmolina.sportfinal2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        // Validaciones de unicidad antes de persistir
        if (userRepository.existsByEmail(user.getEmail())) {
            String msg = "email: El correo ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (userRepository.existsByFirstName(user.getFirstName())) {
            String msg = "firstName: El nombre ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (userRepository.existsByLastName(user.getLastName())) {
            String msg = "lastName: El apellido ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            // Validaciones de unicidad que excluyen el registro actual
            if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
                String msg = "email: El correo ya está registrado por otro usuario";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (userRepository.existsByFirstNameAndIdNot(user.getFirstName(), id)) {
                String msg = "firstName: El nombre ya está registrado por otro usuario";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (userRepository.existsByLastNameAndIdNot(user.getLastName(), id)) {
                String msg = "lastName: El apellido ya está registrado por otro usuario";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
