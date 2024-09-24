package tn.esprit.library.services;

import tn.esprit.library.entities.User;

import java.util.List;

public interface IUserService {
    public User addUser(User user);
    public User getUserById(Long id);
    public List<User> getAllUser();
    public User updateUser(User user);
    public void deleteUser(Long id);
    public User checkProfile(String mail,String pass);


    public void sendSimpleMessage(String to, String subject, String text);

}
