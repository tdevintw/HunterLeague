package dev.yassiraitelghari.hunterleague.repository;

import dev.yassiraitelghari.hunterleague.domain.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt , UUID> {
}
