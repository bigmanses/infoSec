import lab3.DiscreteSecurityModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String nameUser;
        DiscreteSecurityModel info = new DiscreteSecurityModel(reader);
        boolean flagMenu = true;
        while(flagMenu){
            System.out.print("Введите имя пользователя: ");
            nameUser = reader.readLine();
            if(info.isUser(nameUser)){
                info.printMatrix();
                if(info.menu(nameUser) == 0) flagMenu = false;
            } else {
                System.out.println("Такого пользователя не существует.");
            }
        }
    }
}
