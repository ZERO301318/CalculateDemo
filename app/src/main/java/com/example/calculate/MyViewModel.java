package com.example.calculate;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

//最高成绩、本次成绩、当前成绩、
public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    boolean winFlag;
    public final static String KEY_HIGH_SCORE = "key_high_score";
    public final static String KEY_LEFT_NUMBER = "key_left_number";
    public final static String KEY_RIGHT_NUMBER = "key_right_number";
    public final static String KEY_OPERATOR = "key_operator";
    public final static String KEY_ANSWER = "key_answer";
    public final static String KEY_CURRENT_SCORE = "key_current_score";
    private final static String SHP_SAVED_KEY = "shp_saved_key";

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences shp = getApplication().getSharedPreferences(SHP_SAVED_KEY, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_LEFT_NUMBER,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_RIGHT_NUMBER,0);
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle = handle;
        winFlag = false;
    }
    public MutableLiveData<Integer> getLeft(){return handle.getLiveData(KEY_LEFT_NUMBER);}
    public MutableLiveData<Integer> getRight(){return handle.getLiveData(KEY_RIGHT_NUMBER);}
    public MutableLiveData<Integer> getAnswer(){return handle.getLiveData(KEY_ANSWER);}
    public MutableLiveData<String> getOperator(){return handle.getLiveData(KEY_OPERATOR);}
    public MutableLiveData<Integer> getCurrentScore(){return handle.getLiveData(KEY_CURRENT_SCORE);}
    public MutableLiveData<Integer> getHighScore(){return handle.getLiveData(KEY_HIGH_SCORE);}

    /*生成算式
    * 非负数的计算器
    * LEVEL以下级别的计算
    * */
    public void generator(){
        int LEVEL = 20;
        Random random = new Random();
        int x = random.nextInt(LEVEL) + 1;
        int y = random.nextInt(LEVEL) + 1;
        if (x % 2 == 0) {
            getOperator().setValue("+");
            if(x > y){
                getLeft().setValue(x - y);
                getRight().setValue(y);
                getAnswer().setValue(x);
            }else{
                getLeft().setValue(y - x);
                getRight().setValue(x);
                getAnswer().setValue(y);
            }
        }else {
            getOperator().setValue("-");
            if(x < y){
                getAnswer().setValue(y -x);
                getLeft().setValue(y);
                getRight().setValue(x);
            }else {
                getAnswer().setValue(x - y);
                getLeft().setValue(x);
                getRight().setValue(y);
            }
        }
    }

    public void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(SHP_SAVED_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        editor.apply();
    }

    public void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if(getCurrentScore().getValue() > getHighScore().getValue()){
            getHighScore().setValue(getCurrentScore().getValue());
            winFlag = true;
        }
        generator();//继续答题
    }

}