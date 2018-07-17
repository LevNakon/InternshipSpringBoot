package lev_neko.internship.models;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"university","student_list"})
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "internship"
    )
    private University university;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "internship"
    )
    private List<Student> student_list;

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Student> setStudent(University university) {
        List<Student> studentList = university.getStudentList();
        double sum = 0;
        int iteratore = 0;
        for (Student student : studentList) {
            sum+=student.getKnowledge();
            iteratore+=1;
        }
        List<Student> last_list = new LinkedList<>();
        for (Student student : studentList) {
            if(student.getKnowledge() >= (sum / iteratore)){
                last_list.add(student);
            }
        }
        return last_list;
    }

    public String getStudents() {
        return student_list.toString();
    }
}
