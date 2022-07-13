package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void removeUser(Long id);
    User getUser(Long id);
    User editUser(long id, User updated);
    List<User> listUsers();
}
