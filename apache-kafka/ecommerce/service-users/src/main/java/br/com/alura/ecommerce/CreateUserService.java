package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CreateUserService {
    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);
        connection.createStatement().execute("create table if not exists user (" +
                "id varchar(200) primary key," +
                "email varchar(200))");
    }

    public static void main(String[] args) throws SQLException {
        var userService = new CreateUserService();
        try(var service = new KafkaService<>(CreateUserService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                userService::parse,
                Order.class,
                new HashMap<>())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException, SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("Processing new order, checking for new user");
        System.out.println("Value: " + record.value());

        var order = record.value();
        if(isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) throws SQLException {
        var statement = connection.prepareStatement("insert into user (id, email) values (?,?)");
        statement.setString(1, "id");
        statement.setString(2, email);
        statement.execute();
        System.out.println("User added.");
    }

    private boolean isNewUser(String email) throws SQLException {
        var statement = connection.prepareStatement("select id from user where email=? limit 1");
        statement.setString(1, email);
        var results = statement.executeQuery();
        return !results.next();
    }
}
