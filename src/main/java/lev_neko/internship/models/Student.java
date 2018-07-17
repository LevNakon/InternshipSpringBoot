package lev_neko.internship.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"knowledge","university"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "student"
    )
    private Knowledge knowledge;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private University university;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    Internship internship;

    public Student(String name, Knowledge knowledge, University university, Internship internship) {
        this.name = name;
        this.knowledge = knowledge;
        this.university = university;
        this.internship = internship;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public int getKnowledge(){
        return knowledge.getLevel();
    }
    public Knowledge gKnow(){return knowledge;}

}
