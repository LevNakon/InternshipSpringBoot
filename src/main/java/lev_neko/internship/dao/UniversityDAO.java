package lev_neko.internship.dao;

import lev_neko.internship.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityDAO extends JpaRepository<University,Integer> {
}
