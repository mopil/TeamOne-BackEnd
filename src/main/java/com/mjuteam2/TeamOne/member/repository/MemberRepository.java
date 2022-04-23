package com.mjuteam2.TeamOne.member.repository;

import com.mjuteam2.TeamOne.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);

    // 이 어노테이션을 달아줘야 영속성 컨텍스트가 쿼리 실행 후 초기화되어서 새로 업데이트된 객체를 잘 반환해줌
    // 초기화 안하면 기존 영속성 컨텍스트에 있던 객체를 그대로 돌려줘서 업데이트가 반영이 안된 것 처럼 보임(DB에는 들어감)
    // 닉네임 변경
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.nickname = :newNickname WHERE m.id = :id")
    void updateNickname(@Param("id") Long id, @Param("newNickname") String newNickname);

    // 스타 변경
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.star = :newStar WHERE m.id = :id")
    void updateStar(@Param("id") Long id, @Param("newStar") double newStar);

    // 포인트 변경
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.point = :newPoint WHERE m.id = :id")
    void updatePoint(@Param("id") Long id, @Param("newPoint") int newPoint);

    // 자기소개 변경
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.introduce = :newIntroduce WHERE m.id = :id")
    void updateIntroduce(@Param("id") Long id, @Param("newIntroduce") String newIntroduce);

    // 비밀번호 변경
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :newEncryptedPassword WHERE m.id = :id")
    void updatePassword(@Param("id") Long id, @Param("newEncryptedPassword") String newEncryptedPassword);

}
