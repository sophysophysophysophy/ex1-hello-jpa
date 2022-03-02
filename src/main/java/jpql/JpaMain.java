package jpql;

import hellojpa.Address;
import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

//        application loading 시점에 하나만 만들어놔야함
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

//       transaction 일어날 때마다 생성 - 사용 - 폐기. entityManager가 내부적으로 DB connection을 물고 동작하기 때문에 잘 닫아줘야 함
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            hellojpa.Member member = new hellojpa.Member();

        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }

    }
}
