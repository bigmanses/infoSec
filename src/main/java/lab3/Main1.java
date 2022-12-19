package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        DiscreteSecurityModel info = new DiscreteSecurityModel(reader);
        info.printMatrix();
        boolean flagMenu = true;
        while(flagMenu){
            System.out.print("Введите имя пользователя: ");
            String nameUser = reader.readLine();
            if(info.isUser(nameUser)){
                if(info.menu(nameUser) == 0) flagMenu = false;
            } else if (nameUser.equals("exit"))  {
                flagMenu = false;
            } else{
                System.out.println("Такого пользователя не существует.");
            }
        }
    }
}
