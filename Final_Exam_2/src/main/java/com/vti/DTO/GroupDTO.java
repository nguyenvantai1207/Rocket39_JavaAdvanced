package com.vti.DTO;

import com.vti.entity.Account;
import lombok.Data;

import java.util.Date;

@Data
public class GroupDTO {
    private Integer id;
    private String groupName;
    private Integer quantity;
    private Date createDate;
    private String creatorFullName;
}
