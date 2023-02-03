SELECT student.name, student.age, faculty.name
FROM student
         INNER JOIN faculty ON student.faculty_id = faculty.id;

select avatar.id, student.name, student.id
from avatar
         LEFT OUTER JOIN  student on avatar.student_id = student.id