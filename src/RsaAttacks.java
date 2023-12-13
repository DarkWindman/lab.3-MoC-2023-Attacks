import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class RsaAttacks {
    public static BigInteger e = new BigInteger("10001", 16);
    
   /* private static BigInteger sn(BigInteger x, int n)
    {

    }*/

   public static BigInteger Ith_Root(BigInteger N, BigInteger K) {

       BigInteger K1 = K.subtract(BigInteger.ONE);
       BigInteger S  = N.add(BigInteger.ONE);
       BigInteger U  = N;
       while (U.compareTo(S)==-1) {
           S = U;
           U = (U.multiply(K1).add(N.divide(U.pow(K1.intValue())))).divide(K);
       }
       String str=""+N+"^1/"+K+"="+S;System.out.println(str);
       return S;
   }

    public static void main(String[] args) throws Exception {
        ArrayList<String> parse = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\01.txt"));
        String line;
        Scanner scanner;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                parse.add(num);
            }
        }
        System.out.println(parse);
        ArrayList<BigInteger> Ci = new ArrayList<>();
        ArrayList<BigInteger> Ni = new ArrayList<>();
        for(int i =0; i< parse.size(); i++)
        {
            String temp = parse.get(i);
            temp = temp.substring(7,parse.get(i).length());
            if(i %2!=0) Ci.add(new BigInteger(temp,16));
            else Ni.add(new BigInteger(temp,16));

        }
        System.out.println(Ni.get(0).toString(16));
        System.out.println(Ci.get(0).toString(16));
        BigInteger N = new BigInteger("1",10);
        BigInteger C = new BigInteger("1", 16);
        for(int i =0; i< Ni.size();i++)
        {
            N = N.multiply(Ni.get(i));
            C = C.multiply(Ci.get(i));
        }
        System.out.println(C.toString(10));
        System.out.println(Ith_Root(C,new BigInteger("3",10)));


        parse = new ArrayList<>();
        reader = new BufferedReader(new FileReader("C:\\01_Mit.txt"));
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                num = num.substring(6);
                parse.add(num);
            }
        }
        C = new BigInteger(parse.get(0),16);
        N = new BigInteger(parse.get(1), 16);
        System.out.println("C = " + C.toString(10));
        System.out.println();
        System.out.println(N.toString(16));
        int l = 31;
        ArrayList<BigInteger> X = new ArrayList<>();
        ArrayList<BigInteger> T = new ArrayList<>();
        int flag = -1;
        //Calculate T^e and S^-e
        for(int i =1; i < (2^l); i++)
        {
            BigInteger temp = new BigInteger(String.valueOf(i));
            X.add(temp.modPow(e,N));
            T.add(X.get(i-1).modInverse(N));
        }
        //Calculate Cs
        BigInteger RCs = new BigInteger("1");
        for (int i = 0; i < T.size(); i++) {
            BigInteger Cs = C.multiply(T.get(i)).mod(N);
            for (int j = 0; j < X.size(); j++) {
                if (Cs.equals(X.get(j))) {
                    flag = j;
                    break;
                }
            }
            if (flag != -1)
            {
                RCs = X.get(i);
                break;
            }
        }
        if(flag == -1) System.out.println("Didn`t find any");
        else {
            System.out.println("Answer" + flag);

            //Verify
            System.out.println("Expected -- M1 = " + RCs);
            System.out.println("Expected -- M2 = " + X.get(flag));
            System.out.println("(M1*M2)^e = ");
            BigInteger Cnew = RCs.multiply(X.get(flag));
            System.out.println(Cnew.modPow(e, N));
            System.out.println("Real C = ");
            System.out.println(C);
        }
    }
}
