package com.example.demo.query;

public class GiveQuery {

    public static final String SELECT_GIVE_BY_ID_QUERY = "SELECT * FROM Give WHERE users_give_id = :users_give_id";

    public static final String INSERT_GIVE_QUERY = "INSERT INTO Give (date, type, amount, status) " +
            "VALUES (:date, :type, :amount, :status)";
}
