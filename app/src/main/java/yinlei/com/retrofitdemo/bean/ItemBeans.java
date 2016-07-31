package yinlei.com.retrofitdemo.bean;


public class ItemBeans {

    private String format;

    private long published_at;

    private String content;
    private String state;
    private User user;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getPublished_at() {
        return published_at;
    }

    public void setPublished_at(long published_at) {
        this.published_at = published_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ItemBeans{" +
                "format='" + format + '\'' +
                ", published_at=" + published_at +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                ", user=" + user +
                '}';
    }

    public class User {

        /**
         * avatar_updated_at : 1418571809
         * uid : 13846208
         * last_visited_at : 1390853782
         * created_at : 1390853782
         * state : active
         * last_device : android_2.6.4
         * role : n
         * login : ---切随缘
         * id : 13846208
         * icon : 20141215074328.jpg
         */

        private int avatar_updated_at;
        private int uid;
        private int last_visited_at;
        private int created_at;
        private String state;
        private String last_device;
        private String role;
        private String login;
        private int id;
        private String icon;


        public int getAvatar_updated_at() {
            return avatar_updated_at;
        }

        public void setAvatar_updated_at(int avatar_updated_at) {
            this.avatar_updated_at = avatar_updated_at;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getLast_visited_at() {
            return last_visited_at;
        }

        public void setLast_visited_at(int last_visited_at) {
            this.last_visited_at = last_visited_at;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLast_device() {
            return last_device;
        }

        public void setLast_device(String last_device) {
            this.last_device = last_device;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "User{" +
                    "avatar_updated_at=" + avatar_updated_at +
                    ", uid=" + uid +
                    ", last_visited_at=" + last_visited_at +
                    ", created_at=" + created_at +
                    ", state='" + state + '\'' +
                    ", last_device='" + last_device + '\'' +
                    ", role='" + role + '\'' +
                    ", login='" + login + '\'' +
                    ", id=" + id +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

}
