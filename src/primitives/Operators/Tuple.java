package primitives.Operators;

import java.util.List;

/**
 * Wrapper class for a the return type of some methods
 * @author Marcos Almendres
 *
 * @param <E> Use to be a FuzzySet
 */
public class Tuple<E> {
	
	private List<E> f;
	private double[] universe;
	
	public Tuple(List<E> f, double[] universe){
		this.f = f;
		this.universe = universe;
	}
	
	public List<E> getParams(){
		return f;
	}
	
	public double[] getUniverse(){
		return universe;
	}

}
