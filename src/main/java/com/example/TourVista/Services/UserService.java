package com.example.TourVista.Services;

import com.example.TourVista.Models.User;
import com.example.TourVista.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    };

    public void addNewUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, User userUpdated) {
        User userExists = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(("User with id: "+ userId + " does not exists")));

        String updatedEmail = userUpdated.getEmail();

        userExists.setFirstName(userUpdated.getFirstName());
        userExists.setLastName(userUpdated.getLastName());
        userExists.setPassword(userUpdated.getPassword());
        userExists.setMobileNo(userUpdated.getMobileNo());

        if(updatedEmail != null && !updatedEmail.isEmpty() && !Objects.equals(userExists.getEmail(), updatedEmail)){
            Optional<User> userOptional = userRepository.findUserByEmail(updatedEmail);
            if(userOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            userExists.setEmail(updatedEmail);
        }

        userRepository.save(userExists);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalStateException(("User with id : "+userId + " does notexists"));
        }
        userRepository.deleteById(userId);
    }
}
