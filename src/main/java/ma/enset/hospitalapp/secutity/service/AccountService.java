package ma.enset.hospitalapp.secutity.service;

import ma.enset.hospitalapp.secutity.entities.AppRole;
import ma.enset.hospitalapp.secutity.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
