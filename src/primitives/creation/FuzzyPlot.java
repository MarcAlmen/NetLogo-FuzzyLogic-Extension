package primitives.creation;


import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;
import org.nlogo.nvm.ExtensionContext;
import org.nlogo.plot.Plot;
import org.nlogo.plot.PlotManager;
import org.nlogo.plot.PlotPen;
import org.nlogo.window.GUIWorkspace;

import sets.general.FuzzySet;
import sets.points.DiscreteNumericSet;
import sets.points.PiecewiseLinearSet;

/**
 * @author Almendres
 *
 */
public class FuzzyPlot extends DefaultCommand{
	

	public Syntax getSyntax(){
		return Syntax.commandSyntax(new int[]{Syntax.WildcardType()});
	}

	@Override
	public void perform(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet f =(FuzzySet) arg0[0].get();
		ExtensionContext ec = (ExtensionContext) arg1;	
		GUIWorkspace gw = (GUIWorkspace) ec.workspace();
		PlotManager pm = (PlotManager) gw.plotManager();
		Plot p = pm.currentPlot().get();
		//setRanges(p, f.getUniverse());
		if(f.isContinuous()){
			if(f instanceof PiecewiseLinearSet){
				piecewisePlot(p,(PiecewiseLinearSet) f);
			}else{
				continuousPlot(p, f);
			}
		}else{
			discretePlot(p,(DiscreteNumericSet) f);
		}
		p.makeDirty();
		//gw.updatePlots(ec.nvmContext());
	}
	
	public void setRanges(Plot p, double[] universe){
		double minRange = universe[0];
		double maxRange = universe[1];
		//Calculate X range
//		if(p.xMin() <= minRange){
//			minRange = p.xMin();
//		}
//		if(p.xMax() >= maxRange){
//			maxRange = p.xMax();
//		}
		if(minRange == maxRange){
			p.xMin_$eq(0);
			p.xMax_$eq(2*minRange);
		}else{
			p.xMin_$eq(minRange);
			p.xMax_$eq(maxRange);
		}
		//set y range	
		p.yMin_$eq(0);
		p.yMax_$eq(1);
	}
	
	public void piecewisePlot(Plot p, PiecewiseLinearSet f){
		//Create, configure and add a new Pen
		PlotPen pp = p.currentPen().get();
		pp.mode_$eq(0);
		//Plot pen up
		pp.isDown_$eq(false);
		//Iterate over the parameters
		double previousX = Double.NaN;
		double x = Double.NaN;
		for(double[] point : f.getParameters()){
			x = point[0];
			if(x == previousX){
				pp.isDown_$eq(false);
			}
			//move to (x,y)
			pp.plot(x, point[1]);
			//plot pen down
			pp.isDown_$eq(true);
			previousX = x;
		}
		pp.isDown_$eq(false);
	}
	
	public void continuousPlot(Plot p, FuzzySet f) throws ExtensionException{
		double[] universe = f.getUniverse();
		//Create, configure and add a new Pen
		PlotPen pp = p.currentPen().get();
		pp.mode_$eq(2);
		//Steps to iterate
		double steps = Math.floor(1 + ((universe[1] - universe[0]) * SupportFunctions.getResolution()));
		double x = universe[0];
		//plot pen up
		pp.isDown_$eq(false);
		for(int i = 0 ; i < steps ; i++){
			//plot(x,y)
			pp.plot(x, f.evaluate(x));
			//plot pen down
			pp.isDown_$eq(true);
			//increment x
			x += 1/SupportFunctions.getResolution();
			//throw new ExtensionException("" + steps);
		}
		pp.isDown_$eq(false);
	}
	
	public void discretePlot(Plot p, DiscreteNumericSet f){
		//Create, configure and add a new Pen
		PlotPen pp = p.currentPen().get();
		pp.mode_$eq(0);
		//Iterate over the parameters
		double x = 0;
		for(double[] point: f.getParameters()){
			pp.isDown_$eq(false);
			x = point[0];
			//Plot pen up
			//Move to the point(x,0)
			pp.plot(x, 0);
			//Plot pen down
			pp.isDown_$eq(true);
			//Move to the point(x,y)
			pp.plot(x,point[1]);
		}
		pp.isDown_$eq(false);
	}
}
