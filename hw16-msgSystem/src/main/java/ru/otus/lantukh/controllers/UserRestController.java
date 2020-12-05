package ru.otus.lantukh.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import ru.otus.lantukh.front.FrontendService;
import ru.otus.lantukh.dto.UserDto;

@RestController
public class UserRestController {
    private final FrontendService frontendService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public UserRestController(FrontendService frontendService, SimpMessagingTemplate simpMessagingTemplate) {
        this.frontendService = frontendService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @SendTo("/topic/response")
    private void getUserResult(UserDto user) {
        simpMessagingTemplate.convertAndSend("/topic/response", user);
    }

    @MessageMapping("/user")
    public void saveUser(@ModelAttribute UserDto userDto) {
        frontendService.saveUserData(userDto, this::getUserResult);
    }
}
