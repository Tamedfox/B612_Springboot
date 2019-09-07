package com.cf.community.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 热门标签数据传输层，实现自然升序
 */

@Data
public class HotTagDTO implements Comparable , Serializable {

    private Long id;

    private Integer priority;

    //自然升序
    @Override
    public int compareTo(Object o) {
        return this.priority - ((HotTagDTO) o).getPriority();
    }

    public HotTagDTO() {
    }

    public HotTagDTO(Long id, Integer priority) {
        this.id = id;
        this.priority = priority;
    }
}
