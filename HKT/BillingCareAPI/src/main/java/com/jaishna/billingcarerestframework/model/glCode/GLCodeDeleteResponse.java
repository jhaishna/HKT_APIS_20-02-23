package com.jaishna.billingcarerestframework.model.glCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties
public class GLCodeDeleteResponse {

    private String errorCode;
    private String errorDescription;
    private String requestStatus;
    private String glSegment;
    private List<GLCodeDeleteSearchResult> results = new ArrayList<>();
}
