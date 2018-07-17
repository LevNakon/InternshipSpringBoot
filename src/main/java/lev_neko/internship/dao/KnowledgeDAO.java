package lev_neko.internship.dao;

import lev_neko.internship.models.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeDAO extends JpaRepository<Knowledge,Integer> {
}
