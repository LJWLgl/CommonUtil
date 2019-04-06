package io.github.ljwlgl.util;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProtobufUtilTest {
    @Test
    public void serialize() throws Exception {
        List<UserInfo> users = new ArrayList<>();
        for (int i=0; i < 10000; i++) {
            UserInfo user = new UserInfo();
            user.setUid(i + 1);
            user.setUserName("nx");
            users.add(user);
        }
        byte[] bytes = ProtobufUtil.serializeList(users);
        System.out.println("普通序列化" + FastJsonUtil.toJsonString(users).getBytes().length);
        System.out.println("protobuf序列化："+ bytes.length);
        List<UserInfo> userInfos = ProtobufUtil.unSerializeList(bytes, UserInfo.class);
        System.out.println(userInfos.size());
    }

}

class UserInfo {

    private String userName;
    private Integer uid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}