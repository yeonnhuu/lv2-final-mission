package finalmission.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute() {
        clearTable("RESERVATION");
        clearTable("MEMBER");
        clearTable("LECTURE");
    }

    private void clearTable(String tableName) {
        entityManager.createNativeQuery("DELETE FROM " + tableName).executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }
}
