package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            member.setId(1L);
            member.setUsername("HelloA");       // 비영속

            em.persist(member);         // 영속

//            em.detach(member);          // 준영속
//            em.remove(member);          //삭제

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();

        }


    }
}
