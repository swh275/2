package com.example.shiyan21.data;

/**
 * 用于从远程数据源请求身份验证和用户信息，并维护登录状态和用户凭据信息的内存缓存的类。
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDataSource dataSource;

    // 如果用户凭据将缓存到本地存储中，建议对其进行加密
    // @see https://developer.android.com/training/articles/keystore

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public void logout() {
        dataSource.logout();
    }

    private void setLoggedInUser() {
        // 如果用户凭据将缓存到本地存储中，建议对其进行加密
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result login(String username, String password) {
        // 处理登录
        Result result = (Result) dataSource.login();
        if (result instanceof Result.Success) {
            setLoggedInUser();
        }
        return result;
    }
}