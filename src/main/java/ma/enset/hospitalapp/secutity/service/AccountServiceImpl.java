package ma.enset.hospitalapp.secutity.service;

import ma.enset.hospitalapp.secutity.entities.AppRole;
import ma.enset.hospitalapp.secutity.entities.AppUser;
import ma.enset.hospitalapp.secutity.repo.AppRoleRepository;
import ma.enset.hospitalapp.secutity.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword){
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser !=null) throw  new RuntimeException("This user already exists!");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match!");

        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepository.save(appUser);
    }


//    @Override
//    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
//        AppUser appUser=appUserRepository.findByUsername(username);
//        if (username!=null) throw new RuntimeException("This user already exist!");
//        if (password.equals(confirmPassword)) throw new RuntimeException("Password not match!");
//        appUser = AppUser.builder()
//                .userId(UUID.randomUUID().toString())
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .email(email).build();
//        AppUser savedAppUser = appUserRepository.save(appUser);
//        return savedAppUser;
//    }

    @Override
    public AppRole addNewRole(String role){
        return appRoleRepository.findById(role)
                .orElseGet(() -> appRoleRepository.save(
                        AppRole.builder().role(role).build()
                ));
    }


//    @Override
//    public AppRole addNewRole(String role) {
//        AppRole appRole = appRoleRepository.findById(role).orElse(null);
//        if (appRole!=null) throw new RuntimeException("This role already exist!");
//        appRole=AppRole.builder()
//                .role(role)
//                .build();
//        return appRoleRepository.save(appRole);
//    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser); /* Nous n'avons plus besoin de cette ligne, car nous utilisons @ transactional.
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
