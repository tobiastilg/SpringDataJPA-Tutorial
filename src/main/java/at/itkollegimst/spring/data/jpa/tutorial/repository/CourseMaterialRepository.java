package at.itkollegimst.spring.data.jpa.tutorial.repository;

import at.itkollegimst.spring.data.jpa.tutorial.entity.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
}