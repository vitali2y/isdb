/**
 * stackdata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Stack;

/**
 * Глобальний стек збереження даних типа dbdata до наступних транзакцій.
 * <P>Використовуеться для збереження даних попередніх операцій
 * для кожного користувача окремо в глобальний структурі типу FIFO.
 * @version 1.0 fianl, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class stackdata
{
    /** Глобальний стек збереження даних */
    private static Hashtable hashStackData = new Hashtable ();

    /** Встановлен чі ні режим тестування? (по замовчанню: ні = false) */
    private static final boolean MODE_DEBUG = false;

    /**
     * Занесення нового набора даних в стек даних.
     * @param oDBData дані об'екта для занесення в стек
     */
    public static void pushData (dbdata oDBData)
    {
        String strSsnId = oDBData.getSession ();
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception enew = new Exception ("pushData:\n" +  oDBData.toString ());
            enew.printStackTrace ();
        }
        Stack stackData = (Stack) hashStackData.get (strSsnId);
        if (stackData == null)    // ще не був створен стек даних для ціеї сесії?
            stackData = new Stack ();
        stackData.push (oDBData);
        hashStackData.put (strSsnId, stackData);
    }

    /**
     * Повернення самого останнього набору даних.
     * @param oDBData поточни дані об'екта
     * @return набір даних об'екта, який був збережен останнім
     */
    public static dbdata popData (dbdata oDBData)
    {
        String strSsnId = oDBData.getSession ();
        Stack stackData = (Stack) hashStackData.get (strSsnId);
        dbdata oLastDBData = (dbdata) stackData.pop ();
        hashStackData.put (strSsnId, stackData);
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception enew = new Exception ("popData:\n" +  oDBData.toString ());
            enew.printStackTrace ();
        }
        return oLastDBData;
    }

    /**
     * Перевірка заповненості стека даних.
     * @param oDBData поточни дані об'екта
     * @return true - якщо стек пустий
     */
    public static boolean isEmpty (dbdata oDBData)
    {
        return ((Stack) hashStackData.get (oDBData.getSession ())).empty ();
    }

    /**
     * Примусове переформування значеннь стека даних в залежності від східних параметрів.
     * <OL>
     * <LI>Загрузка стека даних об'ектами, яки потрібно обробляти окремо;
     * <LI>Загрузка пустих полей з стеку, якщо вони існують.
     * </OL>
     * @param oDBData поточні дані об'екта
     * @param oPoolData пул значеннь об'ектів
     */
    public static void prepareData (dbdata oDBData, pooldata oPoolData)
    {
        if (isEmpty (oDBData))     // чи є дані для транзакції в стеке?
            return;
        String strKey = null;
        String strVal = null;
        String strTmp = null;
        boolean bRequiredPush = false;
        oDBData = popData (oDBData);
        String strSsnId = oDBData.getSession ();

        Enumeration enumPars = oDBData.getKeys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strVal = oDBData.getVal (strKey);
            if ((strKey.startsWith (isdb.miscs.dclrs.PAR_UPDATE_RECORD) ||
                    strKey.startsWith (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST)) &&           // потрібно переходити на другі форми?
                    (!((String.valueOf (strVal) == "null") ||
                       strVal.equals (""))))
            {
                bRequiredPush = true;
                strTmp = (strKey.substring (5) + "_ID").toUpperCase ();
                if ((String.valueOf (oPoolData.getVal (strSsnId, strTmp)) == "null") || // значення в пулі даних не існуе для цього поля?
                        ((String.valueOf (oPoolData.getVal (strSsnId, strTmp)) != "null") &&
                         oPoolData.containsKey (strSsnId, strKey)))
                {
                    // раніше не було оброблено?
                    if (!oDBData.isPrepared ())
                    {
                        // створення нового об'екта
                        dbdata oNewDBData = new dbdata (oDBData.getSession ());

                        // встановлення признака обробленого значення стека
                        oNewDBData.setPrepared ();

                        // підготовка додаткових параметрів
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));

                        oNewDBData.setVal (strKey, strVal);
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                        oNewDBData.setSession (strSsnId);

                        // перенесення признака друку в новий об'ект
                        String strPrint = null;
                        Enumeration enumTmpPars = oDBData.getKeys ();
                        while (enumTmpPars.hasMoreElements ())
                        {
                            strPrint = (String) enumTmpPars.nextElement ();
                            if (strPrint.startsWith (isdb.miscs.dclrs.PAR_PRINT_REPORT))        // потрібно друкувати звіт?
                                oNewDBData.setVal (strPrint, oDBData.getVal (strPrint));
                        }
                        oNewDBData.setVal (isdb.miscs.dclrs.PAR_TRANSFER_PARAM, oDBData.getVal (isdb.miscs.dclrs.PAR_TRANSFER_PARAM));

                        // загрузка стека тільки що сформованим значенням
                        pushData (oNewDBData);
                        oDBData.removeVal (strKey);

                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enew2 = new Exception ("prepareData (push):\n" + strKey + "="+strVal+
                                                             "\npool: " + oPoolData.getVal (strSsnId, (strKey.substring (5) + "_ID").toUpperCase ()) +
                                                             "\n"+oDBData.toString () +
                                                             "\nnew: "+oNewDBData.toString ());
                            enew2.printStackTrace ();
                        }
                    }
                }
                else            // вже існуе?
                {
                    if (MODE_DEBUG)                // тестувал. режим?
                    {
                        Exception enew3 = new Exception ("before pop:\n(" + oDBData.toString () + "\npool=" +oPoolData.getVal (strSsnId, (strKey.substring (5) + "_ID").toUpperCase ()) + "\nkey="+strKey+
                                                         "\ncontains="+oPoolData.containsKey (strSsnId, strKey)+")");
                        enew3.printStackTrace ();
                    }

                    // ... тоді не потрібно ще раз обробляти
                    oDBData = popData (oDBData);
                    if (MODE_DEBUG)                // тестувал. режим?
                    {
                        Exception enew = new Exception ("prepareData (pop):\n" + strKey + "="+strVal+"\n"+oDBData.toString ());
                        enew.printStackTrace ();
                    }
                }
            }
            else
            {
                // заповнення пустих даних
                if (((String.valueOf (strVal) == "null") ||
                        strVal.equals (isdb.miscs.dclrs.OBJ_NULL)) &&
                        strKey.endsWith ("_ID"))     // ссилочне поле незаповненно?
                {
                    strVal = oPoolData.getVal (strSsnId, strKey);
                    if (String.valueOf (strVal) != "null")  // в стеке вже е введені дани?
                        oDBData.setVal (strKey, strVal);
                }
                else
                {
                    // стандартні записи обробляються по стандартному
                    if (strKey.equals (isdb.miscs.dclrs.PAR_OBJECT))
                        bRequiredPush = true;
                }
            }
        }
        if (bRequiredPush)       // потрібно повернути в стек?
            pushData (oDBData);  // загружаються знов тільки стандартні записи та нові об'екти, які ще не існують
    }
}

