package com.blockattend.backend.dto;

import com.blockattend.backend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
