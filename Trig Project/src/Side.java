
public class Side {
	//Attributes
	private double side;
	private double angle;
	private String name;
	
	
	
	//Setter & Getter Methods
	public double angle() {return angle;}
	public void setAngle(double angle) {this.angle = angle;}
	public double side() {return side;}
	public void setLength(double length) {this.side = length;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Side))
            return false;
        Side other = (Side) obj;
        if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(side) != Double.doubleToLongBits(other.side))
            return false;
        return true;
    }
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
		name = a.getName();
	}
	public String toString() {
        return String.format("%s = %g, %S = %g°", name, side, name, angle);
    }
	public boolean oneKnown() {
		if(Trig.known(angle) ^ Trig.known(side)) { return true; } return false;
	}
	
	
}
