package life.jjk.community.dto;

/**
 * @author JJK
 * @create 2020-01-28 15:54
 **/
public class GithubUser {
    public String name;
    public long id;
    public String bio;

    public GithubUser() {
        super();
    }

    public GithubUser(String name, long id, String bio) {
        this.name = name;
        this.id = id;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


}
