package com.managerPass.provider.repository;

import com.managerPass.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleProvider extends JpaRepository<RoleEntity, Long> {
}
