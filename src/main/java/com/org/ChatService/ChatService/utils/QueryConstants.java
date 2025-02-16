package com.org.ChatService.ChatService.utils;

public class QueryConstants {

    public static final String RECENT_MESSAGES =
            "WITH latest_individual_messages AS (\n" +
                    "    SELECT\n" +
                    "        LEAST(sender_user_name, receiver_user_name) AS user1,\n" +
                    "        GREATEST(sender_user_name, receiver_user_name) AS user2,\n" +
                    "        MAX(created_at) AS latest_time\n" +
                    "    FROM message\n" +
                    "    WHERE receiver_user_name = ?1 OR sender_user_name = ?1\n" +
                    "    GROUP BY user1, user2\n" +
                    "),\n" +
                    "latest_group_messages AS (\n" +
                    "    SELECT\n" +
                    "        group_id,\n" +
                    "        MAX(created_at) AS latest_time\n" +
                    "    FROM message\n" +
                    "    WHERE group_id IS NOT NULL\n" +
                    "    GROUP BY group_id\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    u.first_name,\n" +
                    "    u.last_name,\n" +
                    "    t.sender_user_name,\n" +
                    "    t.receiver_user_name,\n" +
                    "    t.is_read,\n" +
                    "    t.content AS latest_content,\n" +
                    "    t.created_at,\n" +
                    "    t.group_id,\n" +
                    "    ug.name AS group_name\n" +
                    "FROM (\n" +
                    "    SELECT\n" +
                    "        m.sender_user_name,\n" +
                    "        m.receiver_user_name,\n" +
                    "        m.content,\n" +
                    "        m.created_at,\n" +
                    "        m.is_read,\n" +
                    "        m.group_id,\n" +
                    "        ROW_NUMBER() OVER (PARTITION BY LEAST(m.sender_user_name, m.receiver_user_name), GREATEST(m.sender_user_name, m.receiver_user_name) ORDER BY m.created_at DESC) as row_num\n" +
                    "    FROM\n" +
                    "        message m\n" +
                    "        JOIN latest_individual_messages lim\n" +
                    "        ON (m.sender_user_name = lim.user1 AND m.receiver_user_name = lim.user2 OR m.sender_user_name = lim.user2 AND m.receiver_user_name = lim.user1) AND m.created_at = lim.latest_time\n" +
                    "    WHERE m.group_id IS NULL\n" +
                    "    UNION ALL\n" +
                    "    SELECT\n" +
                    "        m.sender_user_name,\n" +
                    "        m.receiver_user_name,\n" +
                    "        m.content,\n" +
                    "        m.created_at,\n" +
                    "        m.is_read,\n" +
                    "        m.group_id,\n" +
                    "        ROW_NUMBER() OVER (PARTITION BY m.group_id ORDER BY m.created_at DESC) as row_num\n" +
                    "    FROM\n" +
                    "        message m\n" +
                    "        JOIN latest_group_messages lgm\n" +
                    "        ON m.group_id = lgm.group_id AND m.created_at = lgm.latest_time\n" +
                    ") t\n" +
                    "LEFT JOIN users u ON t.sender_user_name = u.username\n" +
                    "LEFT JOIN usergroup ug ON t.group_id = ug.group_id\n" +
                    "WHERE t.row_num = 1\n" +
                    "ORDER BY t.created_at DESC";

//            "SELECT\n" +
//                    "    u.first_name,\n" +
//                    "    u.last_name,\n" +
//                    "    t.sender_user_name,\n" +
//                    "t.receiver_user_name , " +
//                    "    t.is_read,\n" +
//                    "    t.content AS latest_content,\n" +
//                    "    t.created_at,\n" +
//                    "t.group_id "+
//                    "FROM (\n" +
//                    "    SELECT\n" +
//                    "        LEAST(sender_user_name, receiver_user_name) AS user1,\n" +
//                    "        GREATEST(sender_user_name, receiver_user_name) AS user2,\n" +
//                    "        MAX(created_at) AS latest_time\n" +
//                    "    FROM message\n" +
//                    "    WHERE receiver_user_name = ?1 OR sender_user_name = ?1\n" +
//                    "    GROUP BY user1, user2\n" +
//                    ") sub\n" +
//                    "JOIN message t ON (\n" +
//                    "    (t.sender_user_name = sub.user1 AND t.receiver_user_name = sub.user2) OR\n" +
//                    "    (t.sender_user_name = sub.user2 AND t.receiver_user_name = sub.user1)\n" +
//                    ") AND t.created_at = sub.latest_time\n" +
//                    "INNER JOIN users u ON (\n" +
//                    "    (t.sender_user_name = u.username AND t.sender_user_name != ?1) OR\n" +
//                    "    (t.receiver_user_name = u.username AND t.receiver_user_name != ?1)\n" +
//                    ")\n" +
//                    "ORDER BY t.created_at DESC;\n";


//            "SELECT u.first_name, u.last_name, m1.sender_user_name, m1.is_read, m1.content AS latest_content, m1.created_at " +
//                    "FROM message m1 " +
//                    "INNER JOIN ( " +
//                    "  SELECT sender_user_name, MAX(created_at) AS latest_time " +
//                    "  FROM message " +
//                    "  WHERE receiver_user_name = ?1 " +
//                    "  GROUP BY sender_user_name " +
//                    ") m2 " +
//                    "ON m1.sender_user_name = m2.sender_user_name AND m1.created_at = m2.latest_time " +
//                    "INNER JOIN users u ON m1.sender_user_name = u.username " +
//                    "WHERE m1.receiver_user_name = ?1 " +
//
//                    "UNION ALL " +
//
//                    "SELECT u.first_name, u.last_name, m1.receiver_user_name AS sender_user_name, m1.is_read, m1.content AS latest_content, m1.created_at " +
//                    "FROM message m1 " +
//                    "INNER JOIN ( " +
//                    "  SELECT receiver_user_name, MAX(created_at) AS latest_time " +
//                    "  FROM message " +
//                    "  WHERE sender_user_name = ?1 " +
//                    "  GROUP BY receiver_user_name " +
//                    ") m2 " +
//                    "ON m1.receiver_user_name = m2.receiver_user_name AND m1.created_at = m2.latest_time " +
//                    "INNER JOIN users u ON m1.receiver_user_name = u.username " +
//                    "WHERE m1.sender_user_name = ?1 " +
//                    "ORDER BY created_at DESC";


//            "SELECT u.first_name , u.last_name , m1.sender_user_name, m1.is_read , m1.content AS latest_content, m1.created_at\n" +
//                    "FROM message m1\n" +
//                    "JOIN (\n" +
//                    "    SELECT sender_user_name, MAX(created_at) AS latest_time\n" +
//                    "    FROM message\n" +
//                    "    WHERE receiver_user_name = ?1\n" +
//                    "    GROUP BY sender_user_name\n" +
//                    ") m2\n" +
//                    "ON m1.sender_user_name = m2.sender_user_name AND m1.created_at = m2.latest_time\n" +
//                    "INNER JOIN users u ON m1.sender_user_name = u.username  -- Join with users on username"+
//                    "WHERE m1.receiver_user_name = ?1\n" +
//                    "ORDER BY m1.created_at DESC";

    public static final String USER_SEARCH_QUERY =
            "SELECT u.username, u.first_name, u.last_name, u.email FROM User u " +
                    "WHERE LOWER(u.first_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(CONCAT(u.first_name, ' ', u.last_name)) LIKE LOWER(CONCAT('%', ?1, '%')) " +
                    "OR LOWER(CONCAT(u.last_name, ' ', u.first_name)) LIKE LOWER(CONCAT('%', ?1, '%'))";


//    public static final String GET_USER_MESSAGE_HISTORY = "select sender_user_name ,  receiver_user_name , content , created_at" +
//            " from message  where sender_user_name IN (?1,?2) " +
//            "AND receiver_user_name IN (?1,?2) ORDER BY created_at ASC";

    public static final String GET_USER_MESSAGE_HISTORY = "SELECT \n" +
            "    m.sender_user_name, \n" +
            "    m.receiver_user_name, \n" +
            "    u.first_name AS sender_first_name,\n" +
            "    u.last_name AS sender_last_name,\n" +
            "    m.content, \n" +
            "    m.created_at\n" +
            "FROM \n" +
            "    message m\n" +
            "JOIN \n" +
            "    users u ON m.sender_user_name = u.username\n" +
            "WHERE \n" +
            "    m.sender_user_name IN (?1, ?2) \n" +
            "    AND m.receiver_user_name IN (?1, ?2)\n" +
            "ORDER BY \n" +
            "    m.created_at ASC;";


    public static final String GET_FULL_NAME_INFO = "select new com.org.ChatService.ChatService.model.NameInfo(u.first_name , u.last_name) from User u where u.username = ?1";


    public static final String UPDATE_READ_QUERY = "update Message m set m.read = true where  m.senderUserName = ?1 AND m.receiverUserName = ?2";
}


