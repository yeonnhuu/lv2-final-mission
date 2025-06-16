package finalmission.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FixtureLoader {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertFixtures() {
        insertMembers();
        insertLectures();
        insertReservations();
    }

    private void insertMembers() {
        entityManager.createNativeQuery("""
            INSERT INTO MEMBER (NAME, EMAIL, PASSWORD)
            VALUES 
                ('멤버1', 'member1@email.com', 'password'),
                ('멤버2', 'member2@email.com', 'password')
        """).executeUpdate();
    }

    private void insertLectures() {
        entityManager.createNativeQuery("""
            INSERT INTO LECTURE (SPORT, DATE)
            VALUES
                ('야구', CURRENT_DATE + 1),
                ('농구', CURRENT_DATE + 2),
                ('축구', CURRENT_DATE + 3),
                ('배구', CURRENT_DATE + 4)
        """).executeUpdate();
    }

    private void insertReservations() {
        entityManager.createNativeQuery("""
            INSERT INTO RESERVATION (MEMBER_ID, LECTURE_ID, RESERVED_AT, RESERVE_COUNT)
            VALUES
                (1, 1, CURRENT_DATE - 3, 1),
                (1, 2, CURRENT_DATE - 2, 1),
                (2, 1, CURRENT_DATE - 2, 1)
        """).executeUpdate();
    }
}
