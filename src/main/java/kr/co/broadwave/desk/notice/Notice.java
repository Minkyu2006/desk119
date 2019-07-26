package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.accounts.AccountRole;
import kr.co.broadwave.desk.bscodes.ApprovalType;
import kr.co.broadwave.desk.teams.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="bs_notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bn_id")
    private Long id;

    @Column(name="bn_subject")
    private String subject;

    @Column(length = 100000, name="bn_content")
    private String content;

    @Column(name="bn_hitcount")
    private Integer hitCount;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="insert_name")
    private String insert_name;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

    @Column(name="modify_name")
    private String modify_name;



}
