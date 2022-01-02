/**
 * dbdata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

import isdb.datas.*;

/**
 * Поточни дані об'екта під час роботи з БД та методи для їх використання.
 * @version 1.0 final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class dbdata
{
    private static final String PREPARED = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "PREPARED";
    private static final String DATA_SQL = isdb.miscs.dclrs.SPECIAL_SERVICE_PREFIX + "DATA_SQL";

    /** Поточни дані об'екта */
    private Hashtable hashVals;
    /** Кнопки управління даними */
    private Hashtable hashButs;
    /** Крітерій вибірки */
    private String strCriteria;
    /** Поточни SQL команда об'екта */
    private String strSqlCmd;
    /** Номер поточної сесії для даних об'екта */
    private String strSsnId;
    /** Кількість вибраних значеннь */
    private int iResult;
    /** Кількість кнопок керування */
    private int iCntButtn;
    /** Номер поточної фази транзакції */
    private int iTrnPhase;

    /**
     * Конструктор.
     * @param strSsnId поточний номер сесії користувача
     */
    public dbdata (String strSsnId)
    {
        hashVals = new Hashtable ();
        hashButs = new Hashtable ();
        this.strSsnId = strSsnId;
        iResult = 0;
        iCntButtn = 0;
        this.strSqlCmd = "";
        this.strCriteria = "";
        this.iTrnPhase = 1;
    }

    /**
     * Встановлення номера сесії.
     * @param strSsnId номер сесії користувача
     */
    public void setSession (String strSsnId)
    {
        this.strSsnId = strSsnId;
    }

    /**
     * Повернення номера сесії.
     * @return поточний номер сесії користувача
     */
    public String getSession ()
    {
        return this.strSsnId;
    }

    /**
     * Повернення набора назв полів об'екта.
     * @return поточний набір назв полів об'екта
     */
    public Enumeration getKeys ()
    {
        return hashVals.keys ();
    }

    /**
     * Повернення результата щодо існування значення поля об'екта.
     * @param oKey назва поля об'екта
     * @return true - якщо існує
     */
    public boolean containsKey (Object oKey)
    {
        return hashVals.containsKey (oKey);
    }

    /**
     * Збереження значення поля об'екта.
     * @param oKey назва поля об'екта
     * @param oValue значення поля об'екта
     */
    public void setVal (Object oKey, Object oValue)
    {
        if (oValue != null)
            hashVals.put (oKey, oValue);
    }

    /**
     * Повернення значення поля об'екта.
     * @param oKey назва поля об'екта
     * @return значення поля об'екта (OBJ_NULL - якщо нульове значення)
     */
    public String getVal (Object oKey)
    {
        String strVal = (String) hashVals.get (oKey);
        /*
        if ((String.valueOf (strVal) == "null") ||
                                    strVal.equals (""))
        strVal = isdb.miscs.dclrs.OBJ_NULL;
        */
        return strVal;
    }

    /**
     * Очішення значення поля об'ектаю.
     * @param oKey назва поля об'екта
     */
    public void removeVal (Object oKey)
    {
        hashVals.remove (oKey);
    }

    /**
     * Встановлення коректного результата попередньої операції.
     * @param iCount результат попередньої операції
     */
    public void setResultCount (int iCount)
    {
        iResult = iCount;
    }

    /**
     * Одержання результата попередньої операції.
     * @return одержання результату
     */
    public int getResultCount ()
    {
        return iResult;
    }

    /**
     * Перевірка одержання коректного результата.
     * @return true - якщо результат одержан
     */
    public boolean isResultOK ()
    {
        return (iResult > 0) ? true : false;
    }

    /**
     * Перевірка одержання невдалого результата.
     * @return true - якщо невдалий результат
     */
    public boolean isResultBad ()
    {
        return (iResult == -1) ? true : false;
    }

    /**
     * Перевірка кількості получених результатів.
     * @return true - якщо одержано більше одного запису
     */
    public boolean isResultNotOne ()
    {
        return (iResult > 1) ? true : false;
    }

    /**
     * Встановлення поточной SQL команды.
     * @param strSqlCmd поточна SQL команда
     * @see #getModeObj ()
     */
    public void setModeObj (String strSqlCmd)
    {
        this.strSqlCmd = strSqlCmd;
    }

    /**
     * Повернення поточной SQL команды, асоціьованой з об'ектом.
     * @return поточна SQL команда
     * @see #setModeObj (String)
     */
    public String getModeObj ()
    {
        return this.strSqlCmd;
    }

    /**
     * Встановлення крітерію вибірки об'екта.
     * @param strCriteria крітерій вибірки об'екта
     * @see #getCriteriaObj ()
     */
    public void setCriteriaObj (String strCriteria)
    {
        this.strCriteria = strCriteria;
    }

    /**
     * Вибірка крітерію вибірки об'екта.
     * @return крітерій вибірки об'екта
     * @see #setCriteriaObj (String)
     */
    public String getCriteriaObj ()
    {
        return this.strCriteria;
    }

    /**
     * Перевірка відповідності потрібному крітерію вибірки об'екта.
     * @return true - значення відповідає потрібному крітерію вибірки об'екта, інакше - false
     */
    public boolean isCriteriaLike (String strCriteriaPattern)
    {
        if (isPresent (isdb.miscs.dclrs.PAR_CRITERIA) &&
                (getCriteriaObj ().startsWith ("(" + strCriteriaPattern) ||
                 getCriteriaObj ().startsWith (strCriteriaPattern)))  // відповідає крітерію?
            return true;
        return false;
    }

    /**
     * Одержання вхідних HTTP параметрів.
     * @param req потік вхідних HTTP параметрів
     */
    public void getData (javax.servlet.http.HttpServletRequest req)
    {
        int iI;
        String strKey;
        String strVal;
        String strTmp;
        String strValues [];
        Enumeration enumPars = req.getParameterNames ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strValues = (String []) req.getParameterValues (strKey);
            iI = 0;
            while (iI < strValues.length)                // сканування вхідних значеннь запит. даних або параметрів форм
            {
                strVal = strValues [iI++];
                if (String.valueOf (strVal) == "null")
                    strVal = isdb.miscs.dclrs.OBJ_NULL;
                strTmp = getVal (strKey);
                if (String.valueOf (strTmp) == "null")  // вже існує такий параметр?
                    strTmp = strVal;
                else
                {
                    if (strVal.equals (isdb.miscs.dclrs.OBJ_NULL) ||
                            strVal.equals (strTmp));
                    else
                        strTmp += "," + strVal;             // формування багаторазового значення
                }
                setVal (strKey, strTmp);
                if (strKey.equals (isdb.miscs.dclrs.PAR_CRITERIA))
                    this.strCriteria = strTmp;
            }
        }
    }

    /**
     * Перевірка існування пустого об'екта по назві.
     * @return true, якщо потрібний об'ект - пустий
     */
    public boolean isObjectNull ()
    {
        return (String.valueOf (getVal (isdb.miscs.dclrs.PAR_OBJECT)) == "null") ? true : false;
    }

    /**
     * Перевірка існування потрібного об'екта по назві.
     * @param strNameObj назва об'екта
     * @return true, якщо це потрібний об'ект
     */
    public boolean isObject (String strNameObj)
    {
        if (isObjectNull ())
            return false;
        return getVal (isdb.miscs.dclrs.PAR_OBJECT).equals (strNameObj) ? true : false;
    }

    /**
     * Встановлення нового об'екта.
     * @param strNameNewObj назва нового об'екта
     */
    public void setObject (String strNameNewObj)
    {
        setVal (isdb.miscs.dclrs.PAR_OBJECT, strNameNewObj);
    }

    /**
     * Перевірка встановлення потрібного параметру.
     * @param strTypeParam тип перевыряемого параметра
     * @param strTypeParam назва параметра
     * @return true - якщо потрібний параметр встановлен
     */
    private boolean isEquals (String strTypeParam, String strNameParam)
    {
        String strVal = (String) getVal (strTypeParam);
        if (String.valueOf (strVal) == "null")
            return false;
        return strVal.equals (strNameParam) ? true : false;
    }

    /**
     * Перевірка встановлення потрібного режима функціонування по назві.
     * @param strNameRegime назва режима
     * @return true - якщо потрібний наступний режим встановлен
     * @see #isEquals (String, String)
     */
    public boolean isRegime (String strNameRegime)
    {
        return isEquals (isdb.miscs.dclrs.PAR_REGIME, strNameRegime);
    }

    /**
     * Перевірка встановлення потрібного наступного режима функціонування по назві.
     * @param strNameNextRegime назва наступного режима
     * @return true - якщо потрібний наступний режим встановлен
     * @see #isEquals (String, String)
     */
    public boolean isNextRegime (String strNameNextRegime)
    {
        return isEquals (isdb.miscs.dclrs.PAR_NEXTREGIME, strNameNextRegime);
    }

    /**
     * Перевірка встановлення типу вибірки даних по назві.
     * @param strNameSelection назва типу вибірки даних
     * @return true - якщо потрібний тип вибірки даних встановлен
     * @see #isEquals (String, String)
     */
    public boolean isSelectionBy (String strNameSelection)
    {
        return isEquals (isdb.miscs.dclrs.PAR_TYPESELECT, strNameSelection);
    }

    /**
     * Встановлення нового режима функціонування по назві.
     * @param strNameNewReg назва нового режима
     */
    public void setRegime (String strNameNewReg)
    {
        setVal (isdb.miscs.dclrs.PAR_REGIME, strNameNewReg);
    }

    /**
     * Перевірка присутності потрібного значення.
     * @param strName назва значення
     * @return true - якщо існує
     */
    public boolean isPresent (String strName)
    {
        if (String.valueOf (getVal (strName)) != "null")
        {
            if (!getVal (strName).equals (isdb.miscs.dclrs.OBJ_NULL))
                return true;
        }
        return false;
    }

    /**
     * Встановлення звіту про помилку.
     * @param strErrName назва об'екта помилки
     */
    public void setError (String strErrName)
    {
        String strTmpErr = getVal (isdb.miscs.dclrs.PAR_ERROR);
        if (String.valueOf (strTmpErr) == "null")
            strTmpErr = "";

        if (strErrName.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX))    // службове призначення?
            strTmpErr += isdb.ifaces.htmli.crlf () +
                         "Параметр" +
                         isdb.ifaces.htmli.crlf () +
                         strErrName + " (=" + getVal (strErrName) + ")" +
                         isdb.ifaces.htmli.crlf () +
                         "не коректний!";
        else
            strTmpErr += strErrName;
        setVal (isdb.miscs.dclrs.PAR_ERROR, strTmpErr);
    }

    /**
     * Перевірка присутності помилки.
     * @return true - якщо вона існує
     */
    public boolean isError ()
    {
        if (String.valueOf (getVal (isdb.miscs.dclrs.PAR_ERROR)) != "null")
            return true;
        else
            return false;
    }

    /**
     * Повернення помилки.
     * @return звіт про помилку
     */
    public String getError ()
    {
        return getVal (isdb.miscs.dclrs.PAR_ERROR);
    }

    /**
     * Повернення подтвердження чи потрібно создавати новий запис даних.
     * <P>Використовуеться параметр: _NEW_object(param1=value1,...,paramN=valueN)=countnewrecords
     * @param oPoolData пул зберегаемих, значеннь використовуемих в транзакціях об'ектів
     * @return true - якщо потрібно создавати
     */
    public boolean isRequiredCreateObj (pooldata oPoolData)
    {
        Enumeration enumPars = hashVals.keys ();
        String strKey = null;
        String strVal = null;
        while (enumPars.hasMoreElements ())
        {
            // if (((String) enumPars.nextElement ()).startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST))            // потрібно створити новий запис?
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST))            // потрібно створити новий запис?
                // && (String.valueOf (oPoolData.getVal (getSession (), strTmp)) == "null"))
            {
                if (oPoolData == null)
                    return true;
                strVal = (String) hashVals.get (strKey);
                String strTmp = (strKey.substring (5) + "_ID").toUpperCase ();
                if (!(strVal.equals ("") ||
                        // (String.valueOf (oPoolData.getVal (getSession (), strTmp)) != "null") ||
                        (String.valueOf (strVal) == "null")))
                    return true;
            }
        }
        return false;
    }

    /**
     * Повернення подтвердження чи потрібно создавати новий запис даних.
     * <P>Використовуеться параметр: _NEW_object(param1=value1,...,paramN=valueN)=countnewrecords
     * @return true - якщо потрібно создавати
     */
    public boolean isRequiredCreateObj ()
    {
        return isRequiredCreateObj (null);
    }

    /**
     * Повернення назви об'екта, запис якого потрібно створити.
     * @return назва об'екта, новий запис якого потрібно створити.
     */
    public String getNameCreateObj ()
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST))
            {
                strVal = (String) hashVals.get (strKey);
                if (!((String.valueOf (strVal) == "null") ||      // чі признак створення нової форми - не нульовий?
                        strVal.equals ("")))
                {
                    int iStartPos = strKey.indexOf ('(');
                    if (iStartPos > -1)       // багатозначний ( OBJECT(ID=X)=1 ) чи ні об'ект?
                        return strKey.substring (5, iStartPos);
                    else
                        return strKey.substring (5);
                }
            }
        }
        return null;
    }

    /**
     * Повернення параметрів об'екта, для створення запису якого потрібно створити.
     * @return параметри об'екта, новий запис якого потрібно створити
     */
    public String getParamCreateObj ()
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST))
            {
                strVal = (String) hashVals.get (strKey);
                if (!((String.valueOf (strVal) == "null") ||      // чі признак створення нової форми - не нульовий?
                        strVal.equals ("")))
                {
                    int iI = (new Integer (strVal)).intValue ();
                    if (--iI == 0)                                // більше не треба створювати записи?
                        hashVals.remove (strKey);
                    else
                        hashVals.put (strKey, (new Integer (iI)).toString ());

                    int iStartPos = strKey.indexOf ('(');
                    int iEndPos;
                    if (iStartPos > -1)       // багатозначний ( OBJECT(ID=X)=1 ) чи ні об'ект?
                    {
                        iEndPos = strKey.indexOf (')');

                        ///
                        Exception e1 = new Exception ("getParamCreateObj: " + strKey.substring (iStartPos + 1, iEndPos) + "\n" + toString ());
                        e1.printStackTrace ();
                        ///

                        return strKey.substring (iStartPos + 1, iEndPos) + "&";
                    }
                }
            }
        }
        return "";
    }

    /**
     * Повернення ідентифікатора запису об'екта, запис якого потрібно створити.
     * @param strNameObj назва об'екта
     * @param oPoolData пул зберегаемих, значеннь використовуемих в транзакціях об'ектів
     * @return ідентифікатор запису об'екта
     */
    public String getIdCreateObj (String strNameObj, pooldata oPoolData)
    {
        String strId = oPoolData.getVal (getSession (), strNameObj /* (strNameObj + "_ID").toUpperCase () */ );

        ///
        Exception e1 = new Exception ("getIdCreateObj: ID=" + strId + "\nstrNameObj="+
                                      strNameObj + "\nPAR_ID="+(String) hashVals.get (isdb.miscs.dclrs.PAR_ID) + "\n" + toString () +
                                      "\noPoolData=" + oPoolData.toString (getSession ()));
        e1.printStackTrace ();
        ///

        if (String.valueOf (strId) == "null")
            strId = (String) hashVals.get (isdb.miscs.dclrs.PAR_ID);
        return strId;
    }

    /**
     * Повернення подтвердження чи потрібно редагувати запис даних.
     * @return подтвердження потрібно (true) чи ні редагувати запис даних
     */
    public boolean isRequiredUpdateObj ()
    {
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            if (((String) enumPars.nextElement ()).startsWith (isdb.miscs.dclrs.PAR_UPDATE_RECORD))            // потрібно редагувати запис?
                return true;
        }
        return false;
    }

    /**
     * Повернення назви об'екта, запис якого потрібно редагувати.
     * @return назва об'екта, запис якого потрібно редагувати
     */
    public String getNameUpdateObj ()
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_UPDATE_RECORD))            // потрібно редагувати запис?
                return strKey.substring (5);
        }
        return null;
    }

    /**
     * Повернення ідентифікатора запису об'екта.
     * який потрібно редагувати
     * @param strNameObj назва об'екта
     * @return ідентифікатор запису об'екта
     */
    public String getIdUpdateObj (String strNameObj)
    {
        strNameObj = isdb.miscs.dclrs.PAR_UPDATE_RECORD + strNameObj;
        String strId = (String) hashVals.get (strNameObj);
        hashVals.remove (strNameObj);
        return strId;
    }

    /**
     * Встановлення подтвердження на якомога швидше оновлення даних.
     */
    public void setRequiredImmedUpdate ()
    {
        setVal (isdb.miscs.dclrs.PAR_IMMEDUPDATE, isdb.miscs.dclrs.YES);
    }

    /**
     * Повернення подтвердження на якомога швидше оновлення даних.
     * @return true - потрібне якомога швидше оновлення даних
     */
    public boolean isRequiredImmedUpdate ()
    {
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.equals (isdb.miscs.dclrs.PAR_IMMEDUPDATE) &&
                    hashVals.get (isdb.miscs.dclrs.PAR_IMMEDUPDATE).equals (isdb.miscs.dclrs.YES))            // потрібно оновляти зразу?
                return true;
        }
        return false;
    }

    /**
     * Повернення подтвердження, чи е не заповнене поле об'екта.
     * @param oPoolData пул зберегаемих, значеннь використовуемих в транзакціях об'ектів
     * @return true - існуе хотя б одне не заповнене поле об'екта
     */
    public boolean isRequiredCreateRefObj (pooldata oPoolData)
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strVal = (String) hashVals.get (strKey);
            if (strKey.endsWith ("_ID") &&
                    (strKey.length () > 3) &&
                    // if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST)) // не заповнене поле?

                    // if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST) &&
                    ((String.valueOf (strVal) == "null") ||
                     strVal.equals (isdb.miscs.dclrs.OBJ_NULL)))            // не заповнене поле?

                /*
                                            if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST) &&
                                                    (String.valueOf (strVal) != "null"))            // не заповнене поле?
                */
            {
                if (String.valueOf (oPoolData.getVal (this.strSsnId, strKey)) == "null")
                    return true;
            }
        }
        return false;
    }

    /**
     * Повернення назви ссилочного об'екта, запис якого потрібно создати.
     * в зв'язку з тим, що не заповнене поточного поле об'екта.
     * @param oPoolData пул зберегаемих, значеннь використовуемих в транзакціях об'ектів
     * @return назва ссилочного об'екта.
     * Якщо не існує - null
     */
    public String getIdCreateRefObj (pooldata oPoolData)
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strVal = (String) hashVals.get (strKey);
            // if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST)) // не заповнене поле?
            if (strKey.endsWith ("_ID") &&
                    (strKey.length () > 3) &&
                    // if (strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST) &&
                    ((String.valueOf (strVal) == "null") ||
                     strVal.equals (isdb.miscs.dclrs.OBJ_NULL)))            // не заповнене поле?
            {
                if (String.valueOf (oPoolData.getVal (this.strSsnId, strKey)) == "null")
                    return strKey;
            }
        }
        return null;
    }

    /**
     * Повернення інформації про друк документів.
     * @return повернення параметр(а/ів) щодо друку в форматі GET метода HTTP
     */
    public String getPrintInfo ()
    {
        String strKey = null;
        String strPrint = "";
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_PRINT_REPORT))           // потрібен друк документів?
                strPrint += "&" + strKey + "=" + (String) hashVals.get (strKey);
        }
        return strPrint;
    }

    /**
     * Повернення подтвердження потрібен чи ні друк документів.
     * @return true - потрібен друк документів, false - ні
     */
    public boolean isRequiredPrint ()
    {
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_PRINT_REPORT))            // потрібен друку документів?
                return true;
        }
        return false;
    }

    /**
     * Повернення назви об'екта, який відповідальний за друк.
     * @return назва об'екта
     */
    public String getPrintRptObj ()
    {
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_PRINT_REPORT))           // параметр друку документів?
                return strKey.substring (isdb.miscs.dclrs.PAR_PRINT_REPORT.length ());
        }
        return null;
    }

    /**
     * Повернення ідентифікатора звіта.
     * @return ідентифікатор звіта
     */
    public String getPrintRptId ()
    {
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.PAR_PRINT_REPORT))           // параметр друку документів?
                return (String) hashVals.get (strKey);
        }
        return null;
    }

    /**
     * Повернення HTTP значеннь параметрів об'екта.
     * @return параметри об'екту в форматі GET метода HTTP
     */
    public String getHTTPParams ()
    {
        String strParam = "";
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strParam += strKey + "=" + (String) hashVals.get (strKey) + "&";
        }
        return strParam;
    }

    /**
     * Повернення HTML значеннь параметрів об'екта.
     * @return значення параметрів об'екта в форматі HTML тегів
     */
    public String getHTMLParams ()
    {
        String strParam = "";
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (!strKey.startsWith (isdb.miscs.dclrs.SPECIAL_SERVICE_PREFIX))                // поле не для внутр. викор-ня?
                strVal = (String) hashVals.get (strKey);
            strParam += isdb.ifaces.htmli.formhidepar (strKey, strVal);
        }
        return strParam;
    }

    /**
     * Очищення всіх не службових полів об'екта.
     */
    public void clearHTMLParams ()
    {
        String strKey = null;
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            if (!strKey.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX))           // службове призначення поля?
                hashVals.remove (strKey);
        }
    }

    /**
     * Встановлення додаткової кнопки навігації.
     * @param strButton додаткова кнопка навігації
     */
    public void setButton (String strButton)
    {
        hashButs.put (new Integer (iCntButtn).toString (), strButton);
        iCntButtn++;
    }

    /**
     * Встановлення кнопки допомоги для форми.
     * <P><B>Увага!</B>
     * <P>Не змінюйте значення об'ектів PAR_OBJECT, PAR_REGIME та PAR_DEPT,
     * тому що файли допомоги мають назву з використанням цих значеннь!
     * @param strDocName назва допомоги
     * @param oOutData поточні вихідні дані об'екта
     * @see #isdb.miscs.dclrs.PAR_DEPT
     * @see #isdb.miscs.dclrs.PAR_OBJECT
     * @see #isdb.miscs.dclrs.PAR_REGIME
     */
    public void setDocButton (String strDocName, outdata oOutData)
    {
        buttondata oDocButton = new buttondata ();
        if (String.valueOf (strDocName) == "null")             // стандартна допомога?
            strDocName = getVal (isdb.miscs.dclrs.PAR_OBJECT) + "_" +
                         getVal (isdb.miscs.dclrs.PAR_REGIME) + "_" +
                         getVal (isdb.miscs.dclrs.PAR_DEPT);
        oDocButton.setUrl (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" +
                           strDocName +
                           ".html", true, "'scrollbars=yes,toolbar=yes'");    // відкривати в вигляді нового вікна
        oDocButton.setName (isdb.miscs.dclrs.REGIME_HELP);
        oDocButton.setName (getTitleButton (isdb.miscs.dclrs.REGIME_HELP));
        setButton (oDocButton.getButton (oOutData));
    }

    /**
     * Встановлення кнопки допомоги для форми.
     * @param oOutData поточні вихідні дані об'екта
     * @see #setDocButton (String, outdata)
     */
    public void setDocButton (outdata oOutData)
    {
        setDocButton (null, oOutData);
    }

    /**
     * Повернення кнопок керування.
     * @return кнопки керування в форматі форм HTTP
     */
    public String getButtons ()
    {
        String strButtons = "";
        Enumeration enumButs = hashButs.keys ();
        while (enumButs.hasMoreElements ())
        {
            strButtons += isdb.ifaces.htmli.cell (
                            ((String) hashButs.get ((String) enumButs.nextElement ())), 100);
        }
        return strButtons;
    }

    /**
     * Клонування об'екта.
     * @return клоновані дані об'екта
     */
    public dbdata cloneData ()
    {
        String strKey = null;
        dbdata oNewDBData = new dbdata (this.strSsnId);
        Enumeration enumPars = hashVals.keys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            oNewDBData.setVal (strKey, hashVals.get (strKey));
        }
        return oNewDBData;
    }

    /**
     * Повернення назви кнопки об'екта в залежності від встановленних режимів.
     * @param strCurrRegime поточний режим
     * @param strNextRegime наступний режим
     * @return назва кнопки об'екта
     */
    public String getTitleButton (String strCurrRegime, String strNextRegime)
    {
        if (String.valueOf (strCurrRegime) == "null")
            strCurrRegime = getVal (isdb.miscs.dclrs.PAR_REGIME);
        if (String.valueOf (strNextRegime) == "null")
            strNextRegime = getVal (isdb.miscs.dclrs.PAR_NEXTREGIME);
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_LOGIN))
            return isdb.miscs.dclrs.TITLE_REG_LOGIN;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_UPLOAD))
            return isdb.miscs.dclrs.TITLE_REG_UPLOAD;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_RETURN))
            return isdb.miscs.dclrs.TITLE_REG_RETURN;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_PRINT))
            return isdb.miscs.dclrs.TITLE_REG_PRINT;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_HELP))
            return isdb.miscs.dclrs.TITLE_REG_HELP;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_CONTINUE))
            return isdb.miscs.dclrs.TITLE_REG_CONTINUE;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_INSCOMMIT) ||
                strCurrRegime.equals (isdb.miscs.dclrs.REGIME_UPDCOMMIT))
            return isdb.miscs.dclrs.TITLE_REG_SAVE;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_INSERT))
            return isdb.miscs.dclrs.TITLE_REG_SELECT4INSERT;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_MAINT) ||
                strCurrRegime.equals (isdb.miscs.dclrs.REGIME_UPDATE))
            return isdb.miscs.dclrs.TITLE_REG_SELECT4UPDATE;
        if (strCurrRegime.equals (isdb.miscs.dclrs.REGIME_QUERY) ||
                strCurrRegime.equals (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                strCurrRegime.equals (isdb.miscs.dclrs.REGIME_RETRIEVE))
        {
            if (String.valueOf (strNextRegime) != "null")
            {
                if (strNextRegime.equals (isdb.miscs.dclrs.REGIME_INSERT))          // наступний режим - введення нових даних?
                    return isdb.miscs.dclrs.TITLE_REG_SELECT4INSERT;
                if (strNextRegime.equals (isdb.miscs.dclrs.REGIME_UPDATE) ||          // наступний режим - оновлення?
                        strNextRegime.equals (isdb.miscs.dclrs.REGIME_MAINT))
                    return isdb.miscs.dclrs.TITLE_REG_SELECT4UPDATE;
            }
            return isdb.miscs.dclrs.TITLE_REG_SELECT4REVIEW;
        }
        return "???";
    }

    /**
     * Повернення назви кнопки об'екта в залежності від встановленних режимів.
     * @param strCurrRegime поточний режим
     * @return назва кнопки об'екта
     * @see #getTitleButton (String, String)
     */
    public String getTitleButton (String strCurrRegime)
    {
        return getTitleButton (strCurrRegime, null);
    }

    /**
     * Повернення назви кнопки об'екта в залежності від встановленних режимів.
     * @return назва кнопки об'екта
     * @see #getTitleButton (String, String)
     */
    public String getTitleButton ()
    {
        return getTitleButton (null, null);
    }

    /**
     * Повернення подтвердження, чи потрібно зберегати дані об'екта.
     * @param oPoolData пул зберегаемих, значеннь використовуемих в транзакціях об'ектів
     * @return true - потрібно зберегати дані об'екта
     */
    public boolean isRequiredWrite (pooldata oPoolData)
    {
        if (String.valueOf (oPoolData.getVal (getSession (), (getVal (isdb.miscs.dclrs.PAR_OBJECT) + "_ID").toUpperCase ())) == "null")
            return true;
        else
            return false;
    }

    /**
     * Встановлення признака обробленого значення для загрузки стека.
     */
    public void setPrepared ()
    {
        setVal (PREPARED, isdb.miscs.dclrs.YES);
    }

    /**
     * Перевірка встановлення признака обробленого значення для загрузки стека.
     * @return true - встановлен признак обробленого значення
     */
    public boolean isPrepared ()
    {
        if (String.valueOf (getVal (PREPARED)) == "null")
            return false;
        else
            return true;
    }

    /**
     * Встановлення нових SQL даних об'екта.
     * @param oSQLData нові SQL дані
     * @see #getSQLData ()
     */
    public void setSQLData (sqldata oSQLData)
    {
        setVal (DATA_SQL, oSQLData);
    }

    /**
     * Повернення SQL даних, які існують в поточних даних об'екта.
     * @return SQL дані
     * @see #setSQLData (sqldata)
     */
    public sqldata getSQLData ()
    {
        return (sqldata) hashVals.get (DATA_SQL);
    }

    /**
     * Перевірка номеру поточної фази транзакції.
     * @param iTrnPhase номер перевіряемої фази транзакції
     * @return true - якщо номер перевіряемої фази транзакції совпадае з поточною
     */
    public boolean isTrnPhase (int iTrnPhase)
    {
        return (iTrnPhase == this.iTrnPhase);
    }

    /**
     * Встановлення слідуючої фази транзакції.
     */
    public void setNextTrnPhase ()
    {
        ++this.iTrnPhase;
    }

    /**
     * Відображення об'екта в текстовій формі.
     * @return параметри об'ект в текстовій формі
     */
    public String toString ()
    {
        String strTmp = "\ndbdata:\n";
        Enumeration enumVals = hashVals.keys ();
        while (enumVals.hasMoreElements ())
        {
            String strKey = (String) enumVals.nextElement ();
            if (strKey.startsWith (isdb.miscs.dclrs.SPECIAL_SERVICE_PREFIX))
                strTmp += strKey + "=<???>\n";
            else
                strTmp += strKey + "=" + (String) hashVals.get (strKey) + "\n";
        }
        strTmp += "mode: " + getModeObj () + "\nresult: " + iResult + "\ncrit:" + this.strCriteria +"\n";;
        if (iCntButtn > 0)
            strTmp += "\nbuttons: " + hashButs.toString ();
        strTmp += "\nTrn Phase=" + (new Integer (iTrnPhase)).toString () + "\n";
        return strTmp;
    }
}

