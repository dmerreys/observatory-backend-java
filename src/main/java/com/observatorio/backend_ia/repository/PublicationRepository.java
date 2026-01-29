// PublicationRepository.java
package com.observatorio.backend_ia.repository;

import com.observatorio.backend_ia.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}