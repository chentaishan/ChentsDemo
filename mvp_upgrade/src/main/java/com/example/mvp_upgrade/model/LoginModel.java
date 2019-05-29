package com.example.mvp_upgrade.model;

import com.example.mvp_upgrade.ResultBean;

import java.util.Random;

public class LoginModel implements LoginContract.Model<ResultBean> {


    @Override
    public void getLoginData(LoginContract.CallBack callBack) {
        String reuslt = "我是网络数据了！！！";
        Random random = new Random();
        int i = random.nextInt();
        if (i%2==0){
            ResultBean bean = new ResultBean();
            bean.setId(i);
            callBack.updateSuccess(bean);

        }else{

            callBack.updateFailed("出错啦！！");
        }
    }
}
