package com.bsming.common.dao.base.service;

import java.util.List;
import java.util.Map;

import com.bsming.common.dao.base.exception.ServiceDaoException;
import com.bsming.common.dao.base.exception.ServiceException;
import org.osoa.sca.annotations.Remotable;

@Remotable
public interface BaseDaoService {
    /**
     * @param clz
     * @param conditions
     * @param start
     * @param limit
     * @return
     * @throws ServiceException
     * @throws ServiceDaoException
     * @query publics_id
     * @order update_at desc
     */

    public List<Long> getIdsByDynamicCondition(Class clz, Map<String, Object> conditions, Integer start, Integer limit)
            throws ServiceException, ServiceDaoException;

    /**
     * 这些接口风险很大。Clz传任何一个都可以
     *
     * @param clz
     * @param id
     * @return
     * @throws ServiceException
     * @throws ServiceDaoException
     */

    public boolean fakeDelete(Class clz, Long id) throws ServiceException, ServiceDaoException;

    /**
     * 这些接口风险很大。Clz传任何一个都可以
     *
     * @param clz
     * @param id
     * @return
     * @throws ServiceException
     * @throws ServiceDaoException
     */

    public void deleteList(Class clz, List<Long> ids) throws ServiceException, ServiceDaoException;

}
