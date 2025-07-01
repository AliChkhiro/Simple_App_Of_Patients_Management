package ma.enset.hospitalapp.secutity.repo;

import ma.enset.hospitalapp.secutity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
