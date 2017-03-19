import java.lang.Math;
public class Trig {
	public static double sin(double degrees) {
		double radians = degrees * (Math.PI / 180);
		return Math.sin(radians);
	}
	
	public static double cos(double degrees) {
		double radians = degrees * (Math.PI / 180);
		return Math.cos(radians);
	}
	
	public static double tan(double degrees) {
		double radians = degrees * (Math.PI / 180);
		return Math.tan(radians);
	}
	
	public static double asin(double value) {
		return Math.asin(value) * (180 / Math.PI);
	}
	
	public static double acos(double value) {
		return Math.acos(value) * (180 / Math.PI);
	}
	
	public static double atan(double value) {
		return Math.atan(value) * (180 / Math.PI);
	}
	
	public static boolean known(double... values) {
		for(double d : values) {
 			if (Double.isNaN(d)) return false;
		}
		return true;
	}
	
	//Returns how many of the values given are known.
	public static double tallyKnown(double... values) {
		int counter = 0;
		for(double d : values) {
			if (Double.isNaN(d)) { counter++; }
		}
		return counter;
	}

	/*
	 * Returns the missing part of A (angle or side) when supplied with a Side B that has both.
	 * 
	 * Preconditions: Side a contains information for only the side or the angle
	 * 				  Side b contains information for both the side & the angle
	 * Postconditions: Edits Side a so that it now contains information for both the side and the angle
	 */
	public static void LawOfSines(Side a, Side b) {
		if(!known(a.side()) & known(a.angle()) & known(b.angle(), b.side())) {
				//If the side is not known
				System.out.printf("Using Law of Sines to determine %s%n", a.getName());
				a.setLength(((b.side() * sin(a.angle()) / sin(b.angle()))));
		}
	}
	
	/*
	 * Returns the missing part of A (angle or side) when supplied with a Side B and Side C that have side measures.
	 * 
	 * Preconditions: Side a contains information for only the side or the angle
	 * 				  Side b and c contain information for the side
	 * Postconditions: Returns the missing measure in double form
	 */
	public static void LawOfCosines(Side a, Side b, Side c) {
		//Checks that the sides of b & c are known, and that Side a has information for one but not both measures.
		if(known(b.side(), c.side()) & a.oneKnown()) {
			if(!known(a.angle())) {
				//If the angle is not known
				// A = acos((b^2 + c^2 - a^2) / 2bc)
				System.out.printf("Using Law of Cosines to determine %S%n", a.getName());
				a.setAngle((acos((Math.pow(b.side(), 2) + Math.pow(c.side(), 2) 
				- Math.pow(a.side(), 2)) / 2*b.side()*c.side())));
			} else if(!known(a.side())) {
				//If the side is not known
				// a = sqrt(b^2 + c^2 - 2*b*c*cosA)
				System.out.printf("Using Law of Cosines to determine %s%n", a.getName());
				a.setLength((Math.sqrt(Math.pow(b.side(), 2) + Math.pow(c.side(), 2) 
				- 2*(c.side())*(b.side())*cos(a.angle()))));
			}
		}
	}
	
	/*
	 * If all of the angles except for one are known, subtracts the measure of the other two from 180
	 * to find the measure of the first angle.
	 */
	public static void LawOf180(Side a, Side b, Side c) {
		if(!known(a.angle()) & known(b.angle(), c.angle())) {
			System.out.printf("Using Law of 180 to determine %S%n", a.getName());
			a.setAngle(180-b.angle()-c.angle());
		}
	}
	
	/*
	 * This method solves all of the sides of a triangle, assuming it is possible to solve.
	 * 
	 * It loops through a list of if-else statements several times, seeing if it can solve for an unknown piece each time.
	 */
	public static void solve(Side a, Side b, Side c) {
		int counter = 0;
		boolean exit = false;
		//As long as not everything is solved;
		while((known(a.angle(), a.side(), b.angle(), b.side(), c.angle(), c.side()) != true) & counter < 20 & exit == false) {
			//Counter ensures it does not enter an infinite loop if something goes wrong
			counter++;
			//Things solvable w/ Law of Cosines
			//If the first parameter has one measure known & the second and third has their side known, uses the Law
			//of Cosines to solve the missing measure.
			LawOfCosines(a, b, c);
			LawOfCosines(b, a, c);
			LawOfCosines(c, a, b);
			
			//Things solvable w/ Law of Sines
			//If the first parameter has one measure known & the second has both known, uses the Law of Sines to
			//solve the missing measure.
			LawOfSines(a, b);
			LawOfSines(a, c);
			LawOfSines(b, c);
			LawOfSines(b, a);
			LawOfSines(c, a);
			LawOfSines(c, b);
			
			//Things solvable w/ Law of 180
			LawOf180(a, b, c);
			LawOf180(b, c, a);
			LawOf180(c, a, b);
			
			//At the end of the first loop, if nothing has been solved, then it is a SSA triangle.
			if(counter == 1 & tallyKnown(a.angle(),a.side(),b.angle(),b.side(),c.angle(),c.side()) == 3.0) {
				solveSSA(a, b, c);
				exit = true;
			}
		}
		if(counter == 20) {
			//Prints an error if the loop goes on too long.
			System.out.println("The loop went on too long and was ended! (Looped 20 times)");
		}
		System.out.println("Counter is " + counter);
	}
	
	public static void solveSSA(Side a, Side b, Side c) {
		System.out.println("Triangle given is SSA! Creating a second triangle...");
		Side a2 = new Side(a);
		Side b2 = new Side(b);
		Side c2 = new Side(c);
		
		LawOfSinesSSA(a, a2, b);
		LawOfSinesSSA(a, a2, c);
		LawOfSinesSSA(b, b2, a); 
		LawOfSinesSSA(b, b2, c);
		LawOfSinesSSA(c, c2, a);
		LawOfSinesSSA(c, c2, b);
		
		//After the angles for the two triangles have been determined, uses the solve() method to solve each triangle.
		solve(a, b, c);
		solve(a2, b2, c2);
		
		//Merges the two objects into one, so they can be returned to the previous method and don't go out of scope.
		a.merge(a2);
		b.merge(b2);
		c.merge(c2);
	}
	
	public static void LawOfSinesSSA(Side a, Side a2, Side b) {
		//If the side of a is known but not the angle, and both the side and angle are known for b, execute.
		if(known(a.side()) & !known(a.angle()) & known(b.angle(), b.side())) {
			//If the angle is not known
			System.out.printf("Using Law of Sines to determine %S%n", a.getName());
			a.setAngle((asin((a.side()*sin(b.angle())) / b.side())));
			a2.setAngle(90 + (90 - a.angle()));
			System.out.println("I came up with " + a.angle() + " and " + a2.angle() + " degrees for the two possible angles.");
		}
		
	}
}
