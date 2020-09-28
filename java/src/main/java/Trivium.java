import org.chocosolver.cutoffseq.LubyCutoffStrategy;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

public class Trivium {
    public static int NBROUNDS = 840;
    public static Tuples propagT;

    public static void main(String[] args) {
        Model m = new Model();
        long start = System.currentTimeMillis();
        solve(m, NBROUNDS);
        long end = System.currentTimeMillis();
        System.out.println("time for Trivium:" + NBROUNDS + " " + (end - start) + " in milliseconds");
    }

    public static void solve(Model m, int Nb) {
        BoolVar[][] X = m.boolVarMatrix(Nb + 1, 288);
        propagT = createpropagT();
        // Initialization Register 1 - Key k12 à 1
        for (int i = 0; i < 11; i++) X[0][i].eq(0).post();
        X[0][11].eq(1).post();
        for (int i = 12; i < 80; i++) X[0][i].eq(0).post();
        // Initialization Register 2 - IV iv[0] à iv[80] à 1 sauf 34 et 47
        for (int i = 93; i < 126; i++) X[0][i].eq(1).post();
//	X[0][126].eq(0).post(); // IV[34]
        for (int i = 127; i < 139; i++) X[0][i].eq(1).post();
//		X[0][139].eq(0).post(); // IV[47]
        for (int i = 140; i < 173; i++) X[0][i].eq(1).post();
//			X[0][173].eq(0).post();
//			X[0][174].eq(0).post();
//			X[0][175].eq(0).post();
//			X[0][176].eq(0).post();
        // Initialization Register 3 -
        // for (int i=177; i<285; i++) X[0][i].eq(0).post();
        //X[0][285].eq(0).post();
        // X[0][286].eq(0).post();
        // X[0][287].eq(0).post();
        for (int r = 1; r < Nb + 1; r++) {
            m.table(new BoolVar[]{X[r - 1][242], X[r - 1][285], X[r - 1][286], X[r - 1][287], X[r - 1][68], X[r][243], X[r][286], X[r][287], X[r][0], X[r][69]}, propagT).post();
            m.table(new BoolVar[]{X[r - 1][65], X[r - 1][90], X[r - 1][91], X[r - 1][92], X[r - 1][170], X[r][66], X[r][91], X[r][92], X[r][93], X[r][171]}, propagT).post();
            m.table(new BoolVar[]{X[r - 1][161], X[r - 1][174], X[r - 1][175], X[r - 1][176], X[r - 1][263], X[r][162], X[r][175], X[r][176], X[r][177], X[r][264]}, propagT).post();
            for (int j = 0; j < 288; j++)
                if (j != 65 && j != 90 && j != 91 && j != 92 && j != 170 && j != 161 && j != 174 && j != 175 && j != 176 && j != 263 && j != 242 && j != 285 && j != 286 && j != 287 && j != 68) {
                    X[r][j + 1].eq(X[r - 1][j]).post();
                }
        }
        // Last state
        for (int i = 0; i < 288; i++)
            if (i != 65 && i != 92 && i != 161 && i != 176 && i != 242 && i != 287)
                X[Nb][i].eq(0).post();
        m.sum(X[Nb], "=", 1).post();
        int cpt = 0;
        Solver solver = m.getSolver();
        while (solver.solve()) {
            cpt++;
            //	 for (int i=0; i < Nb+1; i++)
            System.out.println("Solution Number:" + cpt);
            PrintState(X, 0);
            PrintState(X, Nb);
        }
        System.out.println("Solutions:" + cpt);
        return;
    }

    public static void PrintState(BoolVar[][] X, int N) {
        System.out.println("State:" + N);
        for (int i = 0; i < 93; i++) System.out.print(X[N][i].getValue() + " ");
        System.out.println();
        for (int i = 93; i < 177; i++) System.out.print(X[N][i].getValue() + " ");
        System.out.println();
        for (int i = 177; i < 288; i++) System.out.print(X[N][i].getValue() + " ");
        System.out.println();
    }

    public static Tuples createpropagT() {
        Tuples tuples = new Tuples(true);
        tuples.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        tuples.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 1);

        tuples.add(1, 0, 0, 0, 0, 0, 0, 0, 1, 0);
        tuples.add(0, 1, 1, 0, 0, 0, 0, 0, 1, 0);
        tuples.add(0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
        tuples.add(0, 0, 0, 0, 1, 0, 0, 0, 1, 0);

        tuples.add(1, 0, 0, 0, 1, 0, 0, 0, 1, 1);
        tuples.add(0, 1, 1, 0, 1, 0, 0, 0, 1, 1);
        tuples.add(0, 0, 0, 1, 1, 0, 0, 0, 1, 1);
        tuples.add(0, 0, 0, 0, 1, 0, 0, 0, 1, 1);

        tuples.add(0, 0, 1, 0, 0, 0, 0, 1, 0, 0);

        tuples.add(0, 0, 1, 0, 1, 0, 0, 1, 0, 1);

        tuples.add(1, 0, 1, 0, 0, 0, 0, 1, 1, 0);
        tuples.add(0, 1, 1, 0, 0, 0, 0, 1, 1, 0);
        tuples.add(0, 0, 1, 1, 0, 0, 0, 1, 1, 0);
        tuples.add(0, 0, 1, 0, 1, 0, 0, 1, 1, 0);

        tuples.add(1, 0, 1, 0, 1, 0, 0, 1, 1, 1);
        tuples.add(0, 1, 1, 0, 1, 0, 0, 1, 1, 1);
        tuples.add(0, 0, 1, 1, 1, 0, 0, 1, 1, 1);
        tuples.add(0, 0, 1, 0, 1, 0, 0, 1, 1, 1);

        tuples.add(0, 1, 0, 0, 0, 0, 1, 0, 0, 0);

        tuples.add(0, 1, 0, 0, 1, 0, 1, 0, 0, 1);

        tuples.add(1, 1, 0, 0, 0, 0, 1, 0, 1, 0);
        tuples.add(0, 1, 1, 0, 0, 0, 1, 0, 1, 0);
        tuples.add(0, 1, 0, 1, 0, 0, 1, 0, 1, 0);
        tuples.add(0, 1, 0, 0, 1, 0, 1, 0, 1, 0);

        // page 2
        tuples.add(1, 1, 0, 0, 1, 0, 1, 0, 1, 1);
        tuples.add(0, 1, 0, 1, 1, 0, 1, 0, 1, 1);
        tuples.add(0, 1, 0, 0, 1, 0, 1, 0, 1, 1);
        tuples.add(0, 1, 1, 0, 1, 0, 1, 0, 1, 1);

        tuples.add(0, 1, 1, 0, 0, 0, 1, 1, 0, 0);

        tuples.add(0, 1, 1, 0, 1, 0, 1, 1, 0, 1);

        tuples.add(1, 1, 1, 0, 0, 0, 1, 1, 1, 0);
        tuples.add(0, 1, 1, 0, 0, 0, 1, 1, 1, 0);
        tuples.add(0, 1, 1, 1, 0, 0, 1, 1, 1, 0);
        tuples.add(0, 1, 1, 0, 1, 0, 1, 1, 1, 0);

        tuples.add(1, 0, 0, 0, 0, 1, 0, 0, 0, 0);

        tuples.add(1, 0, 0, 0, 1, 1, 0, 0, 0, 1);

        tuples.add(1, 0, 0, 0, 0, 1, 0, 0, 1, 0);
        tuples.add(1, 1, 1, 0, 0, 1, 0, 0, 1, 0);
        tuples.add(1, 0, 0, 1, 0, 1, 0, 0, 1, 0);
        tuples.add(1, 0, 0, 0, 1, 1, 0, 0, 1, 0);

        tuples.add(1, 0, 0, 0, 1, 1, 0, 0, 1, 1);
        tuples.add(1, 1, 1, 0, 1, 1, 0, 0, 1, 1);
        tuples.add(1, 0, 0, 1, 1, 1, 0, 0, 1, 1);
        tuples.add(1, 0, 0, 0, 1, 1, 0, 0, 1, 1);

        tuples.add(1, 0, 1, 0, 0, 1, 0, 1, 0, 0);

        tuples.add(1, 0, 1, 0, 1, 1, 0, 1, 0, 1);

        tuples.add(1, 0, 1, 0, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 1, 1, 0, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 0, 1, 1, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 0, 1, 0, 1, 1, 0, 1, 1, 0);

        // page 3

        tuples.add(1, 0, 1, 0, 1, 1, 0, 1, 1, 1);
        tuples.add(1, 1, 1, 0, 1, 1, 0, 1, 1, 1);
        tuples.add(1, 0, 1, 1, 1, 1, 0, 1, 1, 1);

        tuples.add(1, 1, 0, 0, 0, 1, 1, 0, 0, 0);

        tuples.add(1, 1, 0, 0, 1, 1, 1, 0, 0, 1);

        tuples.add(1, 1, 0, 0, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 1, 0, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 0, 1, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 0, 0, 1, 1, 1, 0, 1, 0);

        tuples.add(1, 1, 0, 0, 1, 1, 1, 0, 1, 1);
        tuples.add(1, 1, 1, 0, 1, 1, 1, 0, 1, 1);
        tuples.add(1, 1, 0, 1, 1, 1, 1, 0, 1, 1);

        tuples.add(1, 1, 1, 0, 0, 1, 1, 1, 0, 0);

        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 0, 1);

        tuples.add(1, 1, 1, 0, 0, 1, 1, 1, 1, 0);
        tuples.add(1, 1, 1, 1, 0, 1, 1, 1, 1, 0);
        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 1, 0);

        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 1, 1);
        tuples.add(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        return tuples;
    }
}