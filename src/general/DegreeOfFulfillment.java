package general;

import java.util.ArrayList;
import java.util.List;



import org.nlogo.api.LogoList;

import sets.FuzzySet;

public class DegreeOfFulfillment {
	
	public static double discreteFulfillment(FuzzySet a, FuzzySet b){
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
	
	
	public static double mixedFulfillment(FuzzySet a,FuzzySet b){
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
	
	
	public static double continuousFulfillment(FuzzySet a,FuzzySet b){
		//The resulting universe is the intersection of a and b
		double[] universe = andInterval(a.getUniverse(),b.getUniverse());
		//if the universe is empty return Not a Number
		if(universe.length > 0){
			double resolution = SupportFunctions.getResolution();
			double steps = Math.floor(1 + ((universe[1] - universe[0]) * resolution));
			double x = universe[0];
			double evalA = 0;
			double evalB = 0;
			double minimum = Double.POSITIVE_INFINITY;
			double degree = Double.NEGATIVE_INFINITY;
			//Iterate over the defined steps
			for(int i = 0; i < steps ; i++){
				evalA = a.evaluate(x);
				evalB = b.evaluate(x);
				//Save the lower value
				if(evalA <= evalB){
					minimum = evalA;
				}else{
					minimum = evalB;
				}
				//Save the greater of the lowers values
				if(minimum > degree){
					degree = minimum;
				}
				//Increase x
				x += 1/resolution;
			}
			return degree;
		}else{
			return Double.NaN;
		}
	}
	
	//que sea private
	public static double[] andInterval(double[] universe1, double[] universe2){
		double[] resultUniverse = new double[2];
		//Look for the greater low-limit
		if(universe1[0] > universe2[0]){
			resultUniverse[0] = universe1[0];
		}else{
			resultUniverse[0] = universe2[0];
		}
		//Look for the lower great-limit
		if(universe1[1] < universe2[1]){
			resultUniverse[1] = universe1[1];
		}else{
			resultUniverse[1] = universe2[1];
		}
		//If the universe does not intersect return and empty universe
		if(resultUniverse[0] < resultUniverse[1]){
			return resultUniverse.clone();
		}else{
			return new double[]{};
		}
	}
	
	
	//Que sea private
	public static List<Double> pointsToEvaluate(LogoList paramsA, LogoList paramsB,double[] universe){
		double xA = 0;
		double xB = 0;
		int i1 = 0;
		int i2 = 0;
		LogoList pointA, pointB;
		List<Double> pointsDef = new ArrayList<Double>();
		while(i1 < paramsA.size() && i2 < paramsB.size()){
			pointA = (LogoList) paramsA.get(i1);
			pointB = (LogoList) paramsB.get(i2);
			xA = (Double) pointA.first();
			xB = (Double) pointB.first();
			//Look for the lower of both points
			if(xA <= xB){
				//Check if the point is inside the and-universe
				if(xA >= universe[0] && xA <= universe[1] && !(pointsDef.contains(xA))){
					pointsDef.add(xA);
				}
				i1++;
			}else{
				if(xB >= universe[0] && xB <= universe[1] && !(pointsDef.contains(xB))){
					pointsDef.add(xB);
				}
				i2++;
			}
		}
		return pointsDef;
	}
	
//	public static Map<Double,Double> lowerEnvelope(FuzzySet a, FuzzySet b){
//		List<Double> points = pointsToEvaluate(a.getParameters(), b.getParameters(), andInterval(a.getUniverse(), b.getUniverse()));
//		double evalA = 0;
//		double evalB = 0;
//		boolean aLowerThanB = false;
//		double pointAux = 0;
//		double[] paramAux = new double[2];
//		Map<Double,Double> params = new HashMap<Double,Double>();
//		//Iterate over the points where the fuzzy sets must be evaluated
//		for(int i = 0; i < points.size() ; i++){
//			double d = points.get(i);
//			evalA = a.evaluate(d);
//			evalB = b.evaluate(d);
//			//If this is the first point, just introduce it
//			if(params.isEmpty()){
//				if(evalA <= evalB){
//					params.put(d, evalA);
//					aLowerThanB = true;
//				}else{
//					params.put(d, evalB);
//					aLowerThanB = false;
//				}
//			}else{//If this is not the first point
//				if(evalA < evalB){
//					//if evalA is lower and previous evalA was lower too, just put the point
//					if(aLowerThanB){
//						params.put(d, evalA);
//					}else{//if the lines crosses, calculate the cross point and add it. The lower evaluated point should be added too.
//						//Calculate the cross point
//						pointAux = points.get(i-1);
//						paramAux = crossPoint(pointAux, a.evaluate(pointAux), b.evaluate(pointAux), d, evalA, evalB);
//						//Add the cross point
//						params.put(paramAux[0], paramAux[1]);
//						//Add the last point
//						params.put(d, evalA);
//						aLowerThanB = true;
//					}
//				}else if(evalA > evalB){
//					//If evalB is lower and previous evalB was lower too, just put the point
//					if(!aLowerThanB){
//						params.put(d, evalB);
//					}else{//if the lines crosses, calculate the cross point and add it. The lower evaluated point should be added too.
//						//Calculate the cross point
//						pointAux = points.get(i-1);
//						paramAux = crossPoint(pointAux, a.evaluate(pointAux), b.evaluate(pointAux), d, evalA, evalB);
//						//Add the cross point
//						params.put(paramAux[0], paramAux[1]);
//						//add the last point
//						params.put(d, evalB);
//						aLowerThanB = false;
//					}
//				}else{
//					params.put(d, evalA);
//				}
//			}
//		}
//		return params;
//	}
	
	public static List<double[]> lowerEnvelope(FuzzySet a, FuzzySet b){
		List<Double> points = pointsToEvaluate(a.getParameters(), b.getParameters(), andInterval(a.getUniverse(), b.getUniverse()));
		double evalA =  0;
		double evalB = 0;
		boolean aLowerThanB = false;
		double pointAux = 0;
		double[] paramAux = new double[2];
		List<double[]> params = new ArrayList<double[]>();
		//Iterate over the points where the fuzzy sets must be evaluated
		for(int i = 0; i < points.size() ; i++){
			double d = points.get(i);
			evalA = a.evaluate(d);
			evalB = b.evaluate(d);
			//If this is the first point, just introduce it
			if(params.isEmpty()){
				if(evalA <= evalB){
					params.add(new double[]{d,evalA});
					aLowerThanB = true;
				}else{
					params.add(new double[]{d,evalB});
					aLowerThanB = false;
				}
			}else{//If this is not the first point
				if(evalA <= evalB){
					//if evalA is lower and previous evalA was lower too, just put the point
					if(aLowerThanB){
						params.add(new double[]{d,evalA});
					}else{//if the lines crosses, calculate the cross point and add it. The lower evaluated point should be added too.
						//Calculate the cross point
						pointAux = points.get(i-1);
						paramAux = crossPoint(pointAux, b.evaluate(pointAux), a.evaluate(pointAux), d, evalB, evalA);
						//Add the cross point
						params.add(new double[]{paramAux[0], paramAux[1]});
						//Add the last point
						params.add(new double[]{d,evalA});
						aLowerThanB = true;
					}
				}else if(evalA > evalB){
					//If evalB is lower and previous evalB was lower too, just put the point
					if(!aLowerThanB){
						params.add(new double[]{d,evalB});
					}else{//if the lines crosses, calculate the cross point and add it. The lower evaluated point should be added too.
						//Calculate the cross point
						pointAux = points.get(i-1);
						paramAux = crossPoint(pointAux, a.evaluate(pointAux), b.evaluate(pointAux) , d, evalA , evalB);
						//Add the cross point
						params.add(new double[]{paramAux[0], paramAux[1]});
						//add the last point
						params.add(new double[]{d,evalB});
						aLowerThanB = false;
					}
				}
			}
		}
		return params;
	}
	
	private static double[] crossPoint(double x1,double a1,double b1,double x2,double a2,double b2){
		double[] crossPoint = new double[2];
		crossPoint[1] = ((b2 * a1) - (a2 * b1))/((a1 - b1) + (b2 - a2));
		if(b2 == b1){
			crossPoint[0] = x1+((x2-x1)*(a1-b2)/(a1-a2));
			crossPoint[1] = b2;
		}else{
			crossPoint[0] = x1+((x2 - x1)*(crossPoint[1] - b1)/(b2 - b1));
		}
		return crossPoint.clone();
	}
	
	public static double piecewiseFulfillment(FuzzySet a,FuzzySet b){
		List<double[]> paramsEnvelope = lowerEnvelope(a, b);
		double max = Double.NEGATIVE_INFINITY;
		for(double[] point : paramsEnvelope){
			if(point[1] > max){
				max = point[1];
			}
		}
		return max;
	}
	
}
