package Projekt;

public abstract class Person {
private String name;
private int alter;

public Person(String name, int alter) {
	this.setName(name);
	this.setAlter(alter);
}
public String getName(){
	return name;
}

public void setName(String n) {
    if (n == null) {
        throw new IllegalArgumentException("Name darf nicht null sein.");
    }
    for(int i=0;i<n.length();i++) {
		if(!Character.isLetter(n.charAt(i))){
			throw new IllegalArgumentException("Der Name kann nur mit Buchschtaben hinzugefügt werden!");
		}
		else
			name=n;
	}
}
public int getAlter() {
	return alter;
}

public void setAlter(int Alter) {
	if(Alter>=18 && Alter<=80) {
		alter=Alter;
	}
	else
		throw new IllegalArgumentException("Zwischen 18 bis 80 .");
}
public abstract void zeigeDetails();

}
