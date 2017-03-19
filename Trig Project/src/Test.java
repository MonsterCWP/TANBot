public class Test {

	public static void main(String[] args) {
		Side a = new Side(10, Double.NaN, "a");
		Side b = new Side(7, 30, "b");
		Side c = new Side(Double.NaN, Double.NaN, "c");
		
		
		Trig.solve(a, b, c);
		
		System.out.println("Side " + a.getName() + " has a length of " + a.side() + " and an opposite angle of " + a.angle() + ".");
		System.out.println("Side " + b.getName() + " has a length of " + b.side() + " and an opposite angle of " + b.angle() + ".");
		System.out.println("Side " + c.getName() + " has a length of " + c.side() + " and an opposite angle of " + c.angle() + ".");
		System.out.println("Side " + a.getName2() + " has a length of " + a.side2() + " and an opposite angle of " + a.angle2() + ".");
		System.out.println("Side " + b.getName2() + " has a length of " + b.side2() + " and an opposite angle of " + b.angle2() + ".");
		System.out.println("Side " + c.getName2() + " has a length of " + c.side2() + " and an opposite angle of " + c.angle2() + ".");
	}

}
