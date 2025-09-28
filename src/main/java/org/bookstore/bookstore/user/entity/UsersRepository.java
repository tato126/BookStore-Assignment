package org.bookstore.bookstore.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * {@link Users} 저장소 인터페이스.
 *
 * @author chan
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
