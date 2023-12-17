package edu.adrobek.msclient.controller;

import edu.adrobek.msclient.repo.User;
import edu.adrobek.msclient.repo.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
        
    @Autowired
    private UserService userService;

    /**
     *
     * @return zwracana jest lista uzytkownikow
     */
    @RequestMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    /**
     * @param user
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/addUser")
    String AddUser(@ModelAttribute("user") User user, @RequestParam("name") String name, @RequestParam("pass") String password) {
        user.setName(name);
        user.setPassword(password);
        userService.save(user);
        return "Ok";
    }

    @RequestMapping("/addUserWithType")
    public ResponseEntity<User> createUser(@RequestParam("name") String name, @RequestParam("pass") String password, @RequestParam("type") String type) {
        User user = new User(name,password);
        userService.save(user);
        UserSettings usersettings = new UserSettings(userService.findUserByName(name),"user_role", type, user);
        userService.save(usersettings);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @RequestMapping("/editUserByName")
    String EditUser(@ModelAttribute("user") User user, @RequestParam("name") String name, @RequestParam("pass") String password, @RequestParam("oldname") String oldname, @RequestParam("oldpass") String oldpass) {
        user.setName(oldname);
        user.setPassword(oldpass);
        userService.findUserByName(oldname);
        userService.deleteById(userService.findUserByName(oldname));
        logger.info("User modified!");
        user.setName(name);
        user.setPassword(password);
        userService.save(user);
        return "Ok";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public ResponseEntity<User> getUserById(@RequestParam("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public int checkUser(@RequestParam("name") String name, @RequestParam("pass") String pass) {
        return userService.successfulLogin(name, pass);
    }
}