package Projekt;

public class Kurs {
private String kursName;
private String kursCode;
public Kurs(String kursName,String kursCode) {
	this.kursCode=kursCode;
	this.kursName=kursName;
}
public void zeigeKurs() {
	System.out.printf("Kursname: %s%nKurscode: %s%n",kursName,kursCode);
}
public String getKursName() {
	return kursName;
}

}
