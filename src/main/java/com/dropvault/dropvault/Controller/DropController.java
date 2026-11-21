package com.dropvault.dropvault.Controller;

import com.dropvault.dropvault.dto.CreateDropRequest;
import com.dropvault.dropvault.dto.DropResponse;
import com.dropvault.dropvault.entity.SecretDrop;
import com.dropvault.dropvault.service.SecretService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/drops")
@RequiredArgsConstructor 
public class DropController {

    private final SecretService SecretService;

    @PostMapping
    public ResponseEntity<DropResponse> createdDrop(@RequestBody CreateDropRequest request){
        SecretDrop savedDrop = SecretService.createSecret(
            request.content(),
            request.expiresInMinutes(),
            request.maxViews()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(DropResponse.fromEntity(savedDrop));
    }
    
}
