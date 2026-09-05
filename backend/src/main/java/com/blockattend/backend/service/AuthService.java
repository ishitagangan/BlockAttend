package com.blockattend.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blockattend.backend.dto.AuthResponse;
import com.blockattend.backend.dto.LoginRequest;
import com.blockattend.backend.dto.RegisterRequest;
import com.blockattend.backend.entity.Role;
import com.blockattend.backend.entity.Student;
import com.blockattend.backend.entity.Teacher;
import com.blockattend.backend.entity.User;
import com.blockattend.backend.repository.StudentRepository;
import com.blockattend.backend.repository.TeacherRepository;
import com.blockattend.backend.repository.UserRepository;
import com.blockattend.backend.security.CustomUserDetails;
import com.blockattend.backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        if (request.getRole() == Role.STUDENT) {
            Student student = Student.builder()
                    .user(savedUser)
                    .enrollmentNumber(request.getEnrollmentNumber())
                    .branch(request.getBranch())
                    .semester(request.getSemester())
                    .build();
            studentRepository.save(student);

        } else if (request.getRole() == Role.TEACHER) {
            Teacher teacher = Teacher.builder()
                    .user(savedUser)
                    .department(request.getDepartment())
                    .build();
            teacherRepository.save(teacher);
        }

        String token = jwtUtil.generateToken(new CustomUserDetails(savedUser));

        return AuthResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtUtil.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}