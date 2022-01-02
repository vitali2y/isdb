/**
 * loaddata.java
 * ISDBj
 */

package isdb.datas;

import java.io.StreamTokenizer;
import isdb.excs.*;

/**
 * Клас для обробки вхідних необроблених даних
 * для послідуючої загрузки в БД.
 * <P>Цей клас використовується для конвертовання
 * вхідних необроблених даних (ROP звітів, QUDB таблиць, тощо)
 * для подальшої загрузки ціх даних (за допомогою метода writeData) в БД.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class loaddata
{

    /** Поточний потік вхідної інформації */
    private StreamTokenizer stokInTokStream;
    /** Початковий признак пошуку */
    private String strStartToken;

    /**
     * Конструктор.
     * @param stokInTokStream
     */
    public loaddata (StreamTokenizer stokInTokStream)
    {
        this.stokInTokStream = stokInTokStream;
    }

    /**
     * Встановлення ознаки обробки EOL, як токена.
     */
    public void setCheckEOL ()
    {

        // EOL обробляються як токени
        this.stokInTokStream.eolIsSignificant (true);
    }

    /**
     * Встановлення початкового признака пошуку.
     * @param strStartToken початковий признак пошуку
     */
    public void setStartToken (String strStartToken)
    {
        this.strStartToken = strStartToken;
    }

    /**
     * Пошук потрібних значеннь в вхідних даних.
     * @param strFindPattern1 перше шукаеме значення
     * @param strFindPattern2 друге шукаеме значення
     */
    public void findPattern (String strFindPattern1, String strFindPattern2)
    throws isdbloadexception
    {
        int iTokType = 0;
        while (true)
        {
            iTokType = this.stokInTokStream.ttype;
            switch (iTokType)
            {
            case this.stokInTokStream.TT_EOF:
                throw new isdbloadexception ();
            case this.stokInTokStream.TT_WORD:
                if (this.stokInTokStream.sval.equals (this.strStartToken))	// знов ознака початку звіту?
                    throw new isdbloadexception ();
                if (this.stokInTokStream.sval.equals (strFindPattern1))	// перше значення знайдене?
                {
                    if (String.valueOf (strFindPattern2) != "null")
                    {
                        if (this.stokInTokStream.sval.equals (strFindPattern1))	// друге значення знайдене?


                            ;
                    }
                    else


                        ;
                }
                break;
                // case this.stokInTokStream.:
                // break;

            default:


                break;
            }
        }
    }

    /**
     * Пошук потрібних значеннь в вхідних даних.
     * @param strFindPattern1 перше шукаеме значення
     * @see #findPattern (String, String)
     */
    public void findPattern (String strFindPattern1)
    throws isdbloadexception
    {
        findPattern (strFindPattern1, null);
    }
}

