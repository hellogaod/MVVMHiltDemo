# 前沿
基于dagger-hilt、aac、hvvm-habit做的一套MVVM-hilt框架

参考文献：
1. https://github.com/aregpetrosyan/Android-MVVM-Boilerplate-Hilt
2. https://github.com/goldze/MVVMHabit
3. https://github.com/android/architecture-components-samples

我这里的mvvmhilt架构是整合以上三个架构的，所以我只是一个代码搬运工。

# 架构思路

## 一.对象转换思想

1. network.model：表示从网络请求接受到数据对象，通过asDatabaseModel转换成表对象；

2. database：当前表示表对象存储于room，当前对象通过asDatabaseModel转换成bean对象；

3. domain：viewmodel中使用的对象；

## 二. suspend函数的使用

1. 学习参考资料：https://blog.yujinyan.me/posts/understanding-kotlin-suspend-functions/

2. suspend demo：

 - （1）表示开启IO线程操作：

         init {
               //表示切换线程去请求数据 
               viewModelScope.launch(Dispatchers.IO) {
                   userListRepository.refreshUserList()
               }
           }
 - （2）请求网络数据：

       suspend fun refreshUserList() {
           try {
                //表示异步http请求
                val users = userListService.getUserList()
                database.usersDao.insertAll(users.asDatabaseModel())
           } catch (e: Exception) {
                Timber.w(e)
           }
       }
    
        @GET("/repos/square/retrofit/stargazers")
        suspend fun getUserList(): List<NetworkUserListItem>

 - （3）更新ui：

       lifecycleScope.launch {
         val posts = 🏹 retrofit.get<PostService>().fetchPosts();
         // 由于在主线程，可以拿着 posts 更新 UI
       }

3. 可观察对象

 - （1）参考地址：https://www.jianshu.com/p/3c5ecc330f84
 - （2）当前案例：ObservableParcelable

       ObservableBoolean
       ObservableByte
       ObservableChar
       ObservableShort
       ObservableInt
       ObservableLong
       ObservableFloat
       ObservableDouble
       ObservableParcelable
