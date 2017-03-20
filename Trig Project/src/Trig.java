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
    public static void LawOfSines(Side a, Side b) {
        if (!known(a.side()) & known(a.angle()) & known(b.angle(), b.side())) {
            // If the side is not known
            System.out.printf("Using Law of Sines to determine %s%n", a.getName());
            a.setLength(((b.side() * sin(a.angle()) / sin(b.angle()))));
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
                System.out.printf("Using Law of Cosines to determine %S%n", x.getName());
                x.setAngle((acos((Math.pow(y.side(), 2) + Math.pow(z.side(), 2) - Math.pow(x.side(), 2))
                        / (2 * y.side() * z.side()))));
            } else if (!known(x.side())) {
                // If the side is not known
                // a = sqrt(b^2 + c^2 - 2*b*c*cosA)
                System.out.printf("Using Law of Cosines to determine %s%n", x.getName());
                x.setLength((Math.sqrt(
                        Math.pow(y.side(), 2) + Math.pow(z.side(), 2) - 2 * (z.side()) * (y.side()) * cos(x.angle()))));
            }
        }
    }

    /*
     * If all of the angles except for one are known, subtracts the measure of
     * the other two from 180 to find the measure of the first angle.
     */
    public static void LawOf180(Side a, Side b, Side c) {
        if (!known(a.angle()) & known(b.angle(), c.angle())) {
            System.out.printf("Using Law of 180 to determine %S%n", a.getName());
            a.setAngle(180 - b.angle() - c.angle());
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

            out.println("A = " + a.angle() + ", B = " + b.angle() + ", C = " + c.angle() + ".");
            // At the end of the first loop, if nothing has been solved, then it
            // is a SSA triangle.
            if (counter == 1 & tallyKnown(a.angle(), a.side(), b.angle(), b.side(), c.angle(), c.side()) == 3) {
                solveSSA();
                return;
            }
        }
        if (counter == 20) {
            // Prints an error if the loop goes on too long.
            System.out.println("The loop went on too long and was ended! (Looped 20 times)");
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

    public static void LawOfSinesSSA(Side a, Side a2, Side b) {
        // If the side of a is known but not the angle, and both the side and
        // angle are known for b, execute.
        if (known(a.side()) & !known(a.angle()) & known(b.angle(), b.side())) {
            // If the angle is not known
            System.out.printf("Using Law of Sines to determine %S%n", a.getName());
            a.setAngle((asin((a.side() * sin(b.angle())) / b.side())));
            a2.setAngle(90 + (90 - a.angle()));
            System.out.println(
                    "I came up with " + a.angle() + " and " + a2.angle() + " degrees for the two possible angles.");
        }

    }
}
