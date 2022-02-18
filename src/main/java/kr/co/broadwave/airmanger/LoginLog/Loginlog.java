package kr.co.broadwave.airmanger.LoginLog;

import kr.co.broadwave.airmanger.Account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark :
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="bs_login_log")
public class Loginlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="login_id")
    private Long id;

    @ManyToOne(targetEntity = Account.class,fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account accountId;

    @Column(name="user_id")
    private String userId;

    @Column(name="insert_date")
    private LocalDateTime insert_date;

    @Column(name="login_ip")
    private String loginIp;

    @Column(name="success_yn")
    private String successYN;

}
