package model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
/**
 * {@code user} Az entitásosztályunk, amit a adatbázishoz használunk.
 */
public class User {
        /**
         * Konstruktor.
         * @param username Név.
         * @param score Pontszám.
         *
         */
        public User(String username, int score ,String gamemode){
                this.username = username;
                this.score =score;
                this.gamemode = gamemode;
        }

        /**
         * {@code id, username,score, gamemode} Az adatbázisban megjelenő adatok.
         */
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private int id;

        @Column(nullable=false)
        private String username;

        @Column(nullable=false)
        private int score;

        @Column(nullable=false)
        private String gamemode;


}

