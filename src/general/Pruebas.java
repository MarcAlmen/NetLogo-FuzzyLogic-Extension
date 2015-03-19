package general;

import java.util.ArrayList;
import java.util.List;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
//import sets.ExponentialSet;
//import sets.FunctionSet;
import sets.PointSet;

public class Pruebas {

	public static void main(String[] args) {
		List<double[]> paramsA = new ArrayList<double[]>();
		double[] universe = new double[]{1,7};
		List<double[]> paramsB = new ArrayList<double[]>();
		paramsA.add(new double[]{1 , 0.2});
		paramsA.add(new double[]{3 , 0.5});
		paramsA.add(new double[]{7 , 0.9});
		paramsB.add(new double[]{1 , 0.1});
		paramsB.add(new double[]{4 , 0.5});
		paramsB.add(new double[]{7 , 1});
//		paramsB.add(1.0);
//		paramsB.add(-0.25);
//		paramsB.add(0.0);
		PointSet a = new DiscreteNumericSet(paramsA, false, "a", universe);
		PointSet b = new DiscreteNumericSet(paramsB, false, "b", universe);
//		FunctionSet b = new ExponentialSet(paramsB, true, "a", new double[]{0,10});
//		System.out.println(a.getUniverse()[0]);
//		System.out.println(a.getUniverse()[1]);
//		universe[1] = 100;
//		System.out.println(a.getUniverse()[0]);
//		System.out.println(a.getUniverse()[1]);	
		List<FuzzySet> l = new ArrayList<FuzzySet>();
		FuzzySet f = a;
		l.add(f);
		f = b;
		l.add(f);
		for(FuzzySet fs : l){
			System.out.println(fs.getLabel());
		}
//		for(double[] point : a.getParameters()){
//			System.out.println(point[0]);
//			System.out.println(point[1]);
//		}
//		paramsA.add(new double[]{200,150});
//		System.out.println("-----------------------Cambiando-----------------------------");
//		for(double[] point : a.getParameters()){
//			System.out.println(point[0]);
//			System.out.println(point[1]);
//		}
	}

}
