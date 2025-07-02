package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthService {

    @GetMapping("/approved-senders")
    public List<String> getApprovedSenders() {
        return List.of(
                "c-cell@lnmiit.ac.in",
                "c-cell.students@lnmiit.ac.in",
                "24ucs202@lnmiit.ac.in", //Praneel Dev
                "23ucc506@lnmiit.ac.in", //Aditya Kansal
                "22ucc023@lnmiit.ac.in", //Arpit Joshi
                "23ucc525@lnmiit.ac.in",//Atharv Shah
                "22uec053@lnmiit.ac.in",//Harshita Sharma
                "23uec563@lnmiit.ac.in", //Kunal Sharma
                "23ume529@lnmiit.ac.in", //Lakshita Sharma
                "23ucc623@lnmiit.ac.in", //Mudit Choudhary
                "22ucc065@lnmiit.ac.in", //Naman Agarwal
                "23ucs686@lnmiit.ac.in", //Rahul Sharma
                "23uec642@lnmiit.ac.in", //Vibha Gupta
                "23ucc619@lnmiit.ac.in", //Yash Raj
                "23ucc577@lnmiit.ac.in", //Neha Raniwala
                "24UCC192@lnmiit.ac.in", //Armaan Jain
                "24DCS029@lnmiit.ac.in", //Ishita Agarwal
                "24uec163@lnmiit.ac.in", //Nikhila S Hari
                "24ucs120@lnmiit.ac.in" //Panth Moradiya
        );
    }

    public boolean isAuthorized(String email) {
        return getApprovedSenders().contains(email);
    }
}

