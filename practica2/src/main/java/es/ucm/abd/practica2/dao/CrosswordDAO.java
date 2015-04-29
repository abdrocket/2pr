package es.ucm.abd.practica2.dao;

import java.io.Serializable;
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
