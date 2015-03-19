package primitives.Operators;

import general.DegreeOfFulfillment;
import general.SupportFunctions;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;
import sets.MinAndSet;

public class MinAnd extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		//Podría ir en support functions <--------------------------------------------------------------------------------------------Desde aqui
		LogoList listOfSets = arg0[0].getList();
		int type = -1;
		//If there is only one set just report that set.
		if(listOfSets.size() == 1){
			return listOfSets.first();
		}
		type = SupportFunctions.allType(listOfSets);
		switch (type) {
		case 0:
			throw new ExtensionException("The fuzzy sets must be all discrete or all continuous");
		case 1:
			return minDiscrete(listOfSets);
		case 2:
			return minPiecewise(listOfSets);
		case 3:
			return minContinuous(listOfSets);
		default:
			return null;
		}
	}
	
//	private LogoList discreteMinAll(LogoList sets){
//		FuzzySet f;
//		LogoListBuilder logo = new LogoListBuilder();
//		List<LogoList> listOfParams = new ArrayList<LogoList>();
//		for(Object o : sets){
//			f = (FuzzySet) o;
//			LogoList l = f.getParameters();
//			listOfParams.add(f.getParameters());
//		}
//		double min = Double.POSITIVE_INFINITY;
//		double value = 0;
//		int index = -1;
//		LogoList point;
//		while(!listOfParams.isEmpty()){
//			min = Double.POSITIVE_INFINITY;
//			value = 0;
//			for(int i = 0 ; i < listOfParams.size() ; i++){
//				point = (LogoList) listOfParams.get(i).first();
//				if((Double) point.first() < min){
//					min = (Double) point.first();
//					index = i;
//					value =(Double) point.get(1);
//				}else if((Double) point.first() == min){
//					if(value > (Double) point.get(1)){
//						min = (Double) point.first();
//						index = i;
//						value =(Double) point.get(1);	
//					}
//				}
//			}
//			logo.add(listOfParams.get(index).first());
//			listOfParams.add(listOfParams.get(index).butFirst());
//			listOfParams.remove(index);
//			if(listOfParams.get(index).size() == 0){
//				listOfParams.remove(index);
//			}
//		}
//		return logo.toLogoList();
//	}
	
//	private List<double[]> discreteMin(List<double[]> a, List<double[]> b){
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
//				//Find the min y value and add it to the point builder
//				if(pointA[1] <= pointB[1]){
//					resultPoint[1] = pointA[1];
//				}else{
//					resultPoint[1] = pointB[1];
//				}
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
	
	private FuzzySet minDiscrete(LogoList l){
		//First discrete set and his parameters
		PointSet set =(PointSet) l.first();
		List<double[]> resultParameters = set.getParameters();
		double[] universe = new double[2];
		//Iterate over the sets
		for(int i = 1 ; i < l.size() ; i++){
			set = (PointSet) l.get(i);
			//Calculate the min parameters of two sets
			resultParameters = SupportFunctions.discreteMaxMin(resultParameters, set.getParameters(),false);
		}
		//Calculate universe
		double[] point = resultParameters.get(0);
		universe[0] =point[0];
		point = resultParameters.get(resultParameters.size()-1);
		universe[1] = point[0];
		//Create a new Discrete numeric set with the resulting parameters
		return new DiscreteNumericSet(resultParameters, false, "DiscreteMin", universe);
	}
	
	
	private FuzzySet minPiecewise(LogoList l){
		PointSet setA = (PointSet) l.first();
		double[] universe = new double[2];
		PointSet setB;
		//Iterate over the sets
		for(int i = 1 ; i < l.size() ; i++){
			setB = (PointSet) l.get(i);
			//Calculate the andUniverse of the two sets
			universe = DegreeOfFulfillment.andInterval(setA.getUniverse(), setB.getUniverse());
			//Create a new set, the min piecewise of 2 piecewise.
			setA = new PiecewiseLinearSet(DegreeOfFulfillment.lowerEnvelope(setA, setB), true, "Min-piecewise" , universe);
		}
		return setA;
	}
	
	private FuzzySet minContinuous(LogoList l){
		List<FuzzySet> params = new ArrayList<FuzzySet>();
		FuzzySet f = (FuzzySet) l.first();
		params.add(f);
		double[] universe = f.getUniverse();
		for(int i = 1 ; i < l.size() ; i++){
			//Add all the fuzzy sets as parameters to the new set
			f = (FuzzySet) l.get(i);
			params.add(f);
			//Calculate the new universe
			universe = DegreeOfFulfillment.andInterval(universe, f.getUniverse());
		}
		return new MinAndSet(params,true,"Min-continuous",universe);
	}	
}
