import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class RsaAttacks {
    public static BigInteger e = new BigInteger("10001", 16);
    public static BigInteger e1 = new BigInteger("3", 10);
   /* private static BigInteger sn(BigInteger x, int n)
    {

    }*/

    public static BigInteger power(BigInteger N, BigInteger pow){
        BigInteger staticN = N;
        String BitPow = pow.toString(2);
        for(int i=0; i<BitPow.length(); i++){
            N = N.pow(2);
            if(BitPow.charAt(i) == '1'){
                N=N.multiply(staticN);
            }
        }
        return N;
    }

   public static BigInteger Mod_Root(BigInteger a, BigInteger N) {
        BigInteger deg = N.subtract(BigInteger.ONE);
        deg = deg.divide(e1);
        System.out.println( "M = " + a.modPow(deg, N).toString(16));
        return deg;
   }

    public static BigInteger Kth_Root(BigInteger N, BigInteger K) {

        BigInteger K1 = K.subtract(BigInteger.ONE);
        BigInteger S  = N.add(BigInteger.ONE);
        BigInteger U  = N;
        while (U.compareTo(S) == -1) {
            S = U;
            U = power(U, K1);
            N = N.divide(U);
            BigInteger Utemp = U.multiply(K1);
            Utemp = Utemp.add(U);
            U=Utemp.divide(K);
        }
        return S;
    }

    public static ArrayList<BigInteger> China(ArrayList<BigInteger> Ci, ArrayList<BigInteger> Ni) {

        BigInteger N = new BigInteger("1",10);
        BigInteger C = new BigInteger("1", 16);
        ArrayList<BigInteger> Mi = new ArrayList<>();
        ArrayList<BigInteger> ni = new ArrayList<>();
        for(int i =0; i< Ni.size();i++) {
            N = N.multiply(Ni.get(i));
            C = C.multiply(Ci.get(i));
        }
        ArrayList<BigInteger> sol = new ArrayList<>();
        sol.add(N);
        for (BigInteger bigInteger : Ni) {
            BigInteger Mt = N.divide(bigInteger);
            Mi.add(Mt);
        }
        for(int i =0; i< Mi.size();i++) {
            BigInteger Nt = Mi.get(i).modInverse(Ni.get(i));
            ni.add(Nt);
        }
        BigInteger X = new BigInteger("0",10);
        for(int i = 0; i < Ci.size(); i++) {
            X = X.add((ni.get(i).multiply(Ci.get(i))).multiply(Mi.get(i)));
        }
        X = X.mod(N);
        sol.add(X);
        return sol;
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
        ArrayList<BigInteger> NX = China(Ci,Ni);
        BigInteger N = NX.get(0);
        BigInteger C = NX.get(1);
        System.out.println("C=M^" + e1 + " = " + C.toString(16));
        BigInteger deg = Mod_Root(C,N);
        System.out.println("e = " + deg);

        System.out.println("M^" + deg + "= " + C.modPow(deg, N).toString(16));

        //String.valueOf(base.pow(1, n))
        System.out.println("_____________________________________________________________________");
        /*parse = new ArrayList<>();
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
        System.out.println("C = " + C.toString(16));
        System.out.println();
        System.out.println("N = " + N.toString(16));
        long l;
        l = (int) Math.pow(2,26);
        ArrayList<BigInteger> X = new ArrayList<>();
        ArrayList<BigInteger> T = new ArrayList<>();
        int flag = -1, flagX = 0;
        //Calculate T^e and S^-e
        for(int i =1; i < l; i++)
        {
            BigInteger temp = new BigInteger(String.valueOf(i));
            X.add(temp.modPow(e,N));
        }
        //Calculate Cs
        BigInteger RCs = new BigInteger("1");
        for (int i = 0; i < X.size(); i++) {
            T.add(X.get(i).modInverse(N));
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
                flagX = i+1;
                break;
            }
        }
        if(flag == -1) System.out.println("Didn`t find any");
        else {
            System.out.println("Answer" + flag);

            //Verify
            System.out.println("Expected -- M1 = " + flagX);
            System.out.println("Expected -- M2 = " + flag + 1);
            System.out.println("(M1*M2)^e = ");
            BigInteger Cnew = RCs.multiply(X.get(flag));
            System.out.println(Cnew.mod(N));
            System.out.println("Real C = ");
            System.out.println(C);
        }*/

    }
}
