package com.cf.community.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户详细信息
 */
@Entity
@Table(name = "user_detail")
@Data
public class UserDetail {

    @Id
    private Long id;

    private String eMail;

    private String industry;

    private String position;

    private Date birthday;
}
