package com.nest_lot.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.nest_lot.exceptions.paramExecption;

/**
 * baseDao 基本crud 方法声明
 * 
 * @author wf
 *
 */
@SuppressWarnings("all")
public interface BaseDao<T> {

	/**
	 * 添加单条记录
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(T entity) throws paramExecption;

	/**
	 * 批量添加记录
	 * 
	 * @param entites
	 * @return
	 * @throws DataAccessException
	 */
	public int insertBatch(List<T> entites) throws paramExecption;

	/**
	 * 查询记录详情 bean 返回
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public T selectByPrimaryKey(@Param("uuId") String uuId) throws paramExecption;

	/**
	 * 查询记录详情Map 返回
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, String> selectByPrimaryKeyMap(@Param("uuId") String uuId) throws paramExecption;

	/**
	 * 查询记录详情
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public T selectByPrimaryLongKey(@Param("Id") Long Id) throws paramExecption;

	/**
	 * 查询记录List
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> selectByListId(@Param("uuId") String uuId) throws paramExecption;

	/**
	 * 分页多条件查询记录
	 * 
	 * @param baseQuery
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> queryForPage(BaseQuery baseQuery) throws paramExecption;

	/**
	 * 更新单条记录
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int update(T entity) throws paramExecption;

	/**
	 * 批量更新记录
	 * 
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatch(Map map) throws paramExecption;

	/**
	 * 删除单条记录
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public int delete(String id) throws paramExecption;

	/**
	 * 批量删除记录
	 * 
	 * @param ids
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatch(Integer[] ids) throws paramExecption;

	/**
	 * 用户参加活动列表
	 */
	public List<T> selectUserJoinsActivity(@Param("userUuid") String userUuid) throws paramExecption;

}
