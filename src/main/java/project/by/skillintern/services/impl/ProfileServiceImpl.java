package project.by.skillintern.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.by.skillintern.entities.User;
import project.by.skillintern.exceptions.UserAlreadyExistsException;
import project.by.skillintern.repositories.UserRepository;
import project.by.skillintern.services.ProfileService;
import project.by.skillintern.services.UserService;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public Optional<User> getProfile(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateProfile(User user) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("A user with that username already exists");
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void uploadResume(String fileUrl) {
        User user = userService.getUserByUsername(userService.getCurrentUser().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        user.setResume(fileUrl);
        userRepository.save(user);
    }
}
