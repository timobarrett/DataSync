package fivetwentysix.ware.com.dataSync;

/**
 * Created by tim on 1/29/2017.
 */
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ErrorDao {

        @Autowired
        private SessionFactory _sessionFactory;

        private Session getSession() {
            return _sessionFactory.getCurrentSession();
        }

        public void save(ErrorData error) {
            getSession().save(error);
        }

        public void delete(ErrorData error) {
            getSession().delete(error);
        }

        @SuppressWarnings("unchecked")
        public List<ErrorData> getAll() {
            return getSession().createQuery("from error_table").list();
        }
        public ErrorData getById(int id) {
            return (ErrorData) getSession().load(ErrorData.class, id);
        }

        public void update(ErrorData error) {
            getSession().update(error);
        }
}

