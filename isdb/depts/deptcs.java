/**
 * deptcs.java
 * ISDBj
 */

package isdb.depts;

import isdb.depts.deptobj;

/**
 * Клас відділу Інтернет забеспечення.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class deptcs extends deptobj
{
    /**
     * Конструктор.
     */
    public deptcs ()
    {
        super (isdb.miscs.dclrs.DEPT_CS);
        putLogo (179, 133);
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу
     */
    public String getName ()
    {
        return "Відділ Інтернет забеспечення";
    }
}

