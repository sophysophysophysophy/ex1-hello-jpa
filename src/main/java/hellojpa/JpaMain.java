package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.Arrays;
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
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "stree1", "20000"));
            member.getAddressHistory().add(new Address("old2", "stree2", "30000"));
            em.persist(member);

            tx.commit();

            Member findMember = em.find(Member.class, member.getId());
            // 이 때 member 객체는 컬렉션 값 타입 가지고 있지 않음 (지연로딩)

            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address);
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String food : favoriteFoods) {
                System.out.println("food = " + food);
            }


        // 값 타입 수정
        //findMember.getHomeAddress().setCity("city");  // 이렇게 수정하면 문제 발생 가능. 변경 불가한 객체로 생성해야 함
        //
        //값 타입은 통으로 새로 갈아끼워넣어야 함 ! (교체)
        Address oldAddress = findMember.getHomeAddress();
        findMember.setHomeAddress(new Address("new City", oldAddress.getStreet(), oldAddress.getZipCode()));

        // 값타입 컬렉션 수정

        //치킨 -> 한식 변경
        //업데이트 불가. 통째로 교체해야함
        //컬렉션 값만 변경되어도 insert, delete query 날아감. 영속성 전이
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().remove("한식");

        //주소 변경
        //컬렉션에서는 기본적으로 equals()를 통해 같은 객체 찾아냄.
        //따라서 equals() 재정의가 매우!! 중요함!
        findMember.getAddressHistory().remove(new Address("old1", oldAddress.getStreet(), oldAddress.getZipCode()));
        findMember.getAddressHistory().add(new Address("new City1", oldAddress.getStreet(), oldAddress.getZipCode()));


        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }


        Address address = new Address("city", "street", "zipCode");
        Field field = address.getClass().getDeclaredField("city");
        field.setAccessible(true);
        field.set(address, "new City");

        System.out.println(address.getCity());
//        System.out.println("fields = " + fields);

//        Field city = address.getClass().getField("city");
//        city.setAccessible(true);

    }
}
