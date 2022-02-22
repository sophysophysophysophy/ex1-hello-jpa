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
            Address address = new Address("city", "street", "zipCode");
            Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(address);
            em.persist(member);

            Address address1 = new Address(address.getCity(), address.getStreet(), address.getZipCode());
            Member member1 = new Member();
            member.setUsername("member1");
            member1.setHomeAddress(address1);  // 이렇게 새로 만들어서 세팅해주기!
            em.persist(member1);

//            Address a = new Address("Old");
//            Address b = a ;  // 객체 타입은 참조를 전달
//            b.setCity("New");

            int a = 10;
            int b = 10;
            System.out.println(a == b ); // true

            Address addressOne = new Address("city", "street", "100000");
            Address addressTwo = new Address("city", "street", "100000");
            System.out.println(addressOne.equals(addressTwo));  // true

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId()); // select query 나가지 않음! 프록시객체 반환됨
            System.out.println("findMember.getId() = " + findMember.getId()); // 이 시점에 select query 나감!


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }


    }
}
