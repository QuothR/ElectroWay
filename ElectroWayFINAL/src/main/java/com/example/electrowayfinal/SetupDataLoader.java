package com.example.electrowayfinal;

import com.example.electrowayfinal.dtos.UserDto;
import com.example.electrowayfinal.models.Privilege;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.repositories.PrivilegeRepository;
import com.example.electrowayfinal.repositories.RoleRepository;
import com.example.electrowayfinal.repositories.UserRepository;
import com.example.electrowayfinal.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public SetupDataLoader(PrivilegeRepository privilegeRepository, RoleRepository roleRepository, UserService userService, UserRepository userRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        printLogo();
        Privilege driverPrivilege = createPrivilegeIfNotFound("DRIVER_PRIVILEGE");
        Privilege ownerPrivilege = createPrivilegeIfNotFound("OWNER_PRIVILEGE");
        Privilege adminPrivilege = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");

        Collection<Privilege> ownerPrivileges;
        Collection<Privilege> driverPrivileges;
        Collection<Privilege> adminPrivileges;

        driverPrivileges = Collections.singletonList(driverPrivilege);
        ownerPrivileges = Collections.singletonList(ownerPrivilege);
        adminPrivileges = Arrays.asList(driverPrivilege, ownerPrivilege, adminPrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_DRIVER", driverPrivileges);
        createRoleIfNotFound("ROLE_OWNER", ownerPrivileges);

        // Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        UserDto adminUser = new UserDto("root", "root", "0750855596", "radu.harabagiu@gmail.com");

        if (userRepository.findUserByUsername("root").isEmpty())
            userService.registerNewUserAccount(adminUser);

        alreadySetup = true;
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    // TODO this should be void
    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = null;

        if (roleRepository.findByName(name).isEmpty()) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    private void printLogo() {
        System.out.println(" ______     __         ______     ______     ______   ______     ______     __     __     ______     __  __    \n" +
                "/\\  ___\\   /\\ \\       /\\  ___\\   /\\  ___\\   /\\__  _\\ /\\  == \\   /\\  __ \\   /\\ \\  _ \\ \\   /\\  __ \\   /\\ \\_\\ \\   \n" +
                "\\ \\  __\\   \\ \\ \\____  \\ \\  __\\   \\ \\ \\____  \\/_/\\ \\/ \\ \\  __<   \\ \\ \\/\\ \\  \\ \\ \\/ \".\\ \\  \\ \\  __ \\  \\ \\____ \\  \n" +
                " \\ \\_____\\  \\ \\_____\\  \\ \\_____\\  \\ \\_____\\    \\ \\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\__/\".~\\_\\  \\ \\_\\ \\_\\  \\/\\_____\\ \n" +
                "  \\/_____/   \\/_____/   \\/_____/   \\/_____/     \\/_/   \\/_/ /_/   \\/_____/   \\/_/   \\/_/   \\/_/\\/_/   \\/_____/ \n" +
                "                                                                                                               ");
        //https://patorjk.com/software/taag/#p=display&v=0&f=Small&t=v%200.1%20 'small slant'
        System.out.print("         ___   ______ \n" +
                " _  __  / _ \\ <  / _ \\\n" +
                "| |/ / / // / / / // /\n" +
                "|___/  \\___(_)_/\\___/ \n" +
                "                      ");
        System.out.println();
    }
}
