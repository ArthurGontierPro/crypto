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
	 public static int NBROUNDS = 92;
	 public static Model m;
	 public static Tuples calculT;
	 public static Tuples propagT;
	 
	 public static Tuples tableXOR;
	 public static Tuples propagXOR;
	 
	 public	static BoolVar[][] X;
	 public static BoolVar Z;
	 
	public static void main(String [] args) {
		 Model m = new Model();
		 
		 
		long start = System.currentTimeMillis();
		
		solve(m,NBROUNDS);
		long end = System.currentTimeMillis();
		System.out.println("time for Trivium:" + NBROUNDS  + " " + (end - start) + " in milliseconds");
	 
		
	}
	
	
	public static void solve(Model m, int Nb) {
	
		
	
	BoolVar[][] X =m.boolVarMatrix(Nb+1,288);
		
	BoolVar Z = m.boolVar();
	 
		
		 calculT = createT();
		 propagT = createpropagT();
		 tableXOR = createXOR();
		 propagXOR = createpropagXOR();
		
		// Initialization Register 1 - Key + 0
		
		 X[0][0].eq(1).post();
		 for (int i=1; i< 93; i++) X[0][i].eq(0).post();
		 // Initialization Register 2 - IV + 0
		 for (int i=93; i<173;i++) X[0][i].eq(0).post();
		 for (int i=173; i< 177;i++) X[0][i].eq(0).post();
		 // Initialization Register 3 - 0 + 3 x 1
		 for (int i=177; i<285; i++) X[0][i].eq(0).post();
		 X[0][285].eq(0).post();
		 X[0][286].eq(0).post();
		 X[0][287].eq(0).post();

		 
		 for (int r=1; r< Nb+1; r++) {
			 m.table(new BoolVar[]{X[r-1][242],X[r-1][285], X[r-1][286],X[r-1][287],X[r-1][68],X[r][243],X[r][286],X[r][287],X[r][0],X[r][69]}, propagT).post();
			 m.table(new BoolVar[]{X[r-1][65],X[r-1][90], X[r-1][91],X[r-1][92],X[r-1][170],X[r][66],X[r][91],X[r][92],X[r][93],X[r][171]}, propagT).post();
			 m.table(new BoolVar[]{X[r-1][161],X[r-1][174], X[r-1][175],X[r-1][176],X[r-1][263],X[r][162],X[r][175],X[r][176],X[r][177],X[r][264]}, propagT).post(); 

			 for (int j=0; j<288; j++) 
				 if (j != 65 && j!=90 && j != 91 && j !=92 && j !=170 && j!=161 && j!=174 && j!=175 && j!=176 && j != 263 && j!= 242 && j!= 285 && j!= 286 && j!=287 && j!=68)  
				 { X[r][j+1].eq(X[r-1][j]).post();}
				
		 }

		 m.table(new BoolVar[] {X[Nb][65], X[Nb][92], X[Nb][161],X[Nb][176], X[Nb][242], X[Nb][287], Z}, propagXOR).post();

		 Z.eq(1).post();
		 
		 int cpt =0;
		 Solver solver = m.getSolver();
		 while(solver.solve()) {
		
			 cpt++;

			 System.out.println("Solution nb" + cpt);
			 
		//	 for (int i=0; i < Nb; i++)
		 	PrintState(X, Nb-1);
				
				
		 }
		 System.out.println("Solutions:" + cpt);
				return;
		 		 
	}
	
	
	public static void PrintState(BoolVar[][] X, int N) {
		System.out.println("State:" + N);
		
    	for (int i=0;i < 93; i++) {
    	System.out.print(X[N][i].getValue() + " ");
    	}
    	System.out.println();
    	
    	for (int i=93;i < 177; i++) {
        	System.out.print(X[N][i].getValue() + " ");
        	}
        	System.out.println();
        	
        	for (int i=177;i < 288; i++) {
	        	System.out.print(X[N][i].getValue() + " ");
	        	}
	        	System.out.println();
 
	
	}
	
	public static void PrintStream(BoolVar[] Z, int Nstart, int Nend) {
		System.out.println("Stream:");
    	for (int i=Nstart;i < Nend; i++) {
    	System.out.print(Z[i].getValue() + " ");
    	}
    	System.out.println();
    	
	
	}
	
	public static Tuples createpropagT() {
		Tuples tuples = new Tuples(true);
		tuples.add(0,0,0,0,0,0,0,0,0,0);
		
		tuples.add(1,0,0,0,0,1,0,0,0,0);
		tuples.add(1,0,0,0,0,0,0,0,1,0);
		tuples.add(1,0,0,0,0,1,0,0,1,0);
		
		tuples.add(0,1,0,0,0,0,1,0,0,0);
		tuples.add(0,0,1,0,0,0,0,1,0,0);
		tuples.add(0,0,0,1,0,0,0,0,1,0);
	
		tuples.add(0,0,0,0,1,0,0,0,0,1);
		tuples.add(0,0,0,0,1,0,0,0,1,0);
		tuples.add(0,0,0,0,1,0,0,0,1,1);

		tuples.add(0,1,1,0,0,0,1,1,0,0);
		tuples.add(0,1,1,0,0,0,0,0,1,0);
		tuples.add(0,1,1,0,0,0,1,0,1,0);
		tuples.add(0,1,1,0,0,0,0,1,1,0);
		tuples.add(0,1,1,0,0,0,1,1,1,0);
		
		return tuples;
	}
	
	// calcul des ti - tuple (a,b,c,d,e,d,f) -   a + b + cd + e = f
	public static Tuples createT() {
		Tuples tuples = new Tuples(true);
		tuples.add(0,0,0,0,0,0);
		tuples.add(0,0,0,0,1,1);
		tuples.add(0,0,0,1,0,0);
		tuples.add(0,0,0,1,1,1);

		tuples.add(0,0,1,0,0,0);
		tuples.add(0,0,1,0,1,1);
		tuples.add(0,0,1,1,0,1);
		tuples.add(0,0,1,1,1,0);

		tuples.add(0,1,0,0,0,1);
		tuples.add(0,1,0,0,1,0);
		tuples.add(0,1,0,1,0,1);
		tuples.add(0,1,0,1,1,0);

		tuples.add(0,1,1,0,0,1);
		tuples.add(0,1,1,0,1,0);
		tuples.add(0,1,1,1,0,0);
		tuples.add(0,1,1,1,1,1);

		tuples.add(1,0,0,0,0,1);
		tuples.add(1,0,0,0,1,0);
		tuples.add(1,0,0,1,0,1);
		tuples.add(1,0,0,1,1,0);

		tuples.add(1,0,1,0,0,1);
		tuples.add(1,0,1,0,1,0);
		tuples.add(1,0,1,1,0,0);
		tuples.add(1,0,1,1,1,1);

		tuples.add(1,1,0,0,0,0);
		tuples.add(1,1,0,0,1,1);
		tuples.add(1,1,0,1,0,0);
		tuples.add(1,1,0,1,1,1);

		tuples.add(1,1,1,0,0,0);
		tuples.add(1,1,1,0,1,1);
		tuples.add(1,1,1,1,0,1);
		tuples.add(1,1,1,1,1,0);

		return tuples;
	}

	public static Tuples createpropagXOR() {
		Tuples tuples = new Tuples(true);
		tuples.add(0,0,0,0,0,0,0);
		tuples.add(1,0,0,0,0,0,1);
		tuples.add(0,1,0,0,0,0,1);
		tuples.add(0,0,1,0,0,0,1);
		tuples.add(0,0,0,1,0,0,1);
		tuples.add(0,0,0,0,1,0,1);
		tuples.add(0,0,0,0,0,1,1);
		
		return tuples;
	}
	
	public static Tuples createXOR() {
		Tuples tuples = new Tuples(true);
		tuples.add(0,0,0,0,0,0,0);
		tuples.add(0,0,0,0,0,1,1);
		tuples.add(0,0,0,0,1,0,1);
		tuples.add(0,0,0,0,1,1,0);
		
		tuples.add(0,0,0,1,0,0,1);
		tuples.add(0,0,0,1,0,1,0);
		tuples.add(0,0,0,1,1,0,0);
		tuples.add(0,0,0,1,1,1,1);
		
		tuples.add(0,0,1,0,0,0,1);
		tuples.add(0,0,1,0,0,1,0);
		tuples.add(0,0,1,0,1,0,0);
		tuples.add(0,0,1,0,1,1,1);
		
		tuples.add(0,0,1,1,0,0,0);
		tuples.add(0,0,1,1,0,1,1);
		tuples.add(0,0,1,1,1,0,1);
		tuples.add(0,0,1,1,1,1,0);
		
		tuples.add(0,1,0,0,0,0,1);
		tuples.add(0,1,0,0,0,1,0);
		tuples.add(0,1,0,0,1,0,0);
		tuples.add(0,1,0,0,1,1,1);
		
		tuples.add(0,1,0,1,0,0,0);
		tuples.add(0,1,0,1,0,1,1);
		tuples.add(0,1,0,1,1,0,1);
		tuples.add(0,1,0,1,1,1,0);
		
		tuples.add(0,1,1,0,0,0,0);
		tuples.add(0,1,1,0,0,1,1);
		tuples.add(0,1,1,0,1,0,1);
		tuples.add(0,1,1,0,1,1,0);
		
		tuples.add(0,1,1,1,0,0,1);
		tuples.add(0,1,1,1,0,1,0);
		tuples.add(0,1,1,1,1,0,0);
		tuples.add(0,1,1,1,1,1,1);		
		
		tuples.add(1,0,0,0,0,0,1);
		tuples.add(1,0,0,0,0,1,0);
		tuples.add(1,0,0,0,1,0,0);
		tuples.add(1,0,0,0,1,1,1);
		
		tuples.add(1,0,0,1,0,0,0);
		tuples.add(1,0,0,1,0,1,1);
		tuples.add(1,0,0,1,1,0,1);
		tuples.add(1,0,0,1,1,1,0);
		
		tuples.add(1,0,1,0,0,0,0);
		tuples.add(1,0,1,0,0,1,1);
		tuples.add(1,0,1,0,1,0,1);
		tuples.add(1,0,1,0,1,1,0);
		
		tuples.add(1,0,1,1,0,0,1);
		tuples.add(1,0,1,1,0,1,0);
		tuples.add(1,0,1,1,1,0,0);
		tuples.add(1,0,1,1,1,1,1);
		
		tuples.add(1,1,0,0,0,0,0);
		tuples.add(1,1,0,0,0,1,1);
		tuples.add(1,1,0,0,1,0,1);
		tuples.add(1,1,0,0,1,1,0);
		
		tuples.add(1,1,0,1,0,0,1);
		tuples.add(1,1,0,1,0,1,0);
		tuples.add(1,1,0,1,1,0,0);
		tuples.add(1,1,0,1,1,1,1);
		
		tuples.add(1,1,1,0,0,0,1);
		tuples.add(1,1,1,0,0,1,0);
		tuples.add(1,1,1,0,1,0,0);
		tuples.add(1,1,1,0,1,1,1);
		
		
		tuples.add(1,1,1,1,0,0,0);
		tuples.add(1,1,1,1,0,1,1);
		tuples.add(1,1,1,1,1,0,1);
		tuples.add(1,1,1,1,1,1,0);

		
		
		return tuples;
	}

	
	
}
