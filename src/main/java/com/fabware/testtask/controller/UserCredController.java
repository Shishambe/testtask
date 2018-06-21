package com.fabware.testtask.controller;

import com.fabware.testtask.entity.User;
import com.fabware.testtask.entity.UserCred;
import com.fabware.testtask.service.UserCredService;
import com.fabware.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user/password")
public class UserCredController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredService userCredService;

    @RequestMapping(value = "/create", method = POST)
    public ResponseEntity create(@RequestParam String userEmail,
                                 @RequestParam String userPassword,
                                 @RequestParam String name,
                                 @RequestParam String login,
                                 @RequestParam String password,
                                 @RequestParam(value = "tag", required = false) Set<String> tags,
                                 HttpSession session) {
        logger.info("create userCred(" + name + ") for user(" + userEmail + ")request received");

        User user = userService.findUser(userEmail, userPassword);
        if (isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (nonNull(userCredService.create(user, name, login, password, tags))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @RequestMapping(value = "/update", method = PATCH)
    public ResponseEntity update(@RequestParam String userEmail,
                                 @RequestParam String userPassword,
                                 @RequestParam String name,
                                 @RequestParam String login,
                                 @RequestParam String password,
                                 @RequestParam(value = "tag", required = false) Set<String> tags,
                                 HttpSession session) {
        logger.info("update userCred(" + name + ") for user(" + userEmail + ")request received");

        User user = userService.findUser(userEmail, userPassword);
        if (isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (nonNull(userCredService.update(user, name, login, password, tags))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity delete(@RequestParam String userEmail,
                                 @RequestParam String userPassword,
                                 @RequestParam String name) {
        logger.info("delete userCred(" + name + ") for user(" + userEmail + ")request received");

        User user = userService.findUser(userEmail, userPassword);
        if (isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        userCredService.delete(user, name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @RequestMapping(value = "/list", method = POST)
    public ResponseEntity delete(@RequestParam String userEmail,
                                 @RequestParam String userPassword) {
        logger.info("list userCred for user(" + userEmail + ")request received");

        User user = userService.findUser(userEmail, userPassword);
        if (isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Set<UserCred> userCreds = user.getCreds();
        if (userCreds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userCreds);
    }
}
