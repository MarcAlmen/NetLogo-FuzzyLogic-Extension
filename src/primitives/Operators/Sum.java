package primitives.Operators;

import java.util.ArrayList;
import java.util.List;

import general.DegreeOfFulfillment;
import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.DiscreteNumericSet;
import sets.FuzzySet;
import sets.PointSet;
import sets.SumSet;

public class Sum extends DefaultReporter{
	
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
			return sumDiscrete(listOfSets);
		case 2:
			return sumPiecewise(listOfSets);
		case 3:
			return sumContinuous(listOfSets);
		default:
			return null;
		}
	}
	
	private FuzzySet sumDiscrete(LogoList l){
		//First discrete set and his parameters
				PointSet set =(PointSet) l.first();
				List<double[]> resultParameters = set.getParameters();
				double[] universe = new double[2];
				//Iterate over the sets
				for(int i = 1 ; i < l.size() ; i++){
					set = (PointSet) l.get(i);
					//Calculate the min parameters of two sets
					resultParameters = discreteSum(resultParameters, set.getParameters());
				}
				//Calculate universe
				double[] point = resultParameters.get(0);
				universe[0] =point[0];
				point = resultParameters.get(resultParameters.size()-1);
				universe[1] = point[0];
				//Create a new Discrete numeric set with the resulting parameters
				return new DiscreteNumericSet(resultParameters, false, "DiscreteMin", universe);
	}
	
	private List<double[]> discreteSum(List<double[]> a, List<double[]> b){
		List<double[]> paramsA = a;
		List<double[]> paramsB = b;
		List<double[]> result = new ArrayList<double[]>();
		double[] pointA,pointB,resultPoint;
		double xA,xB;
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
				//add the x value to the point builder
				resultPoint[0] = xA;
				//sum the points
				resultPoint[1] = pointA[1] + pointB[1];
				//Clip the value if greater than 1
				if(resultPoint[1] > 1){
					resultPoint[1] = 1;
				}
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
	
	private FuzzySet sumPiecewise(LogoList l){
		return null;
	}
	
	private FuzzySet sumContinuous(LogoList l){
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
		return new SumSet(params,true,"Max-continuous",universe);
	}

}
