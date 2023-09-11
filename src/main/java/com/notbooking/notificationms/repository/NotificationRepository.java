package com.notbooking.notificationms.repository;

import com.notbooking.notificationms.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select u from Notification u where u.user = :user ")
    List<Notification> findAllByUserId(@Param("user") int user);

    @Query("select u from Notification u where u.user = :user and u.read = false")
    List<Notification> findUnreadByUserId(@Param("user") int user);

    @Query("select u from Notification u where u.user = :user and u.read = true ")
    List<Notification> findReadByUserId(@Param("user") int user);
}
