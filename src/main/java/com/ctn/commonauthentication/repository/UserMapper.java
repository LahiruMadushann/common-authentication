package com.ctn.commonauthentication.repository;
import com.ctn.commonauthentication.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User find(String username) ;
    public User findById(Long id);

    public void updatePass(String user_id, String newPass);
    public void add(String user_id, String pass, String role);
    public Integer getId(String username);
    public String getEmail(int id);

    public void updateUserName(String email, String newEmail);

}