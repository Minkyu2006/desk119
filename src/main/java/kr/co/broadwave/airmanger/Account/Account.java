package kr.co.broadwave.airmanger.Account;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : 사용자테이블 엔티티
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="bs_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(unique = true,name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private AccountRole role;

    @Column(name="insert_date")
    private LocalDateTime insert_date;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modify_date;

    @Column(name="modify_id")
    private String modify_id;

}
