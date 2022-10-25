package com.example.tourshare.bean;

import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

public class SqliteUtils {
    private  static  String  TAG   =  SqliteUtils.class.getSimpleName();
    /**
     * 插入搜索历史
     *
     * @param user_id        搜索人
     * @param search_content 搜索内容
     */
    public static void insertSearchHis(long user_id, String search_content) {
        Log.d(TAG, "insertSearchHis: ");
        SearchHis searchHis = new SearchHis();
        searchHis.setSearch_content(search_content);
        searchHis.setUser_id(user_id);
        searchHis.setCreate_timestamp(System.currentTimeMillis());
        searchHis.save();
    }

    /**
     * 依据用户查询搜索历史
     * @param user_id 搜索人
     * @return  用户的搜索历史
     */
    public static List<SearchHis> selectSearchHisByUser(long  user_id){
        return LitePal.where("user_id = ?",String.valueOf(user_id)).order("create_timestamp desc  ").limit(10).find(SearchHis.class);
    }

    /**
     * 已经用户id 查询用户信息
     * @param user_id
     * @return 对应的用户信息
     */
    public static List<User> selectUserById(long user_id){
        return   LitePal.where("id = ? ",String.valueOf(user_id)).find(User.class);
    }
}
