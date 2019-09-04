package com.cf.community.model.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页实体类
 * @param <T>
 */
@Data
public class PageResult<T> {

    private Long total;
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }


}
