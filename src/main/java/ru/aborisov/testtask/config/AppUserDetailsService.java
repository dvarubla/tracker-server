package ru.aborisov.testtask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aborisov.testtask.dao.AppUser;
import ru.aborisov.testtask.dao.RoleRepository;
import ru.aborisov.testtask.dao.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppUserDetailsService implements UserDetailsService, AnonAuthoritiesObtainer {
    private UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userOpt = userRepository.findByLogin(username);
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        AppUser user = userOpt.get();
        return new User(user.getLogin(), user.getPassword(), user.getRole().getPrivileges().stream().map(
                privilege -> new SimpleGrantedAuthority(privilege.getAlias())
        ).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public List<GrantedAuthority> getAuthorities() {
        return roleRepository.findByAlias(RoleAlias.NOBODY.getAlias()).getPrivileges().stream().map(
                privilege -> new SimpleGrantedAuthority(privilege.getAlias())
        ).collect(Collectors.toList());
    }
}
