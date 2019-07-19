package kr.co.broadwave.desk.accounts;

import kr.co.broadwave.desk.teams.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-03-25
 * Time : 09:31
 * Remark : 사용자정보 클래스
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
    private String userid;

    @Column(name="user_name")
    private String username;

    @Column(name="user_password")
    private String password;

    @Column(name="user_email")
    private String email;



    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;


    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private AccountRole role;

    @ManyToOne(targetEntity = Team.class,fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;






}
