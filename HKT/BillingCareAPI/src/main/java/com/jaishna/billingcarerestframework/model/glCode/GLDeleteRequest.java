package com.jaishna.billingcarerestframework.model.glCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties
public class GLDeleteRequest {

    private List<GlDeleteInput> glDeleteInputs;

}
