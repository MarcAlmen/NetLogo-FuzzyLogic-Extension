package general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;

public class SupportFunctions {
	
	private static double resolution = 256;
	
	public static double getResolution(){
		return resolution;
	}
	
	public static double[] universe(LogoList params){
		double[] universe = new double[2];
		LogoList first =(LogoList) params.first();
		LogoList last =(LogoList) params.get(params.size()-1);
		universe[0] = (Double) first.first();
		universe[1] = (Double) last.first();
		return universe;
	}
	
	public static LogoList checkListFormat(LogoList params) throws ExtensionException{
		int n = 0;
		List<LogoList> sortingList = new ArrayList<LogoList>();
		for(Object o : params){
			//Checks the elements are lists
			if(!(o instanceof LogoList)){
				throw new ExtensionException("List of 2 elements lists expected. The element in the position " + Double.valueOf(n) + " is not a list");
			}
			LogoList l = (LogoList) o;
			//Add to a list to use the Collections.sort method
			sortingList.add(l);
			//Checks if the lists contains 2 elements
			if(l.size() != 2){
				throw new ExtensionException("List of 2 elements lists expected. The element in the position " + Double.valueOf(n) + " is not a list of two elements");
			}
			//Checks if the elements are doubles
			if((Double) l.get(1) > 1 || (Double) l.get(1) < 0){
				throw new ExtensionException("The second number of each list should be between 0 and 1 " + Double.valueOf(n) + " is not between 0 and 1");
			}
			n++;
		}
		return sortListOfPoints(sortingList);
	}
	
	public static LogoList sortListOfPoints(List<LogoList> list){
		//Implement the Comparator for LogoList
		Comparator<LogoList> comp = new Comparator<LogoList>(){
			public int compare(LogoList a,LogoList b){
				//Obtain the first element of each LogoList to compare
				double i = (Double) a.get(0);
				double j = (Double) b.get(0);
				//Returns required by comparators(1 if the first is bigger, -1 if smaller and 0 if equal)
				if(i>j){
					return 1;
				}else if(j > i){
					return -1;
				}else{
					return 0;
				}
			}
		};
		//Sort
		Collections.sort(list,comp);
		//Build the sorted LogoList to store in the FuzzySet
		LogoListBuilder log = new LogoListBuilder();
		log.addAll(list);
		LogoList ej = log.toLogoList();
		return ej;
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
	
	public static LogoList trapezoidalFormat(LogoList params) throws ExtensionException{
		if(params.size() != 7){
			throw new ExtensionException("The first argument must be a list of 7 numbers");
		}
		//Create a list with the piecewise linear format
		LogoListBuilder logo = new LogoListBuilder();
		for(int i = 0; i <= 6;i++){
			//Checks the list has only Doubles inside
			if(!(params.get(i) instanceof Double)){
				throw new ExtensionException("The list can only contain numbers");
			}
			LogoListBuilder aux = new LogoListBuilder();
			// list-of-parameters is a list [a, b, c, d, e, f, HEIGHT] 
			// The membership function equals 0 in the interval [a,b],
			// increases linearly from 0 to HEIGHT in the range b to c, 
			// is equal to HEIGHT in the range c to d, 
			// decreases linearly from HEIGHT to 0 in the range d to e,
			// and equals 0 in the interval [e,f].
			aux.add(params.get(i));
			if(i <= 1){
				aux.add(0.0);
				logo.add(aux.toLogoList());
			}else if(i <= 3){
				aux.add(params.get(6));
				logo.add(aux.toLogoList());
			}else if(i <= 5){
				aux.add(0.0);
				logo.add(aux.toLogoList());
			}
		}
		return logo.toLogoList();
	}
}
