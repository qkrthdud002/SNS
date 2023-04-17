package com.project.sns.model.entity;

import com.project.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Table(name = "\"user\"") // postgreSQL은 이미 user 테이블이 존재하기 때문에 \"를 붙여 줘야한다.
@Setter
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; // 권한

    // 실제 유저가 등록된 시각을 저장하는 필드
    @Column(name = "registered_at")
    private Timestamp registeredAt;

    // 업데이트된 시각을 저장하는 필드
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // 삭제된 시각을 저장하는 필드
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    // UserEntity를 반환하는 메소드.
    public static UserEntity of(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }

}
