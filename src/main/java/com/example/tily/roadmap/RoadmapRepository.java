package com.example.tily.roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    @Query("select r from Roadmap r where r.creator.id =:userId and r.category=:category")
    List<Roadmap> findByUserId(@Param("userId") Long userId, @Param("category") Category category);

    @Query("select r from Roadmap r where r.category=:category and (:name is null or r.name=:name) and (r.isPublic=true or r.isPublic is null) and (r.isRecruit=true or r.isRecruit is null)")
    Slice<Roadmap> findAllByOrderByCreatedDateDesc(@Param("category") Category category, @Param("name") String name, Pageable pageable);
}
