package com.jaishna.billingcarerestframework.model.glCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties
public class GLDeleteResponse {

    private String errorCode;
    private String errorDescription;
    private String requestStatus;
    private String deleteStatus;
}
