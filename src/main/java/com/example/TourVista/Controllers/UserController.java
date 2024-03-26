package com.example.TourVista.Controllers;

import com.example.TourVista.Models.Contract;
import com.example.TourVista.Models.User;
import com.example.TourVista.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void addNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user
    ){
        userService.updateUser(userId, user);
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(
            @PathVariable("userId") Long userId
    ){
        userService.deleteUser(userId);
    }
}
