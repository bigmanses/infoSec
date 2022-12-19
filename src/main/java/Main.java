import lab3.InfoMatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        InfoMatrix info = new InfoMatrix();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String nameUser;
        while(true){
            System.out.print("Введите имя пользователя: ");
            nameUser = reader.readLine();
            if(info.isUser(nameUser)){
                info.printMatrix();
                info.menu(nameUser, reader);
            } else {
                System.out.println("Такого пользователя не существует.");
            }
        }
    }
}
