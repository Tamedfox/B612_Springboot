package com.cf.community.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotQuestionDTO implements Comparable, Serializable {

    private Long id;

    private Integer priority;

    public HotQuestionDTO() {
    }

    public HotQuestionDTO(Long id, Integer priority) {
        this.id = id;
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        return this.priority - ((HotQuestionDTO) o).getPriority();
    }
}
