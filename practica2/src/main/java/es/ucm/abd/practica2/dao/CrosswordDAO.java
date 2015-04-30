package es.ucm.abd.practica2.dao;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
=======
>>>>>>> 7def9208ca61dd0192c5bb4b0668483d75b2761d
import java.util.LinkedList;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.commons.lang3.StringUtils;

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
		return (Integer) id;
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

	/**
	 * Devuelve el identificador, título, fecha de creación y número de palabras
	 * de los crucigramas cuyo título contenga la palabra pasada como parámetro.
	 * 
	 * Devuelve una lista de arrays. Cada uno de estos arrays tiene cuatro
	 * componentes:
	 * 
	 * * Identificador del crucigrama (Integer). * Título del crucigrama
	 * (String). * Fecha de creación del crucigrama (Date). * Número de palabras
	 * del crucigrama (Integer o Long).
	 * 
	 * 
	 * @param str
	 *            Cadena de búsqueda del crucigrama.
	 * @return Una lista de arrays con la información descrita anteriormente.
	 */
	/*
	Query query = session
				.createQuery("SELECT c.id, c.titulo, c.fechaCreacion, COUNT(cont.crucigrama) "
						+ "FROM Crucigrama AS c, Contiene AS cont "
						+ "WHERE c.titulo LIKE :str " + "GROUP BY c.id ");
	Query query = session
				.createQuery("SELECT c.crucigrama.id, c.crucigrama.titulo, c.crucigrama.fechaCreacion, COUNT(c.crucigrama.id) "
						+ "FROM Contiene AS c "
						+ "WHERE c.crucigrama.titulo LIKE :str GROUP BY c.crucigrama.id ");
	 */@Override
	public List<Object[]> getCrosswordData(String str) {
		List<Object[]> l = null;
		Session session = sf.openSession();
		Query query = session
				.createQuery("SELECT cr.id, cr.titulo, cr.fechaCreacion, SIZE(cr.palabras) "
						+ "FROM Crucigrama AS cr "
						+ "WHERE cr.titulo LIKE :str GROUP BY cr.id ");
		query.setString("str", "%" + str + "%");
		l = (List<Object[]>) query.list();
		session.close();
		return l;
	}

	/**
	 * El metodo devuelve las palabras que tienen todas las etiquetas pasadas
	 * por parámetro o todas las palabras en caso de pasar un array vacío. Para
	 * solucionarlo he pensado ir seleccionando las palabras que tienen cada una
	 * de las etiquetas e ir añadiendolas a dos mapas(uno con la Palabra en sí y
	 * otro con el numero de veces cuyas etiquetas contiene una etiqueta de la
	 * lista(ambos mapeados por id), de esta forma para que se cumpla la
	 * condicion de seleccionar las palabras con todas las etiquetas del array,
	 * esta palabra ha debido ser seleccionada tantas veces como numero de
	 * etiquetas(ntags) hay.
	 * 
	 * @param tags
	 * @return list of words
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Palabra> findWordsByTags(String[] tags) {
<<<<<<< HEAD
		HashMap<Integer, Palabra> idToPalabra = new HashMap<Integer, Palabra>();
		HashMap<Integer, Integer> idToCount = new HashMap<Integer, Integer>();
		List<Palabra> palabras = null;
		int ntags = 0;

		if (tags.length == 0) {
			Session session = sf.openSession();
			Query query = session.createQuery("FROM Palabra");
			palabras = (List<Palabra>) query.list();
			session.close();
		} else {
			palabras = new LinkedList<Palabra>();
			for (int i = 0; i < tags.length && tags[i] != null; i++) {
				Session session = sf.openSession();
				Query query = session
						.createQuery("FROM Palabra AS p WHERE :tag MEMBER OF p.etiquetas");
				query.setString("tag", tags[i]);

				List<Palabra> matchWords = (List<Palabra>) query.list();

				for (Palabra p : matchWords) {
					int idActual = p.getId();
					if (idToPalabra.containsKey(idActual)) {
						idToPalabra.put(idActual, p);
						idToCount.put(idActual, 0);
					} else {
						idToCount.put(idActual, idToCount.get(idActual) + 1);
					}
				}
				ntags++;
				session.close();
			}

			Iterator<Integer> keyIt = idToCount.keySet().iterator();

			while (keyIt.hasNext()) {
				Integer id = keyIt.next();
				if (idToCount.get(id) == ntags) {
					palabras.add(idToPalabra.get(id));
				}
			}

		}

=======
		List<Palabra> palabras = null;
		Session session = sf.openSession();
		if(tags.length == 0){
			Query query = session.createQuery("FROM Palabra");
			palabras = (List<Palabra>)query.list();
		}else{
			String[] tagsCond = new String[tags.length];
			for (int i = 0; i < tags.length && tags[i] != null; i++) {
				tagsCond[i] = " ? MEMBER OF p.etiquetas ";	
			}
			Query query = session.createQuery("FROM Palabra AS p WHERE" + StringUtils.join(tagsCond, " AND "));
			
			for(int i = 0; i < tags.length; i++){
				query.setString(i, tags[i]);	
			}
			
			palabras = (List<Palabra>)query.list();
		}
		session.close();
>>>>>>> 7def9208ca61dd0192c5bb4b0668483d75b2761d
		return palabras;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Palabra> getMatchingWords(CharConstraint[] constraints) {
		List<Palabra> palabras = null;
		Session session = sf.openSession();
		
		if(constraints.length == 0){
			Query query = session.createQuery("FROM Palabra");
			palabras = (List<Palabra>)query.list();
		}else{
			String[] conditions = new String[constraints.length];
			
			for(int i = 0; i < constraints.length; i++){
				conditions[i] = " ( SUBSTRING(p.palabra,?,1) = ? OR LENGTH(p.palabra)+1 <> ?)";
			}
			//AND LENGTH OR SUBSTRING AND LENGTH OR SUBSTRING ....
//			String[] andConditions = new String[constraints.length];
//			for(int i = 0; i <andConditions.length; i++){
//				andConditionsi[] = StringUtils.join(andConditions, " AND ");
//			}
			
			Query query = session.createQuery("FROM Palabra p WHERE SUBSTRING(p.palabra,2,1) = 'A'"
					+ " AND SUBSTRING(p.palabra,1,1) = 'J'");
			Palabra p = (Palabra) query.uniqueResult();
			System.out.println(p);
		}
		session.close();
		return palabras;
	}

}
