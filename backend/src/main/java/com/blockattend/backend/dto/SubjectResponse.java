package com.blockattend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubjectResponse {

    private Long id;
    private String name;
    private String code;
    private Long teacherId;
    private String teacherName;
}