# unittesting
unit test and local unit test
# Unit Test - 基本的單元測試寫法

---


## 從零開始(會帶入TDD基本開發流程)

### create empty project
![](https://i.imgur.com/dEAe8iS.png)

### 查看build.gradle(MOdule:..)
1. Android Studio預設已經幫我們Implementation三個跟test相關的dependencies
2. 我們再加入truth的依賴，我們要使用它的assert，易讀性較高
3. testImplementation跟androidTestImplementation差別，是用在androidTest跟Test差別.一個是單元測試(不會依賴Android的其他組件或是context),一個是整合測試(需要依賴模擬器，與使用相關元件的context)
4. 下面我們就是把truth加入在兩種測試中
```kotlin=
dependencies {

    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.5.0'
    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'

    //for test
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0' //UI testing
    testImplementation 'com.google.truth:truth:1.0.1' //對應到Project resource set的com.androiddevs.unittesting(test)
    androidTestImplementation 'com.google.truth:truth:1.0.1' //對應到Project resource set的com.androiddevs.unittesting(androidTest)
}
```

## 情境

### 談一下TDD開發五步驟
[TDD開發步驟](https://tw.alphacamp.co/blog/tdd-test-driven-development-example)
1. 選定一個功能，新增測試案例
    * 重點在於思考希望怎麼去使用目標程式，定義出更容易呼叫的 API 介面。
    * 這個步驟會寫好測試案例的程式，同時決定程式的 API 介面。
    * 但尚未實作 API 實際內容。
2. 執行測試，得到 Failed（紅燈）
    * 由於還沒撰寫 API 實際內容，執行測試的結果自然是 failed。
    * 確保測試程式可執行，沒有語法錯誤等等。
3. 實作「夠用」的產品程式
    * 這個階段力求快速實作出功能邏輯，用「最低限度」通過測試案例即可。
    * 不求將程式碼優化一步到位。
4. 再次執行測試，得到 Passed（綠燈）
    * 確保產品程式的功能邏輯已經正確地得到實作。
    * 到此步驟，將完成一個可運作且正確的程式版本，包含產品程式和測試程式。
5. 重構程式
    * 優化程式碼，包含產品程式和測試程式（測試程式也是專案需維護的一部份）。
    * 提升程式的可讀性、可維護性、擴充性。
    * 同時確保每次修改後，執行測試皆能通過。
    
**每個功能重複上述步驟，就是 TDD 的開發流程。**

### 會員註冊表單(有三個欄位:使用者名稱、密碼、密碼確認)
==這邊就像是TDD開發的步驟一:選定功能==
* 在專案路徑下，建立一個物件RegistrationUtil
* 定義這個函數三個參數
* 這邊使用TDD的概念(先寫測試再寫函數的功能邏輯)
```kotlin=
object RegistrationUtil {

    //根據條件，定義出已經存在的使用者名稱
    private val existingUsers = listOf("Peter", "Carl")

    /** 條件:
     * 當輸入條件是以下狀況，回傳false..
     * .. 使用者名稱或者密碼是空的時候
     * .. 當輸入的使用者名稱已經存在時
     * .. 當輸入的密碼與密碼確認不同時
     * .. 當密碼數字組成少於2個的時候(至少包含兩個以上的數字)
     */

    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return true
    }
}
```
## 開始第一個測試程式

### 在程式碼 object的名稱上點右鍵->Generate-> Test
![](https://i.imgur.com/BE29z2c.png)
### 會跳出下面的對話框
* Testing libary:選取JUnit4
* Class name:是自動產生的，不必修改
* 點選ok
![](https://i.imgur.com/CakFe8d.png)
### 會再跳出下面的對話框
* 這邊選取\app\src\test這個路徑
* 如果你要寫的測試是屬於整合測試，就要選第一個
![](https://i.imgur.com/wugaOPF.png)

### 它就會自動幫你產生類別了
==這邊就像是TDD開發的步驟二:寫一個測試失敗的程式==

* 根據情境所設想的條件，開始寫測試程式
    1. 把import的assert換掉
    ```kotlin=
    //import org.junit.Assert.* //要使用google的truth lib 要把這個拿掉
    import com.google.common.truth.Truth.assertThat //替換Junit的Assert .這個比較易讀
    ```
    2. 這邊函數的命名建議使用兩個反引號，可以使用淺顯易懂的描述。
```kotlin=
class RegistrationUtilTest {
/** 條件:
     * 當輸入條件是以下狀況，回傳false..
     * .. 使用者名稱或者密碼是空的時候
     * .. 當輸入的使用者名稱已經存在時
     * .. 當輸入的密碼與密碼確認不同時
     * .. 當密碼數字組成少於2個的時候(至少包含兩個以上的數字)
     */
     
    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
}
```
* 執行測試->執行失敗

![](https://i.imgur.com/NrjWn0C.png)

* 實作函數的功能邏輯
==這邊就像是TDD開發的步驟三:實作夠用的程式==
* 針對我們測試程式的內容，寫對應的函數功能
    1. 使用者名稱為空
    ```kotlin=
    fun validateRegistrationInput(
            username: String,
            password: String,
            confirmPassword: String
        ): Boolean {
            if (username.isEmpty(){
                return false
            }

            return true
        }
    ```
* 再次執行測試
==這邊就像是TDD開發的步驟四:執行測試，應該要成功(綠燈)==
![](https://i.imgur.com/PiJOxt9.png)

* 再來是重構，把其他條件邏輯完成
==這邊就像是TDD開發的步驟五:重構或者新增功能==

```kotlin=
//測試的程式碼
class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly password  returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Rex",
            "123",
            "123"
        )
        assertThat(result).isTrue()

    }

    @Test
    fun ` username already exist returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password is empty returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password confirmed not match password  returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "123",
            "321"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun ` password contain less than 2 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "abcde1",
            "abcde1"
        )
        assertThat(result).isFalse()
    }
}

//主程式區塊
object RegistrationUtil {

    private val existingUsers = listOf("Peter", "Carl")

    /** 條件:
     * 當輸入條件是以下狀況，回傳false..
     * .. 使用者名稱或者密碼是空的時候
     * .. 當輸入的使用者名稱已經存在時
     * .. 當輸入的密碼與密碼確認不同時
     * .. 當密碼數字組成少於2個的時候(至少包含兩個以上的數字)
     */

    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty()){
            return false
        }
        if (username in existingUsers){
            return false
        }
        if (password != confirmPassword){
            return false
        }
        if (password.count { it.isDigit() } <2 ){
            return false
        }
        return true
    }
}
```
* 最終我們就完成了這個函數功能的測試程式

![](https://i.imgur.com/y6ldJrN.png)



學習資源
[Philipp Lackner's channel](https://www.youtube.com/watch?v=W0ag98EDhGc)

###### tags: `test` `Unit Test` `TDD` `kotlin` `Android`

# Unit Test - 測試Android Components


---

* 延續上一篇的筆記專案架構 [Unit Test - 基本的單元測試寫法](https://hackmd.io/awJiLPm1Q-KO7D63AC1VAg)

## 這篇會用到Android test，也就是整合測試
* 整合測試會使用到Android Studio的模擬器
* 會使用到@Bdfore 、 @After ..等聲明

## 開始吧

### 情境
* 輸入一個字串，比對context的resource ID是否相同
* 接續上一篇的架構，新增一個類別ResourceComparer
![](https://i.imgur.com/Do5CT4p.png)

### 試想TDD步驟一: *選定一個功能，新增測試案例*
* 定義函數參數，它需要context, resource ID, 比對的字串
```kotlin=
class ResourceComparer {

    //判斷參數的recource ID跟輸入的字串是否相同
    fun isEqual(context: Context, resId: Int, string:String): Boolean{
        return true
    }
}
```
* 新增測試案例
* 這次因為我們要測試的內容是包含Android Compenents，這邊例子是context，所以要把測試程式寫在\app\src\androidTest這個路徑
![](https://i.imgur.com/fWGu6Hv.png)

### 試想TDD步驟二: *執行測試，得到 Failed（紅燈）*
* 這裡我們使用app name來當我們的recource id
```kotlin=
class ResourceComparerTest{

    private val resourceComparer = ResourceComparer()
  
    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name,"hello" )
        assertThat(result).isFalse()
    }
}
```
![](https://i.imgur.com/3dN8x2C.png)

### 試想TDD步驟三: *實作「夠用」的產品程式*
* 這邊把情境中的條件寫出來
```kotlin=
class ResourceComparer {

    fun isEqual(context: Context, resId: Int, string:String): Boolean{
        return context.getString(resId) == string
    }
}
```
### 試想TDD步驟四: *再次執行測試，得到 Passed（綠燈）*

![](https://i.imgur.com/8cEIc9r.png)

### 試想TDD步驟五: *重構程式*
* 把測試程式的條件寫完- 當resource ID 跟輸入字串相同

```kotlin=
@Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name,"unittesting" )
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name,"hello" )
        assertThat(result).isFalse()
    }
```
![](https://i.imgur.com/K9KwcY6.png)

## 優化
* 這個測試程式需要ResourceComparer類別的實例
* 直接宣告一個全域變數來賦予實例是不好的寫法
* 較好的做法是，當個別的測試程式要執行的時候再去產生實例，避免測試程式之間產生干擾，造成測試結果可能不穩定
* 所以Junit提供了@Before 跟 @After聲明可以使用
* @Before就是在@Test的程式要執行之前都會先執行@Before定義的函數
* @After就是在@Test的函數執行完畢之後才執行@After定義的函數，在這邊的例子還用不到。
```kotlin=

    private lateinit var resourceComparer :ResourceComparer
    
    //  ↓  這樣寫不好
    // private val resourceComparer = ResourceComparer()

    @Before
    fun setUp(){
    //每次執行@Test底下的函數之前都會先產生實例
        resourceComparer = ResourceComparer()
    }
    
    @After
    fun tearDown(){

    }
    
    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "unittesting")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "hello")
        assertThat(result).isFalse()
    }
```

## 補充說明
* 在AndroidTest(整合測試)中，它沒辦法像unitTest可以使用反引號命名函數，例如: `if something returns false`，可能是系統上有什麼考量吧.也許以後可以?
* 整合測試的函數命名，盡量描述清楚

###### tags: `test` `Unit Test` `TDD` `kotlin` `Android`
