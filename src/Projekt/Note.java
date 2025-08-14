package Projekt;

public class Note {
private double note;
public Note(double note) {
	this.setNote(note);
}

public void setNote(double a) {
 if(a<=5 && a>=1)
	 note=a;
else
	throw new IllegalArgumentException("Ungültige Note!");
}
public double getNote () {
	return note;
}

public void zeigeNote() {
	System.out.printf("Note: %.2f%n",note);
}

}
