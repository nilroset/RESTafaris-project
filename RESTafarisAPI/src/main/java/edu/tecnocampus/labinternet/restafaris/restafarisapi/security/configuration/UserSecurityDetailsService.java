package edu.tecnocampus.labinternet.restafaris.restafarisapi.security.configuration;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.UserSecurity;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.UserSecurityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityDetailsService implements UserDetailsService {
    private UserSecurityRepository userSecurityRepository;

    public UserSecurityDetailsService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity user = userSecurityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserSecurityDetails.build(user);
    }
}
