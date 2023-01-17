# 3rd-semester-java-project
## Выполненная работа:
1. [x] [Создание файлов разных типов с поддержкой вложенности](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/FileActions/FileFormer.java)

<details>
    <summary>Подробнее</summary>

  Класс FileFormer может делать с заданным .txt файлом следущее:
  * Перевести в один из поддерживаемых типов файлов (.json, .xml)
  * Заархивировать его (.jar, zip)
  * Зашифровать по заранее заданному ключу или использя стандартный ключ
  * Делать приведённые выше операции последовательно. Максимальная допустимая вложенность, в данной реализации, это
  один тип файла, две операции архивации или шифрования или одно шифрование и одна архивация в любом порядке.
  
  Создание файлов с вложенностью требует фактического создания каждой версии файла для проведения дальнейших операций над ним. Для этого создаётся временная папка в `temp` директории операционной системы, куда отправляются временные файлы.
  
  <details>
    <summary>Ссылки на код</summary>
    
   #### Типы файлов
   * [x] [.json](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/JsonLib.java)
   * [x] [.xml](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/XmlLib.java)
   * [x] [.txt](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/TxtLib.java)
   #### Типы архивации
   * [x] [.jar](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/ArchivingLib.java)
   * [x] [.zip](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/ArchivingLib.java)
   #### Типы шифрования
   * [x] [DES/ECB/PKCS5Padding](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/tools/CryptoLib.java)
  </details>
  
  Пример:
  
  Входной файл:
  ```
  input.txt
  ```
  Примеры выходных файлов: 
  ```
  input.txt.jar.zip, input.txt.axx.axx, input.xml.zip, input.json.jar.zip
  ```
  Расширения файлам ставятся сами, для дальнейшего их автоматического парсинга. Расширение .axx указывает на зашифрованный файл.
  </details>
  
2. [x] [Парсинг файлов разных типов с поддержкой вложенности](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/FileActions/FileParser.java)
<details>
    <summary>Подробнее</summary>
  
  Класс FileParser может делать с заданным файлом, созданным с помощью FileFormer, следущее:
  * Построчно переводит .txt, .json, .xml файлы в ArrayList<String>
  * Разархивировать файл
  * Расшифровать файл по заранее заданному ключу
  * Делать эти операции последоватлеьно, если задать верный порядок при создании
  * Способен автоматически определять порядок и парсить файлы, созданные в FileFormer, основываясь на их названии (для этого и нужно добавление в название файлов расширений)
  
И FileFormer и FileParser наследуются от одного класса [FileAction](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/FileActions/FileAction.java) с целью уменьшения кол-ва повторений в коде. 
</details>

3. [x] [Создан билдер для классов создания и парсинга файлов](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/builders/FileActionsBuilder.java)
  
  <details>
    <summary>Подробнее</summary>
    
   В классе FileActionsBuilder можно создать как FileFormer, так и FileParser методами `buildFormer()`, `buildParser()`.
   Последовательно вызывая методы: `setArchivingType(archivingType)`, `setEncryptionType(encryptionType)` можно задавать      последовательность действий для создаваемых объектов.
    
  </details>
    
4. [x] [Подсчёт арифметических выражений](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/blob/master/src/main/java/org/main/Expression.java)

 <details>
    <summary>Подробнее</summary>
    
   #### Парсинг выражений
  
   * Самостоятельно, с регулярными выражениями
   * С использованием библиотеки "exp4j"
   
   Самостоятельный подсчёт поддерживает операции `+ - * /` с любой вложенностью в скобки, при правильном их расставлении,
   поддерживает как отрицательные, так и дробные числа.
   
   Краткое описание алгоритма, используемого при самостоятельном подсчёте выражений:
   1) Проверяется корректность скобок в выражении, при некорректной расстановке выбрасывается исключение
   2) Полученное выражение упрощается, убираются ненужные для подсчёта скобки
   3) В цикле выражение разбивается на подвыражения на основании расставленных скобок, эти подвыражения переписываются в постфиксную нотацию методом `toRPN()`, далее полученная постфиксная нотация подсчитывается в методе `computeRPN()` простой [Стековой машиной](https://ru.wikipedia.org/wiki/Обратная_польская_запись), всё подвыражение в первоначальном выражении заменяется на своё значение и идёт дальнейший ход цикла
   4) Цикл заканчивается, когда в выражении больше нет подвыражений. Далее происходит подсчёт самого выражения вышеописанным способом
   5) Результаты передаются в виде строки
   
   Также есть метод `computeWithExtension()`, который считает выражения с помощью готовой библиотеки "exp4j"
   Для подсчёта большого числа математических выражений создан класс MathExpressions, который может считать выражения, переданные в виде ArrayList или в виде файлов, созданных в FileBuilder, используя FileParser с автоматической расстановкой действий. Он может выводить результаты в виде ArrayList или сразу записывать из в .txt файл. При получении исключения во время подсчёта очередного выражения он запишет в результат текст ошибки.
   
   <details>
      <summary>Известные недостатки</summary>
      
     Если вызывать подсчёт следующего выражения `1 + aaa 2 ` ,и ему подобных, то выведется ответ `3`, 
     а не сообщение об неверном вводе
     
   </details>
   
</details>
  
5. [x] [UI реализация](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/tree/master/src/main/java/UI)

<details>
   <summary>Подробнее</summary>
   
   
 UI создан с помощью JavaFx, состоит из основного меню и двух окон: Build File и Compute.
    
 ![image](https://user-images.githubusercontent.com/115698928/212960782-eebc144c-cb5d-4b5f-ab24-4728c865062c.png)
 
 Окно Build File описывает весь функционал класса FileFormer, поддерживает ввод как из .txt файла, так и прямой ввод текста прямо в окне, можно задавать последовательность действий при создании файлов, и задавать ключ шифрования для файлов, присутствует обработка большинства ошибок ввода c дальнейшим продолжением работы программы.
 
![image](https://user-images.githubusercontent.com/115698928/213023091-7c714585-8764-4c3f-976c-664c1f56ae7d.png)

 Окно Compute отвечает за подсчёт арифметических выражений, доступен ввод как из файлов созданных в FileFormer (и в окне Build File соответственно), так и прямой ввод выражений, так же как и в предыщушим окне есть обработка ошибок.
 
 ![image](https://user-images.githubusercontent.com/115698928/212957339-14487753-9dae-42a4-995a-5bb00c9c7e96.png)

<details>
      <summary>Известные недостатки</summary>
      
     Возможны неожиданные результаты, если забыть расставить запятые.
     
     Возможны неожиданные результаты из-за проблем описанных в недостатках описанных в пункте 4.
     
     Нет автоматического расставления пробелов в окне вывода результатов.
     
   </details>
   
</details>

</details>

