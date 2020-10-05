/*
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2020, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 *
 * See LICENSE file in the project root for full license information.
 */
//package org.chocosolver.examples.integer;

import org.chocosolver.solver.DefaultSettings;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.learn.XParameters;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.DomOverWDeg;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.tools.ArrayUtils;

/**
 * <br/>
 *
 * @author Charles Prud'homme, Arthur Gontier
 * @since 02/10/2020
 */
public class TriviumDivPropCopy {
    public static int[] F = {0, 1};
    public static int NBROUNDS = 840;

    public static void main(String[] args) throws ContradictionException {
        Model m = new Model(new DefaultSettings().setEnableSAT(false));
        int Nb = NBROUNDS;
        BoolVar[][] X = new BoolVar[Nb + 1][288];
        BoolVar[][] Copy1 = new BoolVar[Nb + 1][5];
        BoolVar[][] Copy2 = new BoolVar[Nb + 1][5];
        BoolVar[][] Copy3 = new BoolVar[Nb + 1][5];
        // Constantes à 0
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

        //Constraints for each round
        int[] tt = {243, 286, 287, 288, 69, 66, 91, 92, 93, 171, 94, 162, 175, 176, 177, 264, 178};
        int[] tt3 = {243, 286, 287, 288, 69, 1};//a+bc+d+e=t
        int[] tt1 = {66, 91, 92, 93, 171, 94};
        int[] tt2 = {162, 175, 176, 177, 264, 178};
        int[] ttr = {242, 285, 286, 287, 68, 65, 90, 91, 92, 170, 161, 174, 175, 176, 263, 177};
        int[] ttrr = {243, 286, 287, 0, 69, 66, 91, 92, 93, 171, 162, 175, 176, 177, 264, 178};
        for (int r = 1; r < Nb + 1; r++) {
            //shift constraints
            for (int j = 0; j < 288; j++) {
                if (j != 65 && j != 90 && j != 91 && j != 92 && j != 170 && j != 161 && j != 174 && j != 175 && j != 176 && j != 263 && j != 242 && j != 285 && j != 286 && j != 287 && j != 68) {
                    assert X[r - 1][j] != null;
                    X[r][j + 1] = X[r - 1][j];
                }
            }
            propagT(m, tt3, r, X, Copy3);
            propagT(m, tt1, r, X, Copy1);
            propagT(m, tt2, r, X, Copy2);
        }

        m.sum(X[Nb], "=", 1).post();

        Solver solver = m.getSolver();
//        XParameters.PRINT_CLAUSE = true;
//        solver.setLearningSignedClauses();
//        Search.Restarts.LUBY.declare(solver, 500, 0.d, 5000);
        solver.showDashboard(); // a supprimer pour accélerer
        solver.propagate();
        int fixed = 0;
        for (BoolVar b : m.retrieveBoolVars()) {
            if (b.getDomainSize() == 1) {
                fixed++;
            }
        }
        System.out.println("=> fixed: " + fixed);

        // solving strategy
        BoolVar[] varsorder = new BoolVar[288 * (NBROUNDS + 1)];
        int cvarsorder = 0;
        for (int r = NBROUNDS; r >= 0; r--) {
            for (int i : tt1) varsorder[cvarsorder++] = X[r][i - 1];
            for (int i : tt2) varsorder[cvarsorder++] = X[r][i - 1];
            for (int i : tt3) varsorder[cvarsorder++] = X[r][i - 1];
            for (int j = 0; j < 288; j++)
                if (j != 0 && j != 93 && j != 177 && j != 65 && j != 90 && j != 91 && j != 92 && j != 170 && j != 161 && j != 174 && j != 175 && j != 176 && j != 263 && j != 242 && j != 285 && j != 286 && j != 287 && j != 68)
                    varsorder[cvarsorder++] = X[r][j];
        }
        solver.setSearch(Search.inputOrderUBSearch(varsorder));

        //Solving
        while (solver.solve()) {
        }
        solver.printStatistics();
    }

    public static void propagT(Model m, int[] a, int r, BoolVar[][] X, BoolVar[][] Copy) {
        //copy of a,b,c,e
        assert Copy[r][0] == null;
        assert Copy[r][1] == null;
        assert Copy[r][2] == null;
        assert Copy[r][3] == null;
        assert Copy[r][4] == null;
        Copy[r][0] = m.boolVar();
        Copy[r][1] = Copy[r][2] = Copy[r][3] = m.boolVar(); // same variable y=b*c
        Copy[r][4] = m.boolVar();
        for (int i = 0; i < 5; i++)
            if (i != 3) {//d is used only once
                if (X[r - 1][a[i] - 1] == null) X[r - 1][a[i] - 1] = m.boolVar();
                if (X[r][a[i]] == null) X[r][a[i]] = m.boolVar();
                m.addClausesBoolOrEqVar(Copy[r][i], X[r][a[i]], X[r - 1][a[i] - 1]);
            }
        //sum setup
        if (X[r - 1][a[3] - 1] == null) X[r - 1][a[3] - 1] = m.boolVar();
        BoolVar[] tmp = {Copy[r][0], Copy[r][3], X[r - 1][a[3] - 1], Copy[r][4]};
        if (X[r][a[5] - 1] == null) X[r][a[5] - 1] = m.boolVar();
        m.sum(tmp, "=", X[r][a[5] - 1]).post();
    }
    public static boolean in(int a, int[] t) {
        for (int i : t)
            if (i == a)
                return true;
        return false;
    }
}