package life.jjk.community.controller;

import life.jjk.community.mapper.UserMapper;
import life.jjk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping({"/"})
    public String index(HttpServletRequest request) {

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectuser(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
