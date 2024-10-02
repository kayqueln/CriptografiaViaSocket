package br.com.fiap;

import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Client {
    private static BigInteger N, e;

    public static void main(String[] args) throws Exception {
        Scanner gravador = new Scanner(System.in);

        Socket socket = new Socket("localhost", 12345);
        System.out.println("Conectado ao servidor!");

        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

        N = new BigInteger(entrada.readLine());
        e = new BigInteger(entrada.readLine());
        System.out.println("Chave pública recebida: N=" + N + " e=" + e);

        System.out.print("Digite a mensagem a ser criptografada e enviada: ");
        String mensagem = gravador.next();

        BigInteger numeroMensagem = stringParaNumero(mensagem);
        System.out.println("Mensagem em formato númerico: " + numeroMensagem);

        BigInteger criptografada = criptografar(numeroMensagem);

        saida.println(criptografada);
        System.out.println("Mensagem criptografada enviada: " + criptografada);

        socket.close();
    }

    private static BigInteger criptografar(BigInteger mensagem) {
        return mensagem.modPow(e, N);
    }

    private static BigInteger stringParaNumero(String mensagem) {
        StringBuilder builder = new StringBuilder();
        for (char c : mensagem.toCharArray()) {
            builder.append((int) c);
        }

        return new BigInteger(builder.toString());
    }

}
