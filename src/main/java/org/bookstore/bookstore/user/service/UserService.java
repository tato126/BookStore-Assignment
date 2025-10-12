package org.bookstore.bookstore.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.user.dto.request.UserRequest;
import org.bookstore.bookstore.user.dto.response.UserResponse;
import org.bookstore.bookstore.user.entity.Users;
import org.bookstore.bookstore.user.entity.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@link org.bookstore.bookstore.user.entity.Users} 서비스 클래스.
 *
 * @author chan
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;

    // 유저 생성 로직
    @Transactional
    public UserResponse createUser(UserRequest userCreate) {

        // db에 동일한 이름이 없으면 등록한다.
        if (usersRepository.existsByEmail(userCreate.email())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        // 유저 등록
        // mapper 클래스로 변경할 수 있다.
        Users user = Users.builder()
                .email(userCreate.email())
                .password(userCreate.password())
                .build();

        // 유저 저장
        Users saveUser = usersRepository.save(user);

        // mapper 클래스로 변경할 수 있다
        return new UserResponse(
                saveUser.getUserId(),
                saveUser.getEmail(),
                saveUser.getCreatedAt()
        );
    }

    // 로그인 서비스
    public UserResponse login(UserRequest loginRequest) {

        // 유저를 조회
        Optional<Users> findByUser = usersRepository.findByEmail(loginRequest.email());

        // 해당 유저가 존재하나요?
        if (findByUser.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        // 해당 유저의 비밀번호가 일치하나요?
        if (!(findByUser.get().getPassword().equals(loginRequest.password()))) {
            throw new IllegalArgumentException("Wrong password!");
        }

        // 등록된 유저 가져오기
        Users user = findByUser.get();

        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    /**
     * ID로 사용자 조회 (장바구니용)
     */
    public Optional<Users> findById(Long userId) {
        return usersRepository.findById(userId);
    }

}
