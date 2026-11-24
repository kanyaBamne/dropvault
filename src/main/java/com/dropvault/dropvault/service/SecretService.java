package com.dropvault.dropvault.service;

import java.time.Instant;
import org.springframework.stereotype.Service;

import com.dropvault.dropvault.dto.SecretContentResponse;
import com.dropvault.dropvault.entity.SecretDrop;
import com.dropvault.dropvault.repository.SecretRepository;
import lombok.RequiredArgsConstructor;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service 
@RequiredArgsConstructor 
public class SecretService {
    
    private final SecretRepository secretRepository;

    public SecretDrop createSecret(String content, Integer expiresInMinutes, Integer maxViews){
        int validMinutes = (expiresInMinutes != null && expiresInMinutes > 0) ? expiresInMinutes : 60;
        int validMaxViews = (maxViews != null && maxViews > 0) ? maxViews : 1;

        SecretDrop secretDrop = SecretDrop.builder()
        .content(content)
        .expiresAt(Instant.now().plus(validMinutes, ChronoUnit.MINUTES))
        .maxViews(validMaxViews)
        .build();

        return secretRepository.save(secretDrop);
    }

    @Transactional(noRollbackFor = ResponseStatusException.class)
    public SecretContentResponse getAndBurnSecret(UUID id){
        SecretDrop drop = secretRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secret not found or already burned"));

        if(Instant.now().isAfter(drop.getExpiresAt())){
            secretRepository.delete(drop);
            throw new ResponseStatusException(HttpStatus.GONE, "Secret has expired and been deleted");
        }

        int remainingViews = drop.getMaxViews() - 1;
        String secretContent = drop.getContent();
        Instant expiresAt = drop.getExpiresAt();

        if (remainingViews <= 0){
            secretRepository.delete(drop);
        } else {
            drop.setMaxViews(remainingViews);
        }

        return new SecretContentResponse(id, secretContent, expiresAt, remainingViews);
    }
}
