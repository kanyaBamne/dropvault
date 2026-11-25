package com.dropvault.dropvault.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateDropRequest( 
    @NotBlank(message = "Secret content must not be blank")
    String content,

    @Positive(message = "Expiration minutes must be greater than 0")
    Integer expiresInMinutes,

    @Positive(message = "Max views must be greater than 0")
    Integer maxViews

) {

}
