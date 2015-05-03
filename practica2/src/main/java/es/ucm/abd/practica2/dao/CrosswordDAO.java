package es.ucm.abd.practica2.dao;

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
		session.save(crossword);
		tr.commit();
		return crossword.getId();
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
	@SuppressWarnings("unchecked")
	public List<Object[]> getCrosswordData(String str) {
		List<Object[]> l = null;
		Session session = sf.openSession();
		Query query = session.createQuery("SELECT cr.id, cr.titulo, cr.fechaCreacion, COUNT(con.id)"
						+ " FROM Crucigrama AS cr LEFT JOIN cr.palabras AS con "
						+ " WHERE cr.titulo LIKE :str GROUP BY cr.id");
		
		query.setString("str", "%" + str + "%");
		l = (List<Object[]>) query.list();
		session.close();
		return l;
	}

	/**
	 * El metodo devuelve las palabras que tienen todas las etiquetas pasadas
	 * por parámetro o todas las palabras en caso de pasar un array vacío. 
	 * 
	 * @param tags
	 * @return list of words
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Palabra> findWordsByTags(String[] tags) {

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
			
			for(int i = 0; i < constraints.length; i++){//AND LENGTH OR SUBSTRING AND LENGTH OR SUBSTRING ....
				conditions[i] = " ( SUBSTRING(p.palabra,?,1) = ? OR LENGTH(p.palabra)+1 < ?) ";
			}

			Query query = session.createQuery("FROM Palabra p WHERE" + StringUtils.join(conditions, " AND "));
			
			int j = 0;
			for (int i = 0; i < constraints.length; i++) {
				query.setInteger(j, constraints[i].getPosition());
				j++;
				query.setCharacter(j, constraints[i].getCharacter());
				j++;
				query.setInteger(j, constraints[i].getPosition());
				j++;
			}
			palabras = (List<Palabra>)query.list();
		}
		session.close();
		return palabras;
	}

}
