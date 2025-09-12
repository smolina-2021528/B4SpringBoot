package com.sebastianmolina.sportfinal2.service;


import com.sebastianmolina.sportfinal2.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    User saveUser(User user);
    User updateUser(Integer id, User user);
    void deleteUser(Integer id);





}
