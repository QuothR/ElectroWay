package com.example.electrowayfinal.service;

import com.example.electrowayfinal.dtos.UserDto;
import com.example.electrowayfinal.exceptions.ForbiddenRoleAssignmentAttemptException;
import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Privilege;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.RoleRepository;
import com.example.electrowayfinal.repositories.UserRepository;
import com.example.electrowayfinal.user.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Qualifier("userService")
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private String secret;

    @Value("electroway")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public UserService(UserRepository userRepository, VerificationTokenService verificationTokenService, EmailService emailService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    //TODO ???
    @Qualifier("getPasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addRole(User user, String roleName) throws RoleNotFoundException, ForbiddenRoleAssignmentAttemptException {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty())
            throw new RoleNotFoundException(roleName);

        Collection<Role> roleList = user.getRoles();
        if  (!roleList.contains(roleRepository.findByName("ROLE_ADMIN").get()) && role.get().getName().equals("ROLE_ADMIN"))
            throw new ForbiddenRoleAssignmentAttemptException(user);

        roleList.add(role.get());

        user.setRoles(roleList);
        userRepository.save(user);
    }

    public void registerNewUserAccount(UserDto userDto) {
        Optional<User> userOptional = userRepository.findUserByEmailAddress(userDto.getEmailAddress());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken!");
        }

        // Se comenteaza pentru ca: Validarea parolei se face pe hashPassword
        // Dupa rezolvarea problemei, se decomenteaza

        User user = new User();

        String encryptedPassword;
        encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        user.setPassword(encryptedPassword);
        user.setEnabled(false);

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        //user.setLastName(userDto.getLastName());
        user.setAddress1(userDto.getAddress1());
        //user.setAddress2(userDto.getAddress2());
        user.setCity(userDto.getCity());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setCountry(userDto.getCountry());
        user.setPhoneNumber(userDto.getPhoneNumber());
        //user.setRegion(userDto.getRegion());
        user.setZipcode(userDto.getZipcode());

        Optional<User> saved = Optional.of(user);

        List<Role> roles = new LinkedList<>();

        for (String role : userDto.getRoles()) {
            if (roleRepository.findByName(role).isPresent())
                roles.add(roleRepository.findByName(role).get());
            else
                log.info("Tried to add inexistent role " + role + '\n');
        }

        user.setRoles(roles);
        saved.ifPresent(u -> {
            String token = UUID.randomUUID().toString();
            verificationTokenService.save(user, token);
            try {
                emailService.sendHtmlMail(u);
            } catch (MessagingException e) {
                log.error(e.getMessage());
            }
        });


        userRepository.save(user);
        log.info(user.toString());

        //saved.get();

    }

    //user
    public User getCurrentUser(HttpServletRequest httpServletRequest) throws UserNotFoundException{
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);

        return user.get();
    }

    public Optional<UserDto> getOptionalUserDto(String email) {
        Optional<User> user = userRepository.findUserByEmailAddress(email);
        if (user.isEmpty())
            return Optional.empty();

        return Optional.of(new UserDto(user.get().getUsername(), user.get().getPassword(), user.get().getFirstName(), user.get().getLastName(),
                user.get().getPhoneNumber(), user.get().getEmailAddress(), user.get().getAddress1(), user.get().getCity(),
                user.get().getCountry(), user.get().getZipcode(), user.get().getRoles().stream().map(Role::getName).collect(Collectors.toCollection(ArrayList::new))));
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException("user with id " + id + " does NOT exist");
        userRepository.deleteById(id);
    }

    public void updateUser(User modifiedUser, HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = getOptionalUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User does not exist!");
        }
        modifiedUser.setId(optionalUser.get().getId());
        modifiedUser.setEnabled(true);
        userRepository.save(modifiedUser);
    }

    //TO DELETE???
//    @Transactional
//    public void updateUser(Long userId, String firstName, String lastName, String emailAddress) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + "does not exist"));
//        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName))
//            user.setFirstName(firstName);
//        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName))
//            user.setFirstName(lastName);
//        if (emailAddress != null && emailAddress.length() > 0 && !Objects.equals(user.getEmailAddress(), emailAddress)) {
//            Optional<User> userOptional = userRepository.findUserByEmailAddress(emailAddress);
//            if (userOptional.isPresent()) {
//                throw new IllegalStateException("email taken");
//            }
//            user.setEmailAddress(emailAddress);
//        }
//    }

    @Transactional
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + "does not exist"));
        user.setEnabled(true);
    }

    // :))))) Aici face load user by email address
    // si face return la UserDetails nu User
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailAddress(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        return user.map(MyUserDetails::new).get();
    }

    public Optional<User> findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    public Optional<User> getOptionalUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void updateResetPasswordToken(String token, String email) {
        Optional<User> user = userRepository.findUserByEmailAddress(email);

        if (user.isPresent()) {
            user.get().setPasswordResetToken(token);
            userRepository.save(user.get());
        } else {
            throw new NoSuchElementException("User does not exist!");
        }
    }

    public User get(String passwordResetToken) {
        return userRepository.findUserByPasswordResetToken(passwordResetToken).isPresent()
                ? userRepository.findUserByPasswordResetToken(passwordResetToken).get()
                : null;

    }

    public void updatePassword(User user, String newPassword, String passwordResetToken) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setPasswordResetToken(passwordResetToken);

        userRepository.save(user);
    }

    public Optional<User> getUserById(long user_id) {
        return userRepository.findUserById(user_id);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
