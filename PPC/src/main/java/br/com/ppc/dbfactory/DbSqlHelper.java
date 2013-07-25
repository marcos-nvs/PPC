/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ppc.dbfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.Registration;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcos
 */

public class DbSqlHelper {
    
    private static Session getSessionDude() {
        
        Session session = null;
        Transaction tx = null;
        
        if (session == null || !session.isOpen()){
            return session = SessionDbSqlFactory.getCurrentSessionFaces();
        } else {
            return session;
        }
    }

    public static List getListOfObjectByKey(Class classBean, String strKey, Object value ) {
        
        Session session = null;
        Transaction tx = null;
        
        List result = null;
        
        try{
            session = getSessionDude();
            tx = session.beginTransaction();
            
            Criteria criteria = session.createCriteria(classBean);
            
            if(strKey != null){
                criteria.add(Restrictions.eq(strKey, value));
            }
            
            result = criteria.list();
            tx.commit();
            
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        
        
        
        return null;
        
    }

    public static Object getObject(Class classbean, String id) {
        
        Session session = null;
        Transaction tx = null;
        Object obj = null;
        
        try{
            obj = session.get(classbean, id);
            tx.commit();
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        
        return obj;
        
        
    }

    public static Object getObject(Class classbean, Integer id) {
        
        Session session = null;
        Transaction tx = null;
        Object obj = null;
        
        try{
            obj = session.get(classbean, id);
            tx.commit();
        }finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        
        return obj;
        
        
    }

    public static Date getDataFromDB() {
        
        Session session= null;
        Transaction tx = null;
        Date dtAtual = null;
        
        try{
            session = getSessionDude();
            tx = session.beginTransaction();
            
            Query query = session.createSQLQuery("select now()");
            List l = query.list();
            tx.commit();
            
            if (l != null && !l.isEmpty()){
                for (int i = 0; i < l.size(); i++) {
                    java.sql.Timestamp t = (java.sql.Timestamp) l.get(i);
                    dtAtual = new Date(t.getTime());
                    
                    if (dtAtual != null){
                        break;
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        } finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        
        return dtAtual;
    }

    public static void saveClass(Object obj) {
        
        Session session = getSessionDude();
//        Session session = SessionDbFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        try {
            if(obj != null){
                session.save(obj);
                tx.commit();
            }
        } finally{
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }
}
