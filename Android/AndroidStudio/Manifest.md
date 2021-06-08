##  meta-data

####  语法
    <meta-data
      android:name="string"           
      android:resource="resource specification"           
      android:value="string" />

  <meta-data> 是一个键值对，用来为父控件存储多余的数据。
  一个控件可以包含任意数量的<meta-data>，这些值都存储在单个Bundle对象中，
  并作为PackageItemInfo.metaData字段提供给父控件。


####  属性
- android:name：该item的唯一名称。为了保证其名字唯一性，可以使用 Java 命名风格
- android:resource：对资源的引用。其值为资源的ID，可以通过该Bundle的 Bundle.getIn()方法获取其ID值
-  android:value：分配给该item的值。更多类型及介绍见官网文档

####  用法
在Android中，可以在 AndroidManifest.xml中定义meta-data信息。
最基本的用法增加item用来存储信息，且该信息可以被整个项目所使用。这时，<meta-data>定义在<activity>外面，且在<application>内，例如：
<manifest>
  <application
      android:icon="@drawable/icon"
      android:label="@string/app_name">

      <meta-data android:name="my_test_metagadata" android:value="testValue" />

      <activity
          android:name=".MainActivity"
          android:label="@string/app_name">

          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
      </activity>
  </application>
<manifest>

读取时如下：

    ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
    Bundle bundle = ai.metaData;
    String myApiKey = bundle.getString("my_test_metagadata");

其中，可以存储的类型还包括boolean, int, string, float


#### Activity用法
<meta-data>的另一个应用是配置Activity，通过这种方式，可以为当前Acitivity配置数据，项目能够更好的处理Activity。这时，<meta-data>标签在<activity>标签内。例如：
<manifest>
  <application
      android:icon="@drawable/icon"
      android:label="@string/app_name">
      <activity
          android:name=".MainActivity"
          android:label="@string/app_name">

          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>

      </activity>

      <activity android:name=".SearchableActivity" >
          <intent-filter>
              <action android:name="android.intent.action.SEARCH" />
          </intent-filter>
          <meta-data android:name="android.app.searchable"
                     android:resource="@xml/searchable"/>
      </activity>
  </application>
<manifest>

从activity标签内读取数据可以通过如下方式：

      try {
            ActivityInfo ai = getPackageManager().getActivityInfo(this.getComponentName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            if (bundle != null) {
                String apiKey = bundle.getString("apikey");
                Log.d(this.getClass().getSimpleName(), "apiKey = " + apiKey);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Utilities.log(this.getClass().getSimpleName(), "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(this.getClass().getSimpleName(), "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
