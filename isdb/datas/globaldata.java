/**
 * globaldata.java
 * ISDBj
 */

package isdb.isdb;

import java.util.Hashtable;

/**
 * Глобальне сховище даних користувачів.
 * <P><B>Увага!</B><BR>Використання сховища можливо
 * ТІЛЬКИ з використанням номеру сесії користувача!
 * @version 1.0 final, 5-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class globaldata
{
    /** Хеш-таблиця для збереження */
    private static Hashtable hashGlobalData = null;

    /**
     * Конструктор
     */
    public globaldata ()
    {
        hashGlobalData = new Hashtable ();
    }

    /**
     * Збереження значення в глобальному сховище даних.
     * @param strSesnId номер сесії користувача
     * @param oKey назва зберегаемого поля
     * @param oValue значення зберегаемого поля
     */
    public void setVal (String strSesnId, Object oKey, Object oValue)
    {
        if ((oKey == null) ||      // нема чого заносити?
                (oValue == null))
            return;
        Hashtable hashLocalStorage = (Hashtable) hashGlobalData.get (strSesnId);
        if (hashLocalStorage == null)            // первинне звернення?
            hashLocalStorage = new Hashtable ();
        hashLocalStorage.put (oKey, oValue);
        hashGlobalData.put (strSesnId, hashLocalStorage);
    }

    /**
     * Повернення значення з глобального сховища даних.
     * @param strSesnId номер сесії
     * @param oKey назва зберегаемого поля
     * @return значення поля
     */
    public String getVal (String strSesnId, Object oKey)
    {
        Hashtable hashLocalStorage = (Hashtable) hashGlobalData.get (strSesnId);
        if (hashLocalStorage == null)
            return null;
        else
            return (String) hashLocalStorage.get (oKey);
    }

    /**
     * Перевірка присутності в глобальному сховище даних.
     * @param strSesnId номер сесії
     * @param oKey назва перевіряемого поля
     * @return true - якщо значення об'екта присутнє в пулі
     */
    public boolean containsKey (String strSesnId, Object oKey)
    {
        Hashtable hashLocalStorage = (Hashtable) hashGlobalData.get (strSesnId);
        if (hashLocalStorage == null)
            return false;
        else
            return hashLocalStorage.containsKey (oKey);
    }

    /**
     * Вилучення значення в глобального сховища даних.
     * @param strSesnId номер сесії
     * @param oKey назва вилучаемого поля
     */
    public void removeVal (String strSesnId, Object oKey)
    {
        ((Hashtable) hashGlobalData.get (strSesnId)).remove (oKey);
    }

    /**
     * Очищення глобального сховища даних користувача.
     * @param strSesnId номер сесії користувача
     */
    public void clear (String strSesnId)
    {
        hashGlobalData.remove (strSesnId);
    }
}

