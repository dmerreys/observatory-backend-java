// repository/ResourceRepository.java
package com.observatorio.backend_ia.repository;

import com.observatorio.backend_ia.model.Resource;
import com.observatorio.backend_ia.model.enums.ResourceTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByType(String type);
    List<Resource> findAllByOrderByCreatedAtDesc();
    Optional<Resource> findByTopicAndFeaturedTrue(ResourceTopic topic);
    List<Resource> findByFeaturedTrue();

    @Query("SELECT r.topic, COUNT(r) FROM Resource r GROUP BY r.topic")
    List<Object[]> countByTopic();

    @Query(value = """
            SELECT r FROM Resource r
            WHERE (:topic IS NULL OR r.topic = :topic)
              AND (:type IS NULL OR r.type = :type)
              AND (:featured IS NULL OR r.featured = :featured)
              AND (:pattern IS NULL
                   OR LOWER(r.title) LIKE :pattern
                   OR LOWER(r.description) LIKE :pattern
                   OR LOWER(r.source) LIKE :pattern)
            """,
            countQuery = """
            SELECT COUNT(r) FROM Resource r
            WHERE (:topic IS NULL OR r.topic = :topic)
              AND (:type IS NULL OR r.type = :type)
              AND (:featured IS NULL OR r.featured = :featured)
              AND (:pattern IS NULL
                   OR LOWER(r.title) LIKE :pattern
                   OR LOWER(r.description) LIKE :pattern
                   OR LOWER(r.source) LIKE :pattern)
            """)
    Page<Resource> searchAdmin(@Param("topic") ResourceTopic topic,
                               @Param("type") String type,
                               @Param("featured") Boolean featured,
                               @Param("pattern") String pattern,
                               Pageable pageable);
}