package br.com.fiap;


import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static BigInteger p, q, N, phi, e, d;

    public static void main(String[] args) throws Exception {
        p = BigInteger.valueOf(373);
        q = BigInteger.valueOf(59);

        gerarChavesRSA();

        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor aguardando conexões...");

        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado!");

        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

        saida.println(N);
        saida.println(e);

        String mensagemCriptografada = entrada.readLine();
        BigInteger criptografada = new BigInteger(mensagemCriptografada);

        BigInteger descriptografada = descriptografar(criptografada);
        System.out.println("Mensagem descriptografada: " + descriptografada);

        socket.close();
        serverSocket.close();
    }

    private static void gerarChavesRSA() {
        N = p.multiply(q);

        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.valueOf(17);
        d = e.modInverse(phi);

        System.out.println("Chaves RSA geradas:");
        System.out.println("N: " + N);
        System.out.println("e (pública): " + e);
        System.out.println("d (privada): " + d);
    }

    private static BigInteger descriptografar(BigInteger criptografada) {
        return criptografada.modPow(d, N);
    }
}

