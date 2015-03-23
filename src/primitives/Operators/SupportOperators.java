package primitives.Operators;

import general.DegreeOfFulfillment;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.LogoList;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;

/**
 * Class to support Operators with common methods.
 * @author Marcos Almendres.
 *
 */
public class SupportOperators {
	
	/**
	 * Calculate the parameters and the universe of continuous sets.
	 * @param l Logolist containing all the fuzzysets.
	 * @return A tuple containing the resulting parameters(List<FuzzySet>) and universe(double[]).
	 */
	public static Tuple<FuzzySet> continuousParamsUniverse(LogoList l){
		List<FuzzySet> params = new ArrayList<FuzzySet>();
		//Get and add the first fuzzy set
		FuzzySet f = (FuzzySet) l.first();
		params.add(f);
		//Get the universe of the first fuzzy set
		double[] universe = f.getUniverse();
		//Iterate over all the fuzzy sets
		for(int i = 1 ; i < l.size() ; i++){
			//Add all the fuzzy sets as parameters to the new set
			f = (FuzzySet) l.get(i);
			params.add(f);
			//Calculate the new universe
			universe = DegreeOfFulfillment.andInterval(universe, f.getUniverse());
		}
		return new Tuple<FuzzySet>(params,universe);
	}
	
	/**
	 * Checks the type of all the fuzzy sets.
	 * @param l The list where the fuzzy sets are.
	 * @return 1 if all discrete, 2 if all piecewise, 3 if all continuous or 0 if all the sets are not the same type.
	 */
	public static int allType(LogoList l){
		int discrete = 0;
		int piecewise = 0;
		int continuous = 0;
		//Count the type of all the fuzzy sets inside the list
		for(Object o : l){
			FuzzySet f =(FuzzySet) o;
			if(f.isContinuous()){
				if(f instanceof PiecewiseLinearSet){
					piecewise++;
				}
					continuous++;
			}else{
				discrete++;
			}
		}
		//If all the sets of the list are discrete return 1
		if(discrete == l.size()){
			return 1;
		}
		//If all the sets of the list are piecewise return 2
		//Return first piecewise linear cause piecewise linear is also continuous
		if(piecewise == l.size()){
			return 2;
		}
		//If all the sets of the list are continuous return 3
		if(continuous == l.size()){
			return 3;
		}
		//If the sets are mixed return 0;
		return 0;
	}
	
//	public static List<double[]> discreteMaxMin(List<double[]> a, List<double[]> b,boolean isMax){
//		List<double[]> paramsA = a;
//		List<double[]> paramsB = b;
//		List<double[]> result = new ArrayList<double[]>();
//		double[] pointA;
//		double[] pointB;
//		double[] resultPoint;
//		double xA;
//		double xB;
//		//If there are points in both lists keep iterating
//		while(paramsA.size() > 0 & paramsB.size() > 0 ){
//			resultPoint = new double[2];
//			//Get the points
//			pointA =paramsA.get(0);
//			pointB =paramsB.get(0);
//			//Get the x values of the points
//			xA = pointA[0];
//			xB = pointB[0];
//			//If the x values are the same
//			if(xA == xB){
//				//add the x value to the point builder
//				resultPoint[0] = xA;
//				//if this is a discrete max calculation
//				if(isMax){
//					//Find the max value
//					if(pointA[1] >= pointB[1]){
//						resultPoint[1] = pointA[1];
//					}else{
//						resultPoint[1] = pointB[1];
//					}
//				}else{//if this is a discrete min calculation
//					//find the min value
//					if(pointA[1] <= pointB[1]){
//						resultPoint[1] = pointA[1];
//					}else{
//						resultPoint[1] = pointB[1];
//					}
//				}
//				
//				//Add the point to the result
//				result.add(resultPoint.clone());
//				//Delete both point
//				paramsA.remove(0);
//				paramsB.remove(0);
//			}else{//If the x value are not the same delete the lower one
//				if(xA < xB){
//					paramsA.remove(0);
//				}else{
//					paramsB.remove(0);
//				}
//			}
//		}
//		return result;
//	}
	
	/**
	 * Calculate the parameters and the universe of discrete operations.
	 * @param l the list where the sets are.
	 * @param operator The operation to perform.
	 * @return A tuple containing the resulting parameters(List<double[]>) and universe(double[]).
	 */
	public static Tuple<double[]> discreteOperations(LogoList l, Command operator){
			//First discrete set and his parameters
				PointSet set =(PointSet) l.first();
				List<double[]> resultParameters = set.getParameters();
				double[] universe = new double[2];
				//Iterate over the sets
				for(int i = 1 ; i < l.size() ; i++){
					set = (PointSet) l.get(i);
					//Calculate the parameters of two sets
					resultParameters = discretePairOperation(resultParameters, set.getParameters(), operator);
				}
				//Calculate universe
				double[] point = resultParameters.get(0);
				universe[0] =point[0];
				point = resultParameters.get(resultParameters.size()-1);
				universe[1] = point[0];
				return new Tuple<double[]>(resultParameters,universe);
	}
	
	/**
	 * Calculate the parameters of two discrete fuzzysets.
	 * @param a The parameters of the first fuzzy set.
	 * @param b The parameters of the second fuzzy set.
	 * @param operator The operation to perform.
	 * @return The resulting parameters.
	 */
	public static List<double[]> discretePairOperation(List<double[]> a, List<double[]> b,Command operator){
		List<double[]> paramsA = a;
		List<double[]> paramsB = b;
		List<double[]> result = new ArrayList<double[]>();
		double[] pointA;
		double[] pointB;
		double[] resultPoint;
		double xA;
		double xB;
		//If there are points in both lists keep iterating
		while(paramsA.size() > 0 & paramsB.size() > 0 ){
			resultPoint = new double[2];
			//Get the points
			pointA =paramsA.get(0);
			pointB =paramsB.get(0);
			//Get the x values of the points
			xA = pointA[0];
			xB = pointB[0];
			//If the x values are the same
			if(xA == xB){
				//Get the resulting point depending on the peration
				resultPoint = operator.execute(pointA, pointB);
				//Add the point to the result
				result.add(resultPoint.clone());
				//Delete both point
				paramsA.remove(0);
				paramsB.remove(0);
			}else{//If the x value are not the same delete the lower one
				if(xA < xB){
					paramsA.remove(0);
				}else{
					paramsB.remove(0);
				}
			}
		}
		return result;
	}
}
