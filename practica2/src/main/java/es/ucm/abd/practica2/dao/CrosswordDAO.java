package es.ucm.abd.practica2.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import es.ucm.abd.practica2.model.Crucigrama;
import es.ucm.abd.practica2.model.Palabra;

public class CrosswordDAO implements AbstractCrosswordDAO<Crucigrama, Palabra> {

	private SessionFactory sf;
	
	public CrosswordDAO() {
	
	}
	@Override
	public void setSessionFactory(SessionFactory f) {
		this.sf = f;
	}

	@Override
	public int insertCrossword(Crucigrama crossword) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		Serializable id = session.save(crossword);
		tr.commit();
		return (Integer)id;
	}

	@Override
	public void insertWord(Palabra word) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.save(word);
		tr.commit();
	}

	@Override
	public Crucigrama findCrosswordById(int id) {
		Crucigrama c = null;
		Session session = sf.openSession();
		Query query = session
				.createQuery("FROM Crucigrama AS c WHERE c.id = :id");
		query.setInteger("id", id);
		c = (Crucigrama) query.uniqueResult();
		session.close();
		return c;
	}

	@Override
	public List<Object[]> getCrosswordData(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * El metodo devuelve las palabras que tienen todas las etiquetas pasadas por parámetro o todas las palabras
	 * en caso de pasar un array vacío.
	 * Para solucionarlo he pensado ir seleccionando las palabras que tienen cada una de las etiquetas
	 * e ir añadiendolas a dos mapas(uno con la Palabra en sí y otro con el numero de veces cuyas etiquetas contiene
	 * una etiqueta de la lista(ambos mapeados por id), de esta forma para que se cumpla la condicion de seleccionar
	 * las palabras con todas las etiquetas del array, esta palabra ha debido ser seleccionada tantas veces
	 * como numero de etiquetas(ntags) hay.
	 * @param tags
	 * @return list of words
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Palabra> findWordsByTags(String[] tags) {
		HashMap<Integer,Palabra> idToPalabra = new HashMap<Integer, Palabra>();
		HashMap<Integer,Integer> idToCount = new HashMap<Integer,Integer>();
		List<Palabra> palabras = null;
		int ntags = 0;
		
		if(tags.length == 0){
			Session session = sf.openSession();
			Query query = session.createQuery("FROM Palabra");
			palabras = (List<Palabra>)query.list();
			session.close();
		}else{
			palabras = new LinkedList<Palabra>();
			for (int i = 0; i < tags.length && tags[i] != null; i++) {
				Session session = sf.openSession();
				Query query = session.createQuery("FROM Palabra AS p WHERE :tag MEMBER OF p.etiquetas");
				query.setString("tag", tags[i]);
			
				List<Palabra> matchWords = (List<Palabra>)query.list();
				
				for(Palabra p : matchWords){
					int idActual = p.getId();
					if(idToPalabra.containsKey(idActual)){
						idToPalabra.put(idActual, p);
						idToCount.put(idActual, 0);
					}else{
						idToCount.put(idActual,idToCount.get(idActual)+1);
					}
				}
				ntags++;
				session.close();
			}
			
			Iterator<Integer> keyIt = idToCount.keySet().iterator();
			
			while(keyIt.hasNext()){
				Integer id = keyIt.next();
				if(idToCount.get(id)==ntags){
					palabras.add(idToPalabra.get(id));
				}
			}
			
			
		}
		
		return palabras;
	}

	@Override
	public List<Palabra> getMatchingWords(CharConstraint[] constraints) {
		// TODO Auto-generated method stub
		return null;
	}

}
