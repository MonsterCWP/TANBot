public class Test {

	public static void main(String[] args) {
		Side a = new Side(10, Double.NaN, "a");
		Side b = new Side(10, Double.NaN, "b");
		Side c = new Side(10, Double.NaN, "c");
		
		
		Trig.solve(a, b, c);
		
		System.out.println("Side " + a.getName() + " has a length of " + a.side() + " and an opposite angle of " + a.angle() + ".");
		System.out.println("Side " + b.getName() + " has a length of " + b.side() + " and an opposite angle of " + b.angle() + ".");
		System.out.println("Side " + c.getName() + " has a length of " + c.side() + " and an opposite angle of " + c.angle() + ".");
			}

}
