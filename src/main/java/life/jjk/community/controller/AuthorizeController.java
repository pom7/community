package life.jjk.community.controller;

import life.jjk.community.dto.AccessTokenDTO;
import life.jjk.community.dto.GithubUser;
import life.jjk.community.mapper.UserMapper;
import life.jjk.community.model.User;
import life.jjk.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author
 * @create 2020-01-28 14:33
 **/
@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    public String ClientId;
    @Value("${github.client.secret}")
    public String ClientSecret;
    @Value("${github.redirect.url}")
    public String RedirectUrl;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(RedirectUrl);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getuser(accessToken);

        //页面跳转
        if (githubUser != null) {
            //把user对象插入数据库
            User user = new User();
            user.setAccountid(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setBio(githubUser.getBio());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertuser(user);
            //写session
            request.getSession().setAttribute("user", githubUser);
            //写cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
