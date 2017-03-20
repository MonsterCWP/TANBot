
public class Side {
	//Attributes
	private double side;
	private double angle;
	private String name;
	private double side2;
	private double angle2;
	private String name2;
	
	
	//Setter & Getter Methods
	public double angle() {return angle;}
	public void setAngle(double angle) {this.angle = angle;}
	public double side() {return side;}
	public void setLength(double length) {this.side = length;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public double angle2() {return angle2;}
	public void setAngle2(double angle) {this.angle2 = angle;}
	public double side2() {return side2;}
	public void setSide2(double length) {this.side2 = length;}
	public String getName2() {return name2;}
	public void setName2(String name) {this.name2 = name;}
	

	
	//Constructors
	//Side(length, angle, name)
	public Side(double a, double A, String name) {
		side = a;
		angle = A;
		this.name = name;
	}
	
	//Used to copy a pre-existing side.
	public Side(Side a) {
		side = a.side;
		angle = a.angle;
		name = (a.getName() + "2");
	}
	public String toString() {
        return String.format("%s = %g, %S = %g°", name, side, name, angle);
    }
	public boolean oneKnown() {
		if(Trig.known(angle) ^ Trig.known(side)) { return true; } return false;
	}
	
	//Merges two objects into one, with the second object's information being stored in 2 separate variables.
	public void merge(Side a2) {
		side2 = a2.side();
		angle2 = a2.angle();
		name2 = a2.getName();
	}
}
