package model;

import org.tinylog.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


/**
 * {@code Database} Az adatbázisban való tárolásért, illetve lekérdezését felelős.
 */
public class Database {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("snake-mysql");

    /**
     * {@code addUsertoDB()} Az adatbázishoz történő hozzáadásért felelős.
     * @param user A paraméterként apott usert adja hozzá az adatbázishoz.
     */
    public static void addUsertoDB(User user) {
        Logger.info("User hozzáadása az adatbázishoz megkezdődik.");
        EntityManager em = emf.createEntityManager();
        User addeduser = User.builder().username(user.getUsername()).score(user.getScore()).gamemode(user.getGamemode()).build();
        try {
            em.getTransaction().begin();
            em.persist(addeduser);
            em.getTransaction().commit();
            Logger.info("Sikeres user hozzáadás az adatbázishoz.");
        } finally {
            em.close();
        }


    }

    /**
     * {@code getUsers()}Lekérdezzük az adatbázis összes elemét fordított sorrendben, hogy a legfrissebb játékok legyenek elöl.
     * @return Listát ad vissza a lekérdezett Userekről.
     */
    public static List<User> getUsers() {
        Logger.info("Standard adatbázis lekérdezés.");
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.id DESC", User.class).getResultList();
        } finally {
            em.close();
        }
    }
    /**
     * {@code getTopTen()}Lekérdezzük az adatbázis 10 legtöbb ponttal rendelkező elemét.
     * @return Listát ad vissza a lekérdezett Userekről.
     */
    public static List<User> getTopTen() {
        Logger.info("Top10-es adatbázis lekérdezés.");
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT u FROM User u ORDER BY u.score DESC", User.class)
                    .setMaxResults(10)
                    .getResultList();}
        finally {
           em.close();
        }
    }

}


