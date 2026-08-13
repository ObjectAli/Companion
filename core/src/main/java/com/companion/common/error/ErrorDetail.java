package com.companion.common.error;

import lombok.Getter;

@Getter
public class ErrorDetail {
    private final String code;
    private final String attribute;
    private final Object value;
    private final String messageRu;
    private final String messageEn;
    private final String remediationRu;
    private final String remediationEn;

    private ErrorDetail(Builder builder) {
        this.code = builder.code.getCode();
        this.attribute = builder.attribute;
        this.value = builder.value;
        this.messageRu = String.format(builder.code.getMessageRuTemplate(), builder.args);
        this.messageEn = String.format(builder.code.getMessageEnTemplate(), builder.args);
        this.remediationRu = String.format(builder.code.getRemediationRuTemplate(), builder.args);
        this.remediationEn = String.format(builder.code.getRemediationEnTemplate(), builder.args);
    }

    public static Builder builder(ErrorCode code) {
        return new Builder(code);
    }

    public static class Builder {
        private final ErrorCode code;
        private String attribute;
        private Object value;
        private Object[] args;

        public Builder(ErrorCode code) {
            this.code = code;
        }

        public Builder attribute(String attribute) {
            this.attribute = attribute;
            return this;
        }

        public Builder value(Object value) {
            this.value = value;
            return this;
        }

        public Builder args(Object... args) {
            this.args = args;
            return this;
        }

        public ErrorDetail build() {
            return new ErrorDetail(this);
        }
    }
}
