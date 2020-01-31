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

import javax.servlet.http.HttpServletRequest;
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
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(ClientId);
        accessTokenDTO.setClient_secret(ClientSecret);
        accessTokenDTO.setRedirect_uri(RedirectUrl);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getuser(accessToken);


        //把user对象插入数据库
        User user = new User();
        user.setAccountid(String.valueOf(githubUser.getId()));
        user.setName(githubUser.getName());
        user.setToken(UUID.randomUUID().toString());
        user.setBio(githubUser.getBio());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insertuser(user);

        //页面跳转
        if (githubUser != null) {
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
