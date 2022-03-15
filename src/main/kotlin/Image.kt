import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.File

/**
 * @Date : 2022/1/19   16:59
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
object Image {
    private val file = File("E:\\An\\mirai_plugin_example\\src\\main\\resources\\image\\yellow_chick_intro_1.jpg")
    val intro1 = file.toExternalResource()
}