package hellojpa;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Parent;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

//        application loading 시점에 하나만 만들어놔야함
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

//       transaction 일어날 때마다 생성 - 사용 - 폐기. entityManager가 내부적으로 DB connection을 물고 동작하기 때문에 잘 닫아줘야 함
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

//            JPQL
            String jpql = "select m From Member m where m.username like '%hello%'";
            List<Member> result  = em.createQuery(jpql, Member.class)
                    .getResultList();

            String jpql2 = "select m From Member m where m.age > 18";
            List<Member> result2  = em.createQuery(jpql2, Member.class)
                    .getResultList();


//            Criteria
//            Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

//            루트 클래스 (조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);

//            쿼리 생성
            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();

        }


    }
}
