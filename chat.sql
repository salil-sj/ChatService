
root>describe users;
+--------------+--------------+------+-----+-------------------+-------------------+
| Field        | Type         | Null | Key | Default           | Extra             |
+--------------+--------------+------+-----+-------------------+-------------------+
| user_id      | int          | NO   | PRI | NULL              | auto_increment    |
| username     | varchar(255) | NO   | UNI | NULL              |                   |
| password     | varchar(500) | NO   |     | NULL              |                   |
| email        | varchar(255) | NO   | UNI | NULL              |                   |
| phone_number | varchar(20)  | YES  |     | NULL              |                   |
| first_name   | varchar(255) | YES  |     | NULL              |                   |
| last_name    | varchar(255) | YES  |     | NULL              |                   |
| created_at   | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+--------------+--------------+------+-----+-------------------+-------------------+


CREATE TABLE message (
  id INT AUTO_INCREMENT PRIMARY KEY,
  conversation_id INT ,
  sender_user_name varchar(30) NOT NULL,
  receiver_user_name varchar(30) NULL,  -- for direct messages
  group_id INT NULL,      -- for group messages
  content TEXT NOT NULL,
  created_at DATETIME  DEFAULT CURRENT_TIMESTAMP,
  is_read TINYINT(1) NOT NULL DEFAULT 0,  -- New flag to track read status
  FOREIGN KEY (sender_user_name) REFERENCES users(username),
  FOREIGN KEY (receiver_user_name) REFERENCES users(username)
);
-- In later stage of time reference this group id to a table which contain group details


SELECT sender_user_name, 
       (SELECT content FROM message m2 
        WHERE m2.sender_user_name = m1.sender_user_name 
          AND m2.receiver_user_name = 'abc123'
          AND m2.created_at = (SELECT MAX(created_at) FROM message m3 
                              WHERE m3.sender_user_name = m1.sender_user_name 
                                AND m3.receiver_user_name = 'abc123')) AS latest_content,
       MAX(created_at) AS created_at
FROM message m1
WHERE receiver_user_name = 'abc123'
GROUP BY sender_user_name
ORDER BY MAX(created_at) DESC;


CREATE  VIEW recent_message AS 
 SELECT sender_user_name, 
       (SELECT content FROM message m2 
        WHERE m2.sender_user_name = m1.sender_user_name 
          AND m2.receiver_user_name = 'abc123'
          AND m2.created_at = (SELECT MAX(created_at) FROM message m3 
                              WHERE m3.sender_user_name = m1.sender_user_name 
                                AND m3.receiver_user_name = 'abc123')) AS latest_content,
       MAX(created_at) AS created_at
FROM message m1
WHERE receiver_user_name = 'abc123'
GROUP BY sender_user_name
ORDER BY MAX(created_at) DESC;






-- Create the Group table
CREATE TABLE `Group` (
  group_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Create the GroupMembership table
CREATE TABLE GroupMembership (
  id INT PRIMARY KEY AUTO_INCREMENT,
  group_id INT NOT NULL,
  username varchar(30) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (group_id) REFERENCES `usergroup`(group_id),
  FOREIGN KEY (username) REFERENCES `Users`(username)
);






------------QUERIES------------------------
--Union query for the side bar
(SELECT 
    m1.sender_user_name AS name, 
    m1.content AS latest_content, 
    m1.created_at
 FROM 
    message m1
 JOIN 
    (SELECT 
        sender_user_name, 
        MAX(created_at) AS latest_time
     FROM 
        message
     WHERE 
        receiver_user_name = 'abc123' AND group_id IS NULL
     GROUP BY 
        sender_user_name
    ) m2
 ON 
    m1.sender_user_name = m2.sender_user_name AND m1.created_at = m2.latest_time
 WHERE 
    m1.receiver_user_name = 'abc123' AND m1.group_id IS NULL)

UNION

(SELECT 
    g.group_name AS name, 
    m1.content AS latest_content, 
    m1.created_at
 FROM 
    message m1
 JOIN 
    (SELECT 
        group_id, 
        MAX(created_at) AS latest_time
     FROM 
        message
     WHERE 
        group_id IN (
            SELECT 
                group_id
            FROM 
                group_members
            WHERE 
                username = 'abc123'
        )
     GROUP BY 
        group_id
    ) m2
 ON 
    m1.group_id = m2.group_id AND m1.created_at = m2.latest_time
 JOIN 
    chat_groups g ON m1.group_id = g.id
 WHERE 
    m1.group_id IS NOT NULL)

ORDER BY 
    created_at DESC;




-- Direct recent messages:

SELECT m1.sender_user_name, m1.content AS latest_content, m1.created_at
FROM message m1
JOIN (
    SELECT sender_user_name, MAX(created_at) AS latest_time
    FROM message
    WHERE receiver_user_name = 'abc123'
    GROUP BY sender_user_name
) m2
ON m1.sender_user_name = m2.sender_user_name AND m1.created_at = m2.latest_time
WHERE m1.receiver_user_name = 'abc123'
ORDER BY m1.created_at DESC;


SELECT m1.sender_user_name, m1.content AS latest_content, m1.created_at
FROM message m1
JOIN (
    SELECT sender_user_name, MAX(created_at) AS latest_time
    FROM message
    WHERE receiver_user_name = ?1
    GROUP BY sender_user_name
) m2
ON m1.sender_user_name = m2.sender_user_name AND m1.created_at = m2.latest_time
WHERE m1.receiver_user_name = ?2
ORDER BY m1.created_at DESC;



[[Ljava.lang.Object;@6700ebe9, [Ljava.lang.Object;@2df0dbf3]