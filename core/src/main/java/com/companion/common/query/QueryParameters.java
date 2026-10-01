package com.companion.common.query;

import com.companion.contracts.validators.DatabaseQueryValidator;
import java.util.Collections;
import com.companion.common.error.ErrorDetail;
import lombok.Data;
import org.jooq.Condition;

import java.util.List;


@Data
public class QueryParameters implements DatabaseQueryValidator {

    List<Condition> conditions;
    Integer limit;
    String offset;
    String sortBy;
    String sortDirection;

    protected QueryParameters(Builder builder) {
        this.conditions = builder.conditions;
        this.sortBy = builder.sortBy;
        this.sortDirection = builder.sortDirection;
        this.offset = builder.offset;
        this.limit = builder.limit;
    }

    public static class Builder {
        private List<Condition> conditions;
        private String sortBy;
        private String sortDirection;
        private String offset;
        private Integer limit;



        public Builder conditions(List<Condition> conditions){
            this.conditions = conditions;
            return this;
        }

        public Builder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder sortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(String offset) {
            this.offset = offset;
            return this;
        }

        public QueryParameters build() {
            return new QueryParameters(this);
        }
    }

    @Override
    public List<ErrorDetail> validate() {
        return Collections.emptyList();
    }
}