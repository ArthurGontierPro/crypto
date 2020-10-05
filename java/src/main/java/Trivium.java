import org.chocosolver.cutoffseq.LubyCutoffStrategy;
import org.chocosolver.solver.DefaultSettings;
import org.chocosolver.util.tools.ArrayUtils;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.DomOverWDeg;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.DomOverWDeg;
import org.chocosolver.util.tools.ArrayUtils;

public class Trivium {
    public static int NBROUNDS;
    public static int NBONE;
    public static Tuples propagT;

    public static void main(String[] args) {
        NBROUNDS = Integer.parseInt(args[0]);
        NBONE = Integer.parseInt(args[1]); // 0 means no obj to improve
        solve(NBROUNDS, NBONE);
    }

    public static void solve(int Nb, int s) {
        Model m = new Model(new DefaultSettings().setEnableSAT(false));
        BoolVar[][] X = new BoolVar[Nb + 1][288];
        propagT = createpropagT();

        //Initialisation
        for (int i = 81; i <= 93; i++) X[0][i - 1] = m.boolVar(false);
        for (int i = 93 + 81; i <= 285; i++) X[0][i - 1] = m.boolVar(false);
        // cube
        for (int i = 1; i <= 80; ++i) {
            if (i != 34 && i != 47) {
                X[0][i + 92] = m.boolVar(true);
            } else {
                X[0][i + 92] = m.boolVar(false);
            }
        }
        // Monome clé (test papier 441 p16)
        for (int i = 1; i <= 80; ++i) {
            if (i != 12) {
                X[0][i - 1] = m.boolVar(false);
            } else {
                X[0][i - 1] = m.boolVar(true);
            }
        }
        // Last state
        for (int i = 0; i < 288; i++)
            if (i != 65 && i != 92 && i != 161 && i != 176 && i != 242 && i != 287)
                X[Nb][i] = m.boolVar(false);

        int[] tt = {243, 286, 287, 288, 69, 66, 91, 92, 93, 171, 162, 175, 176, 177, 264, 178};
        int[] tt3 = {243, 286, 287, 288, 69, 1};//a+bc+d+e=t
        int[] tt1 = {66, 91, 92, 93, 171, 94};
        int[] tt2 = {162, 175, 176, 177, 264, 178};
        int[] ttr = {242, 285, 286, 287, 68, 65, 90, 91, 92, 170, 161, 174, 175, 176, 263, 177};
        int[] ttrr = {243, 286, 287, 0, 69, 66, 91, 92, 93, 171, 162, 175, 176, 177, 264, 178};

        // propagation
        for (int r = 1; r < Nb + 1; r++) {
            for (int j = 0; j < 288; j++)
                if (j != 65 && j != 90 && j != 91 && j != 92 && j != 170 && j != 161 && j != 174 && j != 175 && j != 176 && j != 263 && j != 242 && j != 285 && j != 286 && j != 287 && j != 68) {
                    assert X[r - 1][j] != null;
                    X[r][j + 1] = X[r - 1][j];
                }
            for (int j : ttr)
                if (X[r - 1][j] == null) X[r - 1][j] = m.boolVar();
            for (int j : ttrr)
                if (X[r][j] == null) X[r][j] = m.boolVar();
            m.table(new BoolVar[]{X[r - 1][242], X[r - 1][285], X[r - 1][286], X[r - 1][287], X[r - 1][68], X[r][243], X[r][286], X[r][287], X[r][0], X[r][69]}, propagT).post();
            m.table(new BoolVar[]{X[r - 1][65], X[r - 1][90], X[r - 1][91], X[r - 1][92], X[r - 1][170], X[r][66], X[r][91], X[r][92], X[r][93], X[r][171]}, propagT).post();
            m.table(new BoolVar[]{X[r - 1][161], X[r - 1][174], X[r - 1][175], X[r - 1][176], X[r - 1][263], X[r][162], X[r][175], X[r][176], X[r][177], X[r][264]}, propagT).post();
        }
        m.sum(X[Nb], "=", 1).post();
        // solving part
//        BoolVar[] varsfirst = new BoolVar[15 * (NBROUNDS + 1)];
//        BoolVar[] varssecond = new BoolVar[273 * (NBROUNDS + 1)];
//        int cvarsfirst = 0;
//        int cvarssecond = 0;
//        for (int r = NBROUNDS; r >= 0; r--) {
//            for (int i = 0; i < 288; i++) {
//                if (i == 65 || i == 90 || i == 91 || i == 92 || i == 170 ||
//                        i == 161 || i == 174 || i == 175 || i == 176 || i == 263 ||
//                        i == 242 || i == 285 || i == 286 || i == 287 || i == 68
//                ) {
//                    varsfirst[cvarsfirst++] = X[r][i];
//                } else {
//                    varssecond[cvarssecond++] = X[r][i];
//                }
//            }
//        }
        Solver solver = m.getSolver();
//        solver.setSearch(
//                Search.lastConflict(
//                        new DomOverWDeg(ArrayUtils.append(varsfirst, varssecond), 0, new IntDomainMin())));
        // solving strategy
        BoolVar[] varsorder = new BoolVar[288 * (NBROUNDS + 1)];
        int cvarsorder = 0;
        for (int r = NBROUNDS; r >= 0; r--) {
            for (int i : tt1) varsorder[cvarsorder++] = X[r][i - 1];
            for (int i : tt2) varsorder[cvarsorder++] = X[r][i - 1];
            for (int i : tt3) varsorder[cvarsorder++] = X[r][i - 1];
            for (int j = 0; j < 288; j++)
                if (!in(j+1, tt1) && !in(j+1, tt2) && !in(j+1, tt3))
                    varsorder[cvarsorder++] = X[r][j];
        }
        solver.setSearch(Search.inputOrderUBSearch(varsorder));

        solver.showDashboard(); // a supprimer pour accélerer
        while (solver.solve()) {
        }
        return;
    }

    public static boolean in(int a, int[] t) {
        for (int i : t)
            if (i == a)
                return true;
        return false;
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