package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.MallUser;
import xyz.lattice.mall.util.PageQueryUtil;

import java.util.List;

public interface MallUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(MallUser record);
    // 保存一条新记录
    int insertSelective(MallUser record);

    MallUser selectByPrimaryKey(Long userId);
    // 根据loginName查询记录
    MallUser selectByLoginName(String loginName);
    // 根据loginName和密码字段查询记录
    MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByPrimaryKeySelective(MallUser record);

    int updateByPrimaryKey(MallUser record);

    List<MallUser> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
}
