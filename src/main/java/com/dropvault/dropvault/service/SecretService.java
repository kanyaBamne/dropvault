package com.dropvault.dropvault.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import com.dropvault.dropvault.entity.SecretDrop;
import com.dropvault.dropvault.repository.SecretRepository;
import lombok.RequiredArgsConstructor;
import java.time.temporal.ChronoUnit;

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
}
