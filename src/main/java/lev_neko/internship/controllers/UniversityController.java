package lev_neko.internship.controllers;

import lev_neko.internship.dao.InternshipDAO;
import lev_neko.internship.dao.KnowledgeDAO;
import lev_neko.internship.dao.StudentDAO;
import lev_neko.internship.dao.UniversityDAO;
import lev_neko.internship.models.Internship;
import lev_neko.internship.models.Knowledge;
import lev_neko.internship.models.Student;
import lev_neko.internship.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class UniversityController {
    @Autowired
    UniversityDAO universityDAO;
    @Autowired
    StudentDAO studentDAO;
    @Autowired
    KnowledgeDAO knowledgeDAO;
    @Autowired
    InternshipDAO internshipDAO;

    @GetMapping("/")
    public String mainPage(
            Model model
    ){
        List<University> universities = universityDAO.findAll();
        model.addAttribute("university",universities);
        return "index";
    }

    @PostMapping("/university/create")
    public String createUniversity(
            @RequestParam String name,
            @RequestParam String InternName
    ){
        List<Student> studentListforUniversity = new LinkedList<>();
        List<Student> studentListforInternship = new LinkedList<>();
        University university = University.builder().name(name).studentList(studentListforUniversity).build();
        Internship internship = Internship.builder().name(InternName).student_list(studentListforInternship).build();
        internship.setUniversity(university);
        university.setInternship(internship);
        universityDAO.save(university);
        return "redirect:/";
    }

    @GetMapping("/university/{id}")
    public String University(
            Model model,
            @PathVariable int id
    ){
        Optional<University> byId = universityDAO.findById(id);
        University university = byId.get();
        model.addAttribute("u",university);
        List<Student> studentList = university.getStudentList();
        model.addAttribute("students",studentList);
        return "University";
    }

    @GetMapping("/university/{id}/internship")
    public String Internship(
            Model model,
            @PathVariable int id
    ){
        Optional<University> byId = universityDAO.findById(id);
        University university = byId.get();
        model.addAttribute("u",university);
        int idInter = university.getInternship().getId();
        List<Student> studentList1 = university.getStudentList();
        Optional<Internship> byId1 = internshipDAO.findById(idInter);
        Internship internship = byId1.get();
        List<Student> studentList = internship.setStudent(university);
        for (Student student : studentList1) {
            student.setInternship(null);
        }
        for (Student student : studentList) {
            student.setInternship(internship);
            studentDAO.save(student);
        }
        internshipDAO.save(internship);
        model.addAttribute("intern",studentList);
        return "Internship";
    }

    @GetMapping("/university/{id}/delete")
    public String deleteUniversity(
            @PathVariable int id
    ){
        universityDAO.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("university/{id}/update")
    public String updateUniversity(
            @PathVariable int id,
            @RequestParam String name1,
            @RequestParam String InternName1
    ){
        Optional<University> byId = universityDAO.findById(id);
        University university = byId.get();
        university.setName(name1);
        Internship internship = university.getInternship();
        internship.setName(InternName1);
        internshipDAO.save(internship);
        universityDAO.save(university);
        return "redirect:/";
    }

    @PostMapping("university/{id}/student/create")
    public String studentCreate(
            @PathVariable int id,
            @RequestParam String nameStudent,
            @RequestParam String levelStudent
    ){
        Optional<University> byId = universityDAO.findById(id);
        University university = byId.get();
        Knowledge knowledge = Knowledge.builder().level(Integer.parseInt(levelStudent)).build();
        Student student = Student.builder().name(nameStudent).knowledge(knowledge).university(university).build();
        knowledge.setStudent(student);
        knowledgeDAO.save(knowledge);
        studentDAO.save(student);
        university.addStudent(student);
        universityDAO.save(university);
        return "redirect:/university/{id}";
    }

    @GetMapping("university/{id}/student/{idStud}")
    public String Student(
            @PathVariable int id,
            @PathVariable int idStud,
            Model model
    ){
        Optional<Student> byId = studentDAO.findById(idStud);
        Student student = byId.get();
        model.addAttribute("s",student);
        Optional<University> byId1 = universityDAO.findById(id);
        University university = byId1.get();
        model.addAttribute("u",university);
        return "Student";
    }

    @GetMapping("university/{id}/student/{idStud}/delete")
    public String deleteStudent(
            @PathVariable int id,
            @PathVariable("idStud") int idStud
    ){
        studentDAO.deleteById(idStud);
        return "redirect:/university/{id}";
    }

    @PostMapping("university/{id}/student/{idStud}/update")
    public String updateStudent(
            @PathVariable int idStud,
            @RequestParam String nameSt,
            @RequestParam String knowledge
    ){
        Optional<Student> byId = studentDAO.findById(idStud);
        Student student = byId.get();
        student.setName(nameSt);
        Knowledge knowledge1 = student.gKnow();
        knowledge1.setLevel(Integer.parseInt(knowledge));
        knowledgeDAO.save(knowledge1);
        studentDAO.save(student);
        return "redirect:/university/{id}";
    }
}
