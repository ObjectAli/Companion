package com.companion.contracts.validators;

import com.companion.common.error.ErrorDetail;

import java.util.List;

public interface DatabaseQueryValidator {

    List<ErrorDetail> validate();
}
