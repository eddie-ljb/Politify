package de.ebader.Politify.repositories.benutzerdaten;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.ebader.Politify.entities.benutzerdaten.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT user FROM User user WHERE user.benutzername = :username")
	Optional<User> findByUsername(@Param("username") String username);
	
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.benutzername = :benutzername")
    Boolean existsByUsername(@Param("benutzername") String username);
	
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
    Boolean existsByEmail(@Param("email") String email);

}
