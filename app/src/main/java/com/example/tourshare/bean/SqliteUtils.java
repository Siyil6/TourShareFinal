package com.example.tourshare.bean;

import android.content.ContentValues;
import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

public class SqliteUtils {
    private static String TAG = SqliteUtils.class.getSimpleName();

    /**
     * Insert search history
     *
     * @param user_id        person who we look for
     * @param search_content content
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
     * search history dependent on user
     *
     * @param user_id search target
     * @return search history
     */
    public static List<SearchHis> selectSearchHisByUser(long user_id) {
        return LitePal.where("user_id = ?", String.valueOf(user_id)).order("create_timestamp desc  ").limit(10).find(SearchHis.class);
    }

    /**
     * Know user id, looking for information
     *
     * @param user_id
     * @return related user information
     */
    public static List<User> selectUserById(long user_id) {
        return LitePal.where("id = ? ", String.valueOf(user_id)).find(User.class);
    }




    public static void updateUserDes(long user_id, String des) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("des", des);
        LitePal.update(User.class, contentValues, user_id);
    }

    public static void updateUserNickname(long user_id, String nickname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", nickname);
        LitePal.update(User.class, contentValues, user_id);
    }
    public static void updateUserName(long user_id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        LitePal.update(User.class, contentValues, user_id);
    }
    public static void updateSparedDes(long user_id, String spareDes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("spare des", spareDes);
        LitePal.update(User.class, contentValues, user_id);

    }

    public static void updateCommand(long user_id, String nickName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", nickName);
        LitePal.updateAll(Command.class, contentValues, "user_id = ? ", user_id + "");
    }
}


