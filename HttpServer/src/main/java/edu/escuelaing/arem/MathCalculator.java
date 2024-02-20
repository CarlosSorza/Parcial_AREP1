package edu.escuelaing.arem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class MathCalculator extends Thread {
    private Socket socket;

    public MathCalculator(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String input = reader.readLine();
            String[] parts = input.split(",");
            String command = parts[0].trim();
            Double[] numbers = Arrays.stream(parts).skip(1).map(Double::parseDouble).toArray(Double[]::new);

            if (command.equals("qck")) {
                Arrays.sort(numbers);
                writer.println(Arrays.toString(numbers));
            } else {
                double result = invokeMathOperation(command, numbers);
                writer.println(result);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double invokeMathOperation(String command, Double[] numbers) {
        try {
            Object[] params = new Object[numbers.length];
            System.arraycopy(numbers, 0, params, 0, numbers.length);

            return (double) Math.class.getMethod(command, Double[].class).invoke(null, (Object) params);
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }
}

