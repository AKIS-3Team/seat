package kr.co.newbie.sample.member;

import kr.co.newbie.sample.member.service.impl.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 파라미터는 persistence.xml의 persistence-unit 값으로서 해당 설정을 이용하겠다는 것이다.
        // EMF는 서버가 가동될 때 딱 한 번 생성하여 애플리케이션 전체에서 공유해야 한다.

        EntityManager em = emf.createEntityManager();
        // 실제로는 비즈니스 로직의 요청이 있을 때마다 EntityManager를 생성한다.
        // EM은 쓰레드 간에 공유하면 안 된다. 사용한 후 바로 버려야 한다.

        EntityTransaction tx = em.getTransaction();
        // JPA에서 모든 데이터 변경은 트랜잭션 내에서 실행한다.

        tx.begin();

        try {
            Member member = new Member();
            member.setId(100L);
            member.setName("스폰지밥");

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
        // 사실상 어플리케이션의 종료를 의미한다.
    }
}
