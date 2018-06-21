package com.fabware.testtask.controller;

import com.fabware.testtask.entity.UserCred;
import com.fabware.testtask.service.CredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/password")
public class CredController {
    private static final Logger logger = Logger.getLogger(CredController.class.getName());

    @Autowired
    private CredService credService;

    @RequestMapping(value = "/list", method = GET)
    public ResponseEntity delete(HttpSession session) {
        logger.info("list userCred request received");

        List<UserCred> creds = credService.getList();
        if (creds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(creds);
    }

    @RequestMapping(value = "/search", method = POST)
    public ResponseEntity search(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "inctag") Set<String> incTags,
                                 @RequestParam(value = "exctag") Set<String> excTags,
                                 HttpSession session) {
        logger.info("search userCred request received. name(" + name +") incTags(" + incTags + ") excTags(" + excTags + ")");

        if (name.isEmpty() && incTags.isEmpty() && excTags.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<UserCred> creds = credService.getListByNameAndTags(name, incTags, excTags);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(creds);
    }
}
