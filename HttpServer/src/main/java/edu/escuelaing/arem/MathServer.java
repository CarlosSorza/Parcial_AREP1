package edu.escuelaing.arem;


import java.net.*;
import java.io.*;

public class MathServer {
  public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   try { 
      serverSocket = new ServerSocket(36000);
   } catch (IOException e) {
      System.err.println("Puerto no disponible");
      System.exit(1);
   }

   Socket clientSocket = null;
   boolean running = true;
   while (running) {
    try {
        System.out.println("Listo para recibir ...");
        clientSocket = serverSocket.accept();
        new MathCalculator(clientSocket).start();
    } catch (IOException e) {
        System.err.println("Accept failed.");
        System.exit(1);
   }
   
   }
   PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true);
   BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
   String inputLine, outputLine;
   while ((inputLine = in.readLine()) != null) {
      System.out.println("Recibí: " + inputLine);
      if (!in.ready()) {break; }
   }
   outputLine = 
          "<!DOCTYPE html>" + 
          "<html>" + 
          "<head>" + 
          "<meta charset=\"UTF-8\">" + 
          "<title>Title of the document</title>\n" + 
          "</head>" + 
          "<body>" + 
          "<h1>Mi propio mensaje</h1>" + 
          "</body>" + 
          "</html>"; 
    out.println(outputLine);
    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
  }
}











