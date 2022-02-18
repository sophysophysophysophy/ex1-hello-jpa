package hellojpa.highLevelMapping;

import hellojpa.BaseEntitySecond;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
@Getter @Setter
public class Item extends BaseEntitySecond {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
}

