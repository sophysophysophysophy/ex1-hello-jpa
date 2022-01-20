package hellojpa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",   // 매핑할 데이터베이스 시퀀스 이름
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name") // 객체와 DB Column명이 다를 때
    private String username;

    private Integer age;     // Integer와 가장 적절한 숫자 타입이 DB column으로 생성됨

    @Enumerated(EnumType.STRING)
    private RoleType roleType;  // Enum은 DB에 없기 때문에 Enumerated annotation 적용하면 됨

    @Temporal(TemporalType.TIMESTAMP)   // 날짜 타입 매 (date, time, timestamp 중 선택 필)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate modifiedDate;     // DB date type으로 생됨
    private LocalDateTime modidedDateTest;  // DB timeStamp type으로 생성됨

    @Lob
    private String description;     //@Lab anno 붙이고 문자 타입이면 기본적으로 CLOB column 생성

    @Transient      // DB column과 연결 안함
    private int temp;
}
