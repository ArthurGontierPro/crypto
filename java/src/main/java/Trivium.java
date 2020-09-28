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
    public static Model m;
    public static Tuples calculT;
    public static Tuples propagT;

    public static Tuples tableXOR;
    public static Tuples propagXOR;

    public static boolean[][] T;
    public static BoolVar[][] X;

    public static void main(String[] args) {
        Model m = new Model();
        long start = System.currentTimeMillis();

        solve(m, NBROUNDS);
        long end = System.currentTimeMillis();
        System.out.println("time for Trivium:" + NBROUNDS + " " + (end - start) + " in milliseconds");

    }


    public static void solve(Model m, int Nb) {

        BoolVar[][] X = m.boolVarMatrix(Nb + 1, 288);
        boolean[][] T = new boolean[Nb + 1][288];

        propagT = createpropagT();


        // Propagation constants false means  0 / true means not fixed to 0
        for (int i = 1; i <= 80; i++) T[0][i - 1] = true;
        for (int i = 81; i <= 93; ++i) T[0][i - 1] = false;
        for (int i = 93; i < 93 + 80; i++) T[0][i] = true;
        for (int i = 93 + 80; i <= 284; ++i) T[0][i] = false;
        for (int i = 285; i <= 287; i++) T[0][i] = true;

        for (int r = 1; r < Nb + 1; r++) {
            if (T[r - 1][242] == false && (T[r - 1][285] == false || T[r - 1][286] == false)
                    && T[r - 1][287] == false && T[r - 1][68] == false) {
                T[r][0] = false;
            } else {
                T[r][0] = true;
            }

            if (T[r - 1][65] == false && (T[r - 1][90] == false || T[r - 1][91] == false)
                    && T[r - 1][92] == false && T[r - 1][170] == false) {
                T[r][93] = false;
            } else {
                T[r][93] = true;
            }

            if (T[r - 1][161] == false && (T[r - 1][174] == false || T[r - 1][175] == false)
                    && T[r - 1][176] == false && T[r - 1][263] == false) {
                T[r][177] = false;
            } else {
                T[r][177] = true;
            }

            for (int j = 0; j < 287; j++)
                if (j != 92 && j != 176) {
                    T[r][j + 1] = T[r - 1][j];
                }
        }

        for (int r = 0; r < Nb + 1; r++) {
            for (int i = 0; i < 288; i++) {
                if (T[r][i] == false) {
                    X[r][i].eq(0).post();
                }
            }
        }


        //Initialisation

        // cube
        for (int i = 1; i <= 80; ++i) {
            if (i != 34 && i != 47) X[0][i + 92].eq(1).post();
            else X[0][i + 92].eq(0).post();
        }

        // Monome clé (test)
        for (int i = 1; i <= 80; ++i) {
            if (i != 12) X[0][i - 1].eq(0).post();
            else X[0][i - 1].eq(1).post();
        }


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
            if (i != 65 && i != 92 && i != 161 && i != 176 && i != 242 && i != 287) {
                X[Nb][i].eq(0).post();
            }


        m.sum(X[Nb], "=", 1).post();

        int cpt = 0;
        Solver solver = m.getSolver();
        while (solver.solve()) {
            cpt++;
            System.out.println("Solution Number:" + cpt);
            PrintState(X, 0);
            PrintState(X, Nb);
        }

        System.out.println("Total Solutions:" + cpt);
        return;

    }


    public static void PrintState(BoolVar[][] X, int N) {
        System.out.println("State:" + N);

        for (int i = 0; i < 93; i++) {
            System.out.print(X[N][i].getValue() + " ");
        }
        System.out.println();

        for (int i = 93; i < 177; i++) {
            System.out.print(X[N][i].getValue() + " ");
        }
        System.out.println();

        for (int i = 177; i < 288; i++) {
            System.out.print(X[N][i].getValue() + " ");
        }
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

        //tuples.add(1,0,0,0,1,1,0,0,1,1);
        tuples.add(1, 1, 1, 0, 1, 1, 0, 0, 1, 1);
        tuples.add(1, 0, 0, 1, 1, 1, 0, 0, 1, 1);
        //tuples.add(1,0,0,0,1,1,0,0,1,1);

        tuples.add(1, 0, 1, 0, 0, 1, 0, 1, 0, 0);

        tuples.add(1, 0, 1, 0, 1, 1, 0, 1, 0, 1);

        tuples.add(1, 0, 1, 0, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 1, 1, 0, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 0, 1, 1, 0, 1, 0, 1, 1, 0);
        tuples.add(1, 0, 1, 0, 1, 1, 0, 1, 1, 0);

        // page 3

        // tuples.add(1,0,1,0,1,1,0,1,1,1);
        tuples.add(1, 1, 1, 0, 1, 1, 0, 1, 1, 1);
        tuples.add(1, 0, 1, 1, 1, 1, 0, 1, 1, 1);

        tuples.add(1, 1, 0, 0, 0, 1, 1, 0, 0, 0);

        tuples.add(1, 1, 0, 0, 1, 1, 1, 0, 0, 1);

        tuples.add(1, 1, 0, 0, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 1, 0, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 0, 1, 0, 1, 1, 0, 1, 0);
        tuples.add(1, 1, 0, 0, 1, 1, 1, 0, 1, 0);

        // tuples.add(1,1,0,0,1,1,1,0,1,1);
        tuples.add(1, 1, 1, 0, 1, 1, 1, 0, 1, 1);
        tuples.add(1, 1, 0, 1, 1, 1, 1, 0, 1, 1);

        tuples.add(1, 1, 1, 0, 0, 1, 1, 1, 0, 0);

        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 0, 1);

        // tuples.add(1,1,1,0,0,1,1,1,1,0);
        tuples.add(1, 1, 1, 1, 0, 1, 1, 1, 1, 0);
        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 1, 0);

        tuples.add(1, 1, 1, 0, 1, 1, 1, 1, 1, 1);
        tuples.add(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        return tuples;
    }

}