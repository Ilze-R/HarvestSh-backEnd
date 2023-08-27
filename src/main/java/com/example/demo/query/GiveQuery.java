package com.example.demo.query;

public class GiveQuery {

    public static final String SELECT_GIVE_BY_USER_ID_QUERY = "SELECT * FROM Give WHERE users_give_id = :users_give_id";

    public static final String INSERT_GIVE_QUERY = "INSERT INTO Give (date, type, amount, amount_type, description, img_url, location, status, users_give_id) " +
            "VALUES (:date, :type, :amount, :amountType, :description, :img_url, :location, :status, :users_give_id)";

    public static final String SELECT_GIVES_FOR_USER_QUERY = "SELECT * FROM Give WHERE users_give_id = :userId";

    public static final String SELECT_GIVE_BY_ID_QUERY = "SELECT * FROM Give WHERE id = :id";

    public static final String UPDATE_GIVE_QUERY = "UPDATE Give\n" +
            "SET type = :type,\n" +
            "    amount = :amount,\n" +
            "    description = CASE WHEN :description IS NOT NULL THEN :description ELSE description END,\n" +
            "    location = :location,\n" +
            "    users_give_id = :users_give_id\n" +
            "WHERE id = :id";

    public static final String DELETE_GIVE_QUERY = "DELETE FROM company WHERE id = :id";
}
