package life.jjk.community.mapper;

import life.jjk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountid},#{token},#{gmtCreate},#{gmtModified})")
    void insertuser(User user);

    @Select("select * from user where token=#{token}")
    User selectuser(@Param("token") String token);
}