package com.mjc.school;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mjc.school.helper.Command;
import com.mjc.school.helper.CommandSender;
import com.mjc.school.helper.MenuHelper;
import com.mjc.school.helper.Operations;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private MenuHelper helper;

    @Autowired
    private CommandSender commandSender;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            try {
                helper.printMainMenu();
                String key = keyboard.nextLine();
                if (Integer.toString(Operations.EXIT.getOperationNumber()).equals(key)) {
                    System.exit(0);
                }

                Command command = helper.getCommand(key, keyboard);
                Object result = commandSender.send(command);
                if (result instanceof Iterable<?> it) {
                    it.forEach(System.out::println);
                } else {
                    System.out.println(result);
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
