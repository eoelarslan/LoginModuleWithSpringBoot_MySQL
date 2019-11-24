package com.login.module.controller;


import com.login.module.controller.dto.base.GenericResponseDTO;
import com.login.module.controller.dto.requestdto.RegisterRequestDTO;
import com.login.module.exception.ResourceNotFoundException;
import com.login.module.model.User;
import com.login.module.repository.UserRepository;
import com.login.module.util.helper.MessageHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.login.module.util.enums.MessageStatus.*;


@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageHelper messageHelper;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                messageHelper.getMessageByMessageStatus(DATA_RETRIEVED, null), userRepository.findAll()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                messageHelper.getMessageByMessageStatus(DATA_RETRIEVED, null),userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId))));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id") Long userId,
                           @Valid @RequestBody RegisterRequestDTO userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        modelMapper.map(userDetails,user);

        return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                messageHelper.getMessageByMessageStatus(USER_UPDATED, null), userRepository.save(user)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().body(new GenericResponseDTO<>(HttpStatus.ACCEPTED,
                messageHelper.getMessageByMessageStatus(DELETED, null), user));
    }
}
