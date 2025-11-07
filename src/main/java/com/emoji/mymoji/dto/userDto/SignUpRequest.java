package com.emoji.mymoji.dto.userDto;

public record SignUpRequest(
        String email,
        String password,
        String nickname
) {}
