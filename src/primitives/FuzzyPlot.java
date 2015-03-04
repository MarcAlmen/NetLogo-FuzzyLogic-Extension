package primitives;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.DummyLogoThunkFactory;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoThunkFactory;
import org.nlogo.api.Options;
import org.nlogo.api.Syntax;


import org.nlogo.hubnet.protocol.PlotControl;
import org.nlogo.plot.*;
import org.nlogo.window.Events.InterfaceGlobalEvent;
import org.nlogo.window.NetLogoListenerManager;
import org.nlogo.window.PlotWidget;

import scala.Option;


public class FuzzyPlot extends DefaultCommand{
	
	public Syntax getSyntax(){
		return Syntax.commandSyntax(new int[]{Syntax.WildcardType()});
	}

	@Override
	public void perform(Argument[] arg0, Context arg1) throws ExtensionException, LogoException {
		Plot p = new Plot("prueba");
		LogoThunkFactory thunk = new DummyLogoThunkFactory();
		PlotManager pm = new PlotManager(thunk);
		PlotWidget pw = new PlotWidget(p, pm);
		//Plot p = new Plot("prueba");
		//PlotPen pp;
		//PlotPoint ppoint;
		//PlotLegend pl;
		//PlotControl pc;
//		LogoThunkFactory thunk = new DummyLogoThunkFactory();
//		PlotManager pm = new PlotManager(thunk);
//		String[] nombres = pm.getPlotNames();
//		Plot p = pm.newPlot("prueba1");
//		//Con plotlistener tengo muchas de las opciones que necesito
//		PlotListener listen = pm.listener();
//		p.xMax_$eq(100);
//		p.yMax_$eq(100);
//		pm.compilePlot(p);
//		pm.Update();
//		Option<Plot> mierda = pm.currentPlot();
		//throw new ExtensionException();


	}

}
