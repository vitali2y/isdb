/**
 * jsi.java
 * ISDBj
 */

package isdb.ifaces;

/**
 * Інтерфейс роботи з JavaScript.
 * <P>JavaScript функції використовуються для додатковой підтримки користувачів
 * при перевірці формату введених полів, обовязковості введення полів
 * або вибору зі списку, тощо.
 * <P><B>Увага!</B>
 * <P>Підтримуеться всі можливості JavaScript, починая з версії 1.0!
 * @version 1.0 final, 11-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class jsi
{

    // Загальні події використовуемих функцій JavaScript
    /** Подія onBlur */
    public static String JS_ACTION_ONBLUR = "onBlur";
    /** Подія onLoad */
    public static String JS_ACTION_ONLOAD = "onLoad";
    /** Подія onUnload */
    public static String JS_ACTION_ONUNLOAD = "onUnload";
    /** Подія onClick */
    public static String JS_ACTION_ONCLICK = "onClick";
    /** Подія onDblClick */
    public static String JS_ACTION_ONDBLCLICK = "onDblClick";
    /** Подія onSubmit */
    public static String JS_ACTION_ONSUBMIT = "onSubmit";

    // Список використовуемих функцій JavaScript
    /** CHK_NULL (): перевірка нульового значення */
    public static int JS_FUNC_CHK_NULL = 0;
    /** Функція перевірки максімального розміра поля.
     * <P>CHK_SIZE (item, max): перевірка максімального max розміра поля item
     * <P>CHK_SIZE (item, max, min): перевірка максімального max та мінімального min розмірів поля item
     */
    public static int JS_FUNC_CHK_SIZE = 1;
    /** Функція перевірки значення поля.
     * <P>CHK_VAL (item, max): перевірка max значення поля item,
     * <P>CHK_VAL (item, min, max): перевірка min та max значення поля item
     */
    public static int JS_FUNC_CHK_VAL = 2;
    /** CHK_NUM (item): перевірка поля item на цифрові значення */
    public static int JS_FUNC_CHK_NUM = 3;
    /** CHK_ABCNUM (item): перевірка поля item на буквено-цифрові значення */
    public static int JS_FUNC_CHK_ABCNUM = 4;
    /** Перевірка поля на формат дати.
     * <P>CHK_DATE (item): перевірка поля item на формат дати
     * <P>CHK_DATE (item, maxchk): перевірка поля item на формат дати
     * с додатковой перевіркой на максім. дату, якщо maxchk = false
     */
    public static int JS_FUNC_CHK_DATE = 5;
    /** Перевірка введення не пустого значення у списку.
     * <P>CHK_LST (lstval, chkval): перевірка альтернативи введення
     * не пустого значення списку lstval або
     * значення chkval пімпи "немає в списку"
     * <P>CHK_LST (lstval): перевірка введення не пустого значення lstval списку
     */
    public static int JS_FUNC_CHK_LST = 6;
    /** WND_LOGO (): відображення лого програми */
    public static int JS_FUNC_WND_LOGO = 7;
    /** Виклик нового вікна броузера.
     * <P>WND_OPEN (wnd): виклик нового вікна броузера по URL wnd с параметром вікна - тільки прокрутка,
     * <P>WND_OPEN (wnd, par): виклик нового вікна броузера по URL wnd з параметрами вікна par
     */
    public static int JS_FUNC_WND_OPEN = 8;
    /** WND_CLOSE (): закриття наслідуваного вікна броузера */
    public static int JS_FUNC_WND_CLOSE = 9;
    /** PROMPT_PRINT (): попередження про можливість друкування */
    public static int JS_FUNC_PROMPT_PRINT = 10;
    /** GO_REDIR (returl): перенаправлення на другу сторінку returl */
    public static int JS_FUNC_GO_REDIR = 11;
    /** GO_BACK (): повернення на попередню сторінку */
    public static int JS_FUNC_GO_BACK = 12;
    /** CONFIRM (alertmsg): звернення до користувача з повідомленням alertmsg для підтвердження чі відмови від відповідної дії */
    public static int JS_FUNC_CONFIRM = 13;

    /** Код використовуемих функцій JavaScript */
    private static String [][] strJSFuncData = {
        {
        "function CHK_NULL(item){" +
        "if(item.value==\"\"){" +
        "alert(\"Це поле форми обов'язково потрібно заповнити!\");" +
        "item.focus();return false;}" +
        // "item.focus();item.value=\"?\";return false;}" +
        "return true;}",
        JS_ACTION_ONBLUR,
        "CHK_NULL(this"
        },
        {
          "function CHK_SIZE(){" +
          "var argv=CHK_SIZE.arguments;" +
          "var item=argv[0];" +
          "var max=argv[1];" +
          "if(argv.length>2)" +
          "{var min=argv[2];" +
          "if(item.value.length<min){" +
          "alert(\"Мінімальний розмір цього поля: \"+min+\" символів!\");" +
          "item.focus();return false;}}" +
          "if(item.value.length>max){" +
          "alert(\"Максімальний розмір цього поля: \"+max+\" символів!\");" +
          "item.focus();return false;}" +
          "return true;}",
          JS_ACTION_ONBLUR,
          "CHK_SIZE(this,"
        },
        {
          "function CHK_VAL(){" +
          "var msg=\"\";" +
          "var argv=CHK_VAL.arguments;" +
          "var item=argv[0];" +
          "var max=argv[1];" +
          "if(max>-1&&parseInt(item.value)>max)" + // -1 - не перевіряеться
          "msg=\" меньше за \"+max;" +
          "if(argv.length==2){" +
          "var min=argv[2];" +
          "if(parseInt(item.value)<min)" +
          "msg=\" більше за \"+min;}" +
          "if(msg==null){" +
          "alert(\"Будь ласка, введіть значення \"+item.value+msg+\"!\");" +
          "item.focus();return false;}" +
          "return true;}",
          JS_ACTION_ONBLUR,
          "CHK_VAL(this"
        },
        {
          "function CHK_NUM(item){" +
          "for(i=0;i<item.value.length;i++)" +
          "{" +
          "ch=item.value.substring(i,i+1);" +
          "if(ch>=\"0\"&&ch<=\"9\");" +
          "else" +
          "{alert(\"В полі можливо ввести тільки числові значення!\");" +
          "item.focus();return false;}" +
          "}return true;}",
          JS_ACTION_ONBLUR,
          "CHK_NUM(this"
        },
        {
          "function CHK_ABCNUM(item){" +
          "for(i=0;i<item.value.length;i++)" +
          "{" +
          "ch=item.value.substring(i,i+1);" +
          "if((ch>=\"0\"&&ch<=\"9\")||(ch>=\"a\"&&ch<=\"z\")||(ch>=\"A\"&&ch<=\"Z\")||ch==\"_\");" +
          "else" +
          "{alert(\"В полі можливо ввести тільки слідуючі значення:\\na ... a, A ... Z, _, 0 ... 9!\");" +
          "item.focus();return false;}" +
          "}return true;}",
          JS_ACTION_ONBLUR,
          "CHK_ABCNUM(this"
        },
        {
          "function CHK_DATE(){" +
          "var maxchk=true;" +
          "var argv=CHK_DATE.arguments;" +
          "var item=argv[0];" +
          "if(argv.length>1)" +
          "maxchk=false;" +
          "if(item.value==\"\")return;" +
          "var reterr=true;" +
          "var s3='';" +
          "var h='';" +
          "var i='';" +
          "var s4='';" +
          "v=item.value;" +
          "d=v.substring(0,2);" +    // day
          "s1=v.substring(2,3);" +
          "m=v.substring(3,5);" +    // month
          "s2=v.substring(5,6);" +
          "y=v.substring(6,8);" +    // year
          "s3=v.substring(8,9);" +
          "if(d<1||d>31)reterr=false;" +
          "if(m<1||m>12)reterr=false;" +
          "if(s1=='/'||s1=='-')s1='-';else reterr=false;" +
          "if(s2=='/'||s2=='-')s2='-';else reterr=false;" +
          "if(y<0||y>99)reterr=false;" +
          "if(m==4||m==6||m==9||m==11)" +
          "{if(d==31)reterr=false;}" +
          "if(m==2)" +
          "{" +
          "var t=parseInt(y/4);" +
          "if(isNaN(t))reterr=false;" +
          "if(d>29)reterr=false;" +
          "if(d==29&&((y/4)!=parseInt(y/4)))reterr=false;" +
          "}" +
          "if(!(v.length==8||v.length==14))reterr=false;" +
          "if(v.length==14)" +
          "{" +
          "if(s3!=' ')reterr=false;" +
          "h=v.substring(9,11);" +           // hour
          "s4=v.substring(11,12);" +
          "i=v.substring(12,14);" +          // minute
          "if(h<0||h>23)reterr=false;" +
          "if(s4==':'||s4=='-'||s4=='/')s4=':';else reterr=false;" +
          "if(i<0||i>59)reterr=false;" +
          "}" +
          "if(!reterr){alert('Значення поля не відповідає формату дати:\\nДД-ММ-РР ГГ:ХХ або ДД-ММ-РР');item.focus();}" +
          "else " +
          "{" +
          "n=new Date();" +
          "var bigdate=false;" +
          "var y2=n.getYear()-2000;" +
          "if(y<50&&y>y2&&maxchk)bigdate=true;" +
          "if(y==y2&&m>n.getMonth()+1) bigdate=true;" +
          "if(y==y2&&m==n.getMonth()+1&&d>n.getDate())bigdate=true;" +
          "if(bigdate){alert('Дата, яка була введена, перебільшуе поточну дату!');item.focus();reterr=false;}" + // reterr=true;}" +
          "}" +
          "return reterr;}",
          JS_ACTION_ONBLUR,
          "CHK_DATE(this"
        },
        {
          "function CHK_LST(){" +
          "var argv=CHK_LST.arguments;" +
          "var lst=argv[0];" +
          "var reterr=true;" +
          "var msgerr=\"\";" +
          "if(argv.length>1){" +
          "var chk=argv[1];" +
          "if(((lst.value==\"-\")&&(chk.checked==false))||" + // OBJ_NULL коли не встановлена пимпа "немає в списку"?
          "((lst.value!=\"-\")&&(chk.checked==true))){" + // встановлена пимпа "немає в списку", коли вибрано значення зі списку?
          "msgerr=\' або встановіть ознаку \"немає в списку\" для подальшого введення нового значення\';" +
          "reterr=false;}" +
          "}else{" +
          "if(lst.value==\"-\")" +                         // OBJ_NULL?
          "reterr=false;" +
          "}" +
          "if (!reterr){alert(\"Виберіть потрібне значення зі списку\"+msgerr+\"!\");par.focus();}" +
          "return reterr;}",
          JS_ACTION_ONBLUR,
          "CHK_LST("
        },
        {
          "function WND_LOGO(){" +
          "wnd=window.open ('','','width=355,height=255');" +
          "wnd.document.open();" +
          "wnd.document.writeln(\"<HTML>" +
          "<BODY BGCOLOR='#0033C0' " +
          "onLoad=\\\"setTimeout(\\'window.close()\\', 15000)\\\"><CENTER>" + // таймаут - 15 сек
          "<TITLE>ISDB</TITLE>" +
          "<APPLET CODE=\\\"ColorCycle.class\\\" CODEBASE=\\\"/applets\\\" WIDTH=\\\"338\\\" HEIGHT=\\\"236\\\">" +
          "<PARAM NAME=\\\"logo\\\" VALUE=\\\"../../../images/integra.gif\\\">" +
          "<PARAM NAME=\\\"factor\\\" VALUE=\\\"8\\\">" +
          "<PARAM NAME=\\\"scroll\\\" VALUE=\\\"" + isdb.miscs.dclrs.ISDB_NAME + "\\\">" +
          "</APPLET></CENTER>" +
          "</BODY></HTML>\");" +
          "wnd.document.close();}",
          JS_ACTION_ONLOAD,
          "WND_LOGO("
        },
        {
          "function WND_OPEN(){" +
          "var argv=WND_OPEN.arguments;" +
          "var par=\"\";" +
          "if(argv.length=1)par=argv[1];else par=\"scrollbars=yes\";" +
          // "if(par==\"0\")par=\"scrollbars=yes\";" +
          // "if(par==\"0\")par=\"scrollbars=1,width=screen.availHeight-100,height='+screen.availHeight+',top='+screen.availTop+',left='+screen.availLeft-100\";" +
          // 'width=100,height='+screen.availHeight+',top='+screen.availTop+',left='+screen.availLeft);
          "window.open(argv[0],'',par);" +
          "return false;}",
          JS_ACTION_ONCLICK,
          "WND_OPEN("
        },
        {
          "",
          JS_ACTION_ONCLICK,
          "window.close("
        },
        {
          "function PROMPT_PRINT()" +
          "{alert(\"Для друкування звіту виберіть пункт меню: File->Print або кнопку Print!\");}",
          JS_ACTION_ONLOAD,
          "PROMPT_PRINT("
        },
        {
          "function GO_REDIR(returl)" +
          "{location.replace(returl);}",
          JS_ACTION_ONCLICK,
          "GO_REDIR("
        },
        {
          "function GO_BACK()" +
          "{history.go(-1);}",
          JS_ACTION_ONCLICK,
          "GO_BACK("
        },
        {
          "function CONFIRM(alertmsg)" +
          "{confirm(alertmsg)}",
          JS_ACTION_ONCLICK,
          "return confirm("
        }

        // <A HREF="javascript:window.print()">Click to Print This Page</A>
        // onLoad="window.print()"

        // <FORM><IMPUT TYPE="button" onClick="window.print()"></FORM>

        // onLoad="document.joe.burns.focus()">

        // <FORM NAME="joe">
        // <INPUT TYPE="text" name="burns" size="10" onKeyUp="check()"><BR>
        // <INPUT TYPE="text" name="tammy" size="10" onKeyUp="check2()"><BR>
        // <INPUT TYPE="submit" VALUE="Click to Send" NAME="go">
        // </FORM>
        // function check()
        // {
        // var letters = document.joe.burns.value.length +1;
        // if (letters <= 4)
        // {document.joe.burns.focus()}
        // else
        // {document.joe.tammy.focus()}
        // }

        };

    /**
     * Вибірка тіла виконуемого кода JavaScript функції.
     * @param iJSKey назва JavaScript функції
     * @return тіло JavaScript функції
     */
    public static String getJSBody (int iJSKey)
    {
        return strJSFuncData [iJSKey][0];
    }

    /**
     * Вибірка типу дії виклика JavaScript функції.
     * @param iJSKey назва функції
     * @return тип дії виклика JavaScript функції
     */
    public static String getJSAction (int iJSKey)
    {
        return strJSFuncData [iJSKey][1];
    }

    /**
     * Вибірка виклика JavaScript функції.
     * @param iJSKey назва JavaScript функції
     * @param strParam1 перший вхідний параметр функції
     * @param strParam2 другий вхідний параметр функції
     * @return виклик JavaScript функції
     */
    public static String getJSCall (int iJSKey, String strParam1, String strParam2)
    {
        if (String.valueOf (strParam2) != "null")
            return strJSFuncData [iJSKey][2] +
                   strParam1 + "," +
                   strParam2 + ")";
        if (String.valueOf (strParam1) != "null")
            return strJSFuncData [iJSKey][2] +
                   strParam1 + ")";
        return strJSFuncData [iJSKey][2] + ")";
    }

    /**
     * Вибірка виклика JavaScript функції з одним вхідним параметром.
     * @param iJSKey назва JavaScript функції
     * @param strParam1 параметр функції
     * @return виклик JavaScript функції
     * @see #getJSCall (int, String, String)
     */
    public static String getJSCall (int iJSKey, String strParam1)
    {
        return getJSCall (iJSKey, strParam1, null);
    }

    /**
     * Вибірка виклика JavaScript функції без вхідних параметрів.
     * @param iJSKey назва JavaScript функції
     * @return виклик JavaScript функції
     * @see #getJSCall (int, String, String)
     */
    public static String getJSCall (int iJSKey)
    {
        return getJSCall (iJSKey, null, null);
    }

    /**
     * Повернення всіх використовуемих JavaScript функції.
     * @param strJSBody тіло використовуемих JavaScript функції
     * @return сформований блок JavaScript функцій
     */
    public static String getJS (String strJSBody)
    {
        return isdb.ifaces.htmli.script (strJSBody);
        // return isdb.ifaces.htmli.script ("var err=true;" + strJSBody);
    }
}

