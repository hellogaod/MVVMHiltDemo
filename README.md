# 前沿

当前架构基于[dagger-hilt](https://github.com/aregpetrosyan/Android-MVVM-Boilerplate-Hilt)、[hvvm-habit](https://github.com/goldze/MVVMHabit)整合。

> 后面还会整合[aac](https://github.com/android/architecture-components-samples)框架中的代码

所以我只是一个代码搬运工。

# 架构思路
当前项目采用模块化开发，四个模块app、data、mvvmhabit、resource。

1. app是宿主模块，并且存放view+viewmodel；
2. data是数据层，主要在仓库中获取数据；
3. mvvmhabit：一些基础类，和工具类；
4. resource：所有的资源存放在当前模块中，但是使用要谨慎，命名要确定独一无二，否则资源容易被替换。

>确认一件事情，**dagger支持模块化开发**。当前dagger版本是2.38.1，因为我对当前版本源码深入学习过，用起来感觉安全感高点。

[dagger](https://juejin.cn/post/7077740630367043598)、[hilt](https://juejin.cn/post/7248286946572550205)的使用参考。

## （一）dagger
	@AndroidEntryPoint
	class LoginActivity : BaseActivity() {...}
	
	@HiltViewModel
	class LoginViewModel @Inject constructor(private val demoRepository:DemoRepository) :
	    BaseViewModel() {...}

如上代码所示，view和viewmodel之间使用dagger，如果viewmodel需要使用引入DemoRepository仓库：

	class DemoRepository @Inject constructor(
	    private val demoApiService: DemoApiService
	) 

DemoRepository仓库中的DemoApiService由NetworkModule类中的provideDemoApiService方法提供，后面不在往下举例，可自行去查看。

当前使用dagger非常显著的作用：**插拔**。通过dagger插入，依赖切换非常方便，简单。

> dagger对泛型的支持度非常不友好，所以我在mvvmhilt架构基础上把BaseActivity/BaseFragment泛型给去掉了。

## （二）app模块

宿主模块，主要寄存view + viewmodel。

1.kotlin给我们提供了一个非常方便的suspend挂起函数。异步请求和线程切换的使用非常方便：

	private fun login() {
	        if (TextUtils.isEmpty(userName.get())) {
	            ToastUtils.showToast("请输入账号！")
	            return;
	        }
	        if (TextUtils.isEmpty(password.get())) {
	            ToastUtils.showToast("请输入密码！")
	            return;
	        }
	
	        viewModelScope.launch {
	            showDialog()
	            val todo = withContext(Dispatchers.IO) {
	                demoRepository.login()
	            }
	
	            todo.let {
	                dismissDialog()
	                userName.get()?.let { it -> demoRepository.saveUserName(it) }
	                password.get()?.let { it -> demoRepository.savePassword(it) }
	
	                //进入DemoActivity页面
	                startActivity(DemoActivity::class.java)
	                //关闭页面
	                finish()
	            }
	        }
	    }

>[suspend学习参考文章](https://blog.yujinyan.me/posts/understanding-kotlin-suspend-functions/)

2.在viewmodel中使用比较多的是Observable Fields：
- ObservableBoolean
- ObservableByte
- ObservableChar
- ObservableShort
- ObservableInt
- ObservableLong
- ObservableFloat
- ObservableDouble
- ObservableParcelable
## （三） data模块

data模块提供数据，主要是给viewmodel提供仓库，仓库获取数据，当前架构提供了2中方式：
1. 仓库直接从网络获取：登录后第一个（网络访问），注意改url地址：https://www.oschina.net/
2. 仓库先拉去网络数据到room，再从room数据库获取数据：登录后最后一个（原先HILT的DEMO），注意改url地址：https://api.github.com/。

当前数据模型有3种：
1.  从网络拉取数据转换模型，在network.model包下；
2.  将网络拉去数据转换成room模型，在database包下（如果不仅过room，当前模型不存在）；
3.  repository仓库获取的主要是1和2模型下的数据，viewmodel会将其转换成domain包下的entity对象，view交互的也是该包下的模型数据。

## （四）mvvmhabit模块

该模块下主要一些抽离公共信息、常用工具类。

# 总结

当前项目采用的都是常规性架构，遇到不懂的，一定要先了解清楚，磨刀不误砍柴工。