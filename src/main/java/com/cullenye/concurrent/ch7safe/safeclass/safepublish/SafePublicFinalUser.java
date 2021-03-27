package com.cullenye.concurrent.ch7safe.safeclass.safepublish;

/**
 * 类说明：final类型委托给线程安全的类来发布
 */
public class SafePublicFinalUser {

    private final SynFinalUser user;

    public SynFinalUser getUser() {
        return user;
    }

    public SafePublicFinalUser(FinalUserVo user) {
        this.user = new SynFinalUser(user);
    }

    public static class SynFinalUser{
        private final FinalUserVo userVo;
        private final Object lock = new Object();

        public SynFinalUser(FinalUserVo userVo) {
            this.userVo = userVo;
        }

        public int getAge() {
            synchronized (lock){
                return userVo.getAge();
            }
        }

        public void setAge(int age) {
            synchronized (lock){
                userVo.setAge(age);
            }
        }
    }
}
