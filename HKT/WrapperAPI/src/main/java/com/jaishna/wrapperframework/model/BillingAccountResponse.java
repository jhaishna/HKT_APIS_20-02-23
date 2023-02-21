package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jaishna.wrapperframework.model.errorhandling.ErrorResponse;
import com.portal.pcm.FList;

@JsonIgnoreProperties
public class BillingAccountResponse {

    private FList result;
    private ErrorResponse errorDetails;

    public ErrorResponse getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(ErrorResponse errorDetails) {
        this.errorDetails = errorDetails;
    }

    public FList getResult() {
        return result;
    }

    public void setResult(FList result) {
        this.result = result;
    }
}
