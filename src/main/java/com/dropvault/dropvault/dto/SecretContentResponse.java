package com.dropvault.dropvault.dto;

import java.time.Instant;
import  java.util.UUID;

public record SecretContentResponse(UUID id, String content, Instant expiresAt, Integer remainingViews) {

}
