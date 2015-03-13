package primitives;


import general.SupportFunctions;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;
import org.nlogo.nvm.ExtensionContext;
import org.nlogo.plot.Plot;
import org.nlogo.plot.PlotManager;
import org.nlogo.plot.PlotPen;
import org.nlogo.window.GUIWorkspace;

import sets.FuzzySet;
import sets.PiecewiseLinearSet;


public class FuzzyPlot extends DefaultCommand{
	

	public Syntax getSyntax(){
		return Syntax.commandSyntax(new int[]{Syntax.WildcardType()});
	}

	@Override
	public void perform(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		FuzzySet f =(FuzzySet) arg0[0].get();
		//TODO: Comprobaciones de universo si Luis lo considera necesario
		ExtensionContext ec = (ExtensionContext) arg1;	
		GUIWorkspace gw = (GUIWorkspace) ec.workspace();
		PlotManager pm = (PlotManager) gw.plotManager();
		Plot p = pm.currentPlot().get();
		setRanges(p, f.getUniverse());
		if(f.isContinuous()){
			if(f instanceof PiecewiseLinearSet){
				piecewisePlot(p,f);
			}else{
				continuousPlot(p, f);
			}
		}else{
			discretePlot(p, f);
		}
		gw.updatePlots(ec.nvmContext());
	}
	
	public void setRanges(Plot p, double[] universe){
		double minRange = universe[0];
		double maxRange = universe[1];
		//Calculate X range
		if(p.xMin() <= minRange){
			minRange = p.xMin();
		}
		if(p.xMax() >= maxRange){
			maxRange = p.xMax();
		}
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
	
	public void piecewisePlot(Plot p, FuzzySet f){
		//Create, configure and add a new Pen
		PlotPen pp = p.createPlotPen("piecewise", true);
		pp.mode_$eq(0);
		p.addPen(pp);
		p.currentPen_$eq(pp);
		//Plot pen up
		pp.isDown_$eq(false);
		//Iterate over the parameters
		double previousX = Double.NaN;
		double x = Double.NaN;
		LogoList point;
		for(Object o : f.getParameters()){
			//Each parameter is a point
			point = (LogoList) o;
			x =(Double) point.first();
			if(x == previousX){
				pp.isDown_$eq(false);
			}
			//move to (x,y)
			pp.plot(x,(Double) point.get(1));
			//plot pen down
			pp.isDown_$eq(true);
			previousX = x;
		}
	}
	
	public void continuousPlot(Plot p, FuzzySet f) throws ExtensionException{
		double[] universe = f.getUniverse();
		//Create, configure and add a new Pen
		PlotPen pp = p.createPlotPen("continuous", true);
		pp.mode_$eq(2);
		p.addPen(pp);
		p.currentPen_$eq(pp);
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
	}
	
	public void discretePlot(Plot p, FuzzySet f){
		//Create, configure and add a new Pen
		PlotPen pp = p.createPlotPen("discrete", true);
		pp.mode_$eq(0);
		p.addPen(pp);
		p.currentPen_$eq(pp);
		//Iterate over the parameters
		LogoList point;
		double x = 0;
		for(Object o: f.getParameters()){
			point = (LogoList) o;
			x =(Double) point.first();
			//Plot pen up
			pp.isDown_$eq(false);
			//Move to the point(x,0)
			pp.plot(x, 0);
			//Plot pen down
			pp.isDown_$eq(true);
			//Move to the point(x,y)
			pp.plot(x,(Double) point.get(1));
		}
	}
}
