
BaseMapper: 写着玩的不用写sql的Mybatis Mapper.

> 纯粹是研究Mybatis而写, 类似成熟的功能, `Mybatis_Plus`已经全部具备, 推荐使用.

继承`org.luvx.common.base.BaseMapper`即可实现不用写sql实现数据库操作, 

支持一下操作:
```Java
int insert(@Param("record") T record);
int insertSelective(@Param("record") T record);
int insertList(@Param("records") Collection<T> records);
int deleteByPrimaryKey(@Param("id") Serializable id);
int deleteSelective(@Param("record") T record);
int deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);
int deleteSelectiveList(@Param("records") Collection<T> records);
int updateByPrimaryKey(@Param("record") T record);
int updateByPrimaryKeySelective(@Param("record") T record);
int updateSelective(@Param("record") T record, @Param("target") T target);
int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") T record);
int updateSelectiveList(@Param("records") Collection<T> records, @Param("target") T target);
T selectByPrimaryKey(@Param("id") Serializable id);
List<T> selectSelective(@Param("record") T record);
List<T> selectBatchIds(@Param("ids") Collection<Serializable> ids);
List<T> selectSelectiveList(@Param("records") Collection<T> records);
```