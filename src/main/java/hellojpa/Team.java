package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Team {
    @Id
    private Long id;

    @Column
    private String name;

//    private Member member;
}
