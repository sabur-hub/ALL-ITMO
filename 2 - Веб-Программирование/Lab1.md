# Вопросы с se.ifmo

## 1. Протокол HTTP. Структура запросов и ответов, методы запросов, коды ответов сервера, заголовки запросов и ответов.

HTTP (англ. HyperText Transfer Protocol — «протокол передачи гипертекста») — протокол прикладного уровня передачи данных (изначально — в виде гипертекстовых документов в формате «HTML», в настоящий момент используется для передачи произвольных данных). Основой HTTP является технология «клиент-сервер», то есть предполагается существование:
  - Потребителей (клиентов), которые инициируют соединение и посылают запрос;
  - Поставщиков (серверов), которые ожидают соединения для получения запроса, производят необходимые действия и возвращают обратно сообщение с результатом.

HTTP запрос состоит из трех основных частей, которые идут в нем именно в том порядке, который указан ниже. Между заголовками и телом сообщения находится пустая строка (в качестве разделителя), она представляет собой символ перевода строки.
1. строка запроса (Request Line)
2. заголовки (Message Headers)
3. пустая строка (разделитель)
4. тело сообщения (Entity Body) – необязательный параметр

Строка запроса – указывает метод передачи, URL-адрес, к которому нужно обратиться и версию протокола HTTP.
Заголовки – описывают тело сообщений, передают различные параметры и др. сведения и информацию.
Тело сообщения  — это сами данные, которые передаются в запросе.  Тело сообщения – это необязательный параметр и может отсутствовать.

### Методы запросов:
- `GET` запрашивает представление ресурса, может только извлекать данные.
- `HEAD` как GET, только без тела ответа.
- `POST` используется для отправк сущностей определенному ресурсу. Может изменять данные.
- `PUT` создает новый ресурс или заменят предсавление целевого ресурса (в отличае от POST для идентичных наборов двнных будет иметь одинаковый результат).
- `DELETE` удаляет ресурм.
- `CONNECT` устанавливает "туннель" к серверу, определенному по ресурсу.
- `OPTIONS` для описания параметров соединения с ресурсом
- `TRACE` вызов возвращаемого текстового сообщения
- `PATCH` частичное изменение ресурса

### Коды ответов:
1. 1хх: Informational
2. 2xx: Success
3. 3xx: Redirection (перенаправление)
4. 4xx: Client Error
5. 5xx: Server Error 

## 2. Язык разметки HTML. Особенности, основные теги и атрибуты тегов.

Документ состоит из элементов, начало и конец которых обозначаются _тегами_. Некоторые теги не могут содержать текст
(например, `<br>` — перенос строки, `<img>` — картинка, `<input>` — элемент ввода в форме). Их не нужно закрывать:
```html
<!-- плохо -->
<input type="..."></input>
<!-- хорошо -->
<input type="...">
```

В HTML5 введены _семантические_ теги `<header>`, `<footer>`, `<section>`, которые аналогичны `<div>`, но указывают
на логическую структуру.

## 3. Структура HTML-страницы. Объектная модель документа (DOM).

DOM -  программный интерфейс для HTML и XML документов, описывающийструктурированное представление документа и определяющий, как это структура может быть доступна из программ, которые могут изменять ее содержимое. (Другими словами, DOM соединяет веб-страницу с языками описания сценариев).
  
Согласно DOM документ может быть представлен в виде объектов, с которыми можно проихводить манипуляци:

- генерация и добавление узлов
- получение узлов
- изменение узлов
- изменение связей между ними
- удаление узлов

Обращение к узлам происходит с помощью элементов `document` или `window`.

Есть еще BOM - объектная модель браузера. Основное предназначение — управление окнами браузера и обеспечение их взаимодействия. BOM специфична для каждого браузера. Может в создание системных диалогов, управление информацией о параметрах монитора и браузера и всякое такое.
  
## 4. HTML-формы. Задание метода HTTP-запроса. Правила размещения форм на страницах, виды полей ввода.

Форма предназначена для обмена данными между пользователем и сервером. 

Задается с помощью тега `<form>` и могут содержать в себе атрибуты: `action`, содержищий URI обработчика формы (обязательный атрибут), `method` (по умолчанию, GET), `enctype` (тип кодирования), `accept` (MIME-типы для загрузки файлов), `name`, `onsubmit/onreset` (обработчик события submit/reset для скриптов), `accept-character`.

Виды полей:
- Кнопка `<input>`. Типы кнопок `<submit>`, `<image>`, `<reset>`, `<button>`.
- Checkbox.
- Radio.
- Select.
- Text и многострочный textarea.
- Password.
- Hidden (скрытое поле).
- File.

## 5. Каскадные таблицы стилей (CSS). Структура - правила, селекторы. Виды селекторов, особенности их применения. Приоритеты правил. Преимущества CSS перед непосредственным заданием стилей через атрибуты тегов.

CSS - формальный язык описания внешнего вида документа с помощью языка разметки. 

CSS-правило - блок, состоящий из селектора и блока объявления стилей. 

Селекторы - имя тега, к которому применяется правило. 

### Основные виды селекторов:
- `*` - любые элементы
- `div` элементы с тегом div
- `#id` - элемент по id
- `.class` - элементы с классом class
- `[name="value"]` - селекторы по атрибуту
- `:visited` - псевдоклассы
- `div p` - элементы p, являющиеся потомками div
- `div > p` – только непосредственные потомки
- `div ~ p` – правые соседи: все p на том же уровне вложенности, которые идут после div
- `div + p` – первый правый сосед: p на том же уровне вложенности, который идёт сразу после div

### Приоритеты правил.

1. Самый высокий приоритет имеет атрибут style.
2. Второе по приоритету - присутствие ID в селекторе.
3. Все атрибуты (включая class и псевдоклассы)
4. Самый низкий - селекторы с именами элементов и псевдоэлементами.

`!important` позволяет повысить приоритет стиля.

## 6. LESS, Sass, SCSS. Ключевые особенности, сравнительные характеристики. Совместимость с браузерами, трансляция в "обычный" CSS.

### LESS

LESS - это динамический язык стилей, который является надстройкой над CSS (Поэтому любой CSS код будет валидный LESS). 

Приемущества LESS:
- Переменные (и области видимости переменных).
- Операции (в том числе и для управления цветом, т.е можно смешивать цвета: #941f1f + #222222).
- И другие функции для работы с цветом (осветление, затемнение и т.п.)
- Вложенность (можно вложить одно правило в другое, `article.post p {}` <=> `article.post { p{   }}`).
- Объединение аргуметов.

LESS-файл конвертируется в CSS при помощи js (для этого необходимо скачать less.js с сайта LESS).

```html
<script src="less.js" type="text/javascript"></script>
```

Затем можно привязывать файлы с расширением `.less`.

```html
<link rel="stylesheet/less" type="text/css" href="style.less">
```

### Sass

Sass - это метаязык на основе CSS, предназначенный для увеличения уровня абстракции CSS кода и упрощения файлов каскадных таблиц стилей. 

Приемущества Sass:
- Вложенные правила.
- Переменные.
- Возможность создавать миксины, позволяющие создавать многоразовые CSS-правила - группы деклараций, для многократного использования. *(LESS в это не может)*
- Расширения. Одиночный селектор может быть расширен больше, чес одним селектором с помошью `@extend`.
- Есть логика. (if/then/for). *(Этого в LESS тоже нет)*

*Не может компилироватся на сервере в CSS (LESS использует js).* 

Браузер не распознает файлы Sass, так что сначала их нужно скомпилировать в обычный CSS.

### SCSS

SCSS — "диалект" языка SASS. Отличие SCSS от SASS заключается в том, что SCSS больше похож на обычный CSS код. 

- `@import` - @import "template" подключит template.scss.
- Вложенность.
- $переменные.
- Математика чисел и цветов.
- Строки (умеет складвать строки, поддерживает конструкцию `#{$var}`)

## 7. Клиентские сценарии. Особенности, сферы применения. Язык JavaScript.

Сценарий - код, включенный в состав веб-страницы. Клиентский сценарий выполняется на компьютере-клиенте, для этого необходим встроенный интерпретатор. Вставка сценария в веб-страницу происходит при помощи тега `<script>`.

JavaScript является объектно-ориентированным языком. JavaScript имеет ряд свойств, присущих функциональным языкам — функции как объекты первого класса, объекты как списки, карринг, анонимные функции, замыкания. Js имеет автоматическое приведение типов, автоматическая сборка мусора, анонимные функции, функции как объекты первого класса (т.е. мб сохранены в переменную, переданны в функцию как аргумент, созданы во время выполнения программы и т.п.)

Основные архитектурные черты:
- динамическая типизация;
- слабая типизация;
- автоматическое управление памятью;
- прототипное программирование;
- функции как объекты первого класса.

### Типы данных 

- `number` -- целые, дробные числа, Infinity, NaN
- `String`
- `boolean`
- `null`
- `undefined` -- «значение не присвоено»
- `object` -- для коллекций и более сложных сущностей

## 8. Версии ECMAScript, новые возможности ES6 и ES7.

ECMAScript — это встраиваемый расширяемый не имеющий средств ввода-вывода язык программирования, используемый в качестве основы для построения других скриптовых языков. Стандартизирован международной организацией ECMA в спецификации. (ECMAScript это стандарт, а JavaScript его реализация). 

Имеет 5 примитивных типов данных — Number, String, Boolean, Null и Undefined; Объектный тип данных — Object и 15 различных видов инструкций.

В особенности можно добавить то, что блок не ограничивает область видимости функции. Если переменная объявляется вне функции, то она
попадает в глобальную область видимости. Функция — это тоже объект.

### ES6

Это обновление добавило новый синтаксис для написания классов и модулей, добавились итераторы и циклы for/of, Python-style генераторы, двоичные данные, лямбда-выражения, типизированные массивы, коллекции, обещания (promises), рефлексию и прокси, усовершенствовали числа и математику. Добавлено ключевое слово let (которое помогает объявить переменной область видимости - блок) и const.

### ES7

Добавлена операция возведения в степень (**), Array.prototype.includes().

## 9. Синхронная и асинхронная обработка HTTP-запросов. AJAX.

Синхронный запрос - запрос с ожидением ответа. (скрипт послал запрос (объект) на сервер и ждет ответ).

Асинхронный запрос - запрос без ожидания ответа от сервера. (скрипт послал запрос (объект) на сервер, но продолжает выполняться, когда данные вернутся вступает в дело событие onreadystatechenge. Сам объект меняет это событие, когда у него меняется свойство readyState. Для события создается собственная функция, в которой проверяется свойство readyState. И как только оно становится равным "4" - это значит, что данные с сервера пришли. Теперь можно полученные данные обрабатывать).

```js
   var request = getXmlHttpRequest(); // создание объекта
   request.onreadystatechenge = function(){ // установка обработчика onreadystatechenge и проверка свойства readyState
       if(request.readyState == 4)
       ...
   }
   request.open('GET', url, true); // готовим запрос
   request.send(null); // посылаем запрос
```
### AJAX

Asynchronous Javascript and XML - подход к построению интерактивных пользовательских интерфейсов веб-приложений, заключающийся в «фоновом» обмене данными браузера с веб-сервером.

Пользователь что-то делает -> скрипт определяет с чем там надо работать -> браузер отправляет запрос на сервер -> сервер возвращает только то, от чего ожидаются изменения -> скрипт вносит изменения обратно (без перезагрузки страницы).

## 10. Библиотека jQuery. Назначение, основные API. Использование для реализации AJAX и работы с DOM.

jQuery - библиотека js, помогающая легко получить доступ к любому элементу DOM и манипулировать ими, предоставляет API для работы с AJAX.

jQuery включается в страницу как внешний файл.

```html 
<script src="jquery-2.2.2.min.js">
```

Вся работа с jQuery ведется с помощью функции $. Работу с jQuery можно разделить на 2 типа:

- Получение jQuery-объекта с помощью функции `$()`.
- Вызов глобальных методов у объекта $.

Типичный пример манипуляции сразу несколькими узлами DOM заключается в вызове $ функции со строкой селектора CSS, что возвращает объект jQuery, содержащий некоторое количество элементов HTML-страницы. Эти элементы затем обрабатываются методами jQuery.

```js
$("div.test").add("p.quote").addClass("blue").slideDown("slow");
//находит элементы div с классом test, все элементы p с классом quote, добавляет им класс blue, ...
```

$.ajax и соответствующие функции позволяют использовать методы AJAX

```js
$.ajax({
  type: "POST",
  url: "some.php",      // обращение к some.php
  data: {name: 'John', location: 'Boston'},   //с какими-то параметрами
  success: function(msg){
    alert( "Data Saved: " + msg );  // получный результат выводится в alert
  }
}); 
```

## 11. Реализация AJAX с помощью SuperAgent.

SuperAgent - API для реализации AJAX:

```js
request
  .post('/api/pet')
  .send({ name: 'Manny', species: 'cat' })
  .set('X-API-Key', 'foobar')
  .set('Accept', 'application/json')
  .end(function(err, res){
  if (err || !res.ok) {
    alert('Oh no! Error');
  } else {
    alert('yay got ' + JSON.stringify(res.body));
  }
});
```

## 12. Серверные сценарии. CGI - определение, назначение, ключевые особенности.

CGI (Common Gateway Interface — общий интерфейс шлюза) — стандарт интерфейса, используемого для связи внешней программы с веб-сервером. Программу, которая работает по такому интерфейсу совместно с веб-сервером, принято называть шлюзом (оно же скрипт или CGI-программа). По сути позволяет использовать консоль ввода и вывода для взаимодействия с клиентом.


CGI - сценарии
- CGI — механизм вызова пользователем
программ на стороне сервера.
- Данные отправляются программе посредством
HTTP-запроса, формируемого веб-браузером.
- То, какая именно программа будет вызвана,
обычно определяется URL запроса.
- Каждый запрос обрабатывается отдельным
процессом CGI-программы.
- Взаимодействие программы с веб-сервером
осуществляется через stdin и stdout.

## 13. FastCGI и CGI

CGI — устаревшая технология, позволяющая взаимодействовать веб-серверу с сервером приложений. Для каждого запроса запускается процесс с интерпретатором PHP, после возвращения ответа он завершается. Поскольку это очень неэффективно, был создан FastCGI, в котором процесс интерпретатора не завершается, а используется для последующих запросов.

В PHP возможна многопоточность через расширение `pthreads`, но оно недоступно при использовании PHP как сервера приложений, только при написании скриптов для командной строки.

## 14. Язык PHP - синтаксис, типы данных, встраивание в веб-страницы, правила обработки HTTP-запросов. Особенности реализации принципов ООП в PHP.

PHP - скриптовый язык общего назначения, интенсивно применяемый для разработки веб-приложений.

### типы данных

PHP - яп с динамической типизацией. Преобразования меджду скалярными типами происходят неявно. 

Скалярные типы: integer, float, double, boolean, string. 

Нескалярные: array, object, resource, null

Псевдотипы: mixed (любой тип), number, callback (string или анонимная фунуция), void.

### запрос

```php
$url = 'https://translate.yandex.ru';

$context = stream_context_create([
    'http' => [
        'method' => 'POST',
        'content' => http_build_query([
            'lang' => 'ru-en',
            'text' => 'Все получилось',
        ])
    ]
]);
```

stream_context_create() отвечает за создание контекста запросов различных протоколов, в том числе ftp, ssl, tcp

### ооп в php

```php
class ClassName {
  public $publicName;
  private $privateName;
  protected $protectedName;
  
  const CONST_VAL = 'val';
  
  public function getPrivateName() {
    return $this->$privateName; // $this -- ссылка на сам объект, $parent - на родительский.
  }
};

echo ClassName::CONST_VAL; // для обращения к константам

$classname = new ClassName();

$className->publicName; // доступ к переменной


```

 PHP поддерживает все три основных механизма ООП — инкапсуляцию, полиморфизм подтипов и наследование (с помощью extend). Поддерживаются интерфейсы (с помощью implements). Есть абстрактные и final методы и классы. Множественное наследование не поддерживается, но класс может реализовывть несколько интерфейсов или с помощью механизма особенностей (trait), который имеет средства для разрешения конфликтов.  
 
Методы:
`__construct()` -- конструктор
`__destruct()` -- для деинициализации объекта
`__get()`, `__set()`
`__sleep()`, `__wakeup()`
`__clone()`

# Вопросы к защите

## 1. CORS

Политика same-origin, которую браузеры распространяют на запросы из JS (`fetch()`), клиент не может запросить ресурсы с другого origin'а (origin = домен (example.com), протокол (http/https), порт (80)).

Cross-origin resource sharing позволяет запросить ресурсы (картинки, CSS, JS) не со своего origin'а. Сначала клиент отправляет HTTP OPTIONS запрос с заголовком `Origin: http://www.example.com`, где example.com — адрес сайта. Если сервер не позволяет получить ресурс, возвращается страница ошибки, иначе — заголовок `Access-Control-Allow-Origin: ...`, который может вместо `...` включать адрес запросившего сайта (`http://www.example.com`) или `*`.

Сервер может вернуть заголовок `Access-Control-Allow-Methods: GET, POST, ...`, в котором перечисляются возможные методы для доступа к ресурсу.

Сервер может вернуть заголовок `Access-Control-Allow-Credentials: true`, чтобы указать, что клиент может отправлять cookies вместе с запросом.

## 2. HTTPS

HTTPS позволяет общаться с сервером по зашифрованному каналу, что предотвращает утечку секретов (паролей) и атаки man-in-the-middle (злоумышленник меняет данные, которые передаются между клиентом и сервером).

Шифруется все, что передается в запросе (URL запроса, заголовки, тело). Сервер предоставляет сертификат, который подтверждает его identity (сертификат должен быть издан доверенным лицом — обычно компанией, ключ которой браузеры признают как доверенный). Таким образом, сервер подтверждает, что он является тем, за кого себя выдает — не фишинг-сайтом и не "человеком посредине".

## 3. ООП в JS

В JavaScript у объектов есть _прототипы_. Экземпляр объекта содержит ссылку на его прототип, который содержит ссылку на прототип прототипа — до Object.

Конструктор объявляется как функцией, `this` является в ней экземпляром объекта:

```js
function Person(name) {
  this.name = name;
  this.greeting = function() {
    alert('Hi! I\'m ' + this.name + '.');
  };
}
```

Функции прототипа объявляются отдельно:

```js
Person.prototype.fun = function() { ... }
```

### this

`this` в JavaScript зависит от того, как вызывается функция. Он может быть 

Вне функции, `this` — объект `window`. Внутри функции, он равен `undefined`, если не задан:

```js
function f(arg) { ... }
f(1); // this === undefined, arg === 1
f.call(2, 1); // this === 2, arg === 1
```

Метод `bind` создают новую функцию, переопределяя в ней `this`:

```js
function f() { ... }
f(); // this === undefined
var newF = f.bind(3);
newF(); // this === 3
```

При вызове функции у экземпляра объекта, `this` становится равен экземпляру.

### ES6

`class Foo {}` транслируется в `function Foo()`, т.е. функцию конструктора. При этом тело конструктора объявляется внутри класса:

```js
class Foo {
  constructor(arg1, arg2) {
    this.something = arg1 + arg2;
  }
}
```

Классы могут быть наследованы:

```js
class Foo extends Bar {
  constructor() {
    super(..); // возможно вызвать constructor у Bar
  }
}
```

## 4. CSS-анимация

Все современные браузеры поддерживают `CSS transitions` и `CSS animations`, которые позволяют реализовывать анимацию срадствами CSS.

`CSS transitions`: указываем некоторое свойство, которое будет анимироваться при помощи SCC-правил. Далее, при изменении этого свойства браузер сам будет обрабатывать анимацию.

  Есть 5 свойств, задающих анимацию:
1. `transition-property` - устанавливает свойство для эффекта перехода.
2. `transition-duration` - задает время, которое будет длиться анимация.
3. `transition-timing-function` - математическая функция, указывающая с какой скоростью в зависимости от времени меняется значение свойства.
4. `transition-delay` - время ожидания перед запуском эффекта.
5. `transitionend` - событие после выполнения SCC transition.

`CSS animations`: более сложные анимации можно сделать объединением простых при помощи CSS-правила `@keyframes`. В нем задается имя анимации и правила, по которым нужно анимировать. 
Потом анимацию можно подключить с помощью свойства `animation`.

## 5. Наследование в SCC. Что наследуется, что нет?

Наследуемые свойства: `border-collapse`, `border-spacing`, `caption-side`, `color`, `cursor`, `direction`, `font` и его свита (размер, стиль...),  `letter-spacing`, `line-height`, `list-style` (и его свита тоже), `pitch-range`, `pitch`, `text-indent`, `text-transform`, `white-space`, `widows`, `word-spacing`

Остальные свойства являются ненаследуемыми. 

С помощью `inherit` можно указать, чтобы элемент наследовал значение у своего предка. `initial` - запрещает наследование (и устанавливает исходное значение).

## 6. Веб-сервер и сервер приложений

Веб-сервер — сервер, принимающий HTTP-запросы от клиентов (обычно веб-браузеров), и выдающий им HTTP-ответы, вместе с HTML-страницей, изображениями, файлами и другими данными.

Сервер приложений — программная платформа (фреймворк), предназначенная для эффективного исполнения программ/скриптов, на которых построены приложения. Сервер приложений действует как набор компонентов, доступных разработчику программного обеспечения через API.

Для веб-приложений основная задача компонентов сервера — обеспечивать создание динамических страниц.

## 7. Как выглядят HTTP-запросы

- application/x-www-form-urlencoded: значения кодируются в кортежах с ключом, разделенных символом '&', с '=' между ключом и значением.

```http
POST / HTTP/1.1
Host: foo.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 13

say=Hi&to=Mom
```

- multipart/form-data

```http
POST /test.html HTTP/1.1 
Host: example.org 
Content-Type: multipart/form-data;boundary="boundary" 

--boundary 
Content-Disposition: form-data; name="field1" 

value1 
--boundary 
Content-Disposition: form-data; name="field2"; filename="example.txt" 

value2
```

### Какие символы разрешены в GET 

- Пробел кодируется как %20
- Буквы, цифры, некоторые символы * - . _ кодируются с %2B
- Для некоторых символов есть 16-ричное предстваление
- ~ кодируется как %7E

## 8. Cookie

HTTP cookie (web cookie, cookie браузера) - это небольшой фрагмент данных, отправляемый сервером на браузер пользователя, который тот может сохранить и отсылать обратно с новым запросом к данному серверу. Это позволяет узнать, с одного ли браузера пришли запросы. Они запоминают информацию о состоянии для протокола HTTP, который сам по себе этого делать не умеет.

Используется для: 

- Управления сеансом (логины, корзины для виртуальных покупок)
- Персонализации (пользовательские предпочтения)
- Мониторинга (отслеживания поведения пользователя)

### Создание cookie

Получив HTTP-запрос, вместе с откликом сервер может отправить заголовок  Set-Cookie с ответом. Cookie обычно запоминаются браузером и посылаются в значении заголовка HTTP  Cookie с каждым новым запросом к одному и тому же серверу. 
