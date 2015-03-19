package primitives.Defuzzification;

import java.util.ArrayList;
import java.util.List;

import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;
import sets.PointSet;

public class MeOM extends DefaultReporter{
	
	public Syntax getSyntax(){
		return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.NumberType());
	}

	@Override
	public Object report(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet f =(FuzzySet) arg0[0].get();
		double[] universe = f.getUniverse();
		//If universe empty
		if(universe.length == 0){
			new ExtensionException("You have tried to compute the FOM of an empty set");
		}
		//If continuous but not piecewise
		if(f.isContinuous() && !(f instanceof PiecewiseLinearSet)){
			return continuousMeOM(f);
		}else{
			//If piecewise linear
			if(f instanceof PiecewiseLinearSet){
				return piecewiseMeOM(f);
			}else{
				return discreteMeOM(f);
			}
			
		}
	}
	
	private double continuousMeOM(FuzzySet f){
		double sumOfMax = Double.NEGATIVE_INFINITY;
		double maxVal = Double.NEGATIVE_INFINITY;
		double samples = 0;
		double[] universe = f.getUniverse();
		//First number to evaluate
		double x = universe[0];
		//Number of points to evaluate(Depends on resolution)
		double steps = Math.floor(1 + ((universe[1] - universe[0]) * SupportFunctions.getResolution()));
		double eval = 0;
		for(int i = 0; i < steps ; i++){
			eval = f.evaluate(x);
			if(eval > maxVal){
				sumOfMax = x;
				maxVal = eval;
				samples = 1;
			}else if(eval == maxVal){
				sumOfMax += x;
				samples++;
			}
			//Increment 1/Resolution times
			x = x + (1 / SupportFunctions.getResolution());
		}
		return sumOfMax/samples;
	}
	
	private double piecewiseMeOM(FuzzySet f){
		double maxVal = Double.NEGATIVE_INFINITY;
		double y = 0;
		double[] interval = new double[2];
		boolean inRow = false;
		boolean intervalComplete = false;
		List<double[]> intervals = new ArrayList<double[]>();
		double[] point;
		@SuppressWarnings("unchecked")
		List<double[]> params = f.getParameters();
			for(int i = 0; i < params.size() ; i++){
				point = params.get(i);
				y = point[1];
				//Find the maximum
				if(y > maxVal){
					//Clear the intervals in order to avoid previous maximums
					intervals.clear();
					maxVal = y;
					//Sets the first x of the interval
					interval[0] = point[0];
					inRow = true;
				}else if(y == maxVal){
					//if this maximum is in a row with a previous one
					if(inRow){
						//sets the second x of the interval
						interval[1] = point[0];
						intervalComplete = true;
						//If this is the last point add the interval
						if(i == params.size() - 1){
							intervals.add(interval.clone());
						}
					}else{
						//If not in a row set the first x of the interval
						interval[0] =point[0];
						inRow = true;
					}	
				}else{//If y < maxVal
					//If it comes from an interval add it to the list
					if(intervalComplete){
						intervals.add(interval.clone());
					}
					//Set inRow to false
					inRow = false;
					intervalComplete = false;
				}
			}
			if(intervals.size() == 0){
				return discreteMeOM(f);
			}else{
				return meanOfMax(intervals);
			}
	}

	
	private double meanOfMax(List<double[]> intervals){
		double num = 0;
		double denom = 0;
		for(double[] interval : intervals){
			num += (((interval[1] - interval[0])*(interval[1] + interval[0]))/2);
			denom += (interval[1] - interval[0]);
		}
		return num/denom;
	}
	
	private double discreteMeOM(FuzzySet f){
		PointSet ps = (PointSet) f;
		double maxVal = Double.NEGATIVE_INFINITY;
		double y = 0;
		double sumMaxima = 0;
		double length = 0;
		for(double[] point : ps.getParameters()){
			y = point[1];
			//Each time a new max value is found reset length and sumMaxima
			if(y > maxVal){
				length = 1;
				sumMaxima = point[0];
				maxVal = y;
			}else if(y == maxVal){//Each time the max value is found increase length and sumMaxima
				sumMaxima += point[0];
				length++;
			}
		}
		return sumMaxima/length;
	}

}
