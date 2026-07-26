// repository/ResourceRepository.java
package com.observatorio.backend_ia.repository;

import com.observatorio.backend_ia.model.Resource;
import com.observatorio.backend_ia.model.enums.ResourceTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByType(String type);
    List<Resource> findAllByOrderByCreatedAtDesc();
    Optional<Resource> findByTopicAndFeaturedTrue(ResourceTopic topic);
    List<Resource> findByFeaturedTrue();

    @Query("SELECT r.topic, COUNT(r) FROM Resource r GROUP BY r.topic")
    List<Object[]> countByTopic();
}