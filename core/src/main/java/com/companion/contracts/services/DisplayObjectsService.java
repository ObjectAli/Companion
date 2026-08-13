package com.companion.contracts.services;

import com.companion.common.error.ErrorDetail;
import com.companion.common.query.QueryResult;
import com.companion.common.result.Result;

import java.util.List;

/**
 * ...
 * @param <R> - Display record class
 * @param <S> - Specification class
 */
public interface DisplayObjectsService<R, S> {

    Result<QueryResult<R>, List<ErrorDetail>> execute(S querySpecification);
}
