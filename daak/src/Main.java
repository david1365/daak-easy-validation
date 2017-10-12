import classes.ColorGroupEntity;
import classes.ExternalJobTypeEntity;
import da.sql.DASqlQuery;
import model.BaseEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import java.math.BigDecimal;
import java.util.Map;

import static da.sql.DASqlQuery.*;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("resource/hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {

            DASqlQuery.setSession(session);

            DASqlQuery query =
                        select(ColorGroupEntity.CODE)
                        .from(ColorGroupEntity.class);

            BigDecimal code = (BigDecimal) query.getSingleResult();
            System.out.println(code);

        } finally {
            session.close();
        }

        ClassMetadata hibernateMetadata = session.getSessionFactory().getClassMetadata(ExternalJobTypeEntity.class);
        AbstractEntityPersister persister = (AbstractEntityPersister)hibernateMetadata;
        System.out.println(persister.getTableName());
        System.out.println(persister.getPropertyColumnNames("isActive")[0]);




    }
}
