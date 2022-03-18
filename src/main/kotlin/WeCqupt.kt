import bean.CardStudentBean
import bean.WeCard
import bean.WeCquptCard
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @Date : 2022/1/19   18:51
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
val gson = Gson()
val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()
fun checkCheckInToday(student: String): Boolean {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val key = JsonObject().apply {
        addProperty("xh", student)
        addProperty("timestamp", Date().time / 1000)
    }
    val encode = Base64.getEncoder().encodeToString(key.toString().toByteArray())
    val decode = Base64.getDecoder().decode(encode).toRequestBody().toString()
    val requestBody = JsonObject().apply {
        addProperty("key", encode)
    }.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder()
        .url("https://we.cqupt.edu.cn/api/mrdk/get_mrdk_list_test.php")
        .addHeader("Host", "we.cqupt.edu.cn")
        .addHeader("charset", "utf-8")
        .addHeader(
            "User-Agent",
            "Mozilla/5.0 (Linux; Android 10; MI 6 Build/QQ3A.200805.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2581 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/8301 MicroMessenger/7.0.18.1740(0x27001235) Process/appbrand2 WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64"
        )
        .addHeader("content-type", "application/json")
        .addHeader("Referer", "https://servicewechat.com/wx8227f55dc4490f45/54/page-frame.html")
        .post(requestBody)
        .build()
    val execute = okHttpClient.newCall(request).execute()
    val message = execute.body?.string() ?: ""
    val parseString = JsonParser.parseString(message)
    if (parseString.isJsonObject) {
        val weCquptCard = gson.fromJson<WeCquptCard>(message, WeCquptCard::class.java)
        weCquptCard.data?.getOrNull(0)?.let { data ->
            if (!data.created_at.isNullOrBlank()) {
                val build = Calendar.Builder().setInstant(simpleDateFormat.parse(data.created_at)).build()
                if (build[Calendar.DAY_OF_YEAR] == Calendar.getInstance()[Calendar.DAY_OF_YEAR]) {
                    return true
                }
            }
        }
    }
    return false
}

fun checkCard(student: String) :Boolean{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val key = JsonObject().apply {
        addProperty("xh", student)
        addProperty("timestamp", Date().time / 1000)
    }
    val encode = Base64.getEncoder().encodeToString(key.toString().toByteArray())
    val decode = Base64.getDecoder().decode(encode).toRequestBody().toString()
    val requestBody = JsonObject().apply {
        addProperty("key", encode)
    }.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder()
        .url("https://we.cqupt.edu.cn/api/mrdk/get_mrdk_flag.php")
        .addHeader("Host", "we.cqupt.edu.cn")
        .addHeader("charset", "utf-8")
        .addHeader(
            "User-Agent",
            "Mozilla/5.0 (Linux; Android 10; MI 6 Build/QQ3A.200805.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2581 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/8301 MicroMessenger/7.0.18.1740(0x27001235) Process/appbrand2 WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64"
        )
        .addHeader("content-type", "application/json")
        .addHeader("Referer", "https://servicewechat.com/wx8227f55dc4490f45/54/page-frame.html")
        .post(requestBody)
        .build()
    val execute = okHttpClient.newCall(request).execute()
    val message = execute.body?.string() ?: ""
    val parseString = JsonParser.parseString(message)
    if (parseString.isJsonObject) {
        val weCquptCard = gson.fromJson<WeCard>(message, WeCard::class.java)
        return weCquptCard.data?.count.equals("1")
    }
    return false
}

fun clockIn(student: CardStudentBean) {
    val key = JsonObject().apply {
        addProperty("name",student.name)
        addProperty("xh",student.xh)
        addProperty("xb",student.xb)
        addProperty("openid",student.openId)
        when(student.xxdz) {
            "重庆" ->{
                addProperty("xxdz","重庆邮电大学")
                addProperty("szdq","重庆市,重庆市,南岸区")
                addProperty("locationBig","中国,重庆市,重庆市,南岸区")
                addProperty("locationSmall","重庆市南岸区崇文路")
                addProperty("latitude",29.534128)
                addProperty("longitude",106.606291)
            }
        }
        addProperty("ywjcqzbl","低风险")
        addProperty("ywjchblj","无")
        addProperty("xjzdywqzbl","无")
        addProperty("twsfzc","是")
        addProperty("ywytdzz","无")
        addProperty("beizhu","无")
//        addProperty("mrdkkey",)
        addProperty("timestamp",Date().time / 1000)

    }
    val encode = Base64.getEncoder().encodeToString(key.toString().toByteArray())
    val requestBody = JsonObject().apply {
        addProperty("key", encode)
    }.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder()
        .url("https://we.cqupt.edu.cn/api/mrdk/post_mrdk_info.php")
        .addHeader("Host", "we.cqupt.edu.cn")
        .addHeader("charset", "utf-8")
        .addHeader(
            "User-Agent",
            "Mozilla/5.0 (Linux; Android 10; MI 6 Build/QQ3A.200805.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2581 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/8301 MicroMessenger/7.0.18.1740(0x27001235) Process/appbrand2 WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64"
        )
        .addHeader("content-type", "application/json")
        .addHeader("Referer", "https://servicewechat.com/wx8227f55dc4490f45/54/page-frame.html")
        .post(requestBody)
        .build()
    okHttpClient.newCall(request).execute()

}
