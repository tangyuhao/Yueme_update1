package com.syc.yueme.avobject;

import android.util.Log;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.base.App;

import java.util.Date;

public class User {
    public static enum  relationMode_user {ADD,REMOVE}
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AVATAR = "avatar";
    public static final String SIGN = "sign";
    public static final String NICKNAME = "nickname";
    public static final String LOCATION = "location";
    public static final String GENDER = "gender";
    public static final String INSTALLATION = "installation";
    public static final String SCHOOL = "school";
    public static final String STUID = "stuId";
    public static final String BIRTHDAY = "birthday";
    public static final String PEOPLES = "peoples";
    public static final String HOMETOWN = "hometown";
    public static final String CHARACRISTICS = "characristics";
    public static final String HOBBIES = "hobbies";
    public static final String SPECIALITY = "speciality";
    public static final String IGNMESG = "ignMesg";
    public static final String LIKEMESG = "likeMesg";
    public static final String SENDMESG = "sendMesg";
    public static final String TRYYUEMESG = "tryYueMesg";
    public static final String YUESUCCESSMESG = "yueSuccessMesg";
    public static final String LIKEUSER = "likeUser";
    public static final String HATEUSER = "hateUser";
    public static final String FRIENDS = "friends";
    public static final String EMAIL = "email";
    public static final String EMAILVERIFIED = "emailVerified";
    public static final String MOBILEPHONENUMBER = "mobilePhoneNumber";
    public static final String MOBILEPHONEVERIFIED = "mobilePhoneVerified";
    public static final String CREATEAT_USER = "createAt";
    public static final String UPDATEDAT_USER = "updatedAt";
    public static final String YPA = "YPA";

    public static String[] genderStrings = new String[]{App.ctx.getString(R.string.male),
            App.ctx.getString(R.string.female)};

    public static String getCurrentUserId() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            return user.getObjectId();
        } else {
            return null;
        }
    }

    public static enum Gender {
        Male(0), Female(1);

        int value;

        Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Gender fromInt(int index) {
            return values()[index];
        }
    }

    public static String getAvatarUrl(AVUser user) {
        AVFile avatar = user.getAVFile(AVATAR);
        if (avatar != null) {
            return avatar.getUrl();
        } else {
            return null;
        }
    }

    public static Gender getGender(AVUser user) {
        int genderInt = user.getInt(GENDER);
        return Gender.fromInt(genderInt);
    }

    public static void setGender(AVUser user, Gender gender) {
        user.put(GENDER, gender.getValue());
    }

    public static String getGenderDesc(AVUser user) {
        Gender gender = getGender(user);
        return genderStrings[gender.getValue()];
    }

    public static boolean getEmailverified(AVUser user)
    {
        return user.getBoolean(EMAILVERIFIED);
    }

    public static boolean getMobilePhoneVerified(AVUser user)
    {
        return user.getBoolean(MOBILEPHONEVERIFIED);
    }

    public static void setNickname(AVUser user, String nickname)
    {
        user.put(NICKNAME,nickname);
    }

    public static String getNickname(AVUser user)
    {

        return user.getString(NICKNAME);
    }

    public static void setSign(AVUser user, String sign)
    {
        user.put(SIGN,sign);
    }

    public static String getSign(AVUser user)
    {
        return user.getString(SIGN);
    }

    public static void setMobilephonenumber(AVUser user, String mobilePhoneNumber)
    {
        user.put(MOBILEPHONENUMBER,mobilePhoneNumber);
    }

    public static String getMobilephonenumber(AVUser user)
    {

        return user.getString(MOBILEPHONENUMBER);
    }

    public static void setYPA(AVUser user, int ypa)
    {
        user.put(YPA,ypa);
    }

    public static int getYPA(AVUser user)
    {
        int ypa = user.getInt(YPA);
        return ypa;
    }

    public static void setSchool(AVUser user, String school)
    {
        user.put(SCHOOL,school);
    }

    public static String getSchool(AVUser user)
    {
        String school = user.getString(SCHOOL);
        return school;
    }

    public static void setStuId(AVUser user, String stuId)
    {
        user.put(STUID,stuId);
    }

    public static String getStuId(AVUser user)
    {
        String stuId = user.getString(STUID);
        return stuId;
    }




    public static AVInstallation getInstallation(AVUser user) {
        try {
            return user.getAVObject(INSTALLATION, AVInstallation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void setPeoples(AVUser user, String peoples)
    {
        user.put(PEOPLES,peoples);
    }
    public static String getPeoples(AVUser user) {
        return user.getString(PEOPLES);
    }

    public static void setHometown(AVUser user, String hometown)
    {
        user.put(HOMETOWN,hometown);
    }

    public static String getHometown(AVUser user) {
        return user.getString(HOMETOWN);
    }

    public static void setCharacristics(AVUser user, String characristics)
    {
        user.put(CHARACRISTICS,characristics);
    }

    public static String getCharacristics(AVUser user) {
        return user.getString(CHARACRISTICS);
    }

    public static void setHobbies(AVUser user, String hobbies)
    {
        user.put(HOBBIES,hobbies);
    }

    public static String getHobbies(AVUser user) {
        return user.getString(HOBBIES);
    }

    public static void setSpeciality(AVUser user, String speciality)
    {
        user.put(SPECIALITY,speciality);
    }

    public static String getSpeciality(AVUser user) {
        return user.getString(SPECIALITY);
    }

    public static void setEmail(AVUser user, String email)
    {
        user.put(EMAIL,email);
    }

    public static String getEmail(AVUser user) {
        return user.getString(EMAIL);
    }

    public static String getBirthday(AVUser user) {
        return user.getString(BIRTHDAY);
    }

    public static void setBirthday(AVUser user, String birthday)
    {
        user.put(BIRTHDAY,birthday);
    }

    public static void changeIgnMesg(AVUser user, AVObject ignMesg, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(IGNMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(ignMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(ignMesg);
        }
    }

    public static void changeLikeMesg(AVUser user, AVObject likeMesg, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(LIKEMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(likeMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(likeMesg);
        }
    }

    public static void changeTryYueMesg(AVUser user, AVObject tryYueMesg, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(TRYYUEMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(tryYueMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(tryYueMesg);
        }
    }

    public static void changeSendMesg(AVUser user, AVObject sendMesg, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(SENDMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(sendMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(sendMesg);
        }
    }

    public static void changeYueSuccessMesg(AVUser user, AVObject YueSuccessMesg, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(YUESUCCESSMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(YueSuccessMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(YueSuccessMesg);
        }
    }

    public static void changeLikeUser(AVUser user, AVUser likeUser, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(LIKEUSER);
        if (mode == relationMode_user.ADD)
        {
            relation.add(likeUser);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(likeUser);
        }
    }

    public static void changeHateUser(AVUser user, AVUser hateUser, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(HATEUSER);
        if (mode == relationMode_user.ADD)
        {
            relation.add(hateUser);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(hateUser);
        }
    }

    public static void changeFriends(AVUser user, AVUser friends, relationMode_user mode)
    {
        if (user != null) Log.d("获得当前USER", user.getUsername());
        AVRelation<AVObject> relation = user.getRelation(FRIENDS);
        if (mode == relationMode_user.ADD)
        {
            relation.add(friends);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(friends);
        }
    }







}
