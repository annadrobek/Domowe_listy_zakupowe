package edu.adrobek.msclient.controller;

import edu.adrobek.msclient.repo.User;
import edu.adrobek.msclient.repo.UserRepo;
import edu.adrobek.msclient.repo.UserSettings;
import edu.adrobek.msclient.repo.UserSettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private UserSettingsRepo userSettingsRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }
    
    public User update(User user) {
        userRepository.delete(user);
        User newUser = userRepository.save(user);
        return newUser;
    }
    
    public void delete(User user) {
        userRepository.delete(user);
        logger.info("User "+ user.toString() +" deleted");
    }
    
    public void deleteById(int id) {
        userRepository.deleteById(id);
        logger.info("User "+ id + "["+userRepository.findById(id)+"] deleted");
    }

    public UserSettings save(UserSettings usersettings) {
        UserSettings newUserSettings = userSettingsRepository.save(usersettings);
        return newUserSettings;
    }

    /*
    * int status 0 - failed, 1 - standard user, 2 admin user
     */
    public int successfulLogin(String name, String pass) {
        int status = 0;
        User user = userRepository.findByName(name);
        Optional<UserSettings> userSettings = userSettingsRepository.findById(user.getId());
        if (pass.equals(user.getPassword())) {
            status=1;
            /*if (userSettings.get().getValue().equals("admin")) {
                status = 2;
            } else {
                status = 1;
            }*/
        }
        return status;
    }

    public int findUserByName(String name) {
        int id = Integer.MAX_VALUE;
        User newUser = userRepository.findByName(name);
        id = newUser.getId();
        if (id <= Integer.MAX_VALUE) {
            return id;
        }
        System.out.println("ID = " + id);
        return id;
    }
}
