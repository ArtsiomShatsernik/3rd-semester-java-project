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
  Расшиерния файлам ставятся сами, для дальнейшего их автоматического парсинга. Расширение .axx указывает на зашифрованный файл.
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
   * [x] Самостоятельно, с регулярными выражениями
   * [x] С использованием библиотеки "exp4j"
  </details>
  
5. [x] [UI реализация](https://github.com/ArtsiomShatsernik/3rd-semester-java-project/tree/master/src/main/java/UI)
