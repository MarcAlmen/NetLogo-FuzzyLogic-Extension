package general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoList;

import sets.FuzzySet;

public class SupportFunctions {
	
	private static double resolution = 256;
	
	public static double getResolution(){
		return resolution;
	}
	
	public static void setResolution(double d){
		resolution = d;
	}
	//--------------------------------------------------------Creation of Fuzzy sets-----------------------------------------------------------------------------------------
	public static double[] universe(LogoList params){
		double[] universe = new double[2];
		LogoList first =(LogoList) params.first();
		LogoList last =(LogoList) params.get(params.size()-1);
		universe[0] = (Double) first.first();
		universe[1] = (Double) last.first();
		return universe;
	}
	
	public static List<double[]> checkListFormat(LogoList params) throws ExtensionException{
		int n = 0;
		List<double[]> sortingList = new ArrayList<double[]>();
		double[] point = new double[2];
		if(params.size() == 0){
			throw new ExtensionException("The list is empty, please enter a valid list: [[a b] [c d] [e f]]");
		}
		for(Object o : params){
			//Checks the elements are lists
			if(!(o instanceof LogoList)){
				throw new ExtensionException("List of 2 elements lists expected. The element in the position " + Double.valueOf(n) + " is not a list");
			}
			LogoList l = (LogoList) o;
			//Checks if the lists contains 2 elements
			if(l.size() != 2){
				throw new ExtensionException("List of 2 elements lists expected. The element in the position " + Double.valueOf(n) + " is not a list of two elements");
			}
			point[0] = (Double) l.first();
			point[1] = (Double) l.get(1);
			//Add to a list to use the Collections.sort method
			sortingList.add(point.clone());
			//Checks if the elements are doubles
			if((Double) l.get(1) > 1 || (Double) l.get(1) < 0){
				throw new ExtensionException("The second number of each list should be between 0 and 1 " + Double.valueOf(n) + " is not between 0 and 1");
			}
			n++;
		}
		return sortListOfPoints(sortingList);
	}
	
	public static List<double[]> sortListOfPoints(List<double[]> list){
		//Implement the Comparator for LogoList
		Comparator<double[]> comp = new Comparator<double[]>(){
			public int compare(double[] a,double[] b){
				//Obtain the first element of each LogoList to compare
				//Returns required by comparators(1 if the first is bigger, -1 if smaller and 0 if equal)
				if(a[0]>b[0]){
					return 1;
				}else if(b[0] > a[0]){
					return -1;
				}else{
					return 0;
				}
			}
		};
		//Sort
		Collections.sort(list,comp);
		//Build the sorted LogoList to store in the FuzzySet
		return list;
	}
	
	/**
	 * Checks the format of Logistic,Exponential and Gaussian sets
	 * @param params the parameters of the set
	 * @param n Integer to check if 3 or 4 parameters required
	 * @return The universe of the set [lower-limit, upper-limit]
	 * @throws ExtensionException
	 */
	public static double[] LGEFormat(LogoList params,int n) throws ExtensionException{
		double[] universe = new double[2];
		if(params.size() != n){
			throw new ExtensionException("must be a list with " + (n-1) + " numbers and one 2-number list");
		}
		for(int i = 0; i < params.size() ; i++){
			if(i < n-1){
				if(!(params.get(i) instanceof Double)){
					throw new ExtensionException("The first " + (n-1) + " parameters must be numbers");
				}
			}else{
				if(!(params.get(i) instanceof LogoList)){
					throw new ExtensionException("The " + n + "th item must be a list of 2 elements list");
				}
				LogoList l = (LogoList) params.get(i);
				if(l.size() != 2){
					throw new ExtensionException("The " + n + "th item must be a list of 2 elements list");
				}
				universe = new double[]{(Double) l.first(),(Double) l.get(1)};
			}
		}
		return universe;
	}
	
	public static double[] IWPFormat(LogoList params) throws ExtensionException{
		//Checks the first parameter is a list
				if(!(params.first() instanceof LogoList) || !(params.get(1) instanceof LogoList)){
					throw new ExtensionException("The list should contain 2 lists");
				}
				LogoList f = (LogoList) params.first();
				//Checks the first parameter is a 2 element list with the first element a list and the second a number
				if(f.size() != 2 || !(f.first() instanceof LogoList) || !(f.get(1) instanceof Double)){
					throw new ExtensionException("The first element of parameters must look like [[low-limit high-limit] value]");
				}
				LogoList interval =(LogoList) f.first();
				//Checks the first element(of the first parameter) is a list of 2 numbers
				if(interval.size() != 2 || !(interval.first() instanceof Double) || !(interval.get(1) instanceof Double)){
					throw new ExtensionException("The interval must be a list of two numbers");
				}
				//The universe in interval with point sets is stored in a different way.
				//[lower-limit,higher-limit] this is the normal way to store universes.
				//[lower-limit,higher-limit,default-value] this is how universe is stored in interval with point sets.
				double[] universe = new double[]{(Double)interval.first(),(Double)interval.get(1),(Double) f.get(1)}; 
				if(universe[0] >= universe[1]){
					throw new ExtensionException("The interval should be like[lower higher]");
				}
				return universe;
	}
	
	public static List<double[]> trapezoidalFormat(LogoList params) throws ExtensionException{
		if(params.size() != 7){
			throw new ExtensionException("The first argument must be a list of 7 numbers");
		}
		List<double[]> resultParams = new ArrayList<double[]>();
		for(int i = 0; i <= 6;i++){
			//Checks the list has only Doubles inside
			if(!(params.get(i) instanceof Double)){
				throw new ExtensionException("The list can only contain numbers");
			}
			// list-of-parameters is a list [a, b, c, d, e, f, HEIGHT] 
			// The membership function equals 0 in the interval [a,b],
			// increases linearly from 0 to HEIGHT in the range b to c, 
			// is equal to HEIGHT in the range c to d, 
			// decreases linearly from HEIGHT to 0 in the range d to e,
			// and equals 0 in the interval [e,f].
			if(i <= 1){
				resultParams.add(new double[]{(Double) params.get(i),0});
			}else if(i <= 3){
				resultParams.add(new double[]{(Double) params.get(i),(Double) params.get(6)});
			}else if(i <= 5){
				resultParams.add(new double[]{(Double) params.get(i),0});
			}
		}
		return resultParams;
	}
	
	public static void addToRegistry(FuzzySet f,String name,Context c) throws ExtensionException{
		Map<String,FuzzySet> registry = FuzzyLogic.getRegistry();
		//GUIWorkspace gw =(GUIWorkspace) ((ExtensionContext) c).workspace();
		if(registry.containsKey(name)){
			registry.remove(name);
			registry.put(name, f);
//			OptionDialog.show(gw.getFrame(), "User Message", "The name: " + name + " was already registered, and the previous one has been overided",
//			new String[]{I18N.gui().get("common.buttons.ok"),I18N.gui().get("common.buttons.halt")});
			//throw new ExtensionException("The name: " + name + " was already registered, and the previous one has been overided");
		}else{
			registry.put(name, f);
		}
	}
}
