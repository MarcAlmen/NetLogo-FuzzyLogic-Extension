package general;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

import primitives.*;
import primitives.Defuzzification.*;
import primitives.Operators.*;


public class FuzzyLogic extends DefaultClassManager{
	
	//Crear un WeakHashMap para hacer de regisitro?
	//Crear un id para asignarlo a cada entrada del weakHashmap
	//Probar a hacerlo con una simple lista
	public double resolution = 256;

	@Override
	public void load(PrimitiveManager primitiveManager) throws ExtensionException {
		//Creation of fuzzy sets
		primitiveManager.addPrimitive("fuzzy-evaluation", new Evaluation());
		primitiveManager.addPrimitive("fuzzy-discrete-numeric-set", new DiscreteNumeric());
		primitiveManager.addPrimitive("fuzzy-piecewise-linear-set", new PiecewiseLinear());
		primitiveManager.addPrimitive("fuzzy-trapezoidal-set", new Trapezoidal());
		primitiveManager.addPrimitive("fuzzy-logistic-set", new Logistic());
		primitiveManager.addPrimitive("fuzzy-gaussian-set", new Gaussian());
		primitiveManager.addPrimitive("fuzzy-exponential-set", new Exponential());
		primitiveManager.addPrimitive("fuzzy-interval-with-points-set", new Interval());
		primitiveManager.addPrimitive("fuzzy-discrete-numeric-set-with-label", new DiscreteNumericWithLabel());
		primitiveManager.addPrimitive("fuzzy-piecewise-linear-set-with-label", new PiecewiseLinearWithLabel());
		primitiveManager.addPrimitive("fuzzy-trapezoidal-set-with-label", new TrapezoidalWithLabel());
		primitiveManager.addPrimitive("fuzzy-logistic-set-with-label", new LogisticWithLabel());
		primitiveManager.addPrimitive("fuzzy-gaussian-set-with-label", new GaussianWithLabel());
		primitiveManager.addPrimitive("fuzzy-exponential-set-with-label", new ExponentialWithLabel());
		primitiveManager.addPrimitive("fuzzy-interval-with-points-set-with-label", new IntervalWithLabel());
		//Defuzzification
		primitiveManager.addPrimitive("fuzzy-FOM", new FOM());
		primitiveManager.addPrimitive("fuzzy-LOM", new LOM());
		primitiveManager.addPrimitive("fuzzy-MOM", new MOM());
		primitiveManager.addPrimitive("fuzzy-MeOM", new MeOM());
		primitiveManager.addPrimitive("fuzzy-COG", new COG());
		//Checks results
		primitiveManager.addPrimitive("fuzzy-plot", new FuzzyPlot());
		primitiveManager.addPrimitive("fuzzy-evaluation", new Evaluation());
		//Operators with fuzzy sets
		primitiveManager.addPrimitive("fuzzy-min", new MinOrAnd());
		primitiveManager.addPrimitive("fuzzy-and", new MinOrAnd());
		//Addittional functions
		primitiveManager.addPrimitive("check", new Checker());
		primitiveManager.addPrimitive("checkPoint", new PointChecker());
		primitiveManager.addPrimitive("and-interval", new AndInterval());
		primitiveManager.addPrimitive("points", new Points());
		primitiveManager.addPrimitive("lower-envelope", new LowerEnvelope());

		
		
	}

}
