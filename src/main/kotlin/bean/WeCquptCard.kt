package bean

/**
 * @Date : 2022/1/18   19:25
 * @By ysh
 * @Usage :
 * @Request : God bless my code
 **/
data class WeCquptCard(
    var `data`: List<Data>? = null,
    var message: String? = null,
    var status: Int
)

data class Data(
    var created_at: String? = null,
    var name: String? = null,
    var stsfjk: String? = null,
    var szdq: String? = null,
    var xxdz: String? = null
)