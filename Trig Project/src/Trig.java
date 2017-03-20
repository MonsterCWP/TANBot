import java.io.PrintWriter;
import java.io.StringWriter;

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
        if (value < -1 || value > 1)
            throw new IllegalArgumentException("Can't compute asin of " + value);
        return Math.asin(value) * (180 / Math.PI);
    }

    public static double acos(double value) {
        if (value < -1 || value > 1)
            throw new IllegalArgumentException("Can't compute acos of " + value);
        return Math.acos(value) * (180 / Math.PI);
    }

    public static double atan(double value) {
        return Math.atan(value) * (180 / Math.PI);
    }

    public static boolean known(double... values) {
        for (double d : values) {
            if (Double.isNaN(d))
                return false;
        }
        return true;
    }

    // Returns how many of the values given are known.
    public static int tallyKnown(double... values) {
        int counter = 0;
        for (double d : values) {
            if (Double.isNaN(d)) {
                counter++;
            }
        }
        return counter;
    }

    /*
     * Returns the missing part of A (angle or side) when supplied with a Side B
     * that has both.
     * 
     * Preconditions: Side a contains information for only the side or the angle
     * Side b contains information for both the side & the angle Postconditions:
     * Edits Side a so that it now contains information for both the side and
     * the angle
     */
    public void LawOfSines(Side x, Side y) {
        if (!known(x.side()) & known(x.angle(), y.angle(), y.side())) {
            // If the side is not known
            out.printf("Using Law of Sines to determine %s from %S°, %S° and %s%x", x.getName(), x.getName(), y.getName(), y.getName());
            out.printf("%s = (%g * sin(%g°) / sin(%g°)%n", x.getName(), y.side(), x.angle(), y.angle());
            x.setLength(((y.side() * sin(x.angle()) / sin(y.angle()))));
            out.printf("%s = %g%n%n", x.getName(), x.side());
        }
    }

    /*
     * Returns the missing part of A (angle or side) when supplied with a Side B
     * and Side C that have side measures.
     * 
     * Preconditions: Side a contains information for only the side or the angle
     * Side b and c contain information for the side Postconditions: Returns the
     * missing measure in double form
     */
    public  void LawOfCosines(Side x, Side y, Side z) {
        // Checks that the sides of b & c are known, and that Side a has
        // information for one but not both measures.
        if (known(y.side(), z.side()) & x.oneKnown()) {
            if (!known(x.angle())) {
                // If the angle is not known
                // A = acos((b^2 + c^2 - a^2) / 2bc)
                out.printf("Using Law of Cosines to determine %S° from %s, %s and %s%n", x.getName(), x.getName(), y.getName(), z.getName());
                out.printf("%S° = cos⁻¹((%g² + %g² - %g²)/(2*%g*%g))%n", x.getName(), y.side(), z.side(), x.side(), y.side(), z.side());
                x.setAngle((acos((Math.pow(y.side(), 2) + Math.pow(z.side(), 2) - Math.pow(x.side(), 2))
                        / (2 * y.side() * z.side()))));
                out.printf("%S° = %g%n%n", x.getName(), x.angle());
            } else if (!known(x.side())) {
                // If the side is not known
                // a = sqrt(b^2 + c^2 - 2*b*c*cosA)
                out.printf("Using Law of Cosines to determine %s from %s, %s and %S°%n", x.getName(), y.getName(), z.getName(), x.getName());
                out.printf("%s = sqrt(%g² + %g² - 2*%g*%g*cos(%g°)%n", x.getName(), y.side(), z.side(), y.side(), z.side(), x.angle());
                x.setLength((Math.sqrt(
                        Math.pow(y.side(), 2) + Math.pow(z.side(), 2) - 2 * (z.side()) * (y.side()) * cos(x.angle()))));
                out.printf("%s = %g%n%n", x.getName(), x.side());
            }
        }
    }

    /*
     * If all of the angles except for one are known, subtracts the measure of
     * the other two from 180 to find the measure of the first angle.
     */
    public void LawOf180(Side x, Side y, Side z) {
        if (!known(x.angle()) & known(y.angle(), z.angle())) {
            out.printf("Using Law of 180 to determine %S° from %S° and %S°%n", x.getName(), y.getName(), z.getName());
            out.printf("%S = 180° - %g° - %g°%n", x.getName(), y.angle(), z.angle());
            x.setAngle(180 - y.angle() - z.angle());
            out.printf("%S = %g°%n%n", x.getName(), x.angle());
        }
    }

    Side a, b, c;
    StringWriter stringWriter = new StringWriter();
    PrintWriter out = new PrintWriter(stringWriter);
    Trig variant;

    public Trig(Side a, Side b, Side c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Trig))
            return false;
        Trig other = (Trig) obj;
        if (a == null) {
            if (other.a != null)
                return false;
        } else if (!a.equals(other.a))
            return false;
        if (b == null) {
            if (other.b != null)
                return false;
        } else if (!b.equals(other.b))
            return false;
        if (c == null) {
            if (other.c != null)
                return false;
        } else if (!c.equals(other.c))
            return false;
        return true;
    }

    public static Trig solve(Side a, Side b, Side c) {
        Trig triangle = new Trig(a, b, c);
        triangle.solve();
        return triangle;
    }

    /*
     * This method solves all of the sides of a triangle, assuming it is
     * possible to solve.
     * 
     * It loops through a list of if-else statements several times, seeing if it
     * can solve for an unknown piece each time.
     */
    public void solve() {
        out.println("Solving:");
        out.println(a);
        out.println(b);
        out.println(c);
        int counter = 0;
        // As long as not everything is solved;
        while ((known(a.angle(), a.side(), b.angle(), b.side(), c.angle(), c.side()) != true) & counter < 20
                ) {
            // Counter ensures it does not enter an infinite loop if something
            // goes wrong
            counter++;
            // Things solvable w/ Law of Cosines
            // If the first parameter has one measure known & the second and
            // third has their side known, uses the Law
            // of Cosines to solve the missing measure.
            LawOfCosines(a, b, c);
            LawOfCosines(b, a, c);
            LawOfCosines(c, a, b);

            // Things solvable w/ Law of Sines
            // If the first parameter has one measure known & the second has
            // both known, uses the Law of Sines to
            // solve the missing measure.
            LawOfSines(a, b);
            LawOfSines(a, c);
            LawOfSines(b, c);
            LawOfSines(b, a);
            LawOfSines(c, a);
            LawOfSines(c, b);

            // Things solvable w/ Law of 180
            LawOf180(a, b, c);
            LawOf180(b, c, a);
            LawOf180(c, a, b);

            // At the end of the first loop, if nothing has been solved, then it
            // is a SSA triangle.
            if (counter == 1 & tallyKnown(a.angle(), a.side(), b.angle(), b.side(), c.angle(), c.side()) == 3) {
                solveSSA();
               break;
            }
        }
        
        if (counter == 20) {
            // Prints an error if the loop goes on too long.
            System.out.println("The loop went on too long and was ended! (Looped 20 times)");
        } else {
        	System.out.println("Solution found");
        	System.out.println(stringWriter.toString());
        }
        
    }

    public void solveSSA() {
        out.println("Triangle given is SSA! Creating a second triangle...");
        Side a2 = new Side(a);
        Side b2 = new Side(b);
        Side c2 = new Side(c);
        variant = new Trig(a2, b2, c2);
        variant.out.print(stringWriter.toString());

        LawOfSinesSSA(a, a2, b);
        LawOfSinesSSA(a, a2, c);
        LawOfSinesSSA(b, b2, a);
        LawOfSinesSSA(b, b2, c);
        LawOfSinesSSA(c, c2, a);
        LawOfSinesSSA(c, c2, b);

        if (this.equals(variant)) {
            this.variant = null;
            solve();
        } else {
            // After the angles for the two triangles have been determined, uses
            // the solve() method to solve each triangle.
            solve();
            variant.solve();
        }

    }

    public void LawOfSinesSSA(Side x, Side x2, Side y) {
        // If the side of a is known but not the angle, and both the side and
        // angle are known for b, execute.
        if (known(x.side()) & !known(x.angle()) & known(y.angle(), y.side())) {
            // If the angle is not known
            out.printf("Using Law of Sines to determine %S° from %s, %S° and %s%n", x.getName(), x.getName(), y.getName(), y.getName());
            out.printf("%S = sin⁻¹(%g * sin(%g°) / %g)", x.getName(), x.side(), y.angle(), y.side());
            x.setAngle((asin((x.side() * sin(y.angle())) / y.side())));
            x2.setAngle(90 + (90 - x.angle()));
            out.printf("I came up with %g° and %g° for the two possible angles.%n%n", x.angle(), x2.angle());
        }

    }
}
