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
public class GlDeleteInput {
    private String glSegment;
    private String createdDate;
    private String modifiedDate;
    private String description;
    private String glId;
    private String attribute;
    private String creditWorksOrder;
    private String creditCostCenterCode;
    private String creditGLAccountCode;
    private String debitWorksOrder;
    private String debitCostCenterCode;
    private String debitGLAccountCode;
    private String type;
}
