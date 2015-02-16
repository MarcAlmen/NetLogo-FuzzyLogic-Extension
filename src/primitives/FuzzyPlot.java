package primitives;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.PlotInterface;
import org.nlogo.api.PlotPenInterface;
import org.nlogo.api.Syntax;
import org.nlogo.plot.Plot;
import org.nlogo.plot.PlotManager;
import org.nlogo.prim.plot.PlotReporter;

import scala.Option;

public class FuzzyPlot extends DefaultCommand{
	
	public Syntax getSyntax(){
		return Syntax.commandSyntax(new int[]{Syntax.WildcardType()});
	}

	@Override
	public void perform(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		Plot p = new Plot("pedrito");
		p.yMax_$eq(10000);
		p.makeDirty();
	}

}
