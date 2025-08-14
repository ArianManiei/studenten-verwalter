package Projekt;
import java.util.ArrayList;
public class StudentenVerwaltung {
	private ArrayList<Student>StudentenListe;
public StudentenVerwaltung(ArrayList<Student>externeListe) {
	this.StudentenListe=externeListe;
}

public void studentHinzufügen(Student sh) {
	StudentenListe.add(sh);
	}

public Student nachIdSuchen(String eingabe) {
	for(Student s:StudentenListe) {
		if(s.getStudentId().toUpperCase().equals(eingabe.toUpperCase())) {
return s;
}
	}
	return null;
}

	
	
}
