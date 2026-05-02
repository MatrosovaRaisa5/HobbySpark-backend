package com.hobbyspark.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.dto.InviteResponse;

@RestController
@RequestMapping("/api/invite")
public class InviteController {

    @Value("${app.invite-text}")
    private String textTemplate;

    @Value("${app.invite-link}")
    private String link;

    @GetMapping
    public InviteResponse getInvite() {
        String text = textTemplate.replace("{}", link);
        return new InviteResponse(text, link);
    }
}