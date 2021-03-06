package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.CuartoDTO;
import com.ipn.mx.modelo.entidades.Grafica;
import com.ipn.mx.utilidades.HibernateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.hibernate.result.ResultSetOutput;

public class CuartoDAO {
    
    private static final String SQL_GRAFICAR = "call sp_datosGrafica()";
    
    public void create(CuartoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.save(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public void update(CuartoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.update(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public void delete(CuartoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.delete(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public CuartoDTO read(CuartoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getIdCuarto()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List<CuartoDTO> readAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<CuartoDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Cuarto c order by c.idCuarto");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public List<CuartoDTO> readReservables(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<CuartoDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Cuarto c where c.esReservable = 1 order by c.idCuarto");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public List grafica(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List l = new ArrayList();
        try{
            transaction.begin();
            Query q = session.createSQLQuery(SQL_GRAFICAR).addEntity(Grafica.class);
            for(Grafica g : (List<Grafica>) q.list()){
                l.add(g);
            }
            transaction.commit();
        }catch(HibernateException he){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return l;
    }
    
    public String estaEnUo(CuartoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String msj = "";
        try{
            transaction.begin();
            ProcedureCall call = session.createStoredProcedureCall( "sp_estaEnUso" );
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(java.sql.Date.valueOf(LocalDate.now()));
            
            call.registerParameter("idC",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getIdCuarto()+"");
            call.registerParameter("fecha",String.class,ParameterMode.IN).bindValue(fecha);
            ResultSetOutput rs = (ResultSetOutput)call.getOutputs().getCurrent();            
            msj = (String) rs.getSingleResult();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive())
                transaction.rollback();
        }
        return msj;
    } 
}
