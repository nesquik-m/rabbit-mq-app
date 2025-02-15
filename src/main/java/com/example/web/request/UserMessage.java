package com.example.web.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage {

    private String username;

    private String message;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

}