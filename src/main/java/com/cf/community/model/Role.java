package com.cf.community.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色实体类
 */
@Entity
@Data
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private String description;

}
