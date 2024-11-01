package dev.yassiraitelghari.hunterleague.repository;

import dev.yassiraitelghari.hunterleague.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByUsername(String username);

    public List<User> findByFirstNameLike(String firstName) ;

    public Optional<User> findByEmail(String email);

}
