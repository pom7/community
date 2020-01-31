package life.jjk.community.mapper;

import life.jjk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountid},#{token},#{gmtCreate},#{gmtModified})")
    void insertuser(User user);
}