package tn.esprit.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.library.entities.User;

import tn.esprit.library.repository.IUserRepository;

import tn.esprit.library.repository.IUserRepository;

import tn.esprit.library.services.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static tn.esprit.library.entities.Status.APPROVED;


import java.util.Random;

import static tn.esprit.library.entities.Status.APPROVED;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*") // Allow requests from Angular app
public class UserController {
    IUserService userService;
    IUserRepository userRepository;
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
    @GetMapping("/getall")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @PostMapping("/login/{email}/{pass}")
    public String login(@PathVariable String email, @PathVariable String pass) {
        User u = userService.checkProfile(email,pass);
        if (u==null)
            return "Verifier votre données";
        return generateToken(u.getId_user());
    }
    private String generateToken(Long userId) {
        User u=userService.getUserById(userId);
        long timestamp = System.currentTimeMillis();
        String key = "1a2fac207899e0a0e16d01c428feedcbc2b93513f8f8576d93ddf5411582c3df";  // Use a secure key in real scenarios

        return Jwts.builder().signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + 900000))
                .claim("userId", userId.toString())
                .claim("role",u.getType())
                .compact();
    }
    @PutMapping("/accept/{id}")
   public User acceptUser(@PathVariable("id") Long id){
        User u=userService.getUserById(id);
        u.setState(APPROVED);
        String subject="Your E-Library Account is Now Active - Welcome Aboard!";
        String text="Dear "+u.getFirstname()+" "+u.getLastname()+","
                +"\n"
                +"We are thrilled to inform you that your E-Library account is now active! You can now log in and start exploring our vast collection of resources.\n" +
                "\n" +
                "Feel free to navigate through our website and discover a wide array of PFE books, articles, and other valuable materials designed to support your academic journey. Whether you're researching for a project or just curious, E-Library has something for everyone.\n" +
                "\n" +
                "We’re excited to have you with us, and we can’t wait for you to explore all that E-Library has to offer. Should you need any assistance, our support team is just a click away.\n" +
                "\n" +
                "Happy exploring!\n" +
                "\n" +
                "Best regards,\n" +
                "The E-Library Team";
        userService.sendSimpleMessage(u.getMail(),subject,text);
        return userService.updateUser(u);
    }
    @PostMapping("/forgotpassword/{email}")
    public String forgotPassword(@PathVariable("email") String email) {
        User user = userRepository.findByMail(email);
        if (user == null) {
            return "User not found";
        }
        String tempPassword="";
        while (tempPassword.length()!=8){
            Random random = new Random();
            int randomValue;
            while (true) {
                randomValue = random.nextInt(123); // Generates a value between 0 and 122
                if ((randomValue >= 48 && randomValue <= 57) ||
                        (randomValue >= 65 && randomValue <= 90) ||
                        (randomValue >= 97 && randomValue <= 122)) {
                    break; // Exit loop if the value is within one of the desired ranges
                }
            }
            char character = (char)randomValue;
            tempPassword=tempPassword+character;
        }
        String subject="Password Reset Request";
        String text="Your temporary password is: " + tempPassword;
        user.setPassword(tempPassword);
        userRepository.save(user);
        userService.sendSimpleMessage(user.getMail(),subject,text);
        return "Temporary password sent to your email.";
    }
}
