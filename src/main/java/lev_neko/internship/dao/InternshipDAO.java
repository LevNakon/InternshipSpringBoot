package lev_neko.internship.dao;

import lev_neko.internship.models.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipDAO extends JpaRepository<Internship,Integer> {
}
