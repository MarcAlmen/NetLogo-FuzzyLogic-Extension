package general;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.LogoList;

import sets.FuzzySet;

public class DegreeOfFulfillment {
	
	public static Double discreteFulfillment(FuzzySet a, FuzzySet b){
		LogoList paramsA = a.getParameters();
		LogoList paramsB = b.getParameters();
		double va;
		double vb;
		double degree = Double.NEGATIVE_INFINITY;
		double minimum = Double.POSITIVE_INFINITY;
		//Save the parameters of the 2 FuzzySets(Both Discrete)
		LogoList elementA;
		LogoList elementB;
		//While the 2 FuzzySets have elements we iterate
		while(paramsB.size() > 0 & paramsA.size() > 0){
			//Save the first element(Point) of each parameters
			elementA =(LogoList) paramsA.first();
			elementB =(LogoList) paramsB.first();
			//Save the x value
			va = (Double) elementA.first();
			vb = (Double) elementB.first();
			//If they have the same value
			if(va == vb){
				//Save the second value of the Point
				va =(Double) elementA.get(1);
				vb =(Double) elementB.get(1);
				//We save the minimum of them
				if(va < vb){
					minimum = va;
				}else{
					minimum = vb;
				}
				//If the degree is lower than the new minimum value, we save it in degree
				if(degree < minimum){
					degree = minimum;
				}
				//Delete the evaluated points
				paramsA = paramsA.butFirst();
				paramsB = paramsB.butFirst(); 
			}else{//If they are different
				//If the first element of A is lower than the first element of B we delete from A, if not we delete from B
				if(va < vb){
					paramsA = paramsA.butFirst();
				}else{
					paramsB = paramsB.butFirst();
				}
			}
		}
	return degree;
	}
	
	
	public static Double mixedFulfillment(FuzzySet a,FuzzySet b){
		FuzzySet discrete;
		FuzzySet continuous;
		//Determine which one is the continuous
		if(a.isContinuous()){
			continuous = a; 
			discrete = b;
		}else{
			continuous = b; 
			discrete = a;
		}
		//Get the parameters of the discrete fuzzy set
		LogoList elementsD = discrete.getParameters();
		LogoList point;
		double x;
		double[] universe = continuous.getUniverse();
		List<Double> numbersToEvaluate = new ArrayList<Double>();
		//Iterate over the parameters and add to a list the first number(x) of each points
		for(Object o : elementsD){
			point =(LogoList) o;
			x = (Double) point.first();
			//If not in the universe of the continuous set dont add it
			if(x >= universe[0] && x <= universe[1]){
				numbersToEvaluate.add((Double) point.first());
			}	
		}
		double max = Double.NEGATIVE_INFINITY;
		double evCont,evDisc;
		//Iterate over the numbersToEvaluate
		for(double d : numbersToEvaluate){
			//Evaluate the number in each Fuzzy Set
			evCont = continuous.evaluate(d);
			evDisc = discrete.evaluate(d);
			//Get the lower of them
			if(evCont < evDisc){//evCont is lower
				//Compare to the max value
				if(evCont > max){
					max = evCont;
				}
			}else{//evDisc is lower
				if(evDisc > max){
					max = evDisc;
				}
			}
		}	
		return max;
	}
	
	
	public static Double continuousFulfillment(FuzzySet a,FuzzySet b){
		return 0.0;
	}
	public static Double piecewiseFulfillment(FuzzySet a,FuzzySet b){
		return 0.0;
	}
}
