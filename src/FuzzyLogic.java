

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;


public class FuzzyLogic extends DefaultClassManager{
	
	//Crear un WeakHashMap para hacer de regisitro?
	//Crear un id para asignarlo a cada entrada del weakHashmap
	//Probar a hacerlo con una simple lista

	@Override
	public void load(PrimitiveManager primitiveManager) throws ExtensionException {
		primitiveManager.addPrimitive("fuzzy-evaluation", new Evaluation());
		primitiveManager.addPrimitive("fuzzy-discrete-numeric-set", new DiscreteNumeric());
		primitiveManager.addPrimitive("fuzzy-piecewise-linear-set", new PiecewiseLinear());
		primitiveManager.addPrimitive("fuzzy-discrete-numeric-set-with-label", new DiscreteNumericWithLabel());
		primitiveManager.addPrimitive("fuzzy-piecewise-linear-set-with-label", new PiecewiseLinearWithLabel());
		primitiveManager.addPrimitive("check", new Checker());
		
		
	}

}
