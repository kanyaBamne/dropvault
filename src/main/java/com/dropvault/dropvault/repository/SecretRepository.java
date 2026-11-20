package com.dropvault.dropvault.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dropvault.dropvault.entity.SecretDrop;

@Repository 
public interface SecretRepository extends JpaRepository<SecretDrop, UUID>{
   
} 
