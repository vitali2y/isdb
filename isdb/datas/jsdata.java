/**
 * jsdata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

import isdb.ifaces.jsi;

/**
 * Поточні дані JavaScript об'екта.
 * @version 1.0 final, 11-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class jsdata
{
    /** Дані JavaScript об'екта */
    private Hashtable hashJS;
    private static String PAR_SUBMIT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "SUBMIT";

    /** Встановлен чі ні режим тестування? (по замовчанню: false - ні) */
    private static boolean MODE_DEBUG = false;

    /**
     * Конструктор.
     */
    public jsdata ()
    {
        hashJS = new Hashtable ();
    }

    /**
     * Перевірка встановлення JavaScript функції для необхідного поля об'екта.
     * @param strNameField назва поля  об'екта
     * @param iJSKey назва JavaScript функції
     * @return true - якщо встановлена функція, інакше - false
     */
    public boolean isJS (String strNameField, int iJSKey)
    {
        Hashtable hashField = (Hashtable) hashJS.get (strNameField);
        if (hashField != (Hashtable) null)
        {
            String strKey = null;
            Enumeration enumVals = hashField.keys ();
            while (enumVals.hasMoreElements ())
            {
                strKey = (String) enumVals.nextElement ();
                strKey = strKey.substring (0, strKey.indexOf ('('));
                if (isdb.ifaces.jsi.getJSCall (iJSKey).startsWith (strKey))
                {
                    if (MODE_DEBUG)                // тестувал. режим?
                    {
                        Exception e1 = new Exception ("isJS : strNameField=" + strNameField +
                                                      "\nstrKey=" + strKey +
                                                      "\nhashField=" +  hashField.toString () +
                                                      "\ngetJSCall (iJSKey)=" + isdb.ifaces.jsi.getJSCall (iJSKey));
                        e1.printStackTrace ();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Збереження інформації щодо перевірочних викликів JavaScript функцій.
     * для проведення послідуючої превірки поперед відправки форми з кліента на сервер.
     * @param strNameField назва обробляемого поля
     * @param strJSCheckCall сформована інформація щодо викликів для перевірки поля
     */
    public void setJSSubmitControl (String strNameField, String strJSCheckCall)
    {
        int iPos = 0;
        String strTmp = "";
        String strSubmit = (String) hashJS.get (PAR_SUBMIT);
        if (String.valueOf (strSubmit) == "null")
            strSubmit = "";
        while (true)
        {
            iPos = strJSCheckCall.indexOf ("this");  // пощук магічного слова
            if (iPos == -1)                          // нема?
            {
                strTmp += strJSCheckCall;
                break;
            }
            strTmp += strJSCheckCall.substring (0, iPos) + strNameField;
            strJSCheckCall = strJSCheckCall.substring (iPos + 4, strJSCheckCall.length ());
        }
        strSubmit += "if(err)err=" + strTmp + ";";
        hashJS.put (PAR_SUBMIT, strSubmit);
    }

    /**
     * Збереження інформації стосовно JavaScript функції для поля об'екта.
     * @param strNameField назва поля  об'екта
     * @param iJSKey назва JavaScript функції
     * @param strParam1 перший параметр функції
     * @param strParam2 другий параметр функції
     */
    public void setJS (String strNameField, int iJSKey, String strParam1, String strParam2)
    {
        String strCall = null;

        // перевірка кількості параметрів?
        if (String.valueOf (strParam2) == "null")       // додатковий - один параметр?
        {
            if (String.valueOf (strParam1) == "null")   // нема додаткових параметрів?
                strCall = isdb.ifaces.jsi.getJSCall (iJSKey /* strNameField */ );
            else
                strCall = isdb.ifaces.jsi.getJSCall (iJSKey, /* strNameField */ strParam1);
        }
        else                                            // додаткових - два параметра?
            strCall = isdb.ifaces.jsi.getJSCall (iJSKey, strParam1, strParam2);
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception e1 = new Exception ("setJS: strNameField=" + strNameField + "=" + strCall +
                                          "\ngetJSAction=" + isdb.ifaces.jsi.getJSAction (iJSKey) +
                                          "\n1 par=" + strParam1 +
                                          "\n2 par=" + strParam2);
            e1.printStackTrace ();
        }
        setJSSubmitControl (strNameField, strCall);
        Hashtable hashField = (Hashtable) hashJS.get (strNameField);
        if (hashField == null)            // ще не існує місце для збереження інформації?
            hashField = new Hashtable ();
        hashField.put (strCall, isdb.ifaces.jsi.getJSAction (iJSKey));
        hashJS.put (strNameField, hashField);
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception e2 = new Exception ("setJS 2: strNameField=" + strNameField + "\n" +
                                          isdb.ifaces.jsi.getJSAction (iJSKey) +
                                          "\nhashJS=" + hashJS.toString ());
            e2.printStackTrace ();
        }
    }

    /**
     * Збереження інформації стосовно JavaScript функції для поля об'екта.
     * @param strNameField назва поля
     * @param iJSKey назва JavaScript функції
     * @see #setJS (String, int, String, String)
     */
    public void setJS (String strNameField, int iJSKey)
    {
        setJS (strNameField, iJSKey, null, null);
    }

    /**
     * Збереження інформації стосовно JavaScript функції для поля об'екта.
     * @param strNameField назва поля
     * @param iJSKey назва JavaScript функції
     * @param strParam1 перший параметр функції
     * @see #setJS (String, int, String, String)
     */
    public void setJS (String strNameField, int iJSKey, String strParam1)
    {
        setJS (strNameField, iJSKey, strParam1, null);
    }

    /**
     * Одержання інформації стосовно JavaScript функції поля для потрібної події.
     * @param strNameField назва поля
     * @param strAction подія функції JavaScript
     * @return сформовани JavaScript функції поля для потрібной події,
     * якщо відсутня - то пусту стрічку
     */
    public String getJS (String strNameField, String strAction)
    {
        String strKey = null;
        String strVal = null;
        String strRet = "";

        if (String.valueOf (strNameField) == "null")            // для всіх полів та подій?
        {
            Enumeration enumFields = hashJS.keys ();

            // рекурсивно викликаеться функція одержання сформованих JavaScript функцій
            while (enumFields.hasMoreElements ())
                strRet += getJS ((String) enumFields.nextElement (), null);
            return strRet;
        }
        else
        {
            Hashtable hashField = (Hashtable) hashJS.get (strNameField);
            if (hashField != null)
            {
                Enumeration enumField = hashField.keys ();
                while (enumField.hasMoreElements ())
                {
                    strKey = (String) enumField.nextElement ();
                    strVal = (String) hashField.get (strKey);
                    if (strNameField.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX))    // спеціальне призначення?
                        strRet += strKey + ";";
                    else
                        if ((String.valueOf (strAction) == "null") ||            // для всіх подій?
                                strVal.equals (strAction))                       // вибрана потрібна дія функції?
                            strRet += "if(err)err=" + strKey + ";";
                }
                if (strNameField.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX))    // спеціальне призначення?
                    strRet = strAction + "=\"" + strRet.substring (0, strRet.length () - 1) + "\"";
                else
                    strRet = strAction + "=\"var err=true;" +
                             strRet.substring (0, strRet.length ()) + "return err\"";
                if (MODE_DEBUG)                // тестувал. режим?
                {
                    Exception e7 = new Exception ("getJS : strRet=" + strRet + "\nhashJS=" + hashJS.toString ());
                    e7.printStackTrace ();
                }
                return strRet;
            }
        }
        return "";
    }

    /**
     * Одержання інформації стосовно JavaScript функції для всіх подій поля.
     * @param strNameField назва поля
     * @return сформовани JavaScript функції поля для всіх подій
     * @see #getJS (String, String)
     */
    public String getJS (String strNameField)
    {
        return getJS (strNameField, null);
    }

    /**
     * Одержання інформації стосовно JavaScript функцій всіх полів форми.
     * @return сформовани JavaScript функції
     */
    public String getJS ()
    {
        String strVal = (String) hashJS.get (PAR_SUBMIT);
        if (String.valueOf (strVal) == "null")
            strVal = "";
        return isdb.ifaces.jsi.JS_ACTION_ONSUBMIT + "=\"var err=true;" + strVal + "return err\"";
    }

    /**
     * Відображення об'екта в текстовій формі.
     * @return параметри об'екта в текстовій формі
     */
    public String toString ()
    {
        String strTmp = "jsdata:\n";
        Enumeration enumVals = hashJS.keys ();
        while (enumVals.hasMoreElements ())
        {
            String strKey = (String) enumVals.nextElement ();
            strTmp += strKey + "=" + ((Object) hashJS.get (strKey)).toString () + "\n";
        }
        return strTmp;
    }
}

