package general;

import java.util.Map;
import java.util.WeakHashMap;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

import primitives.*;
import primitives.Defuzzification.*;
import primitives.Operators.*;
import primitives.implication.Cut;
import primitives.implication.Power;
import primitives.implication.Prod;
import primitives.rules.CutRule;
import primitives.rules.MaxCutRule;
import primitives.rules.MaxProdRule;
import primitives.rules.MinCutRule;
import primitives.rules.MinProdRule;
import primitives.rules.ProdRule;
import sets.FuzzySet;


public class FuzzyLogic extends DefaultClassManager{
	
	//Crear un WeakHashMap para hacer de regisitro?
	//Crear un id para asignarlo a cada entrada del weakHashmap
	private static Map<String,FuzzySet> registry = new WeakHashMap<String,FuzzySet>();
	public static boolean errorShown = false;
	
	public static Map<String,FuzzySet> getRegistry(){
		return registry;
	}

	@Override
	public void load(PrimitiveManager primitiveManager) throws ExtensionException {
		//Creation of fuzzy sets
		primitiveManager.addPrimitive("evaluation", new Evaluation());
		primitiveManager.addPrimitive("discrete-numeric-set", new DiscreteNumeric());
		primitiveManager.addPrimitive("piecewise-linear-set", new PiecewiseLinear());
		primitiveManager.addPrimitive("trapezoidal-set", new Trapezoidal());
		primitiveManager.addPrimitive("logistic-set", new Logistic());
		primitiveManager.addPrimitive("gaussian-set", new Gaussian());
		primitiveManager.addPrimitive("exponential-set", new Exponential());
		primitiveManager.addPrimitive("interval-with-points-set", new Interval());
		primitiveManager.addPrimitive("discrete-numeric-set-with-label", new DiscreteNumericWithLabel());
		primitiveManager.addPrimitive("piecewise-linear-set-with-label", new PiecewiseLinearWithLabel());
		primitiveManager.addPrimitive("trapezoidal-set-with-label", new TrapezoidalWithLabel());
		primitiveManager.addPrimitive("logistic-set-with-label", new LogisticWithLabel());
		primitiveManager.addPrimitive("gaussian-set-with-label", new GaussianWithLabel());
		primitiveManager.addPrimitive("exponential-set-with-label", new ExponentialWithLabel());
		primitiveManager.addPrimitive("interval-with-points-set-with-label", new IntervalWithLabel());
		//Defuzzification
		primitiveManager.addPrimitive("FOM-of", new FOM());
		primitiveManager.addPrimitive("LOM-of", new LOM());
		primitiveManager.addPrimitive("MOM-of", new MOM());
		primitiveManager.addPrimitive("MeOM-of", new MeOM());
		primitiveManager.addPrimitive("COG-of", new COG());
		//Check results
		primitiveManager.addPrimitive("plot", new FuzzyPlot());
		primitiveManager.addPrimitive("evaluation-of", new Evaluation());
		//Operators with fuzzy sets
		primitiveManager.addPrimitive("min", new MinAnd());
		primitiveManager.addPrimitive("and", new MinAnd());
		primitiveManager.addPrimitive("max", new MaxOr());
		primitiveManager.addPrimitive("or", new MaxOr());
		primitiveManager.addPrimitive("sum", new Sum());
		primitiveManager.addPrimitive("prob-or", new ProbOr());
		primitiveManager.addPrimitive("not", new Not());
		//Implication Operators and hedges
		primitiveManager.addPrimitive("truncate", new Cut());
		primitiveManager.addPrimitive("prod", new Prod());
		primitiveManager.addPrimitive("power", new Power());
		//Rules
		primitiveManager.addPrimitive("rule", new CutRule());
		primitiveManager.addPrimitive("truncate-rule", new CutRule());
		primitiveManager.addPrimitive("prod-rule", new ProdRule());
		primitiveManager.addPrimitive("min-truncate-rule", new MinCutRule());
		primitiveManager.addPrimitive("and-rule", new MinCutRule());
		primitiveManager.addPrimitive("min-prod-rule", new MinProdRule());
		primitiveManager.addPrimitive("max-truncate-rule", new MaxCutRule());
		primitiveManager.addPrimitive("or-rule", new MaxCutRule());
		primitiveManager.addPrimitive("max-prod-rule", new MaxProdRule());
		//Additional functions(Required)
		primitiveManager.addPrimitive("set-with-label", new SetFinder());
		//Additional functions(Mine)
		primitiveManager.addPrimitive("set-resolution", new Resolution());
		primitiveManager.addPrimitive("check", new Checker());
		primitiveManager.addPrimitive("checkPoint", new PointChecker());
		primitiveManager.addPrimitive("and-interval", new AndInterval());
		primitiveManager.addPrimitive("points", new Points());
		primitiveManager.addPrimitive("lower-envelope", new LowerEnvelope());
		primitiveManager.addPrimitive("degree-of-fulfillment", new Degree());

		
		
	}

}
