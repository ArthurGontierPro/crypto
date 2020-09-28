import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.BoolVar;

public class TriviumDivPropCopy {
    public static int[] F = {0, 1};
    public static int NBROUNDS = 840;

    public static void main(String[] args) {
        Model m = new Model();
        int Nb = NBROUNDS;
        BoolVar[][] X = m.boolVarMatrix(Nb + 1, 288);
        BoolVar[][] Copy1 = m.boolVarMatrix(Nb + 1, 6);
        BoolVar[][] Copy2 = m.boolVarMatrix(Nb + 1, 6);
        BoolVar[][] Copy3 = m.boolVarMatrix(Nb + 1, 6);
        // constantes à 0
        for (int i = 81; i <= 93; ++i) X[0][i - 1].eq(0).post();
        for (int i = 93 + 80; i <= 285; ++i) X[0][i - 1].eq(0).post();
        // cube
        for (int i = 1; i <= 80; ++i) {
            if (i != 34 && i != 47) X[0][i + 92].eq(1).post();
            else X[0][i + 92].eq(0).post();
        }
        // Monome clé (test papier 441 p16)
        for (int i = 1; i <= 80; ++i) {
            if (i != 12) X[0][i - 1].eq(0).post();
            else X[0][i - 1].eq(1).post();
        }
        //Déclaration des contraintes pour chaque round
        for (int r = 1; r < Nb + 1; r++) {
            int[] tt3 = {243, 286, 287, 288, 69, 1};
            int[] tt1 = {66, 91, 92, 93, 171, 94};
            int[] tt2 = {162, 175, 176, 177, 264, 178};
            propagT(r, tt3, m, X, Copy3);
            propagT(r, tt1, m, X, Copy1);
            propagT(r, tt2, m, X, Copy2);
            for (int j = 0; j < 287; j++)
                if (j != 1 && j != 94 && j != 178)
                    X[r][j + 1].eq(X[r - 1][j]).post();
        }
        Solver solver = m.getSolver();
        while (solver.solve()) {}
        solver.printStatistics();
    }

    public static void propagT(int r, int[] a, Model m, BoolVar[][] X, BoolVar[][] Copy) {
        for (int i = 0; i < 5; i++)
            m.table(new BoolVar[]{X[r - 1][a[i] - 1], Copy[r][i], X[r][a[i] - 1]}, propagCopy()).post();
        Copy[r][5].eq(Copy[r][1]).post();
        Copy[r][5].eq(Copy[r][2]).post();
        BoolVar[] tmp = {Copy[r][0], Copy[r][3], Copy[r][4], Copy[r][5]};
        m.sum(tmp, "=", X[r][a[5] - 1]).post();
//        m.arithm(Copy[r][0],"+",Copy[r][1],"*",Copy[r][2],"+",Copy[r][3],"+",Copy[r][4]).eq(X[r][a[5]]).post();
    }

    public static Tuples propagCopy() {//x=a=b
        Tuples tuples = new Tuples(true);
        tuples.add(0, 0, 0);
        tuples.add(1, 1, 0);
        tuples.add(1, 0, 1);
        tuples.add(1, 1, 1);
        return tuples;
    }
}
