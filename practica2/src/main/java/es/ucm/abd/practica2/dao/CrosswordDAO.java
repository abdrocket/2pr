package es.ucm.abd.practica2.dao;

import java.io.Serializable;
import java.util.List;

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

	@Override
	public List<Palabra> findWordsByTags(String[] tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Palabra> getMatchingWords(CharConstraint[] constraints) {
		// TODO Auto-generated method stub
		return null;
	}

}
