import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.ucm.abd.practica2.dao.AbstractCrosswordDAO;
import es.ucm.abd.practica2.dao.CrosswordDAO;
import es.ucm.abd.practica2.model.Contiene;
import es.ucm.abd.practica2.model.Crucigrama;
import es.ucm.abd.practica2.model.Orientation;
import es.ucm.abd.practica2.model.Palabra;


public class CrosswordFacade implements
		AbstractCrosswordFacade<Crucigrama, Palabra> {

	@Override
	public Crucigrama newCrossword(String title, Date date) {
		return new Crucigrama(title, date);
	}

	@Override
	public Palabra newDefinition(String sequence, String hint, String[] tags) {
		return new Palabra(sequence,hint,new byte[]{},tags);
	}

	@Override
	public void addWordToCrossword(Crucigrama crossword, Palabra word, int row,
			int column, Orientation orientation) {
		crossword.addWord(crossword, word, row, column, orientation);
	}

	@Override
	public String getAnswerOfWord(Palabra word) {
		return word.getPalabra();
	}

	@Override
	public String[] getTagsOfWord(Palabra word) {
		return word.getEtiquetas();
	}

	@Override
	public String getHintOfWord(Palabra word) {
		return word.getEnunciado();
	}

	@Override
	public String getTitleOfCrossword(Crucigrama crossword) {
		return crossword.getTitulo();
	}

	@Override
	public Date getDateOfCrossword(Crucigrama crossword) {
		return crossword.getFechaCreacion();
	}

	@Override
	public List<Object[]> getWordsOfCrossword(Crucigrama crossword) {
		List<Contiene> palabras = crossword.getPalabras();
		List<Object[]> crossWords = new LinkedList<Object[]>();
		for(Contiene c:palabras){
			Palabra p = c.getPalabra();
			crossWords.add(new Object[]{p.getPalabra(),
					c.getX(),c.getY(),c.getOrientacion()});
		}
		return crossWords;
	}

	@Override
	public AbstractCrosswordDAO<Crucigrama, Palabra> createDAO() {
		return new CrosswordDAO();
	}


}
