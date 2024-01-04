package com.vti.filter;

import lombok.Data;

import java.util.Date;

@Data
public class GroupFilter {

    private int minQuantity;

    private int maxQuantity;

    private int quantity;
}
