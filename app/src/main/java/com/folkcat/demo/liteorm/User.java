package com.folkcat.demo.liteorm;

import com.litesuits.orm.db.annotation.Check;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;

@Table("user_table")
public class User extends BaseModel {
    private static final long serialVersionUID = 1L;
    // 非空约束 形同于@Check("userName NOT NULL")
    @NotNull
    private String userName;

    // 性别用 0 - 1 - 2替代,0=男,1=女,2=未知
    @Check("userSex >= 0 AND userSex < 3")
    // 设置默认值
    @Default("2")
    private int userSex;

    // 唯一键约束
    @Unique
    @NotNull
    private String userPhone;

    private String userEmail;

    /**
     * 构造方法，传入数据
     *
     * @param name
     * @param sex
     * @param phone
     * @param email
     */
    public User(String name, int sex, String phone, String email) {
        this.userName = name;
        this.userSex = sex;
        this.userEmail = email;
        this.userPhone = phone;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserID(int id) {
        super.id = id;
    }
}