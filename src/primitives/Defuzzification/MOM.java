package primitives.Defuzzification;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;

public class MOM extends DefaultReporter {
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.NumberType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet f =(FuzzySet) arg0[0].get();
		double[] universe = f.getUniverse();
		double maximum = Double.NEGATIVE_INFINITY;
		double maximum2 = Double.NEGATIVE_INFINITY;
		double maxVal = Double.NEGATIVE_INFINITY;
		boolean twoMax = false;
		//If universe empty
		if(universe.length == 0){
			new ExtensionException("You have tried to compute the FOM of an empty set");
		}
		//If continuous but not piecewise
		if(f.isContinuous() && !(f instanceof PiecewiseLinearSet)){
			//First number to evaluate
			double x = universe[0];
			//Number of points to evaluate(Depends on resolution)
			double steps = Math.floor(1 + ((universe[1] - universe[0]) * SupportFunctions.getResolution()));
			double eval = 0;
			for(int i = 0; i < steps ; i++){
				eval = f.evaluate(x);
				//if bigger than the stored max value, save the x, update max value and set false the two-maximum-found boolean
				if(eval > maxVal){
					maximum = x;
					maxVal = eval;
					twoMax = false;
				}else if(eval == maxVal){// if equal to the stored max value, save the x in the second maximum and set the boolean to true
					maximum2 = x;
					twoMax = true;
				}
				//Increment 1/Resolution times
				x = x + (1 / SupportFunctions.getResolution());
			}
		}else{
			LogoList point;
			for(Object param : f.getParameters()){
				point = (LogoList) param;
				double y =(Double) point.get(1);
				if(y > maxVal){
					maximum = (Double) point.first();
					maxVal = y;
					twoMax = false;
				}else if(y == maxVal){
					maximum2 = (Double) point.first();
					twoMax = true;
				}
			}
		}
		if(twoMax){
			return (maximum + maximum2)/2;
		}else{
			return maximum;
		}
	}

}
