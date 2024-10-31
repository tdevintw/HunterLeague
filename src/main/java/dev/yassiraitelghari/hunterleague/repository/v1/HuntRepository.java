package dev.yassiraitelghari.hunterleague.repository.v1;

import dev.yassiraitelghari.hunterleague.domain.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HuntRepository extends JpaRepository<Hunt , UUID> {
}
