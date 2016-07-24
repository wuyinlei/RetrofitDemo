package yinlei.com.retrofitdemo.bean;

import java.util.List;

/**
 * 糗事百科首页bean
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: PageBean.java
 * @author: myName
 * @date: 2016-07-24 10:06
 */

public class PageBean {



    private int count;
    private int err;
    private int total;
    private int page;
    private int refresh;
    /**
     * format : word
     * image : null
     * published_at : 1469325902
     * tag :
     * user : {"avatar_updated_at":1468315617,"uid":26801634,"last_visited_at":1426853592,"created_at":1426853592,"state":"bonded","last_device":"android_6.4.0","role":"n","login":"布鲁斯·刘能","id":26801634,"icon":"20160712092656.jpg"}
     * image_size : null
     * id : 117077621
     * votes : {"down":-3,"up":42}
     * created_at : 1469323835
     * content : 女人抓狂的喊：“你再动动试试！” …… …… …… 男人低声羞愧道：“不行了，已经射了……” 妈的，画面反转太快，都跟不上了…
     * state : publish
     * comments_count : 0
     * allow_comment : true
     * share_count : 0
     * type : fresh
     */

    private List<ItemsBean> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String format;
        private Object image;
        private int published_at;
        private String tag;
        /**
         * avatar_updated_at : 1468315617
         * uid : 26801634
         * last_visited_at : 1426853592
         * created_at : 1426853592
         * state : bonded
         * last_device : android_6.4.0
         * role : n
         * login : 布鲁斯·刘能
         * id : 26801634
         * icon : 20160712092656.jpg
         */

        private UserBean user;
        private Object image_size;
        private int id;
        /**
         * down : -3
         * up : 42
         */

        private VotesBean votes;
        private int created_at;
        private String content;
        private String state;
        private int comments_count;
        private boolean allow_comment;
        private int share_count;
        private String type;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public int getPublished_at() {
            return published_at;
        }

        public void setPublished_at(int published_at) {
            this.published_at = published_at;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public Object getImage_size() {
            return image_size;
        }

        public void setImage_size(Object image_size) {
            this.image_size = image_size;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public VotesBean getVotes() {
            return votes;
        }

        public void setVotes(VotesBean votes) {
            this.votes = votes;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
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

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public boolean isAllow_comment() {
            return allow_comment;
        }

        public void setAllow_comment(boolean allow_comment) {
            this.allow_comment = allow_comment;
        }

        public int getShare_count() {
            return share_count;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class UserBean {
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
        }

        public static class VotesBean {
            private int down;
            private int up;

            public int getDown() {
                return down;
            }

            public void setDown(int down) {
                this.down = down;
            }

            public int getUp() {
                return up;
            }

            public void setUp(int up) {
                this.up = up;
            }
        }
    }
}
