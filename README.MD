###一键换肤


*在Applocation中初始化*
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
```
*所有页面继承BaseSkinActivity，换肤时传入需要加载的皮肤资源路径，注意读写权限申请：*


```java
public class MainActivity extends BaseSkinActivity {
    public void change(View view) {
         String skinPath = Environment.getExternalStorageDirectory() + File.separator + "plugin.skin";
         SkinManager.getInstance().loadSkin(skinPath);

     }
     
    @Override
    public void changeSkin(SkinResource resource) {
        super.changeSkin(resource);
    }
 }
```
#####自定义View的特殊属性可在changeSkin回调方法方法自己实现换肤功能



