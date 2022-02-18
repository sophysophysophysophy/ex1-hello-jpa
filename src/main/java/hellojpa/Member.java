package hellojpa;
import hellojpa.highLevelMapping.Book;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter @Setter
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",   // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Column(name = "name") // 객체와 DB Column명이 다를 때
    private String username;

    private Integer age;     // Integer와 가장 적절한 숫자 타입이 DB column으로 생성됨

    @Enumerated(EnumType.STRING)
    private RoleType roleType;  // Enum은 DB에 없기 때문에 Enumerated annotation 적용하면 됨

//    @Temporal(TemporalType.TIMESTAMP)   // 날짜 타입 매 (date, time, timestamp 중 선택 필)
//    private Date createdDate;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

//    private LocalDate modifiedDate;     // DB date type으로 생됨
//    private LocalDateTime modidedDateTest;  // DB timeStamp type으로 생성됨

    @Lob
    private String description;     //@Lab anno 붙이고 문자 타입이면 기본적으로 CLOB column 생성

    @Transient      // DB column과 연결 안함
    private int temp;


}
