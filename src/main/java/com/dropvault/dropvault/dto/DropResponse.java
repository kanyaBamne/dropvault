package com.dropvault.dropvault.dto;

import java.time.Instant;
import java.util.UUID;
import com.dropvault.dropvault.entity.SecretDrop;

public record DropResponse(UUID id, Instant createdAt, Instant expiresAt, Integer maxViews) {
    public static DropResponse fromEntity(SecretDrop drop) {
        return new DropResponse(drop.getId(), drop.getCreatedAt(), drop.getExpiresAt(),
                drop.getMaxViews());
    }
}