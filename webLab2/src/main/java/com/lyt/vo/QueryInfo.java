package com.lyt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QueryInfo {
    private int pageNum;
    private int pageSize;

    private String id;
}
