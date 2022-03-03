package jpql;

import hellojpa.Member;

import javax.persistence.*;
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

//           TypeQuery, Query
            TypedQuery<Member> typedQuery = em.createQuery("SELECT m FROM Member m", Member.class);
            List<Member> resultList = typedQuery.getResultList();
            Member singleResult = typedQuery.getSingleResult();

            Query query = em.createQuery("SELECT m FROM Member m");

            TypedQuery<Member> typedQueryBinding = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);
            typedQueryBinding.setParameter("username", "member1");
            Member singleResult1 = typedQueryBinding.getSingleResult();
            System.out.println("singleResult1.getUsername() = " + singleResult1.getUsername());

            Member result = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

//            위치 기준 바인딩
            Member result2 = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                    .setParameter(1, "member1")
                    .getSingleResult();

            em.flush();
            em.clear();
//            프로젝션
            List<Member> resultTest = em.createQuery("SELECT m from Member m", Member.class)
                    .getResultList();
            Member findMember = resultTest.get(0);
            findMember.setUsername("hahaha");


            em.createQuery("SELECT m.team FROM Member m", Team.class).getResultList();
            em.createQuery("SELECT t FROM Member m join m.team t", Team.class).getResultList();
            em.createQuery("SELECT o.address from Order o", Address.class).getResultList();
            em.createQuery("SELECT DISTINCT m.username, m.age FROM Member m", Member.class);

            List resultListObj = em.createQuery("SELECT DISTINCT m.username, m.age FROM Member m")
                    .getResultList();
            Object o = resultListObj.get(0);
            Object[] resultObj = (Object[]) o;
            System.out.println("username(resultObj[0]) = " + resultObj[0]);
            System.out.println("age(resultObj[0]) = " + resultObj[1]);


            List<Object[]> resultListType = em.createQuery("SELECT DISTINCT m.username, m.age FROM Member m")
                    .getResultList();

            Object[] resultObj1 = resultListType.get(0);
            System.out.println("username(resultObj[0]) = " + resultObj1[0]);
            System.out.println("age(resultObj[0]) = " + resultObj1[1]);


            List<MemberDTO> memberDTOList = em.createQuery("SELECT new jpql.MemberDTO(m.username, m.age) FROM Member m", MemberDTO.class)
                    .getResultList();


//            paging api
            String jpql = "SELECT m FROM Member m order by m.username desc";
            List<Member> members = em.createQuery(jpql, Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }

    }
}
