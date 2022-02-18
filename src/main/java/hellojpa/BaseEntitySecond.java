package hellojpa;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntitySecond extends BaseEntity{
    private String second;
}
