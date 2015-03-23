package primitives.Operators;

/**
 * Interface to define the operation to perform
 * @author Marcos Almendres
 *
 */
public interface Command {
	public double[] execute(double[] pointA, double[] pointB);
}
