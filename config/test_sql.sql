 CREATE TABLE courses (
 c_no text PRIMARY KEY,
 title text,
hours integer
);

INSERT INTO courses(c_no, title, hours)
VALUES ('CS301', 'Базы данных', 30),
('CS305', 'Сети ЭВМ', 60);

'integer — целые числа;
• text — текстовые строки;
• boolean — логический тип, принимающий значения
true («истинно») или false («ложно»).
Помимо обычных значений, определяемых типом данных,
поле может иметь неопределенное значение NULL — его
можно рассматривать как «значение неизвестно» или «значение не задано».'

CREATE TABLE students(
s_id integer PRIMARY KEY,
name1 text,
start_year integer
);

INSERT  INTO students (s_id, name1, start_year)
VALUES (1451, 'Анна', 2014),
(1432, 'Виктор', 2014),
(1556, 'Нина', 2015);

'Запись в таблице экзаменов идентифицируется совокупностью номера студбилета и номера курса. Такое ограничение целостности, относящее сразу к нескольким столбцам,
определяется с помощью предложения CONSTRAINT:

Кроме того, с помощью предложения REFERENCES мы добавили два ограничения ссылочной целостности, называемые внешними ключами. Такие ограничения показывают,
что значения в одной таблице ссылаются на строки в другой таблице.
Теперь при любых действиях СУБД будет проверять соответствие всех идентификаторов s_id, указанных в таблице
экзаменов, реальным студентам (то есть записям в таблице студентов), а также номера c_no — реальным курсам.
Таким образом, будет исключена возможность поставить
оценку несуществующему студенту или же по несуществующей дисциплине — независимо от действий пользователя
или возможных ошибок в приложении.'

CREATE TABLE exams(
s_id integer REFERENCES students(s_id),
c_no text REFERENCES courses(c_no),
score integer,
CONSTRAINT pk PRIMARY KEY(s_id, c_no)
);

'Для массовой загрузки данных из внешнего источника команда INSERT подходит плохо,
зато есть специально предназначенная для этого команда COPY: postgrespro.ru/doc/
sql-copy.'


INSERT INTO exams(s_id, c_no, score)
VALUES (1451, 'CS301', 5),
(1556, 'CS301', 5),
(1451, 'CS305', 5),
(1432, 'CS305', 4);

SELECT title AS course_title, hours FROM courses;

SELECT s_id AS student_title, c_no, score FROM exams;

SELECT * FROM courses;

SELECT start_year FROM students;

SELECT DISTINCT start_year FROM students;

SELECT 2+2 AS result;

'Обычно при выборке данных требуется получить не все
строки, а только те, которые удовлетворят какому-либо
условию. Такое условие фильтрации записывается в предложении WHERE:'

SELECT * FROM courses WHERE hours > 45;

SELECT * FROM courses, exams;

SELECT * FROM courses, exams, students;

'С точки зрения СУБД эти формы эквивалентны друг другу,
так что можно использовать тот способ, который представляется более наглядным'

SELECT courses.title, exams.s_id, exams.score
FROM courses, exams
WHERE courses.c_no = exams.c_no;

SELECT courses.title, exams.s_id, exams.score
FROM courses
 JOIN exams
 ON courses.c_no = exams.c_no;


SELECT students.name1, exams.score
FROM students
JOIN exams
ON students.s_id = exams.s_id
AND exams.c_no = 'CS305';

SELECT students.name1, exams.score
FROM students, exams
WHERE  students.s_id = exams.s_id
AND exams.c_no = 'CS305';

'Теперь в выдаче есть и те строки из левой таблицы (поэтому
операция называется LEFT JOIN), для которых не нашлось
пары в правой. При этом для столбцов правой таблицы возвращаются неопределенные значения.'

SELECT students.name1, exams.score
FROM students
LEFT JOIN exams
ON students.s_id = exams.s_id
AND exams.c_no = 'CS305';

 'Условия после WHERE применяются к уже соединенным
строкам, поэтому, если вынести ограничение на дисциплины из условия соединения в предложение WHERE, Нина не
попадет в выборку — ведь для нее значение exams.c_no
не определено:'

SELECT students.name1, exams.score
FROM students
LEFT JOIN exams ON students.s_id = exams.s_id
WHERE exams.c_no = 'CS305';


'Оператор SELECT формирует таблицу, которая (как мы уже
видели) может быть выведена в качестве результата, а может быть использована в другой конструкции языка SQL
в любом месте, где по смыслу может находиться таблица.
Такая вложенная команда SELECT, заключенная в круглые
скобки, называется подзапросом.
Если подзапрос возвращает ровно одну строку и ровно
один столбец, его можно использовать как обычное скалярное выражение:'

SELECT name1,
(SELECT score
FROM exams
WHERE exams.s_id = students.s_id
AND exams.c_no = 'CS305')
FROM students;

'Скалярные подзапросы можно также использовать в условиях фильтрации. Получим все экзамены, которые сдавали
студенты, поступившие после 2014 года:'


SELECT *
FROM exams
WHERE (SELECT start_year
FROM students
WHERE students.s_id = exams.s_id) > 2014;

'В SQL можно формулировать условия и на подзапросы,
возвращающие произвольное количество строк. Для этого существует несколько конструкций, одна из которых —
отношение IN — проверяет, содержится ли значение в таблице, возвращаемой подзапросом.
Выведем студентов, получивших какие-либо оценки по указанному курсу:'


SELECT name1, start_year
FROM students
WHERE s_id IN (SELECT s_id
FROM exams
WHERE c_no = 'CS305');

'Оператор NOT IN возвращает противоположный результат.
Например, список студентов, не получивших ни одной отличной оценки:'

SELECT name1, start_year
FROM students
WHERE s_id NOT IN
(SELECT s_id FROM exams WHERE score = 5);

'Еще одна возможность — использовать предикат EXISTS,
проверяющий, возвратил ли подзапрос хотя бы одну строку. С его помощью можно записать предыдущий запрос
в другом виде:'


SELECT name1, start_year
FROM students
WHERE NOT EXISTS (SELECT s_id
FROM exams
WHERE exams.s_id = students.s_id
AND score = 5);

'Выше мы дополняли имена столбцов именами таблиц, чтобы избежать неоднозначности, но иногда этого недостаточно. Например, одна и та же таблица может фигурировать
в запросе дважды, или на месте таблицы в предложении
FROM может стоять безымянный подзапрос. В таких случаях после подзапроса можно указывать его псевдоним
(alias). Обычным таблицам тоже можно присваивать псевдонимы.
Выведем имена студентов и их оценки по предмету «Базы
данных»:'

SELECT s.name1, ce.score
FROM students s
JOIN (SELECT exams.*
FROM courses, exams
WHERE courses.c_no = exams.c_no
AND courses.title = 'Базы данных') ce
ON s.s_id = ce.s_id;

'Здесь s — псевдоним таблицы, а ce — псевдоним подзапроса.
 Псевдонимы лучше делать короткими, но понятными.
Тот же запрос можно записать и без подзапросов:'

SELECT s.name1, e.score
FROM students s, courses c, exams e
WHERE c.c_no = e.c_no
AND c.title = 'Базы данных'
AND s.s_id = e.s_id;

'Для вывода строк результата в определенном порядке используется предложение ORDER BY со списком выражений,
по которым надо выполнить сортировку. После каждого выражения (ключа сортировки) можно указать направление:
ASC — по возрастанию (этот порядок используется по умолчанию) — или DESC — по убыванию.'


SELECT *
FROM exams
ORDER BY score, s_id, c_no DESC;

'При группировке в одной строке результата размещается
значение, вычисленное на основании данных нескольких
строк исходных таблиц. Вместе с группировкой используют
агрегатные функции. Например, выведем общее количество проведенных экзаменов, количество сдававших их
студентов и средний балл:'

SELECT count(*), count(DISTINCT s_id),
avg(score)
FROM exams;

'Ту же информацию можно получить в разбивке по номерам курсов
с помощью предложения GROUP BY, в котором
указываются ключи группировки:'

SELECT c_no, count(*),
count(DISTINCT s_id), avg(score)
FROM exams
GROUP BY c_no;

'При использовании группировки может возникнуть необходимость
отфильтровать строки на основании результатов агрегирования.
Такие условия можно задать в предложении HAVING.
Отличие от WHERE состоит в том, что условия
WHERE применяются до группировки (в них можно использовать столбцы исходных таблиц),
 а условия HAVING — после группировки (и в них также можно использовать
 столбцы таблицы-результата).
Выберем имена студентов, получивших более одной пятерки по любому предмету:'

SELECT students.name1
FROM students, exams
WHERE students.s_id = exams.s_id AND exams.score = 5
GROUP BY students.name1
HAVING count(*) > 1;

'Изменение данных в таблице выполняет оператор UPDATE,
в котором указываются новые значения полей для строк,
определяемых предложением WHERE (таким же, как в операторе SELECT).
Например, увеличим число лекционных часов для курса
«Базы данных» в два раза:'

UPDATE courses
SET hours = hours * 2
WHERE c_no = 'CS301';

'Оператор DELETE удаляет из указанной таблицы строки,
определяемые все тем же предложением WHERE:'

DELETE FROM exams WHERE score < 5;

'Теперь усложним схему данных и распределим студентов
по группам, причем у каждой группы должен быть староста.
Для этого создадим таблицу групп:'

CREATE TABLE groups1 (
g_no text PRIMARY KEY,
monitor integer NOT NULL REFERENCES students(s_id)
);

'в уже существующую таблицу можно добавить новый
столбец:'

ALTER TABLE students
ADD g_no text REFERENCES groups1 (g_no);



INSERT INTO groups1(g_no, monitor)
SELECT 'A-101', s_id
FROM students
WHERE name1 = 'Анна';


                SELECT * FROM groups1;


UPDATE students SET g_no = 'A-101';


                SELECT * FROM students;

CREATE TABLE order_details (
random_text text PRIMARY KEY,
random_int integer
);

ALTER TABLE order_details
  ADD order_date date,
  ADD quantity integer;

DROP TABLE order_details;

 UPDATE resume
 SET full_name = ?,
 WHERE uuid = ?;

drop table resume cascade ;

 SELECT COUNT(*) FROM имя_таблицы WHERE условие;

 SELECT COUNT(*) FROM resume ;