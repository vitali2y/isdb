/**
 * dclrs.java
 * ISDBj
 */

package isdb.miscs;

/**
 * Головний файл загальних описаній програмного забеспечення проекта ISDBj.
 * <P><I><B>Загальна інформація</B></I><BR>
 * <P>Інтегрована база даних абонентів Утел (Integrated Subscriber's DataBase, ISDB),
 * призначена для підключення, обслуговування та супроводу
 * бізнес-абонентів, абонентів на правах аренди, тощо,
 * які експлуатують телефони АТ Утел.
 * <P>Система створена в дусі трьохрівневої архітектури клієнт-сервер
 * з використанням Web-технологій с застосуванням сучасних мов програмування Java
 * (звідсі робоча назва проекту - ISDBj), JavaScript, HTML та SQL.<BR>
 * <IMG SRC="doc-files/logo.gif">
 * @author Yermolenko Vitaly, Utel-Zhitomir <a href="mailto:VYermolenko@kyiv.utel.com.ua">VYermolenko@kyiv.utel.com.ua</a>
 * @version 1.0 final, 29-V-2000
 */
public final class dclrs
{

    // Загальна інформація про систему
    /** Дата версії             */
    public static final String ISDB_DATE = "29.V.2000";
    /** Повна назва системи	*/
    public static final String ISDB_NAME = "Інтегрована база даних абонентів Утел";
    /** Скорочена назва системи	*/
    public static final String ISDB_SHORTNAME = "ISDB";
    /** Поточна версія системи	*/
    public static final String ISDB_VERSION = "Версія 1.0 final";
    /** Розробник системи       */
    public static final String ISDB_AUTHOR = "Розробник:" +
            isdb.ifaces.htmli.crlf () +
            "Єрмоленко Віталій" + isdb.ifaces.htmli.crlf () +
            "Утел, Житомир" + isdb.ifaces.htmli.crlf () +
            "тел. (0412) 20-82-07" + isdb.ifaces.htmli.crlf () +
            "E-mail: " + isdb.ifaces.htmli.href (
              "mailto:VYermolenko@kyiv.utel.com.ua?subject=ISDB feedback message!",
              "VYermolenko@kyiv.utel.com.ua");

    // Існуючі режіми роботи з БД
    /** Невідомий режим роботи                  */
    public static final String REGIME_NULL       = "0";
    /** Режим вибірки значеннь з БД             */
    public static final String REGIME_QUERY         = "1";
    /** Режим редагування полів БД              */
    public static final String REGIME_UPDATE        = "2";
    /** Режим внесення нової інформації в БД    */
    public static final String REGIME_INSERT        = "3";
    /** Режим витягнення інформації з БД             */
    public static final String REGIME_RETRIEVE      = "4";
    /** Режим вибірки інформації по категоріям */
    public static final String REGIME_TYPESELECT    = "5";
    /** Режим продовження виконання роботи */
    public static final String REGIME_CONTINUE      = "6";
    /** Режим повернення після виконання деяких функцій */
    public static final String REGIME_RETURN        = "7";
    /** Режим реєстрації */
    public static final String REGIME_LOGIN         = "8";
    /** Режим друку інформації */
    public static final String REGIME_PRINT     = "9";
    /** Режим отримання допомоги */
    public static final String REGIME_HELP     = "10";
    /** Режим обслуговування */
    public static final String REGIME_MAINT     = "11";
    /** Режим: попередня форма */
    public static final String REGIME_PREVFORM     = "12";
    /** Режим вилучення інформації з БД    */
    public static final String REGIME_DELETE        = "13";
    /** Режим upload */
    public static final String REGIME_UPLOAD = "14";
    /** Режим проведення транзакцій після внесення нової інформації в БД */
    public static final String REGIME_UPDCOMMIT     = "22";
    /** Режим проведення транзакцій після оновлення інформації в БД */
    public static final String REGIME_INSCOMMIT     = "23";
    /** Режим вибірки для огляду інформації в БД */
    public static final String REGIME_SELECT4REVIEW = "31";
    /** Режим вибірки та послідуючого оновлення інформації в БД */
    public static final String REGIME_SELECT4UPDATE = "32";
    /** Режим вибірки та послідуючого введення інформації в БД */
    public static final String REGIME_SELECT4INSERT = "33";
    // Існуючі меню
    /** Головна сторінка */
    public static final String MENU_MAIN = "main";

    // Використовуемі сервлети
    /** Реєстрація */
    public static final String APPL_LOGIN = "isdblogin";
    /** Меню */
    public static final String APPL_MENU = "isdbmenu";
    /** Форми */
    public static final String APPL_FORM = "isdbform";
    /** Властивости */
    public static final String APPL_PROPERTY = "isdbproperty";
    /** Звіти */
    public static final String APPL_REPORT = "isdbreport";
    /** Графіки */
    public static final String APPL_GRAPH = "isdbgraph";
    /** Опції */
    public static final String APPL_OPTION = "isdboption";
    /** Інформація */
    public static final String APPL_INFO = "isdbinfo";
    /** Транзакції */
    public static final String APPL_COMMIT = "isdbcommit";

    // Лейби на кнопках
    /** Лейба: Друкувати */
    public static final String TITLE_REG_PRINT = "Друкувати";
    /** Лейба: Новий запис */
    public static final String TITLE_REG_NEW_RECORD = "Новий запис";
    /** Лейба: Допомога */
    public static final String TITLE_REG_HELP = "Допомога";
    /** Лейба: Виправити */
    public static final String TITLE_REG_PREVFORM = "Виправити"; // "<<<";
    /** Лейба: Оновити */
    public static final String TITLE_REG_UPDATE = "Оновити";
    /** Лейба: Вийти */
    public static final String TITLE_REG_RETURN = "Вийти";
    /** Лейба: Реєстрація */
    public static final String TITLE_REG_LOGIN = "Реєстрація";
    /** Лейба: Зберегти */
    public static final String TITLE_REG_SAVE = "Зберегти";
    /** Лейба: Вибрати */
    public static final String TITLE_REG_SELECT = "Вибрати";
    /** Лейба: Продовжити */
    public static final String TITLE_REG_CONTINUE = "Продовжити";
    /** Лейба: Первиний стан */
    public static final String TITLE_REG_ORIGIN_STATE = "Первиний стан";
    /** Лейба: Вибрати для огляду */
    public static final String TITLE_REG_SELECT4REVIEW = "Вибрати для огляду";
    /** Лейба: Вибрати для оновлення */
    public static final String TITLE_REG_SELECT4UPDATE = "Вибрати для оновлення";
    /** Лейба: Вибрати для введення */
    public static final String TITLE_REG_SELECT4INSERT = "Вибрати для введення";
    /** Лейба: Вилучити */
    public static final String TITLE_REG_DELETE = "Вилучити";
    /** Лейба: Завантажити */
    public static final String TITLE_REG_UPLOAD = "Завантажити";

    // Конфігураційни параметри файла isdb.ini
    /** Конфігураційний параметр site	*/
    public static final String OPT_SITE		=	"site";
    /** Конфігураційний параметр	*/
    public static final String OPT_DEBUG	=	"debug";
    /** Конфігураційний параметр switch	*/
    public static final String OPT_SWITCH	=	"switch";
    /** Конфігураційний параметр servlet_home	*/
    public static final String OPT_SERVLET_HOME = "servlet_home";
    /** Конфігураційний параметр doc_home	*/
    public static final String OPT_DOC_HOME = "doc_home";
    /** Конфігураційний параметр	*/
    public static final String OPT_GUEST = "guest";
    /** Конфігураційний параметр emailadm	*/
    public static final String OPT_EMAILADM = "emailadm";
    /** Конфігураційний параметр	*/
    public static final String OPT_PERSON_DIRECTOR_UTEL = "person_director_utel";
    /** Конфігураційний параметр	*/
    public static final String OPT_PERSON_DIRECTOR_TELECOM = "person_director_telecom";
    /** Конфігураційний параметр	*/
    public static final String OPT_PERSON_CHIEF_ENGINEER = "person_chief_engineer";
    /** Конфігураційний параметр	*/
    public static final String OPT_NAME_SALES = "name_sales";
    /** Конфігураційний параметр	*/
    public static final String OPT_PERSON_SALES = "person_sales";
    /** Конфігураційний параметр	*/
    public static final String OPT_NAME_UTEL = "name_utel";
    /** Конфігураційний параметр	*/
    public static final String OPT_NAME_TELECOM = "name_telecom";
    /** Конфігураційний параметр	*/
    public static final String OPT_SALES_OUT_NUMBER = "sales_out_number";
    /** Конфігураційний параметр font: фонт відображення */
    public static final String OPT_FONT = "font";
    /** Конфігураційний параметр color_bg: кольор фону   */
    public static final String OPT_COLOR_BG = "color_bg";
    /** Конфігураційний параметр color_title: кольор назви форми */
    public static final String OPT_COLOR_TITLE = "color_title";
    /** Конфігураційний параметр: кольор фону назви форми */
    public static final String OPT_COLOR_BGTITLE = "color_bgtitle";
    /** Конфігураційний параметр picture_bg: фонові шпалери */
    public static final String OPT_PICTURE_BG = "picture_bg";
    /** Конфігураційний параметр color_form: кольор форм   */
    public static final String OPT_COLOR_FORM = "color_form";
    /** Конфігураційний параметр color_text: кольор інформації користувача  */
    public static final String OPT_COLOR_TEXT = "color_text";
    /** Конфігураційний параметр color_head: кольор заголовків форм  */
    public static final String OPT_COLOR_HEAD = "color_head";
    /** Конфігураційний параметр color_link: кольор гіпер-переходів  */
    public static final String OPT_COLOR_LINK = "color_link";
    /** Конфігураційний параметр help_enabled: контролювання режиму підказування */
    public static final String OPT_HELP_ENABLED = "help_enabled";
    /** Конфігураційний параметр news: встановлення режиму новин */
    public static final String OPT_NEWS = "news";
    /** Конфігураційний параметр javacharset: Java кодовий набор */
    public static final String OPT_JAVACHARSET = "javacharset";
    /** Конфігураційний параметр htmlcharset: HTML кодовий набор */
    public static final String OPT_HTMLCHARSET = "htmlcharset";
    /** Конфігураційний параметр language: мова відображення */
    public static final String OPT_LANGUAGE = "language";
    /** Конфігураційний параметр: тип станції 5ESS
     * @see #switch
     */
    public static final String OPTPAR_5ESS	=	"5ESS";
    /** Конфігураційний параметр: тип станції EWSD
     * @see #switch
     */
    public static final String OPTPAR_EWSD	=	"EWSD";
    /** Конфігураційний параметр: мова уркаїнська
     * @see #OPT_LANGUAGE
     */
    public static final String OPTPAR_LANG_UA = "ua";
    /** Конфігураційний параметр: мова россійска
     * @see #OPT_LANGUAGE
     */
    public static final String OPTPAR_LANG_RU = "ru";
    /** Конфігураційний параметр: мова англійска
     * @see #OPT_LANGUAGE
     */
    public static final String OPTPAR_LANG_EN = "en";

    /** Загальнодіючі параметри застосування загальних можливостей */
    public static final String PRAGMA_CROSSMAP_DISTRIB = "objcrossmap_distrib";
    public static final String PRAGMA_CROSSMAP_ATETRUNKLINE = "objcrossmap_atetrunkline";
    public static final String PRAGMA_CROSSMAP_SHELFTRUNKLINE= "objcrossmap_shelftrunkline";
    public static final String PRAGMA_CROSSMAP_ATECROSSLINE = "objcrossmap_atecrossline";

    // Загально використовуемі значення HTML параметрів
    /** Признак службового параметра: префікс додаткового параметру */
    public static final String SPECIAL_PARAM_PREFIX = "_";
    /** Признак службового параметра: префікс для внутрішнього використання параметру */
    public static final String SPECIAL_SERVICE_PREFIX = "#";
    /** Значення HTML параметра: номер користувача БД ISDB	*/
    public static final String PAR_PERSON_ID	=	"PERSON_ID";
    /** Значення HTML параметра: поточна дата транзакції	*/
    public static final String PAR_STATEDATE	=	"STATEDATE";
    /** Значення HTML параметра	*/
    public static final String PAR_LOGOFF	=	SPECIAL_PARAM_PREFIX + "LOGOFF";
    /** Значення HTML параметра	*/
    public static final String PAR_WNDEXIT = SPECIAL_PARAM_PREFIX + "WNDEXIT";
    /** Значення HTML параметра	*/
    public static final String PAR_IMMEDUPDATE	=	SPECIAL_PARAM_PREFIX + "IMMEDUPDATE";
    /** Значення HTML параметра	*/
    public static final String PAR_DEPT	=	SPECIAL_PARAM_PREFIX + "DEPT";
    /** Значення HTML параметра	*/
    public static final String PAR_MENU	=	SPECIAL_PARAM_PREFIX + "MENU";
    /** Значення HTML параметра	*/
    public static final String PAR_ID	=	SPECIAL_PARAM_PREFIX + "ID";
    /** Значення HTML параметра	*/
    public static final String PAR_POOL_ID	=	SPECIAL_PARAM_PREFIX + "POOL_ID";
    /** Значення HTML параметра	*/
    public static final String PAR_RPT_ID	=	SPECIAL_PARAM_PREFIX + "RPT_ID";
    /** Значення HTML параметра	*/
    public static final String PAR_REPORT	=	SPECIAL_PARAM_PREFIX + "REPORT";
    /** Значення HTML параметра	*/
    public static final String PAR_RPT_TITLE	=	SPECIAL_PARAM_PREFIX + "RPT_TITLE";
    /** Значення HTML параметра	*/
    public static final String PAR_RPT_NUMB = SPECIAL_PARAM_PREFIX + "PAR_RPT_NUMB";
    /** Значення HTML параметра	*/
    public static final String PAR_DETAILMSG	=	SPECIAL_PARAM_PREFIX + "DTL";
    /** Значення HTML параметра	*/
    public static final String PAR_SQLCMD = SPECIAL_PARAM_PREFIX + "SQLCMD";
    /** Значення HTML параметра	*/
    public static final String PAR_APPL = SPECIAL_PARAM_PREFIX + "APPL";
    /** Значення HTML параметра	*/
    public static final String PAR_NEXTAPPL = SPECIAL_PARAM_PREFIX + "NEXTAPPL";
    /** Значення HTML параметра	*/
    public static final String PAR_REGIME = SPECIAL_PARAM_PREFIX + "REGIME";
    /** Значення HTML параметра	*/
    public static final String PAR_NEXTREGIME = SPECIAL_PARAM_PREFIX + "NEXTREGIME";
    /** Значення HTML параметра	*/
    public static final String PAR_OBJECT = SPECIAL_PARAM_PREFIX + "OBJECT";
    /** Значення HTML параметра	*/
    public static final String PAR_NEXTOBJECT = SPECIAL_PARAM_PREFIX + "NEXTOBJECT";
    /** Значення HTML параметра	*/
    public static final String PAR_TYPESELECT = SPECIAL_PARAM_PREFIX + "TYPESELECT";
    /** Значення HTML параметра	*/
    public static final String PAR_FAILAPPL = SPECIAL_PARAM_PREFIX + "FAILAPPL";
    /** Значення HTML параметра	*/
    public static final String PAR_CRITERIA = SPECIAL_PARAM_PREFIX + "CRITERIA";
    /** Значення HTML параметра	*/
    public static final String PAR_ERROR = dclrs.SPECIAL_PARAM_PREFIX + "ERROR";
    /** Значення HTML параметра	*/
    public static final String PAR_ABSENT_IN_LIST = SPECIAL_PARAM_PREFIX + "NEW_";
    /** Значення HTML параметра	*/
    public static final String PAR_TRANSFER_PARAM = SPECIAL_PARAM_PREFIX + "TRANSFER";
    /** Значення HTML параметра	*/
    public static final String PAR_UPDATE_RECORD = SPECIAL_PARAM_PREFIX + "OLD_";
    /** Значення HTML параметра	*/
    public static final String PAR_PRINT_REPORT = SPECIAL_PARAM_PREFIX + "PRINT_";
    /** Значення HTML параметра	*/
    public static final String PAR_ADDED_IN_LIST  = SPECIAL_PARAM_PREFIX + "ADDED";
    /** Значення HTML параметра	*/
    public static final String PAR_BUTTONS = SPECIAL_PARAM_PREFIX + "BUTTONS";
    /** Значення HTML параметра	*/
    public static final String PAR_TYPE_OUT = SPECIAL_PARAM_PREFIX + "TYPE_OUT";
    /** Значення HTML параметра	*/
    public static final String PAR_ROLLBACK = SPECIAL_PARAM_PREFIX + "ROLLBACK";
    /** Значення HTML параметра	*/
    public static final String PAR_TMP1 = SPECIAL_PARAM_PREFIX + "TMP1";
    /** Значення HTML параметра	*/
    public static final String PAR_TMP2 = SPECIAL_PARAM_PREFIX + "TMP2";
    /** Значення HTML параметра	*/
    public static final String PAR_CONTINUE_NEXT_TIME = dclrs.SPECIAL_PARAM_PREFIX + "CONTINUE_NEXT_TIME";
    /** Значення HTML параметра	*/
    public static final String PAR_POSSIBLECREATENEW = SPECIAL_PARAM_PREFIX + "POSCREATENEW_";

    // Типи відображення інформації
    /** Тип відображення інформації */
    public static final String TYPE_OUT_REPORT = "1";
    /** Тип відображення інформації */
    public static final String TYPE_OUT_PIECHART = "2";
    /** Тип відображення інформації */
    public static final String TYPE_OUT_GRAPHCHART = "3";
    /** Тип відображення інформації */
    public static final String TYPE_OUT_HELP = "4";
    /**
     * Тип відображення інформації:
     * вибірка інформації у вигляді файла
     */
    public static final String TYPE_OUT_FILE = "5";

    /** Використовуемі форми, звіти, графіки */
    /** Номер форми, звіта або графіка	*/
    public static final String FORM_2_4_F2 = "FRM1";
    /** Номер форми, звіта або графіка	*/
    public static final String FORM_FAIL_RPT_MONTH = "FRM2";
    /** Номер форми, звіта або графіка	*/
    public static final String FORM_FAIL_RPT_MONTH_DTL = "FRM3";
    /** Номер форми, звіта або графіка	*/
    public static final String FORM_FAIL_RPT_HISTORY = "FRM4";
    /** Номер форми, звіта або графіка	*/
    public static final String GRAPH_FAIL_RPT_YEAR = "GRPH1";
    /** Номер форми, звіта або графіка	*/
    public static final String GRAPH_CONN_DIRNR_YEAR = "GRPH2";
    /** Номер форми, звіта або графіка	*/
    public static final String GRAPH_CONN_WWWSERVICE_YEAR = "GRPH3";
    /** Номер форми, звіта або графіка:
     * "Статистика укладених угод за рік" */
    public static final String GRAPH_SIGN_AGREEMENT_YEAR = "GRPH4";
    /** Номер форми, звіта або графіка:
     * "Статистика по незадіянної емкості" */
    public static final String GRAPH_DIRNR_UNASSIGN_CAPACITY = "GRPH5";
    /** Номер форми, звіта або графіка:
     * "АВН" */
    public static final String GRAPH_DIRNR_ANI = "GRPH6";
    /** Номер форми, звіта або графіка:
     * "Тарификаційни пакети" */
    public static final String GRAPH_AGREEMENT_TARIFF_PACKET = "GRPH7";
    /** Номер форми, звіта або графіка	*/
    public static final String REPORT_FAIL_RPT_CURRENT = "RPT4";
    /** Номер форми, звіта або графіка	*/
    public static final String REPORT_BLACK_LIST_CURRENT = "RPT5";
    /** Номер форми, звіта або графіка	*/
    public static final String REPORT_SUBSSERVICE_CURRENT = "RPT6";
    /** Номер форми, звіта або графіка	*/
    public static final String REPORT_WWWSERVICE_CURRENT = "RPT7";
    /** Номер форми, звіта або графіка:
     * "Звіт по абонентам, угоди з якими укладени на цей час" */
    public static final String REPORT_FIRM_RPT_CURRENT = "RPT8";
    /** Номер форми, звіта або графіка:
     * "Звіт по укладеним угодам на цей час" */
    public static final String REPORT_AGREEMENT_RPT_CURRENT = "RPT9";
    /** Номер форми, звіта або графіка:
     * "Звіт по телефонам, угоди з якими укладени на цей час" */
    public static final String REPORT_DIRNR_RPT_CURRENT = "RPT10";
    /** Номер форми, звіта або графіка:
     * "Розподілення абонентів по тарифікаційним пакетам" */
    public static final String REPORT_AGREEMENT_TARIFF_PACKET = "RPT11";
    /** Номер форми, звіта або графіка:
     * "Угоди по типам обраних пакетів"	*/
    public static final String REPORT_AGREEMENT_BY_TYPE = "RPT12";
    /** Номер форми, звіта або графіка:
     * "Робочий звіт по плануванню діяльності"	*/
    public static final String REPORT_PLAN_PROJECT = "RPT13";
    /** Номер форми, звіта або графіка:
     * "Звіт по прибуткам"	*/
    public static final String REPORT_FIRMINCOME_CURRENT = "RPT14";
    /** Номер форми, звіта, графіка або файла:
     * "Вибірка даних сесій Internet для тарифікації"	*/
    public static final String FILE_INTERNET_SESSION = "FILE1";
    /** Номер форми, звіта або графіка	*/
    public static final String PAPER_REQUEST_LINES_PTT = "PAP1";
    /** Номер форми, звіта або графіка	*/
    public static final String PAPER_ORDER_LINE = "PAP2";
    /** Номер форми, звіта або графіка	*/
    public static final String PAPER_INFO_CONN_LINE = "PAP3";
    /** Номер форми, звіта або графіка	*/
    public static final String PAPER_ORDER_WWWORDER = "PAP4";

    /** Поточні стани телефонів */
    /** Поточний стан: реєстрація */
    public static final String STAGE_CURR_REGISTER = "PHONESTATE_ID='@UA' or PHONESTATE_ID='@RJ'"; // "PHONESTATE_ID='@UA'"
    public static final String STAGE_CURR_ANSWER = "PHONESTATE_ID='@TR'";
    public static final String STAGE_CURR_ORDER = "PHONESTATE_ID='@LT'";
    public static final String STAGE_CURR_AGREEMENT = "PHONESTATE_ID='@OR'";
    public static final String STAGE_CURR_CONNINPROGRESS = "PHONESTATE_ID='@AG'";
    public static final String STAGE_CURR_CONNALLOWED = "PHONESTATE_ID='@IN'";
    public static final String STAGE_CURR_CONNECTED = "PHONESTATE_ID='@PD'";
    public static final String STAGE_CURR_OK = "PHONESTATE_ID='@CN'";
    public static final String STAGE_CURR_CONNNOTAVAILABLE = "PHONESTATE_ID%20like%20'%23%25'";
    // public static final String STAGE_CURR_CONNNOTAVAILABLE = "PHONESTATE_ID like '#%'";

    /** Наступні стани телефонів */
    /** Наступний стан телефонів: реєстрація */
    public static final String STAGE_NEXT_REGISTER = "@TR";
    public static final String STAGE_NEXT_ANSWER = "@LT";
    public static final String STAGE_NEXT_ORDER = "@OR";
    public static final String STAGE_NEXT_AGREEMENT = "@AG";
    public static final String STAGE_NEXT_CONNINPROGRESS = "@IN";
    public static final String STAGE_NEXT_CONNALLOWED = "@PD";
    public static final String STAGE_NEXT_CONNECTED = "@CN";
    public static final String STAGE_NEXT_REJECT = "@RJ";

    /** Поточни ствтуси телефонів */
    /** Поточний ствтус телефона: реєстрація */
    public static final String STAGE_STATUS_REGISTER = "@UA";
    public static final String STAGE_STATUS_ANSWER = "@TR";
    public static final String STAGE_STATUS_ORDER = "@OR";
    public static final String STAGE_STATUS_AGREEMENT = "@AG";
    public static final String STAGE_STATUS_CONNINPROGRESS = "@IN";
    public static final String STAGE_STATUS_CONNALLOWED = "@PD";
    public static final String STAGE_STATUS_CONNECTED = "@CN";
    public static final String STAGE_STATUS_REJECT = "@RJ";

    public static final String MODE_INSERT = "Режим занесення нової інформації";
    public static final String MODE_UPDATE = "Режим оновлення інформації";
    public static final String MODE_REVIEW = "Режим огляду інформації";


    /** Департаменти */
    /** Відділ продажу                      */
    public static final String DEPT_SALES   = "sales";
    /** Відділ техничного обслуговування    */
    public static final String DEPT_MAINT   = "maint";
    /** Адміністрація                        */
    public static final String DEPT_MNGR   = "mngr";
    /** Відділ Інтернет забеспечення         */
    public static final String DEPT_CS = "cs";

    // Значення
    public static final String NOTSELECTED  = "Не вибрано";
    public static final String ISABSENT     = "Немає даних";
    public static final String NOTINSTALL   = "Не встановлено";
    // Можливі значення
    public static final String YES      = "yes";
    public static final String NO      = "no";
    /** Не вибрано потрібне значення! */
    public static final int MSG_NOTSEARCHVAL = 1;

    /** Типи ISDB обьектів */
    /** Пустий об'ект            */
    public static final String OBJ_NULL             = "-";
    /** Об'ект таблиці ATES       */
    public static final String OBJ_ATE     = "ate";
    /** Об'ект: використуеться меню                 */
    public static final String OBJ_MENU            = "menu";
    /** Об'ект: інформація користувачу             */
    public static final String OBJ_INFO            = "info";
    /** Об'ект таблиці DIRNRS       */
    public static final String OBJ_DIRNR            = "dirnr";
    public static final String OBJ_EXCHCROSS        = "exchcross"; //
    public static final String OBJ_TYPEEXCHCROSS    = "typeexchcross"; //
    /** Об'ект таблиці LENS         */
    public static final String OBJ_LEN              = "len";
    public static final String OBJ_EXCHMDF          = "exchmdfpair";  // EXCHMDF
    public static final String OBJ_LINEMDF          = "linemdfpair";  // LINEMDF
    public static final String OBJ_DISTRIB          = "distrib";
    public static final String OBJ_ATETRUNKLINE     = "atetrunkline"; // ATETRUNKLINES
    public static final String OBJ_ATECROSSPAIR     = "atecrosspair"; //
    public static final String OBJ_SHELFTRUNKLINE   = "shelftrunkline"; //
    /** Об'ект таблиці FIRMS         */
    public static final String OBJ_FIRM             = "firm"; //
    public static final String OBJ_PROPERTY         = "propertie";
    /** Об'ект таблиці FAILRPTS         */
    public static final String OBJ_FAILRPT          = "failrpt";
    /** Об'ект таблиці PHONESTATES         */
    public static final String OBJ_PHONESTATE          = "phonestate";
    /** Об'ект таблиці TYPEPHONES         */
    public static final String OBJ_TYPEPHONE          = "typephone";
    /** Об'ект таблиці ZIPS         */
    public static final String OBJ_ZIP          = "zip";
    /** Об'ект таблиці BANKS       */
    public static final String OBJ_BANK            = "bank";
    /** Об'ект таблиці STREETS       */
    public static final String OBJ_STREET            = "street";
    /** Об'ект таблиці TARIFFLEVELS       */
    public static final String OBJ_TARIFFLEVEL            = "tarifflevel";
    /** Об'ект: телефони по категоріям */
    public static final String OBJ_PHONEBYCAT       = "phonebycat";
    /** Об'ект: додаткове обладнання */
    public static final String OBJ_EQUIPDIRNR = "equipdirnr";
    /** Об'ект: технични довідки               */
    public static final String OBJ_TECHREF = "techref";
    /** Об'ект: угоди               */
    public static final String OBJ_AGREEMENT = "agreement";
    /** Об'ект: кросіровки               */
    public static final String OBJ_CROSSMAP = "crossmap";
    public static final String OBJ_DISTRIBSHELF = "distribshelve";
    public static final String OBJ_TSTRPT = "tstrpt";
    public static final String OBJ_SHELFTRUNK = "shelftrunk";
    public static final String OBJ_TYPEEQUIP = "typeequip";
    public static final String OBJ_PHCROSSES = "phcrosse";
    public static final String OBJ_BLRESTRICTION = "blrestriction";
    public static final String OBJ_BLDIRNR = "bldirnr";
    /** Об'ект таблиці AGREEMENTSTATES        */
    public static final String OBJ_AGREEMENTSTATE = "agreementstate";
    /** Об'ект таблиці FIRMINCOMES        */
    public static final String OBJ_FIRMINCOME = "firmincome";
    /** Об'ект таблиці CONSTRUCTORS        */
    public static final String OBJ_CONSTRUCTOR = "constructor";
    /** Об'ект таблиці PLANS        */
    public static final String OBJ_PLAN = "plan";
    /** Об'ект таблиці PLANS        */
    public static final String OBJ_PLANSERVICE = "planservice";
    /** Об'ект таблиці PLANS        */
    public static final String OBJ_PLANEQUIP = "planequip";
    /** Об'ект таблиці ACTIVITIES */
    public static final String OBJ_ACTIVITY = "activitie";
    /** Об'ект таблиці FIRMSTATES */
    public static final String OBJ_FIRMSTATE = "firmstate";
    /** Об'ект таблиці TYPEFIRMS */
    public static final String OBJ_TYPEFIRM = "typefirm";
    /** Об'ект таблиці TYPEREJECTAGRS */
    public static final String OBJ_TYPEREJECTAGR = "typerejectagr";
    /** Об'ект таблиці REGIONS */
    public static final String OBJ_REGIONS = "region";
    /** Об'ект таблиці SESSIONS */
    public static final String OBJ_SESSION = "session";

    // Об'екти: представлення
    public static final String OBJ_DSTRCROSSTRACE = "dstrcrosstrace";
    public static final String OBJ_LOCCROSSTRACE = "loccrosstrace";
    public static final String OBJ_SHLFCROSSTRACE = "shlfcrosstrace";
    public static final String OBJ_EQUIPCROSSTRACE = "equipcrosstrace";
    public static final String OBJ_ATECROSSTRACE = "atecrosstrace";
    public static final String OBJ_TRUNKCROSSTRACE = "trunkcrosstrace";
    public static final String OBJ_TYPELOCATION = "typelocation";
    public static final String OBJ_DEPT = "dept";
    public static final String OBJ_PERSON = "person";
    public static final String OBJ_TYPEWWWSERVICE = "typewwwservice";
    public static final String OBJ_WWWORDER = "wwworder";
    public static final String OBJ_WWWSERVICE = "wwwservice";
    public static final String OBJ_WWWSRVWWW = "wwwsrvwww";
    public static final String OBJ_WWWSRVDNS = "wwwsrvdns";
    public static final String OBJ_WWWSRVCONSTIP = "wwwsrvconstip";
    public static final String OBJ_WWWSRVEMAIL = "wwwsrvemail";
    public static final String OBJ_WWWSRVSMTP = "wwwsrvsmtp";
    public static final String OBJ_WWWSRVWEBHOST = "wwwsrvwebhost";
    public static final String OBJ_WWWSRVISDNCARD = "wwwsrvisdncard";
    public static final String OBJ_TYPESUBSSERVICE = "typesubsservice";
    public static final String OBJ_SUBSSERVICE = "subsservice";
    public static final String OBJ_TYPEAGREEMENT = "typeagreement";
    public static final String OBJ_ANICATEGORY = "anicategorie";
    public static final String OBJ_WWWORDERSTATE = "wwworderstate";
    public static final String OBJ_SERVICE = "service";

    // Таблиці БД
    /** Таблиця DIRNRS         */
    public static final String TBL_DIRNR = "DIRNRS";
    /** Таблиця ATECROSSPAIRS         */
    public static final String TBL_ATECROSSPAIR = "ATECROSSPAIRS";
    /** Таблиця FIRMS         */
    public static final String TBL_FIRM = "FIRMS";
    public static final String TBL_BANK = "BANKS";
    public static final String TBL_TARIFFLEVEL = "TARIFFLEVELS";
    public static final String TBL_TYPEPHONE = "TYPEPHONES";
    public static final String TBL_PHONESTATE = "PHONESTATES";
    public static final String TBL_TECHNICIAN = "TECHNICIANS";
    public static final String TBL_STREET = "STREETS";
    public static final String TBL_PROPERTY = "PROPERTIES";
    public static final String TBL_EXCHMDF = "EXCHMDFPAIRS";
    public static final String TBL_LINEMDF = "LINEMDFPAIRS";
    public static final String TBL_DISTRIB = "DISTRIBS";
    public static final String TBL_ATE = "ATES";
    public static final String TBL_EQUIP = "EQUIPS";
    public static final String TBL_EQUIPDIRNR = "EQUIPDIRNRS";
    public static final String TBL_ATETRUNKLINE = "ATETRUNKLINES";
    public static final String TBL_SHELFTRUNK = "SHELFTRUNKS";
    public static final String TBL_ATETRUNK = "ATETRUNKS";
    public static final String TBL_FAILRPT = "FAILRPTS";
    public static final String TBL_TECHREF = "TECHREFS";
    public static final String TBL_AGREEMENT = "AGREEMENTS";
    public static final String TBL_ZIP = "ZIPS";
    public static final String TBL_LEN = "LENS";
    public static final String TBL_DISTRIBSHELF = "DISTRIBSHELVES";
    public static final String TBL_TYPELOCATION = "TYPELOCATIONS";
    public static final String TBL_TYPEEQUIP = "TYPEEQUIPS";
    public static final String TBL_PHCROSS = "PHCROSSES";
    public static final String TBL_SHELFTRUNKLINE = "SHELFTRUNKLINES";
    public static final String TBL_BLRESTRICTION = "BLRESTRICTIONS";
    public static final String TBL_BLDIRNR = "BLDIRNRS";
    public static final String TBL_DEPT = "DEPTS";
    public static final String TBL_PERSON = "PERSONS";
    public static final String TBL_TYPEWWWSERVICE = "TYPEWWWSERVICES";
    public static final String TBL_WWWORDER = "WWWORDERS";
    public static final String TBL_WWWSERVICE = "WWWSERVICES";
    public static final String TBL_TYPESUBSSERVICE = "TYPESUBSSERVICES";
    public static final String TBL_SUBSSERVICE = "SUBSSERVICES";
    public static final String TBL_TYPEAGREEMENT = "TYPEAGREEMENTS";
    public static final String TBL_ANICATEGORY = "ANICATEGORIES";
    public static final String TBL_WWWORDERSTATE = "WWWORDERSTATES";
    /** Таблиця AGREEMENTSTATES        */
    public static final String TBL_AGREEMENTSTATE = "AGREEMENTSTATES";
    /** Таблиця FIRMINCOMES        */
    public static final String TBL_FIRMINCOME = "FIRMINCOMES";
    /** Таблиця CONSTRUCTORS       */
    public static final String TBL_CONSTRUCTOR = "CONSTRUCTORS";
    /** Таблиця PLANS */
    public static final String TBL_PLAN = "PLANS";
    /** Таблиця PLANSERVICES */
    public static final String TBL_PLANSERVICE = "PLANSERVICES";
    /** Таблиця PLANEQUIPS */
    public static final String TBL_PLANEQUIP = "PLANEQUIPS";
    /** Таблиця SERVICES */
    public static final String TBL_SERVICE = "SERVICES";
    /** Таблиця ACTIVITIES */
    public static final String TBL_ACTIVITY = "ACTIVITIES";
    /** Таблиця FIRMSTATES */
    public static final String TBL_FIRMSTATE = "FIRMSTATES";
    /** Таблиця TYPEFIRMS */
    public static final String TBL_TYPEFIRM = "TYPEFIRMS";
    /** Таблиця TYPEREJECTAGRS */
    public static final String TBL_TYPEREJECTAGR = "TYPEREJECTAGRS";
    /** Таблиця REGIONS */
    public static final String TBL_REGION = "REGIONS";
    /** Таблиця SESSIONS */
    public static final String TBL_SESSION = "SESSIONS";

    // Представлення
    /** Представлення DSTRCROSSTRACES */
    public static final String VIEW_DSTRCROSSTRACE = "DSTRCROSSTRACES";
    public static final String VIEW_LOCLENTRACE = "LOCLENTRACES";
    public static final String VIEW_LOCEXCHMDFTRACE = "LOCEXCHMDFTRACES";
    public static final String VIEW_LOCLINEMDFTRACE = "LOCLINEMDFTRACES";
    public static final String VIEW_EQUIPCROSSTRACE = "EQUIPCROSSTRACES";
    public static final String VIEW_SHLFCROSSTRACE = "SHLFCROSSTRACES";
    public static final String VIEW_ATECROSSTRACE = "ATECROSSTRACES";
    public static final String VIEW_TRUNKCROSSTRACE = "TRUNKCROSSTRACES";
    public static final String VIEW_LOCATION = "LOCATIONS";
    public static final String VIEW_PHTECHNICIAN = "ATETECHNICIANS";

    // Типи вибірки даних
    /** Тип вибірки даних: ідентифікатор */
    public static final String PROPERTY_ID      = "1";
    /** Тип вибірки даних: вибірка у вигляді полів та списків */
    public static final String PROPERTY_FIELDS   = "2";
    // public static final String PROPERTY_VALUES   = "3";
    /** Тип вибірки даних: вибірка у вигляді списку */
    public static final String PROPERTY_LIST    = "4";
    /** Тип вибірки даних: вибірка у вигляді головного поля */
    public static final String PROPERTY_FIELD   = "5";

    public static final String RSLT_YES    = "Так";
    public static final String RSLT_NO    = "Ні";

    /** Звіти користувачу БД */
    /** Реєстрація */
    public static final String RPT_REGISTR = "Реєстрація користувача БД";
    /** Реєстрація успішно закінчена! */
    public static final String RPT_REGISTR_OK = "Реєстрація успішно закінчена!";
    /** Реєстрація */
    public static final String RPT_REGISTR_NG = "";
    /** Як гість Ви маете обмежени права! */
    public static final String RPT_REGISTR_RESTRICT = "Як гість Ви маете обмежени права!";
    /** Невдалий результат */
    public static final String RPT_NOT_CORRECT_RESULT = "Невдалий результат!";
    /** Невдале збереження інформації! */
    public static final String RPT_TRANSACT_NG  = "Невдале збереження інформації!";
    /** Успішне збереження інформації! */
    public static final String RPT_TRANSACT_OK  = "Успішне збереження інформації!";
    /** Відсутнє! */
    public static final String RPT_OBJ_EMPTY  = "Відсутнє!";
    /** Натисніть кнопку */
    public static final String RPT_PRESS_KEY	= "Для повернення натисніть кнопку ...";
    /** Увага */
    public static final String RPT_ATTENTION	= "Увага!";
    /** Потрібно зареєструватися */
    public static final String RPT_LOGIN = "Для подальшої роботи Вам потрібно зареєструватися!";
    /** Виберіть потрібне */
    public static final String RPT_SELECT_ANY = "-- Виберіть потрібне --";
    /** немає в списку */
    public static final String RPT_IS_NOT_LIST = "немає в списку";
    /** друкувати документи */
    public static final String RPT_PRINT_REPORT = "друкувати документи";
    /** друкувати документи */
    public static final String RPT_HAVE_BEEN_SELECTED = "Телефон вже був вибран!";

    /** Стани телефонів */
    /** Усі телефони			*/
    public static final String PHONE_STATE_ALL	= "1";
    /** Незадіянні телефони		*/
    public static final String PHONE_STATE_UNAS	= "2";
    /** Телефони у стані підключення	*/
    public static final String PHONE_STATE_PRGS	= "3";
    /** Підключенні телефони		*/
    public static final String PHONE_STATE_CONN	= "4";
    /** Телефони у стані пошкодження	*/
    public static final String PHONE_STATE_FAIL	= "5";
    /** Телефони, яки були відключени	*/
    public static final String PHONE_STATE_DSBL	= "6";
    /** Телефони, яки поки що неможливо підключити */
    public static final String PHONE_STATE_IMPS	= "7";
    /** Статистика по телефонам		*/
    public static final String PHONE_STATE_STAT	= "8";

    /** Типи телефонів */
    /** Бізнес-телефони			*/
    public static final String PHONE_TARIFF_BSN	= "1";
    /** Арендни телефони			*/
    public static final String PHONE_TARIFF_LSD	= "2";
    /** Службові телефони			*/
    public static final String PHONE_TARIFF_UTL	= "3";
    /** Карткофони			*/
    public static final String PHONE_TARIFF_PAY	= "4";
    /** Всі телефони філії			*/
    public static final String PHONE_TARIFF_ALL	= "5";

    public static final String SEL_MULTIPLE_ID = SPECIAL_PARAM_PREFIX + "SEL_MULTIPLE_ID";

    public static final String CONSTRAINT_NOT_NULL = SPECIAL_PARAM_PREFIX + "CONSTR_NOT_NULL";

    // Використовуемі cookies
    /** Ідентифікатор користувача конкретного компьютера, який останній раз реєструвався */
    public static final String COOKIE_LASTLOGINUSER = "ISDBLASTLOGINUSER";

    // Типи SQL команд
    /** SQL: SELECT */
    public static String SQL_SELECT = "select ";
    /** SQL: UPDATE */
    public static String SQL_UPDATE = "update ";
    /** SQL: INSERT */
    public static String SQL_INSERT = "insert into ";
    /** SQL: DELETE */
    public static String SQL_DELETE = "delete ";

    /** SQL: WHERE */
    public static String SQL_WHERE = " where ";
    /** SQL: FROM */
    public static String SQL_FROM = " from ";
    /** SQL: ORDER BY */
    public static String SQL_ORDERBY = " order by ";
    /** SQL: GROUP BY */
    public static String SQL_GROUPBY = " group by ";
    /** SQL: AND */
    public static String SQL_AND = " and ";
    /** SQL: IS */
    public static String SQL_IS = " is ";
    /** SQL: SET */
    public static String SQL_SET = " set ";
    /** SQL: DISTINCT */
    public static String SQL_DISTINCT = "distinct ";
    /** SQL: DESC */
    public static String SQL_DESC = "desc ";
}

