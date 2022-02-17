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

//          팀 저장
            Team team = new Team();
            team.setName("TeamA");

//            회원 저장
            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);   // 단방향 연관관계 설정, 참조 저장

            em.persist(member);


//            조회
            Member findMember = em.find(Member.class, member.getId());

//            참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();

// 연관관계 수정
//            새로운 팀B
            Team teamB = new Team();
            team.setName("teamB");
            em.persist(teamB);

//            회원1에 새로운 팀B 설정
            member.setTeam(teamB);


//            em.detach(member);          // 준영속
//            em.remove(member);          //삭제

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
//            em.close();
//            emf.close();

        }


    }
}
