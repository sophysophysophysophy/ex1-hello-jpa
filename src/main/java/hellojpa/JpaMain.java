package hellojpa;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Parent;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {

//        application loading 시점에 하나만 만들어놔야함
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

//       transaction 일어날 때마다 생성 - 사용 - 폐기. entityManager가 내부적으로 DB connection을 물고 동작하기 때문에 잘 닫아줘야 함
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId()); // select query 나가지 않음! 프록시객체 반환됨
            System.out.println("findMember.getId() = " + findMember.getId()); // 이 시점에 select query 나감!

            // 프록시 객체 초기화
            Member member1 = em.getReference(Member.class, "id1");
            member1.getUsername();

//            프록시 인스턴스 초기화 여부 확인
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(member1));

//            프록시 클래스 확인 방법
            System.out.println(member1.getClass().getName());

//            프록시 강제 초기화
            Hibernate.initialize(member1);


            Member memberLazy = new Member();
            memberLazy.getTeam().getName(); // 이 때 team 초기화됨 (DB 조회)


//            Parent parent = em.find(Parent.class, id);
//            parent.getChildren().remove(0); //자식 엔티티를 컬렉션에서 제거 -> 바로 해당 오펀 객체 DB에서 DELETE됨

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }


    }
}
