package dev.yassiraitelghari.hunterleague.repository;

import dev.yassiraitelghari.hunterleague.domain.Competition;
import dev.yassiraitelghari.hunterleague.domain.enums.SpeciesType;
import dev.yassiraitelghari.hunterleague.dto.CompetitionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CompetitionRepository extends JpaRepository<Competition , UUID> {
    @Query(value = "SELECT * FROM Competition c ORDER BY c.date  ASC" , nativeQuery = true)
    List<Competition> findAllOrderByDateAsc(Pageable pageable);

    @Query(value = "SELECT * FROM Competition c ORDER BY c.date  DESC " , nativeQuery = true)
    List<Competition> findAllOrderByDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM Competition  c WHERE c.code = :code" , nativeQuery = true)
    Competition findByCode(String code);

    Competition findAllBySpeciesType(SpeciesType speciesType);

    Competition findAllByOpenRegistration(boolean openRegistration);


}
