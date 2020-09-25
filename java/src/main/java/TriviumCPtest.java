import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.BoolVar;

public class TriviumCPtest {
    public static int[] F = {0, 1};
    public static int NBROUNDS = 288;

    public static void main(String[] args) {
        Model m = new Model();
        int Nb = NBROUNDS;
        BoolVar[] X = m.boolVarArray(1 + 288 + 3 * Nb);
        BoolVar Z = m.boolVar();
        int ii = itrivium(Nb, 7);
        X[0].eq(0).post();
        X[1].eq(1).post();
//        for (int i = 1; i <= 80; i++) X[i].eq(1).post();//K

        for (int i = 2; i <= 80; i++) X[i].eq(0).post();//K
        for (int i = 81; i <= 93; i++) X[i].eq(0).post();
        // Initialization Register 2 - IV + 0
        for (int i = 94; i <= 173; i++) X[i].eq(0).post();//IV
        for (int i = 174; i <= 177; i++) X[i].eq(0).post();
        // Initialization Register 3 - 0 + 3 x 1
        for (int i = 178; i <= 285; i++) X[i].eq(0).post();
        X[286].eq(0).post();
        X[287].eq(0).post();
        X[288].eq(0).post();
        Tuples tuplesft = tuplesft();
        Tuples tuplesfz = tuplesfz();
        Solver solver = m.getSolver();
        for (int r = 1; r <= Nb; r++) {
            m.table(new BoolVar[]{X[itrivium(r - 1, 243)], X[itrivium(r - 1, 286)], X[itrivium(r - 1, 287)], X[itrivium(r - 1, 288)], X[itrivium(r - 1, 69)], X[itrivium(r, 1)]}, tuplesft).post();
            m.table(new BoolVar[]{X[itrivium(r - 1, 66)], X[itrivium(r - 1, 91)], X[itrivium(r - 1, 92)], X[itrivium(r - 1, 93)], X[itrivium(r - 1, 171)], X[itrivium(r, 94)]}, tuplesft).post();
            m.table(new BoolVar[]{X[itrivium(r - 1, 162)], X[itrivium(r - 1, 175)], X[itrivium(r - 1, 176)], X[itrivium(r - 1, 177)], X[itrivium(r - 1, 264)], X[itrivium(r, 178)]}, tuplesft).post();
        }
//        m.table(new BoolVar[]{X[itrivium(Nb,65)], X[itrivium(Nb,92)], X[itrivium(Nb,161)], X[itrivium(Nb,176)], X[itrivium(Nb,242)], X[itrivium(Nb,287)], Z}, tuplesfz).post();
        Z.eq(1).post();
        int cpt = 0;
        while (solver.solve()) {
            cpt++;
            System.out.println("Solution nb" + cpt);
            for (int r = 0; r <= Nb; r++)
                PrintState(X, r);
        }
        solver.findAllSolutions();
        solver.printStatistics();
    }

    public static Tuples tuplesft() {//division property propagation through a+b*c+d+e=t
        Tuples tuples = new Tuples(true);
        tuples.add(1, 0, 0, 0, 0, 1);
        tuples.add(0, 0, 0, 1, 0, 1);
        tuples.add(0, 0, 0, 0, 1, 1);
        tuples.add(0, 1, 1, 0, 0, 1);
        for (int a : F)
            for (int b : F)
                for (int c : F)
                    for (int d : F)
                        for (int e : F)
                            if (!((a == 1 && b == 0 && c == 0 && d == 0 && e == 0) || (a == 0 && b == 0 && c == 0 && d == 1 && e == 0) || (a == 0 && b == 0 && c == 0 && d == 0 && e == 1) || (a == 0 && b == 1 && c == 1 && d == 0 && e == 0)))
                                tuples.add(a, b, c, d, e, 0);
        return tuples;
    }

    public static Tuples tuplesfz() {//division property propagation through a+b+c+d+e+f=z
        Tuples tuples = new Tuples(true);
        tuples.add(1, 0, 0, 0, 0, 0, 1);
        tuples.add(0, 1, 0, 0, 0, 0, 1);
        tuples.add(0, 0, 1, 0, 0, 0, 1);
        tuples.add(0, 0, 0, 1, 0, 0, 1);
        tuples.add(0, 0, 0, 0, 1, 0, 1);
        tuples.add(0, 0, 0, 0, 0, 1, 1);
        for (int a : F)
            for (int b : F)
                for (int c : F)
                    for (int d : F)
                        for (int e : F)
                            for (int f : F)
                                if (a + b + c + d + e + f != 1)
                                    tuples.add(a, b, c, d, e, f, 0);
        return tuples;
    }

    public static int itrivium(int r, int i) {
        if (r == 0) return i;//init state
        else if (i == 1) return 288 + (r - 1) * 3 + 1;//t3
        else if (i == 94) return 288 + (r - 1) * 3 + 2;//t1
        else if (i == 178) return 288 + (r - 1) * 3 + 3;//t2
        else return itrivium(r - 1, i - 1);
    }

    public static void PrintState(BoolVar[] X, int r) {
        System.out.println("State:" + r);
        for (int i = 1; i <= 93; i++) System.out.print(X[itrivium(r, i)].getValue() == 1 ? "1" : ".");
        System.out.println();
        for (int i = 94; i <= 177; i++) System.out.print(X[itrivium(r, i)].getValue() == 1 ? "1" : ".");
        System.out.println();
        for (int i = 178; i <= 288; i++) System.out.print(X[itrivium(r, i)].getValue() == 1 ? "1" : ".");
        System.out.println();
    }

    private static void verifind() {
        for (int r = 0; r <= 288; r++) {
            System.out.println("\nround: " + r);
            for (int i = 1; i <= 288; i++)
                System.out.print(" " + itrivium(r, i));
        }
    }
}