package dev.yassiraitelghari.hunterleague.repository.v1;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CompetitionRepository extends JpaRepository<Competition , UUID> {
}
