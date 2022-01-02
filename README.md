Integrated Subscribers DataBase (ISDB)
======================================

This software has been developed by myself @ 2000 (at that sweet time it was used a manually compiled [Kaffe Virtual Machine](https://github.com/kaffe/kaffe) because no *Sun* *Java* for *Digital*'s *Alpha Server* yet!) and practically used @ *Zhitomir* affiliate of *Utel* company, *Ukraine*, during quite a long time.

Conversion for some *Java* source files has been executed as below:

```
find . -name "*.java" -exec ls {} + | awk '{ system("iconv --from-code WINDOWS-1251 --to-code utf-8 "$0" -o "$0".new && mv "$0".new "$0) }' | more
find . -exec dos2unix {} +
```
