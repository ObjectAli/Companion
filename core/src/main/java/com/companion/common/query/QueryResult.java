package com.companion.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryResult<D> {

    List<D> dto;
    Integer count;
    Integer totalCount;
    String offset;
    String sortBy;
    String sortDirection;
}
