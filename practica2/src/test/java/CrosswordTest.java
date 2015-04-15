import es.ucm.abd.practica2.model.Crucigrama;
import es.ucm.abd.practica2.model.Palabra;


public class CrosswordTest extends CrosswordTestBase<Crucigrama, Palabra> {

	@Override
	protected AbstractCrosswordFacade<Crucigrama, Palabra> buildFacade() {
		return new CrosswordFacade();
	}

}
