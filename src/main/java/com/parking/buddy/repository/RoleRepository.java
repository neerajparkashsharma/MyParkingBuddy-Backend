package com.parking.buddy.repository;

import com.parking.buddy.entity.Role;
import com.parking.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    // Find a role by its name
    Role findByName(String name);

    // Find all roles for a given user
    List<Role> findAllByUsers(User user);

    // Find all roles that have a given user as one of their users
    List<Role> findAllByUsersContains(User user);

    // Find all roles that do not have a given user as one of their users
    List<Role> findAllByUsersNotContains(User user);

    // Delete a role by its name
    void deleteByName(String name);

    // Delete all roles for a given user
    void deleteAllByUsers(User user);
}
