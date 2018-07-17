package lev_neko.internship.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"student"})
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int level;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Student student;

    public Knowledge(int level, Student student) {
        this.level = level;
        this.student = student;
    }

    public int getLevel() {
        return level;
    }
}
