// ContributorRepository.java
package com.observatorio.backend_ia.repository;

import com.observatorio.backend_ia.model.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {
}