package dev.yassiraitelghari.hunterleague.repository.v1;

import dev.yassiraitelghari.hunterleague.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParticipationRepository  extends JpaRepository<Participation , UUID> {
}
