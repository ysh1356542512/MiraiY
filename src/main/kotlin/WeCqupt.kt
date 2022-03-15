import bean.WeCquptCard
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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
fun checkCheckInToday(student: String): Boolean {
    val gson = Gson()
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

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
