package com.dmd.common.dao.base.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import com.bsming.common.dao.base.exception.ServiceDaoException;
import com.bsming.common.dao.base.exception.ServiceException;
import com.bsming.common.dao.base.service.BaseDaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.gemantic.dal.dao.Dao;
import com.gemantic.dal.dao.exception.DaoException;

public class BaseDaoServiceImpl implements BaseDaoService {

    protected Dao dao;

    private static final Log log = LogFactory.getLog(BaseDaoServiceImpl.class);

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        log.info("set dao " + dao);
        this.dao = dao;
    }

    @Override
    public List<Long> getIdsByDynamicCondition(Class clz, Map<String, Object> conditions, Integer start, Integer limit)
            throws ServiceException, ServiceDaoException {
        // log.info(clz + " want run dynamic  " + conditions + " start " + start
        // + " size " + limit);

        String table = null;
        String sql = "";
        Method method;
        try {
            Annotation annotation = clz.getAnnotation(Table.class);
            Class<Annotation> type = (Class<Annotation>) annotation.annotationType();

            method = type.getDeclaredMethod("name", null);
            table = (String) method.invoke(annotation, null);
        } catch (Throwable t) {
            t.printStackTrace();
            log.error(clz + "  run dynamic  wrong " + conditions + " start " + start + " size " + limit);
        }

        try {

            if (conditions.containsKey("@table")) {
                table = (String) conditions.get("@table");
            }
            sql = convert2Sql(conditions, start, limit, table);
            // log.info("sql:" + sql);

            Object o = dao.excuteSimpleSql(sql, clz);

            if (o instanceof List) {
                log.info(sql + " result is list " + o);
                List<Long> ids = new ArrayList();

                for (Object oo : (List) o) {
                    BigInteger id = (BigInteger) oo;
                    ids.add(id.longValue());
                }
                return ids;
            } else {
                log.info(sql + " result is not list " + o + " instance " + o.getClass());

                List<Long> ids = new ArrayList();
                BigInteger id = (BigInteger) o;
                ids.add(id.longValue());
                return ids;
            }

        } catch (DaoException e) {
            log.error(" count by getPuserIds " + sql);
            log.error(e);
            e.printStackTrace();
            throw new ServiceDaoException(e);
        }
    }

    private String convert2Sql(Map<String, Object> conditions, Integer start, Integer limit, String table) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer = sqlBuffer.append("select ");
        if (conditions.containsKey("@query")) {
            sqlBuffer = sqlBuffer.append(conditions.get("@query"));
        } else {
            sqlBuffer = sqlBuffer.append("id");
        }

        sqlBuffer = sqlBuffer.append(" from ");
        sqlBuffer = sqlBuffer.append(table);
        sqlBuffer = sqlBuffer.append(" where 1 = 1 ");

        boolean querySentence = false;
        for (String field : conditions.keySet()) {

            String[] ps = field.split("&");

            if (ps.length == 1) {
                if (field.startsWith("@")) {

                    continue;
                } else {

                    sqlBuffer = sqlBuffer.append(" and ");
                }

                sqlBuffer = sqlBuffer.append(field);
                sqlBuffer = sqlBuffer.append(" = ");

            } else {
                sqlBuffer = sqlBuffer.append(" and ");
                sqlBuffer = sqlBuffer.append(ps[0]);

                sqlBuffer = sqlBuffer.append(ps[1]);

            }


            sqlBuffer = sqlBuffer.append(conditions.get(field));


        }

        if (conditions.containsKey("@order")) {
            sqlBuffer = sqlBuffer.append(" order by ");
            sqlBuffer = sqlBuffer.append(conditions.get("@order"));
        } else {

        }

        sqlBuffer = sqlBuffer.append(" limit ");
        sqlBuffer = sqlBuffer.append(start);
        sqlBuffer = sqlBuffer.append(" , ");
        sqlBuffer = sqlBuffer.append(limit);

        String sql = sqlBuffer.toString();
        return sql;
    }

    @Override
    public boolean fakeDelete(Class clz, Long id) throws ServiceException, ServiceDaoException {
        try {
            this.dao.fakeDelete(clz, id);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public void deleteList(Class clz, List<Long> ids) throws ServiceException, ServiceDaoException {

        log.info(clz + " want delete " + ids);
        try {
            this.dao.deleteList(clz, ids);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }


//    public Object getObjectsByDynamicCondition(Class clz, Map<String, Object> conditions, Integer start, Integer limit)
//            throws ServiceException, ServiceDaoException {
//        // log.info(clz + " want run dynamic  " + conditions + " start " + start
//        // + " size " + limit);
//
//        String table = null;
//        String sql = "";
//        Method method;
//        try {
//            Annotation annotation = clz.getAnnotation(Table.class);
//            Class<Annotation> type = (Class<Annotation>) annotation.annotationType();
//
//            method = type.getDeclaredMethod("name", null);
//            table = (String) method.invoke(annotation, null);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            log.error(clz + "  run dynamic  wrong " + conditions + " start " + start + " size " + limit);
//        }
//
//        try {
//
//            if (conditions.containsKey("@table")) {
//                table = (String) conditions.get("@table");
//            }
//            sql = convert2Sql(conditions, start, limit, table);
//            log.info("sql:" + sql);
//
//            Object o = dao.excuteSimpleSql(sql, clz);
//
//            log.info("o is :" + o);
//            log.info("o is :" + o.toString());
//            return o;
///*				if (o instanceof List) {
//                    log.info(sql + " result is list " + o);
//					List<Object> result = new ArrayList();
//					result=(List) o;
//					log.info("result is :"+result);
//					log.info("result is :"+result.size());
//					log.info("result is :"+result.toArray());
//					log.info("result is :"+result.get(0));
//					return result;
//				} else {
//					log.info(sql + " result is not list " + o + " instance " + o.getClass());
//
//					List<Object> result = new ArrayList();
//					result.add(o);
//					return result;
//				}*/
//
//        } catch (DaoException e) {
//            log.error(" count by getPuserIds " + sql);
//            log.error(e);
//            e.printStackTrace();
//            throw new ServiceDaoException(e);
//        }
//    }
//
//    public Object getObjectsByDynamicCondition(Class clz, List<Long> ids)
//            throws ServiceException, ServiceDaoException {
//
//        if (ids == null || ids.size() == 0) {
//            log.info("ids is null");
//            return null;
//        }
//
//        String table = null;
//        Method method;
//        try {
//            Annotation annotation = clz.getAnnotation(Table.class);
//            Class<Annotation> type = (Class<Annotation>) annotation.annotationType();
//
//            method = type.getDeclaredMethod("name", null);
//            table = (String) method.invoke(annotation, null);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            log.error(clz + "  run dynamic  wrong " + ids);
//        }
//
//        String sql = "select * from " + table + " where id in (" + StringUtils.join(ids, ",") + ")";
//        try {
//            Object o = dao.excuteSimpleSql(sql, clz);
//            return o;
//        } catch (DaoException e) {
//            log.error(" query sql " + sql);
//            log.error(e);
//            e.printStackTrace();
//            throw new ServiceDaoException(e);
//        }
//    }

    public static void main(String[] args) throws ServiceException, ServiceDaoException {

        BaseDaoServiceImpl s = new BaseDaoServiceImpl();

        Map<String, Object> params = new HashMap();

        params.put("p.type & like ", "\"%团购%\"");
        params.put("p.name & like ", "\"%恒信饮料%\"");
        params.put(" p.update_by", "29");
        params.put(" p.status", "0");
        params.put("p.id", "pr.provider_id");
        params.put("@query", "p.id");
        params.put("@table", "provider p ,provider_publics_relation pr");
        params.put("@order", "p.update_at desc");
        params.put("p.id & in ", "3,5,6");
        // s.getIdsByDynamicCondition(String.class, params, 0,
        // Integer.MAX_VALUE);
        String sql = s.convert2Sql(params, 0, Integer.MAX_VALUE, "provider p ,provider_publics_relation pr");
        log.info("sql is " + sql);

    }

}
