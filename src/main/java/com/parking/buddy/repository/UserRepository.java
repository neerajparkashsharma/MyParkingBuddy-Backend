package com.parking.buddy.repository;

import com.parking.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String email);
    User findByPhoneNumber(String phoneNumber);

    // save user
    User save(User user);

    // delete user by id
    void deleteById(long id);
    List<User> findByNameLike(String name);
    List<User> findByPhoneNumberNotLike(String phoneNumber);

    List<User> findByLocationDistanceGreaterThan(double distance);
    List<User> findByCreatedDateLessThanEqual(Date date);

    List<User> findByLocationDistanceBetween(double minDistance, double maxDistance);
    List<User> findByCreatedDateBetween(Date startDate, Date endDate);

    List<User> findByPhoneNumberIn(List<String> phoneNumbers);
    List<User> findByIsActiveIn(List<Boolean> isActiveList);
    List<User> findByIsActiveNot(boolean isActive);
    List<User> findByLocationDistanceNot(double distance);

}
