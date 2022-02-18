package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Team extends BaseEntitySecond {
    @Id
    @GeneratedValue
    private Long id;
}
