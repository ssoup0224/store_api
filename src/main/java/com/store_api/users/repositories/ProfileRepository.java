package com.store_api.users.repositories;

import com.store_api.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> {
}
