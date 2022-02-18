package hellojpa;

import hellojpa.highLevelMapping.Book;
import hellojpa.highLevelMapping.Item;

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
//            member.setId(1L);
            member.setUsername("HelloA");       // 비영속


            Member member1 = new Member();
            Member member2 = new Member();
            member1.setUsername("HelloB");
            member2.setUsername("helloC");

            Book book = new Book();
            book.setAuthor("정현주");

            Item item = new Item();
            em.persist(item);
            member.setBook(book);
            em.persist(book);
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }


    }
}
