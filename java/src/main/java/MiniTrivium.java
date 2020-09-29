import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.BoolVar;

public class MiniTrivium {
    public static int[] F = {0, 1};
    public static int NBROUNDS = 30;

    public static void main(String[] args) {
        Model m = new Model();
        int Nb = NBROUNDS;
        BoolVar[][] X = m.boolVarMatrix(Nb + 1, 3);

        X[0][0].eq(1).post();
        X[0][1].eq(0).post();
        X[0][2].eq(0).post();
        Tuples tuplesfr = tuplesfr();
        for (int r = 1; r <= Nb; r++) {
            m.table(new BoolVar[]{X[r-1][0],X[r-1][1],X[r-1][2],X[r][0],X[r][1],X[r][2]}, tuplesfr).post();
        }
        X[Nb][0].eq(1).post();
        X[Nb][1].eq(0).post();
        X[Nb][2].eq(0).post();
        Solver solver = m.getSolver();
        while (solver.solve()) {}
        solver.printStatistics();
        PrintState(X, Nb);
    }

    public static Tuples tuplesfr() {//division property propagation through a+b*c+d+e=t
        Tuples tuples = new Tuples(true);
        tuples.add(1, 0, 0, 1, 0, 0);
        tuples.add(1, 0, 0, 0, 1, 0);
        tuples.add(0, 1, 0, 0, 0, 1);
        tuples.add(0, 1, 1, 1, 0, 0);
        tuples.add(1, 0, 0, 1, 1, 0);
        tuples.add(1, 1, 1, 1, 1, 0);
        tuples.add(1, 1, 0, 0, 1, 1);
        tuples.add(1, 1, 0, 1, 1, 1);
        tuples.add(1, 1, 1, 1, 1, 1);
        tuples.add(1, 1, 0, 1, 0, 1);
        tuples.add(0, 1, 1, 1, 0, 1);
        tuples.add(0, 0, 0, 0, 0, 0);
        return tuples;
    }

    public static void PrintState(BoolVar[][] X, int r) {
        System.out.println("State:" + r);
        for (int i = 0; i <= 2; i++) System.out.print(X[r][i].getValue() == 1 ? "1" : ".");
        System.out.println();
    }
}