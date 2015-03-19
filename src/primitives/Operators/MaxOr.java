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
import sets.PiecewiseLinearSet;
import sets.PointSet;
import sets.MaxOrSet;

public class MaxOr extends DefaultReporter{
	
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
			return maxDiscrete(listOfSets);
		case 2:
			return maxPiecewise(listOfSets);
		case 3:
			return maxContinuous(listOfSets);
		default:
			return null;
		}
	}
	
	
	private FuzzySet maxDiscrete(LogoList l){
		//First discrete set and his parameters
		PointSet set =(PointSet) l.first();
		List<double[]> resultParameters = set.getParameters();
		double[] universe = new double[2];
		//Iterate over the sets
		for(int i = 1 ; i < l.size() ; i++){
			set = (PointSet) l.get(i);
			//Calculate the min parameters of two sets
			resultParameters = SupportFunctions.discreteMaxMin(resultParameters, set.getParameters(),true);
		}
		//Calculate universe
		double[] point = resultParameters.get(0);
		universe[0] =point[0];
		point = resultParameters.get(resultParameters.size()-1);
		universe[1] = point[0];
		//Create a new Discrete numeric set with the resulting parameters
		return new DiscreteNumericSet(resultParameters, false, "DiscreteMin", universe);
	}
	
	private FuzzySet maxPiecewise(LogoList l){
		PointSet setA = (PointSet) l.first();
		double[] universe = new double[2];
		PointSet setB;
		//Iterate over the sets
		for(int i = 1 ; i < l.size() ; i++){
			setB = (PointSet) l.get(i);
			//Calculate the andUniverse of the two sets
			universe = DegreeOfFulfillment.andInterval(setA.getUniverse(), setB.getUniverse());
			//Create a new set, the min piecewise of 2 piecewise.
			setA = new PiecewiseLinearSet(DegreeOfFulfillment.upperEnvelope(setA, setB), true, "Min-piecewise" , universe);
		}
		return setA;
	}
	
	private FuzzySet maxContinuous(LogoList l){
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
		return new MaxOrSet(params,true,"Max-continuous",universe);
	}


}
