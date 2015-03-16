package primitives.Operators;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.api.Syntax;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PiecewiseLinearSet;

public class MinOrAnd extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.ListType()},Syntax.WildcardType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		LogoList listOfSets = arg0[0].getList();
		int type = -1;
		//If there is only one set just report that set.
		if(listOfSets.size() == 1){
			return listOfSets.first();
		}
		type = allType(listOfSets);
		switch (type) {
		case 0:
			throw new ExtensionException("The fuzzy sets must be all discrete or all continuous");
		case 1:
			//return new DiscreteNumericSet(param, false,"discrete-min", universe);
		case 2:
			return null;
		case 3:
			return null;
		default:
			return null;
		}
		
	}
	
	private int allType(LogoList l){
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
	
	private LogoList discreteMin(LogoList A, LogoList B){
		LogoList paramsA = A;
		LogoList paramsB = B;
		LogoList pointA;
		LogoList pointB;
		LogoListBuilder result = new LogoListBuilder();
		LogoListBuilder resultPoint;
		double xA;
		double xB;
		//If there are points in both lists keep iterating
		while(paramsA.size() > 0 & paramsB.size() > 0 ){
			//Reset the point builder
			resultPoint = new LogoListBuilder();
			//Get the points
			pointA =(LogoList) paramsA.first();
			pointB =(LogoList) paramsB.first();
			//Get the x values of the points
			xA = (Double) pointA.first();
			xB = (Double) pointB.first();
			//If the x values are the same
			if(xA == xB){
				//add the x value to the point builder
				resultPoint.add(xA);
				//Find the min y value and add it to the point builder
				if((Double) pointA.get(1) <= (Double) pointB.get(1)){
					resultPoint.add(pointA.get(1));
				}else{
					resultPoint.add(pointB.get(1));
				}
				//Add the point to the result
				result.add(resultPoint.toLogoList());
				//Delete both point
				paramsA = paramsA.butFirst();
				paramsB = paramsB.butFirst();
			}else{//If the x value are not the same delete the lower one
				if(xA < xB){
					paramsA = paramsA.butFirst();
				}else{
					paramsB = paramsB.butFirst();
				}
			}
		}
		return result.toLogoList();
	}
	
	private FuzzySet createDiscreteMin(LogoList l){
		FuzzySet set =(FuzzySet) l.first();
		LogoList resultParameters = set.getParameters();
		double[] universe = new double[2];
		for(int i = 1 ; i < l.size() ; i++){
			set = (FuzzySet) l.get(i);
			resultParameters = discreteMin(resultParameters, set.getParameters());
		}
		LogoList point = (LogoList) resultParameters.first();
		universe[0] =(Double) point.first();
		//TODO: COmpletar
		return null;
	}
	
	
}
