package project.by.skillintern.services;

import project.by.skillintern.entities.User;
import project.by.skillintern.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface ProfileService {
    Optional<User> getProfile(String username);
    void updateProfile(User user) throws UserAlreadyExistsException;
}
