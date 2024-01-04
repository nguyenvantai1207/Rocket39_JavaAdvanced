package com.vti.request;

import lombok.Data;

import java.util.Date;

@Data
public class GroupRequest {
    private Integer id;
    private String groupName;
    private Integer quantity;
    private Date createDate;
    private Integer creatorId;
}
