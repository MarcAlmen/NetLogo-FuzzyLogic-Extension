package general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.ArrayList;
//import java.util.List;
//
//import sets.DiscreteNumericSet;
//import sets.FuzzySet;
//import sets.PiecewiseLinearSet;
//import sets.ExponentialSet;
//import sets.FunctionSet;
//import sets.PointSet;

public class Pruebas {

	public static void main(String[] args) {
//		boolean[] trues = new boolean[]{true,true,true,true,true};
//		boolean[] mixed = new boolean[]{true,false,true,false,true};
//		boolean[] falses = new boolean[]{false,false,false,false,false};
//		boolean t = true;
//		boolean m = true;
//		boolean f = true;
//		for(int i = 0; i < trues.length ; i++){
//			t &= !trues[i];
//			m &= !mixed[i];
//			f &= !falses[i];
//		}

//		List<double[]> paramsA = new ArrayList<double[]>();
//		double[] universe = new double[]{1,4};
//		List<double[]> paramsB = new ArrayList<double[]>();
//		double[] universeB = new double[]{0,10};
//		paramsA.add(new double[]{1 , 0.3});
//		paramsA.add(new double[]{2 , 0.5});
//		paramsA.add(new double[]{4 , 0.7});
//		paramsB.add(new double[]{0 , 0.6});
//		paramsB.add(new double[]{10 , 0.6});
//		paramsB.add(new double[]{7 , 1});
//		paramsB.add(1.0);
//		paramsB.add(-0.25);
//		paramsB.add(0.0);
//		PointSet a = new PiecewiseLinearSet(paramsA, true, "a", universe);
//		PointSet b = new PiecewiseLinearSet(paramsB, true, "b", universeB);
//		List<double[]> p = DegreeOfFulfillment.upperEnvelope(a, b);
//		for(double[] point : p){
//			System.out.println(point[0] + "," + point[1]);
//		}
//		FunctionSet b = new ExponentialSet(paramsB, true, "a", new double[]{0,10});
//		System.out.println(a.getUniverse()[0]);
//		System.out.println(a.getUniverse()[1]);
//		universe[1] = 100;
//		System.out.println(a.getUniverse()[0]);
//		System.out.println(a.getUniverse()[1]);	
//		List<FuzzySet> l = new ArrayList<FuzzySet>();
//		FuzzySet f = a;
//		l.add(f);
//		f = b;
//		l.add(f);
//		for(FuzzySet fs : l){
//			System.out.println(fs.getLabel());
//		}
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
		
		
//		Map<Double,double[]> map = new HashMap<Double, double[]>();
//		map.put(1.0, new double[]{1,1});
//		map.put(1.0, new double[]{1,2});
//		map.put(1.0, new double[]{1,4});
//		map.put(2.0, new double[]{2,6});
//		Collection<double[]> l = map.values();
//		System.out.println(map.size());	
//		for(double[] d : l){
//			System.out.println(d[0]);
//			System.out.println(d[1]);
//		}
		
		List<double[]> l = new ArrayList<double[]>();
		l.add(new double[]{1.0,2.0});
		double[] point = new double[]{1.0 , 2.0};
		if(l.contains(point)){
			System.out.println("si");
		}else{
			System.out.println("No");
		}
	}

}
