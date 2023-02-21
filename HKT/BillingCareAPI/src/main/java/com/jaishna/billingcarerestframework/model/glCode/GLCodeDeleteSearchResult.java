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
public class GLCodeDeleteSearchResult {

    private String createdDate;
    private String modifiedDate;
    private String glid;
    private String description;
    private String attribute;
    private String glARAccount;
    private String glOffSetAccount;
    private String status;
}
