package com.blockattend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blockattend.backend.dto.SubjectRequest;
import com.blockattend.backend.dto.SubjectResponse;
import com.blockattend.backend.entity.Subject;
import com.blockattend.backend.entity.Teacher;
import com.blockattend.backend.repository.SubjectRepository;
import com.blockattend.backend.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public SubjectResponse createSubject(SubjectRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + request.getTeacherId()));

        Subject subject = Subject.builder()
                .name(request.getName())
                .code(request.getCode())
                .teacher(teacher)
                .build();

        Subject saved = subjectRepository.save(subject);
        return toResponse(saved);
    }

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new IllegalArgumentException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }

    private SubjectResponse toResponse(Subject subject) {
        return SubjectResponse.builder()
                .id(subject.getId())
                .name(subject.getName())
                .code(subject.getCode())
                .teacherId(subject.getTeacher().getId())
                .teacherName(subject.getTeacher().getUser().getFullName())
                .build();
    }
}